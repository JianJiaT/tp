# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

{Give detailed description of each feature}
### 1. Handle categories: category
#### **_SYNOPSIS_**
```dtd    
category [add|list|delete] [category_name]
```
#### **_DESCRIPTION_**
Main command to handle categories. Add 
and delete should be accompanied by category name. List will list all categories.
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

### 2. Summarising expenses: summarise
#### **_SYNOPSIS_**
```dtd    
summarise [/n NAME] [/d DATE] [/c CATEGORY] [/from BEGIN_INDEX] [/to END_INDEX]
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
Example of summarising expenses with the date `14/02` up to the 7th index: <br>
```dtd
summarise /d 14/02 /to 7
```
Example of summarising expenses with the name `chicken` and the category `food` beginning from the 2nd index: <br>
```dtd
summarise /n chicken /c food /from 2
```

### 2. Deleting expenses: delete
#### **_SYNOPSIS_**
```dtd    
delete [INDEX]
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


### Adding a todo: `todo`
Adds a new item to the list of todo items.

Format: `todo n/TODO_NAME d/DEADLINE`

* The `DEADLINE` can be in a natural language format.
* The `TODO_NAME` cannot contain punctuation.  

Example of usage: 

`todo n/Write the rest of the User Guide d/next week`

`todo n/Refactor the User Guide to remove passive voice d/13/04/2020`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
