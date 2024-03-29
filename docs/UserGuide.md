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
