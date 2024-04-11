# Yap Shan Teng - Project Portfolio Page 

## Overview
- This project is a personal finance management application that helps users keep track of their expenses and income. 
- Code Contribution : [RepoSense](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=STeng618&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=STeng618&tabRepo=AY2324S2-CS2113-F14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

## Features implemented
### 1. File Storage
- Implemented the file storage feature that allows users to save and load data from the hard disk.
- Handled the formatting of storage objects and the parsing of file data to reproduce the objects.

### 2. Order Parser 
- Implemented the order parser feature that parses a string based on Keywords, Arguments and Expected Order.
- Used in the parsing of user input to extract the necessary information for command execution, and saving and loading data from the hard disk.

### 3. Dashboard
- Implemented the dashboard class that keeps a reference to the manager classes and provides a centralised interface for the commands to interact with the managers.

### 4. Events
- Implemented the event class that helps to group expenese items together.
- Main challenge was to manage the circular dependency between the event and expense classes, and ensure data consistency in storage.

## Contributions to User Guide
- Wrote the user guide for the event class.

## Contributions to Developer Guide
- Provided the class diagram for the event class, and sequence diagram for several commands related to the Event feature such as adding an event to the event manager, and executing a command to add an expense to an event.
