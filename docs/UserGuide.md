# User Guide

## Introduction

Brokeculator is a CLI application designed for university students to log and view their
expenses. It aims to tackle the challenge they face of managing a myriad of expenses across various categories. For 
experienced CLI users, they can enter their expenses faster compared to GUI applications

## Quick Start

1. Ensure that you have Java 11 or above installed
2. Download the latest version of `brokeculator` from [here](https://github.com/AY2324S2-CS2113-F14-1/tp/releases)
3. Open a command terminal and `cd` to the folder you put the `brokeculator.jar` file in
4. Enter `java -jar brokeculator.jar` into the terminal to run the application
5. Refer to the Features section below for details of each command

## Features
1. Words in UPPER_CASE are parameters
2. Parameters in square brackets are optional parameters
### 1. Handle categories: category
#### **_SYNOPSIS_**
```dtd    
category [add|list|delete] [CATEGORY_NAME]
```
#### **_DESCRIPTION_**
Main command to handle categories. `add` 
and `delete` should be accompanied by `CATEGORY_NAME`. `list` will list all categories.
When deleting a category, all expenses using that category must be deleted first.
The category specified in `add` and `delete` will be converted to uppercase.
#### **_USAGE_**
Example of adding category: <br>
```dtd
category add test1
```
Example of deleting category: <br>
```dtd
category delete test2
```
Example of listing categories: <br>
```dtd
category list
```

### 2. Adding expenses: add
#### **_SYNOPSIS_**
```dtd
add /n EXPENSE_NAME /d DATE /a AMOUNT [/c CATEGORY]
```
#### **_DESCRIPTION_**
Adds an expense to the list of expenses tracked by the application.
The expense must have a name, date and amount.
#### **_PARAMETERS_**
`/n EXPENSE_NAME` : Name of the expense. <br>
`/d DATE` : Date of the expense, mus be in DD-MM-YYYY format. <br>
`/a AMOUNT` : Amount of the expense. Must be a float/integer value, either 0 or 2 decimal places. <br>
#### **_OPTIONAL PARAMETERS_**
`/c CATEGORY` : Category of the expense. Category must be present in the category list.
If not present, add the category using the `category add` command. <br>
#### **_USAGE_**
Examples of usage: <br>
```dtd
add /n tea /d 14-02-2002 /a 2.50 /c food
```
```dtd
add /n coffee /d 15-02-2003 /a 3.00
```
```dtd
add /n milk /d 15-02-2004 /a 3 /c food
```
### 3. Deleting expenses: delete
#### **_SYNOPSIS_**
```dtd    
delete INDEX
```
#### **_DESCRIPTION_**
Deletes the expense at the specified INDEX.
#### **_USAGE_**
Example of deleting the first expense: <br>
```dtd
delete 1
```
Example of deleting the 5th expense: <br>
```dtd
delete 5
```

### 4. Listing expenses: list
#### **_SYNOPSIS_**
```dtd
list [AMOUNT_TO_LIST]
```
#### **_DESCRIPTION_**
Lists the tasks tracked by the application
#### **_OPTIONAL PARAMETERS_**
`AMOUNT_TO_LIST` : The number of tasks to list. If specified, lists the first `AMOUNT_TO_LIST` tasks. 
Must be a positive integer. If not provided, will list all tasks.
#### **_USAGE_**
Example of usage: <br>
```dtd
list
```
Example of listing the first 5 tasks: <br>
```dtd
list 5
```
### 5. Summarising expenses: summarise
#### **_SYNOPSIS_**
```dtd    
summarise [/n NAME] [/d DATE] [/c CATEGORY] [/from BEGIN_INDEX] [/to END_INDEX] [/start START_DATE] [/end END_DATE]
```
#### **_DESCRIPTION_**
Displays a summary of the expenses between the specified indices that match all of the user specifications. The summary
consists of the sum of the cost of said expenses as well as a list of the expenses that were summarised. If no parameters
are provided, will summarise all expenses tracked by the application.
#### **_OPTIONAL PARAMETERS_**
`/n NAME` : Expenses need to have this `NAME` to be summarised <br>
`/d DATE` : Expenses need to have this `DATE` to be summarised <br>
`/c CATEGORY` : Expenses need to have this `CATEGORY` to be summarised <br>
`/from BEGIN_INDEX` : Expenses from this `BEGIN_INDEX` onwards (inclusive) will be summarised. Must be positive integer.
If not provided, will summarise from start of list <br>
`/to END_INDEX` : Expenses up to this `END_INDEX` (inclusive) will be  summarised. Must be positive integer.
If not provided, will summarise up to end of list <br>
`/start START_DATE` : Expenses from this `START_DATE` onwards (inclusive) or till the end date (if specified) 
will be summarised. Must be in the format `dd-MM-yyyy`.
`/end END_DATE` : Expenses up to this `END_DATE` (inclusive) or from the start date (if specified) will be summarised.

#### **_USAGE_**
Example of summarising all expenses: <br>
```dtd
summarise
```
Example of summarising expenses beginning from the 3rd index: <br>
```dtd
summarise /from 3
```
Example of summarising expenses up to the 5th index: <br>
```dtd
summarise /to 5
```
Example of summarising expenses between the 4th and 6th indices: <br>
```dtd
summarise /from 4 /to 6
```
Example of summarising all expenses with the name `tea`: <br>
```dtd
summarise /n tea
```
Example of summarising expenses with the date `14-02-2002` up to the 7th index: <br>
```dtd
summarise /d 14-02-2002 /to 7
```
Example of summarising expenses with the name `chicken` and the category `food` beginning from the 2nd index: <br>
```dtd
summarise /n chicken /c food /from 2
```

### 6. Adding events: event
#### **_SYNOPSIS_**
```dtd
event /n EVENT_NAME /d EVENT_DESCRIPTION
```
#### **_DESCRIPTION_**
Adds an event to the list of events tracked by the application.
This event can be used to group expenses together. This is to be used
in conjunction with `addExpenseToEvent` to add expenses to the event.
#### **_PARAMETERS_**
`/n EVENT_NAME` : Name of the event. <br>
`/d EVENT_DESCRIPTION` : Description of the event. <br>

#### **_USAGE_**
Examples of usage: <br>
```dtd
event /n birthday /d birthday party for bob
```

### 7. Viewing events: listEvents
#### **_SYNOPSIS_**
```dtd
listEvents
```

#### **_DESCRIPTION_**
Lists all events tracked by the application. Each event has an index associated with it.
This index can be used to add expenses to the event using the `addExpenseToEvent` command.

#### **_USAGE_**
Example of usage: <br>
```dtd
listEvents
```

### 8. Deleting events: deleteEvent
#### **_SYNOPSIS_**
```dtd
deleteEvent /i INDEX
```
#### **_DESCRIPTION_**
Delete an event that you no longer wish to track. Note that events associated with expenses cannot be deleted. To delete
an event, first delete the expenses associated with the event using `deleteExpenseFromEvent` and then delete the event.
#### **_PARAMETERS_**
`/i INDEX` : Index of the event to be deleted. <br>

#### **_USAGE_**
Examples of usage: <br>
```dtd
deleteEvent /i 1
```

### 9. Adding expenses to events: addExpenseToEvent
#### **_SYNOPSIS_**
```dtd
addExpenseToEvent /exi EXPENSE_INDEX /evi EVENT_INDEX
```
#### **_DESCRIPTION_**
Adds an expense to an event. The expense and event are identified by their respective indices.
If an expense already belongs to an event, it will be removed from the previous event and added to the new event.

#### **_PARAMETERS_**
`/exi EXPENSE_INDEX` : Index of the expense to be added to the event. <br>
`/evi EVENT_INDEX` : Index of the event to which the expense is to be added. <br>

#### **_USAGE_**
Examples of usage: <br>
```dtd
addExpenseToEvent /exi 1 /evi 1
```

### 10. Deleting expenses from events: deleteExpenseFromEvent
#### **_SYNOPSIS_**
```dtd
deleteExpenseFromEvent /i EXPENSE_INDEX_FROM_LIST_COMMAND
```
#### **_DESCRIPTION_**
Deletes an expense from an event. The expense is identified by its index in the list of expenses.
#### **_PARAMETERS_**
`/i EXPENSE_INDEX_FROM_LIST_COMMAND` : Index of the expense to be deleted from the event. <br>

#### **_USAGE_**
Examples of usage: <br>
```dtd  
deleteExpenseFromEvent /i 1
```

### 11. Viewing expenses in events: viewEvent
#### **_SYNOPSIS_**
```dtd
viewEvent /i INDEX
```
#### **_DESCRIPTION_**
Displays the expenses associated with the event at the specified index.
#### **_PARAMETERS_**
`/i INDEX` : Index of the event whose expenses are to be displayed. <br>

#### **_USAGE_**
Examples of usage: <br>
```dtd
viewEvent /i 1
```

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: copy over the files in ./data (in the directory that you ran the jar file from)
and run the jar on your other computer. The categories, events and expenses will be automatically loaded on
application startup.

## Command Summary

1. **Category**: 
    * Add category `category add CATEGORY_NAME`
    * List categories `category list`
    * Delete category `category delete CATEGORY_NAME`
2. **Expenses**:
    * Add expense `add n/EXPENSE_NAME d/DATE a/AMOUNT c/CATEGORY`
    * List expenses `list INDEX`
    * Delete expense `delete INDEX`
    * summarise expenses `summarise /n NAME /d DATE /c CATEGORY /from BEGIN_INDEX /to END_INDEX`
3. **Events**:
    * Add event `event n/EVENT_NAME d/DESCRIPTION`
    * List events `listEvents`
    * Delete event `deleteEvent /i INDEX`
    * Add expense to event `addExpenseToEvent /exi EXPENSE_INDEX /evi EVENT_INDEX `
    * delete expense from event `deleteExpenseFromEvent /i EXPENSE_INDEX_FROM_LIST_COMMAND`
    * List expenses in event `viewEvent /i INDEX`


