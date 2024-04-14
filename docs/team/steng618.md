## Yap Shan Teng - Project Portfolio Page 

### Project Overview
Brokeculator is a CLI application designed for university students to log and view their
expenses. It aims to tackle the challenge they face of managing a myriad of expenses across various categories. For
experienced CLI users, they can enter their expenses faster compared to GUI applications 

Code Contribution : [RepoSense](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=STeng618&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=STeng618&tabRepo=AY2324S2-CS2113-F14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Features implemented
#### 1. File Storage
- Implemented the storage feature for saving and loading of data from the hard disk.
- The main challenge was to maintain separation of concerns between the storage classes and the data classes so that the string representation of the data objects would not be dependent on file parsing and vice versa. 
- The `FileKeyword` class defined the keywords used in the data files for each object saved and formatted the string representation of these data objects into file strings. It also parsed the file strings to identify the keywords and extract the original data strings, which are then passed to the respective data classes for crecreating the data objects, achieving separation of concerns.

#### 2. Order Parser 
- Implemented the order parser feature that parses user input.
- The main challenge was correctly identifying the arguments based on the expected order, and handling of illegal inputs.
- The parser allowed the specification of expected arguments through the `Keyword` class, which defined markers for the start and end of an argument, the meaning of the argument, and whether the argument was required or optional. The order parser then parsed the user input based on the specified `Keyword` objects.

#### 3. Dashboard
- Implemented the dashboard class that keeps a reference to the manager classes and provides a centralised interface for the commands to interact with the managers.
- Thi allowed the commands to have a consistent method signature for execution.

#### 4. Events
- Implemented the `Event` class that helped to group expenese items together.
- The main challenge was managing the circular dependency between `Event` and `Expense`, which was inevitable as the event class needed to access the expenses associated with it frequently. 
- `EventExpenseManager` was created to oversee the association between events and expenses, providing methods to update the associations between them.

### Contributions to User Guide
- Added the user guide for the event feature

### Contributions to Developer Guide
- Added the descriptions of the manager classes, data classes and supporting classes
- Added the sequence diagram for adding an expense
- Added the UML diagrams for the event feature 
- Added the explanations for the storage feature
- Added non-functional requirements 

### Contributions to team-based tasks 
- Handled formatting issues in the PDF version of the user guide and developer guide
- Managed GitHub issues, linked PRs to issues and milestones

### Mentoring contributions
- Reviewed the UML diagrams in the developer guide and provided feedback on the clarity and completeness of the diagrams
