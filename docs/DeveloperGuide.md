# Developer Guide

* [Acknowledgements](#acknowledgements)
* [Architecture](#architecture)
* [Design](#design)
* [Implementation](#implementation)
  * [Category](#category)
  * [Summarising expenses](#summarising-expenses)
  * [Event](#event)
* [Product scope](#product-scope)
  * [Target user profile](#target-user-profile)
  * [Value proposition](#value-proposition)
* [User Stories](#user-stories)
* [Non-Functional Requirements](#non-functional-requirements)
* [Glossary](#glossary)
* [Instructions for manual testing](#instructions-for-manual-testing)

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Architecture

The UML diagram below shows the main relationships between the classes in the Brokeculator application.
![img.png](images/architecture.png)

## Design 

The manager classes of the application are:
- `UI`: This class is responsible for interacting with the user. It prints messages to the user and reads input from the user.
- `Logic`: This class coordinates the interaction between the `UI` and back-end classes. It processes user input into commands and executes them.
- `ExpenseManager`: This class is responsible for storing and managing the expenses.
- `EventManager`: This class is responsible for storing and managing the events.
- `EventExpenseManager`: This class is responsible for managing circular dependencies between events and expenses.
- `Dashboard`: This class is responsible for storing the managers classes of the application and providing access to them.
- `Category`: This class is responsible for storing the categories of expenses.
- `FileManager` class: This class is responsible for reading and writing data to files.

There are three main classes that store the data of the application:
- `Expense`: This class is responsible for storing the details of an expense.
- `Event`: This class is responsible for storing the details of an event.
- `Category`: This class is responsible for storing the categories of expenses.

There are several supporting classes that facilitate the interaction between the user and the application:
- `GeneralInputParser`: This class does the initial parsing of the user input and directs it to the appropriate parser.
- `GeneralFileParser`: This class parses the data in the files to recreate the expense and event objects.
- `Command`: This is an abstract class that represents a command that can be executed by the application. The commands are produced by the parser classes.

The following code snippet shows how the `GeneralInputParser` class is used to parse the user input:
```java
    public static Command getCommandFromUserInput(String userInput) {
        Command commandToExecute;
        try {
            String commandKeyword = userInput.split(" ")[0];
            String normalizedKeyword = commandKeyword.toLowerCase().trim();
            switch (normalizedKeyword) {
            case "add":
                commandToExecute = AddParser.parseInput(userInput);
                break;
            case "delete":
                commandToExecute = DeleteParser.parseInput(userInput);
                break;
            // Other cases omitted for brevity
            default:
                commandToExecute = new HelpCommand();
            }
        } catch (Exception e) {
            commandToExecute =  new HelpCommand();
        }
        return commandToExecute;
    }
```

The following code snippet shows how the GeneralFileParser class is used to parse the data in the files:
```java
    public static Command getCommandFromFileInput(String fileString) {

        SaveableType saveableType = FileKeyword.getSaveableType(fileString);
        if (saveableType == null) {
            return new InvalidCommand("Corrupted entry: " + fileString);
        }
        String fileStringWithoutKeyword = FileKeyword.removeKeyword(fileString);
        switch (saveableType) {
        case EXPENSE:
            return new AddExpenseFromFileCommand(fileStringWithoutKeyword);
        case CATEGORY:
            return new AddCategoryFromFileCommand(fileStringWithoutKeyword);
        case EVENT:
            return new AddEventFromFileCommand(fileStringWithoutKeyword);
        case CONNECTION:
            return new AddConnectionFromFileCommand(fileStringWithoutKeyword);
        default:
            return new InvalidCommand("Corrupted entry: " + fileString);
        }
    }
```

To illustrate the flow of the application, the sequence diagram below shows how the user input is processed to add an expense:
![img.png](images/overview.png)

Without loss of generality, the high-level execution flow of the application is as follows:
1. The user enters a command in the CLI
2. The `UI` class reads the user input and passes it to the `Logic` class
3. The `Logic` class uses the `GeneralInputParser` class to parse the user input 
4. The `GeneralInputParser` class directs the user input to the appropriate parser class
5. The parser class constructs a command object from the user input.
6. The `Logic` class executes the command object
7. The command object interacts with the relevant manager classes via the dashboard to perform the desired operation
8. The `UI` class prints feedback, if any, to the user

## Implementation
This section describes the implementation details of selected features of the Brokeculator application.

### Category
**Implementation** <br>
The category feature is mainly facilitated by the `Category` class. The `Category` class is responsible for storing the names of the categories present in expenses. 
In order for the user to be able to add expenses with a category, the category must be added using the `addCategory` method. 
The `addCategory` method takes in a string as a parameter and adds it to the set of categories.
The `Category` class implements the following operations:
- `addCategory(String category)` Adds a category to the set of categories
- `getCategoryListString()` Returns a string representation of the set of categories
- `removeCategory(String category)` Deletes a category from the set of categories

The `Category` class is supplemented by the following classes to interact with the user:
- `CategoryCommand` This class is responsible for handling and executing the commands related to categories 
- `CategoryParser` It is responsible for parsing the user input

The UML diagram below shows the main relationships between the classes in the category feature.
![category_class.png](images/category_class.png)
<br>
The Following sequence diagram shows how a user input is processed to add, delete or list the categories:
![category_parse_sequence.png](images/category_parse_sequence.png)
<br>
**User input category parsing sequence**
1. The user enters a command to add a category
2. The `CategoryParser` class parses the user input and returns a `CategoryCommand` object or an `InvalidCommand` object
depending on whether the user input is valid or not
3. The returned Command object is executed by the Logic Class (omitted in the diagram for brevity)
4. The appropriate method in the `Category` class is called to add the category, based on how the constructor
of the `CategoryCommand` object was called. result of the command would be returned from the `Category` class
to the `CategoryCommand` object, which would then be printed by the `UI` class to be viewed by the user (printing 
omitted from sequence diagram for brevity)

**Initialization** <br>
On startup, the `Category` class has its' set of categories loaded from the file `categories.txt` in the data folder.
This is facilitated by the `FileManager` and `GeneralFileParser` classes, with the `Logic` class serving as the main logic loop. 
The `GeneralFileParser` class reads the file and returns a list of strings.
The process is shown in the sequence diagram below:
![category_load_sequence.png](images/category_load_sequence.png)
In addition, at program initialisation, the function `setDashboard(dashboard: Dashboard)`
is called to set the dashboard object in the `Category` class.
This is to allow the `Category` class to access the `ExpenseManager` object stored in the `Dashboard` object.

### Summarising expenses
**Implementation** <br>
The expense summarising functionality is mainly facilitated by the `SummariseCommand`and `SummariseParser` classes. 
The `SummariseParser` class is responsible for constructing a `SummariseCommand` object from valid user input, which upon
execution would call the `summariseExpenses` method of the application's `ExpenseManager` object to obtain a summary of the expenses
currently tracked.

The following sequence diagram shows how user input is parsed to produce a summary of expenses in accordance with user
specifications:

![img.png](images/summarise_sequence.png)

**User input parsing sequence**
1. The user enters a command to summarise expenses, which is caught by the `UI` class and returned to the `Logic` class
2. The `Logic` class directs the user input to the `GeneralInputParser` class, which sees the `summarise` keyword
in the user input and directs it to the `SummariseParser` class
3. The `SummariseParser` class parses the user input and returns a `SummariseCommand` object or an `InvalidCommand` object 
depending on whether the user input is valid or not. A `SummariseCommand` object would store relevant information from
the user input in its private fields, whereas an `InvalidCommand` object would store an error message specifying the issues
of the user input
4. The `Logic` class calls the `execute(Dashboard dashboard)` method of the returned `Command` object
5. Upon execution, a `SummariseCommand` object would retrieve a reference to the `ExpenseManager` object stored within the 
`dashboard` and execute its `summariseExpenses` method, passing in its fields as parameters to the method call. This summarises
the expenses stored in the `ExpenseManager` object according to the user's specifications. The summary is then printed 
by the `UI` class to be viewed by the user
6. Executing an `InvalidCommand` object would instead have its error message printed by the `UI` class to be viewed by the user

### Event 
**Implementation** <br>
The event feature aims to group expenses happening on specific occasions together. 
The `Event` class stores the details of the event and the list of expenses that are associated with the event.
The `EventManager` class is responsible for aggregate operations on the events.

The UMl diagram below shows the relationships between the classes in the event feature 
(irrelevant methods are omitted) <br>
![img.png](images/Event_class.png)

The `EventExpenseManager` class mainly interacts with the `EventManager` and `ExpenseManager` classes 
via the `getExpense` and `getEvent` methods, 
which are used to retrieve event and expense objects at specific indexes. 
The connection between events and expenses should be managed through the `EventExpenseManager` class, 
which handles complexities such as circular dependencies between events and expenses.

Notably, the `EventExpenseManager` class also implements the following operations:
- `getConnectionsStringRepresentation()` Returns a string representation of all the connections between events and expenses for saving purposes
- `loadConnection(stringRepresentation : String)` Loads the connections between events and expenses from a string representation retrieved from the data file

It is possible to represent the connections between events and expenses using a dedicated `Connection` class. 
However, as the connections are frequently accessed and modified, 
it is more efficient to store the information directly in the expense and event objects.

The following sequence diagram show how a (valid) user input is processed by the `GeneralInputParser`
to create an `AddEventCommand`: <br>
![img.png](images/addEventCommand.png) <br>

This sequence diagram shows how an `AddEventCommand` is then executed to create an `Event` and 
add it to the `EventManager`
![img.png](images/executeAddEventCommand.png)

**User input event main parsing sequence**
1. The user enters a command to add an event
2. The `EventParser` class parses the user input to identify the event name and the event description
3. The `AddEventCommand` object is created with the event name and description
4. When executed, the `AddEventCommand` object creates an `Event` object with the event name and description
5. The `AddEventCommand` object calls the `addEvent` method in the `EventManager` class to add the event created in step 4
6. Feedback is given to the user via the UI

The following sequence diagram shows the execution of an `AddExpenseToEventCommand` command object <br>

![img.png](images/executeAddExpenseToEventCommand.png)

**Execution sequence of AddExpenseToEventCommand**
1. The command checks the validity of the event and expense indexes 
2. If the indexes are invalid, a feedback message is returned to the user
3. The event and expense objects are retrieved based on the indexes
4. If the expense already exists in the event, a feedback message is returned to the user
5. The original owning event of the expense is retrieved 
6. If the expense has an owning event, the expense is removed from the owning event. Note that this owning event is different from the event the expense is being added to, else the command execution would have been terminated in step 4
7. The expense is added to the new event, and its owning event is updated

## Product scope
### Target user profile

This project is designed to cater to the needs of university students who encounter the challenge of managing a myriad of expenses across various categories.
university students also do not have an extreme amount of expenses and a large budget to track, so having a simple CLI application to track expenses would be sufficient,
rather than a large scale application with a database management system.
### Value proposition

Brokeculator is a CLI application designed for university students to log and view their expenses. 
As a CLI application, it allows for faster input of expenses compared to GUI applications, saving time, a 
valuable resource for university students. The application also allows for the categorization of expenses,
and classification of expenses into events, which is useful for students who need to track their spending habits.
The application also allows for the import and export of data from a CSV file, which is useful for students who use multiple devices,
but do not want to reveal their data via the internet.
For experienced CLI users, they can enter their expenses faster compared to GUI applications

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
| v1.0 | student | see a basic summary of my expenses to see how much i have spent in total | ------------------ |
| v1.0 | student | view the expenses I have logged | know how much I have spent |
| v1.0 | paranoid user | save my expenses into a file | backup locally via a file to prevent data loss |
| v1.0 | student | have the ability to add expenses | ------------------ |
| v1.0 | student | have the ability to delete expenses | remedy my erroneous expenses |
| v1.0 | student who cares about privacy | track expenses offline | retain my privacy |
| v2.0 | university student | retrieve spending based on time periods | track important spending days |
| v2.0 | new user | see instructions on how to use the CLI commands| understand how to use the application |
| v2.0 | student | search and filter expenses based on various criteria such as dates, keywords and categories | track my spending more accurately|
| v2.0 | student frequently using excel | import/export existing data from spreadsheet/csv | record existing information |
| v2.0 | student | log expenses based on their categories | manage my spending habits within each category |
| v2.0 | university committee member | classify expenses in groups of events | check expenses of events organized|

## Non-Functional Requirements

1. **OS requirements**: The application should be able to run on any mainstream OS with Java 11 installed
2. **Performance**: The application should be able to handle user input without significant lag
3. **Reliability**: The application should be able to handle user input without crashing
4. **Usability**: The application should be easy to use for users familiar with CLI applications, instructions should be clear,
and what the application carried out based on user input should be clear to the user


## Glossary

* *mainstream OS* - Windows, Linux, MacOS

## Instructions for manual testing

This section details how to do manual testing of the application. The following sections are to be followed in sequence to test the reliability of the application.

### Testing loading of data

All data files should reside in the data folder, in the directory that the user has launched the application from.
The following are to be done in sequence to test reliability of loading data:

**Loading of category data** <br>
In the data folder, create a file named `category.txt` and populate it with the following data:
```dtd
--category--CAT3
--category--CAT2
--category--CAT1
```

**Loading of expense data** <br>
In the data folder, create a file named `data.txt` and populate it with the following data:
```dtd
--expense--|__EXPENSE_DESCRIPTION__|:test1|__EXPENSE_DATE__|:12-12-2024|__EXPENSE_AMOUNT__|:16.00
--expense--|__EXPENSE_DESCRIPTION__|:test2|__EXPENSE_DATE__|:12-12-2024|__EXPENSE_AMOUNT__|:20.00|__EXPENSE_CATEGORY__|:CAT1
--expense--|__EXPENSE_DESCRIPTION__|:test3|__EXPENSE_DATE__|:12-01-2024|__EXPENSE_AMOUNT__|:100.00|__EXPENSE_CATEGORY__|:CAT3
```

**Loading of event data** <br>
In the data folder, create a file named `event.txt` and populate it with the following data:
```dtd
--event--|__EVENT_NAME__|:eventtest|__EVENT_DESCRIPTION__|:test 1
--event--|__EVENT_NAME__|:eventtest2|__EVENT_DESCRIPTION__|:test 2
--event--|__EVENT_NAME__|:eventtest3|__EVENT_DESCRIPTION__|:test 3
```

**Loading of event connections data** <br>
In the data folder, create a file named `connection.txt` and populate it with the following data:
```dtd
--connection--|__EXPENSE__|:1|__EVENT__|:1
--connection--|__EXPENSE__|:2|__EVENT__|:2
```

Start the application by running the `Brokeculator.jar` file in the same directory as the data file. 
The application should load the data from the files and the user should see the following:
```dtd
------------------------------------
Hello! I'm Brokeculator!
If this is your first time using me, type 'help' to see what I can do for you.
------------------------------------
    ->
```

### Testing of viewing expenses

The user should be able to view the expenses by typing `list` and pressing enter. The user should see the following:
```dtd
------------------------------------
1. test1 $16.00 (Thursday, 12 December 2024)
2. test2 $20.00 (Thursday, 12 December 2024) [CAT1]
3. test3 $100.00 (Friday, 12 January 2024) [CAT3]
------------------------------------
```

### Testing of viewing categories

The user should be able to view the categories by typing `category list` and pressing enter. The user should see the following:
```dtd
------------------------------------
Categories:
- CAT3
- CAT2
- CAT1
------------------------------------
```

### Testing of viewing events

The user should be able to view the events by typing `listEvents` and pressing enter. The user should see the following:
```dtd
------------------------------------
1. eventtest (test 1)
2. eventtest2 (test 2)
3. eventtest3 (test 3)
------------------------------------
```

### Testing of viewing expenses in events

The user should be able to view the expenses in the first event by typing `viewEvent /i 1` and pressing enter. The user should see the following:
```dtd
eventtest (test 1)
Event has 1 expenses:
test1 $16.00 (Thursday, 12 December 2024)
```
The user should be able to view the expenses in the second event by typing `viewEvent /i 2` and pressing enter. The user should see the following:
```dtd
------------------------------------
eventtest2 (test 2)
Event has 1 expenses:
test2 $20.00 (Thursday, 12 December 2024) [CAT1]
------------------------------------
```
The user should be able to view the expenses in the third event by typing `viewEvent /i 3` and pressing enter. The user should see
that there are no expenses in the event:
```dtd
------------------------------------
eventtest3 (test 3)
Event has no expenses
------------------------------------
```

### Basic testing of summarising expenses

The user should be able to summarise all the expenses by typing `summarise` and pressing enter. The user should see the following:
```dtd
------------------------------------
1. test1 $16.00 (Thursday, 12 December 2024)
2. test2 $20.00 (Thursday, 12 December 2024) [CAT1]
3. test3 $100.00 (Friday, 12 January 2024) [CAT3]
------------------------------------
------------------------------------
The total is $136.00
------------------------------------
```
The user can view the expenses on 12 Decmber 2024 by typing `summarise /start 12-12-2024 /end 12-12-2024` and pressing enter. The user should see the following:
```dtd
------------------------------------
1. test1 $16.00 (Thursday, 12 December 2024)
2. test2 $20.00 (Thursday, 12 December 2024) [CAT1]
------------------------------------
------------------------------------
The total is $36.00
------------------------------------
```
The user can view the expenses with descriptions starting with a 3 in it by typing `summarise /n 3` and pressing enter. The user should see the following:
```dtd
------------------------------------
1. test3 $100.00 (Friday, 12 January 2024) [CAT3]
------------------------------------
------------------------------------
The total is $100.00
------------------------------------
```
The rest of the summarise command options can be tested in a similar manner, by following the user guide.

### Testing of cycling through command history

The user should be able to navigate through the command history by pressing the up and down arrow keys. The user should be able to see the previous command by pressing the up arrow key and the next command entered by pressing the down arrow key.

### Testing of Adding an expense

The user should be able to add an expense by typing `add /n test4 /d 11-12-2024 /a 50.00 /c CAT2` and pressing enter. The user should see the following:
```dtd
------------------------------------
Added expense: test4 $50.00 (Wednesday, 11 December 2024) [CAT2]
------------------------------------
```
upon typing `list` and pressing enter, the user should see the following:
```dtd
------------------------------------
1. test1 $16.00 (Thursday, 12 December 2024)
2. test2 $20.00 (Thursday, 12 December 2024) [CAT1]
3. test3 $100.00 (Friday, 12 January 2024) [CAT3]
4. test4 $50.00 (Wednesday, 11 December 2024) [CAT2]
------------------------------------
```

### Testing of Deleting an expense

The user should be able to delete an expense by typing `delete /i 4` and pressing enter. The user should see the following:
```dtd
------------------------------------
Deleted expense at index 4
------------------------------------
```
upon typing `list` and pressing enter, the user should see the following:
```dtd
------------------------------------
1. test1 $16.00 (Thursday, 12 December 2024)
2. test2 $20.00 (Thursday, 12 December 2024) [CAT1]
3. test3 $100.00 (Friday, 12 January 2024) [CAT3]
------------------------------------
```

### Testing of Adding an event

The user should be able to add an event by typing `event /n eventtest4 /d test 4` and pressing enter. The user should see the following:
```dtd
------------------------------------
Event added successfully
------------------------------------
```
upon typing `listEvents` and pressing enter, the user should see the following:
```dtd
------------------------------------
1. eventtest (test 1)
2. eventtest2 (test 2)
3. eventtest3 (test 3)
4. eventtest4 (test 4)
------------------------------------
```

### Testing of Deleting an event

The user should be able to delete an event by typing `deleteEvent /i 4` and pressing enter. The user should see the following:
```dtd
------------------------------------
Event deleted
------------------------------------
```
upon typing `listEvents` and pressing enter, the user should see the following:
```dtd
------------------------------------
1. eventtest (test 1)
2. eventtest2 (test 2)
3. eventtest3 (test 3)
------------------------------------
```

### Testing of Adding an expense to an event

The user should be able to add an expense to an event by typing `addExEv /exi 3 /evi 3` and pressing enter. The user should see the following:
```dtd
------------------------------------
Expense added to event successfully
------------------------------------
```
upon typing `viewEvent /i 3` and pressing enter, the user should see the following:
```dtd
------------------------------------
eventtest3 (test 3)
Event has 1 expenses:
test3 $100.00 (Friday, 12 January 2024) [CAT3]
------------------------------------
```

### Testing of Deleting an expense from an event

The user should be able to delete an expense from an event by typing `delExEv /i 3` and pressing enter. The user should see the following:
```dtd
------------------------------------
Expense removed from event successfully
------------------------------------
```
upon typing `viewEvent /i 3` and pressing enter, the user should see the following:
```dtd
------------------------------------
eventtest3 (test 3)
Event has no expenses
------------------------------------
```

### Testing of adding a category

The user should be able to add a category by typing `category add CAT4` and pressing enter. The user should see the following:
```dtd
------------------------------------
Category added: CAT4
------------------------------------
```
upon typing `category list` and pressing enter, the user should see the following:
```dtd
------------------------------------
Categories:
- CAT4
- CAT3
- CAT2
- CAT1
------------------------------------
```

### Testing of deleting a category

The user should be able to delete a category by typing `category delete CAT4` and pressing enter. The user should see
the following:
```dtd
------------------------------------
Category removed: CAT4
------------------------------------
```
upon typing `category list` and pressing enter, the user should see the following:
```dtd
------------------------------------
Categories:
- CAT3
- CAT2
- CAT1
------------------------------------
```

## Manual testing to get full coverage
The above tests are not exhaustive and are meant to be a guide to test the application. 
To conduct more tests, the user should refer to the user guide and test all the commands and options available in the application.
