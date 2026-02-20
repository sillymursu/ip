# **LeGoat**
<img width="619" height="310" alt="image" src="https://github.com/user-attachments/assets/0f216faa-9888-45db-8fb7-ffe159a08f25" />
<img width="619" height="542" alt="Screenshot 2026-02-20 172740" src="https://github.com/user-attachments/assets/bb5a7d55-5929-41ab-aae8-d531005743d4" />

# Ever wanted LeGoat to manage your tasks for you?
LeGoat is an application that can be used to manage tasks. You can easily add/remove/update tasks that will be saved/loaded across sessions.
LeGoat comes neatly packaged in his jar file, your wishes are his commands! 🏀💨

# LeGoat's Features ⛹️‍♂️
## Adding Todos
"todo \<taskName\>"
* Adds the most basic form of task to the list of tasks.
* Todo task type is indicated by the "[ T ]" in from of the task.
* All task types have a mandatory field "\<taskName\>" that specifies the name of each task!
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/91d334e1-ef25-4443-8f2b-51dd770ac71c" />

## Adding Deadlines
"deadline \<taskName\> \\by \<deadline\>"
* Adds a Deadline Task to the list of tasks.
* Deadline task type is indicated by the "[ D ]" in from of the task.
* Deadline tasks have a mandatory field "\<deadline\>" that specifies when this task has to be completed by!
* If "\<deadline\>" is written in "YYYY MM DD hhmm" format, LeGoat shall automatically format the deadline! (Thanks LeGoat!)
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/f3c8388a-b630-4b10-a20b-9bd33624c31d" />

## Adding Events
"event \<taskName\> \\from \<beginning\> \\to \<ending\>"
* Adds a Event Task to the list of tasks.
* Event task type is indicated by the "[ E ]" in from of the task.
* Event tasks have 2 mandatory fields, "\<beginning\>" and "\<ending\>" that specifies when this task begins, and when it ends!
* If "\<beginning\>" and/or "\<ending\>" is written in "YYYY MM DD hhmm" format, LeGoat shall automatically format these fields!
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/9e3969fa-c65e-4470-9361-a4f271d145df" />

## Display your list of tasks
"list"
* LeGoat displays your list of current tasks for you!
* Tasks are indexed by their position in the list, and are specified by the number in front of each task when displayed as a list.
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/126f442b-49bd-4d7e-ad5f-9fb53490d805" />

## Update a task from your list
* LeGoat accepts updates to tasks in these 4 formats!

Applicable to all tasks:
* "update \<list idx\> name \<updatedTaskName\>"

Applicable only to Deadline tasks:
* "update \<list idx\> deadline \<updatedDeadline\>"

Applicable only to Event tasks:
* "update \<list idx\> event \from \<updatedBeginning\>"
* "update \<list idx\> event \to \<updatedEnding\>"
<img width="619" height="480" alt="image" src="https://github.com/user-attachments/assets/8eb8cb8c-37f0-47b7-985d-dfc85fe618ec" />

## Mark/Unmark a task in your list
"mark \<list idx\>"
"unmark \<list idx\>"
* If a task is incomplete, it is indicated as a "[]" when the task is displayed!
* Done with a task? Mark a incomplete task as completed, indicated as a "[ X ]" when the task is displayed!
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/cc1e2b71-eb36-4df5-a4be-8adc08e3410d" />

* Mistakenly marked a task as complete? Unmark that task and render it incompleted with unmark!
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/ba6b63ff-a21a-4f8a-9015-f52f91fc499a" />

## Find a task in your list
"find \<keyword\>"
* Want to find task(s) that have \<keyword\> in their taskName(s)? Use find!
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/6db4c5d4-ebf5-4f74-89c5-5281483c86f9" />

## Delete a task from your list
"delete \<list idx\>"
* This deletes a task at specified list index from the list of tasks!
<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/fbb29f1c-b680-4291-99d2-885adcd6c243" />

## Had to much greatness for today? Bye!
* "bye"
* This command force LeGoat to exit.

<img width="619" height="240" alt="image" src="https://github.com/user-attachments/assets/50681dcf-a722-4c9e-a22f-cd025522b843" />

# Installation Steps:
> 1. Download LeGoat.jar under [releases](https://github.com/sillymursu/ip/releases)
> 2. Double click LeGoat.jar
> 3. Enjoy LeGoat's task managing skills ⛹️‍♂️
