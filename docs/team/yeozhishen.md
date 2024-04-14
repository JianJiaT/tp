# Yeo Zhi Shen - Project Portfolio Page

## Overview
## Code contributed [RepoSense_link](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=yeozhishen&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-02-23&tabOpen=true&tabType=authorship&tabAuthor=yeozhishen&tabRepo=AY2324S2-CS2113-F14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

## Features implemented
### 1. Main Logic
The main logic class is used to handle the main logic of the program. It is responsible for handling the flow
of the program and calling the respective classes to handle the user input and calling the load save function.

### 2.AddParser
The AddParser class is used to parse the user input for the add command. 
It returns an InValidCommandException if the user input is invalid, else it returns an AddCommand command.
Main challenges faced was coming up with a way to dynamically parse the user input. 

### 3. Added category class
The category class is used to store the categories that the user has added. It also handles the deletion of categories.
This class also checks if the expense entered from files/commands has a valid category.
Main challenges faced were a result of coordinating with the other classes to preserve data integrity

## Enhancements implemented
### 1. Added dynamic parsing in AddParser
The AddParser class now dynamically parses the user input to check if the desired option fields are entered by the user.
The developer will just need to edit an array of options allowed and call a function in the class to retrieve the user entered option.

## Contribution to team tasks
- Setting up tooling such as github milestones and issues, github actions.
- Maintaining issue tracker
- Updating user docs not related to features

## Contributions to the User Guide
Gave rough structure to the user guide and filled up details for features
## Contributions to the Developer Guide
- Filled up the class and sequence diagrams for the category class
- Set up instructions on manual testing