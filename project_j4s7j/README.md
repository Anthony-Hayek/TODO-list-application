# To-do list

## Anthony Hayek


This application will allow users to organize their tasks by creating **a to-do list**. The target audience for this 
application would be people with busy schedules such as university students. As university students we are often
hammered with assignments or homework. This application will help those who need to keep track of all the things 
that they need to do, such as projects or study for a test and make sure they don't forget. This project is of interest 
to me as I tend to be pretty unorganized, so it would help me stay on top of things. 

##User Stories

* As a user, I want to be able to add a task to my to-do list
* As a user, I want to be able to remove a task from my to-do list
* As a user, I want to be able to mark tasks as completed
* As a user, I want to be able to look at all the task in my to-do list
* As a user, I want to be able to see how many tasks are incomplete
* As a user, I want to be able to save my to-do list to a file
* As a user, I want to be able to load my to-do list from a file


Phase 4: Task 2
(robustness)
In the TodoList class, I made the addTask methods throw an exception if the user inputted an empty
string as it doesn't make sense to have an empty task

Phase 4: Task 3
If I had more time, in terms of refactoring, I would...
* Create a separate class for the buttons, as this would increase cohesion.
    *   Possibly even use an abstract class for the buttons as there is a lot of duplication
* Make a separate class for the list scroll pane 
    *  This would overall improve cohesion as there is too much going on in the GUI




