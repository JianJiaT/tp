# Yap Shan Teng - Project Portfolio Page 

## Project: Brokeculator

### Overview
Brokeculator is a CLI application designed for university students to log and view their
expenses. It aims to tackle the challenge they face of managing a myriad of expenses across various categories. For
experienced CLI users, they can enter their expenses faster compared to GUI applications 

Code Contribution : [RepoSense](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=STeng618&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=STeng618&tabRepo=AY2324S2-CS2113-F14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

## Features implemented
### 1. File Storage
- Implemented the storage feature that allows saving and loading of data from the hard disk.
- The main challenge was to maintain separation of concerns between the storage classes and the data classes, where the data classes focused on generating string representations of the data objects, and the storage classes focused on the reading and writing of data to the hard disk. 
- This was achieved by using dedicated formatting classes that handled the conversion between the data string representation and the file string representation.
- In particular, the `FileKeyword` class defined the keywords used in the file format for each `SaveableType` object such as `Event`, `Expense`, `Category`, and `Connection`, and formatted the string representation of these data objects into file strings that could be written to the hard disk. The `FileKeyword` class then parsed file strings by identifying the keywords and extracting the original data strings, which are then passed to the respective data classes for parsing into data objects.
- Thus, the storage classes only needed to know the `SaveableType` of the data objects they were reading and writing, and the data classes only needed to know how to convert their data objects into string representations and back, achieving separation of concerns.

### 2. Order Parser 
- Implemented the order parser feature that parses a string based on `Keyword`, provided arguments and the expected order of the arguments.
- Used in the parsing of user input to extract the necessary information for command execution, and saving and loading data from the hard disk.
- The main challenge was to ensure that the order parser could identify the correct arguments based on the expected order, and handle the case where the user input did not match the expected order. 
- Prior to the use of the order parser, the parsing of user input was done manually by splitting the input string by spaces and extracting the necessary information based on the position of the arguments. This was error-prone and difficult to maintain as the number of arguments increased.
- The order parser provided a more robust and maintainable solution by allowing the specification of expected arguments through the `Keyword` class, which defined markers for the start and end of an argument, the meaning of the argument, and whether the argument was required or optional. The order parser then parsed the user input based on the specified order of all `Keyword` objects.

### 3. Dashboard
- Implemented the dashboard class that keeps a reference to the manager classes and provides a centralised interface for the commands to interact with the managers.
- This simplified the execution of commands by providing a single point of access to the managers, and allowed the managers to be easily swapped out or extended without affecting the commands. Additionally, the commands now have a consistent method signature for execution, which makes it easier to add new commands.

### 4. Events
- Implemented the event class that helps to group expenese items together.
- The main challenge was to maage circular dependencies between `Event` and `Expense`, where the event class needed to keep track of the expenses associated with it, and the expense class needed to keep track of the event it belonged to.
- While a custom `Connection` class may be used to manage the circular dependencies, we decided to preserve the navigability as the event class needed to access the expenses associated with it frequently. 
- To overcome the circular dependencies, a custom manager class `EventExpenseManager` was created to manage the association between events and expenses. The `EventExpenseManager` class kept track of the events and expenses, and provided methods to update the associations between them.

## Contributions to User Guide
- Added the user guide for the event feature

## Contributions to Developer Guide
- Added the descriptions of the manager classes, data classes and supporting classes in the Overview section 
- Added the sequence diagram for adding an expense to illustrate the main execution flow of the application 
- Added the class diagram for the event feature and three sequence diagrams that illustrate the execution of sample commands involving events
- Added the explanations for the sorage feature
- Added non-functional requirements 

## Contributions to team-based tasks 
- Handled formatting issues in the PDF version of the user guide and developer guide
- Managed GitHub issues, linked PRs to issues and milestones

## Mentoring contributions
- Reviewed the UML diagrams in the developer guide and provided feedback on the clarity and completeness of the diagrams
- Shared tips on the formatting of UML diagrams to ensure conformity with the course requirements based on the PlantUML documentation
