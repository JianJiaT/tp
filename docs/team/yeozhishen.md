# Yeo Zhi Shen - Project Portfolio Page

## Overview
Brokeculator is a CLI application designed for university students to log and view their
expenses. It aims to tackle the challenge they face of managing a myriad of expenses across various categories.

# Table of contents
* [Summary of contributions](#Summary-of-contributions)
* [Contributions to the Developer Guide (extracts)](#Contributions-to-the-developer-guide-extracts)

# Summary of contributions
## Code contributed 
[RepoSense_link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=yeozhishen&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=yeozhishen&tabRepo=AY2324S2-CS2113-F14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

## Features implemented
### 1. Main Logic
The main logic class is used to handle the main logic of the program. It is responsible for handling the flow
of the program and calling the respective classes to handle the user input and calling the load save function.

### 2.AddParser
The AddParser class is used to parse the user input for the add command. 
It returns an InvalidCommandException if the user input is invalid, else it returns an AddCommand command.
- Main challenges faced was coming up with a way to dynamically parse the user input. 

### 3. Added category class and corresponding commands/parsers
The category class is used to store the categories that the user has added. It also handles the deletion of categories.
This class also checks if the expense entered from files/commands has a valid category.
- Main challenges faced were a result of coordinating with the other classes to preserve data integrity
- The commands and parser serve as the interface for the user to interact with the category class.

## Enhancements implemented
### 1. Added dynamic parsing in AddParser
The AddParser class now dynamically parses the user input to check if the desired option fields are entered by the user.
The developer will just need to edit an array of options allowed and call a function in the class to retrieve the user entered option.
- Initial implementation of the class was to use just the string.contains to check if the user input contained the desired option, and find the string indexes which they started from. 
- Instead of using the initial implementation, which was bug prone based on the user input, the current solution was developed to be more robust and dynamic.


### 2. Using regex to parse inputs relating to values
Utilised regex to ensure that user inputs conformed with the desired option input,
due to edge cases not being handled the corresponding parseDouble function.

## Contribution to team tasks
- Setting up tooling such as github milestones and issues, github actions.
- Maintaining issue tracker
- Updated user and developer docs not related to features

## Contributions to the User Guide 
- Added structure to the user guide and filled up details for some commands (refer to reposense)
- Added FAQ section to the user guide to avoid user confusion on file corruption issues
- Added command summary section

## Contributions to the Developer Guide
- Added the explanations, class and sequence diagrams for the category class
- Added instructions on manual testing
- Added user stories section

<div style="page-break-after: always;"></div>

# Contributions to the developer guide (extracts)

The following images are the class and sequence diagrams created and elaborated on in the
developer guide:

## Category Class Diagram
![category_class.png](../images/category_class.png)


## Category Parsing Sequence Diagram

![category_parse_sequence.png](../images/category_parse_sequence.png)

## Category Loading Sequence Diagram

![category_load_sequence.png](../images/category_load_sequence.png)

The following snippet is an example of the manual testing instructions provided in the developer guide:
```dtd
### Testing of viewing expenses

The user should be able to view the expenses by typing `list` and pressing enter. The user should see the following:
------------------------------------
1. test1 $16.00 (Thursday, 12 December 2024)
2. test2 $20.00 (Thursday, 12 December 2024) [CAT1]
3. test3 $100.00 (Friday, 12 January 2024) [CAT3]
------------------------------------
```
