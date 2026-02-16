# Woody User Guide

Woody is a lightweight task manager that helps you track todos, deadlines, and events.
You interact with it using short commands and get responses in a chat-style interface.

## Quick start

1. Prerequisites: ensure you have Java 17 or above installed.
   ```
   java -version
   ```
2. Download the latest `woody.jar` from GitHub Releases.
3. Run the app:
   ```
   java -jar woody.jar
   ```
4. Start managing tasks:
   ```
   todo buy grapes
   deadline report /by 20/4/1993 1900
   list
   ```

## Command summary

- `list` shows all tasks
- `todo <description>` adds a todo
- `deadline <description> /by <date/time>` adds a deadline
- `event <description> /from <start> /to <end>` adds an event
- `mark <index>` marks a task as done
- `unmark <index>` marks a task as not done
- `delete <index>` removes a task
- `find <keyword>` lists tasks that contain the keyword
- `bye` exits the app

## Listing tasks

Example: `list`

Expected outcome:
```
Here are the tasks in your list:
1. [T][ ] buy groceries
2. [D][X] submit report (by: 20 Apr 1993 19:00)
```

## Adding a todo

Example: `todo buy groceries`

Expected outcome:
```
Got it. I've added this task:
  [T][ ] buy groceries
Now you have 1 tasks in the list.
```

## Adding a deadline

Use this when you need a task that must be completed by a specific time.

Example: `deadline submit report /by 20/4/1993 1900`

Expected outcome:
```
Got it. I've added this task:
  [D][ ] submit report (by: 20 Apr 1993 19:00)
Now you have 1 tasks in the list.
```

## Adding an event

Example: `event team sync /from 21/4/1993 1400 /to 21/4/1993 1500`

Expected outcome:
```
Got it. I've added this task:
  [E][ ] team sync (from: 21 Apr 1993 14:00 to: 21 Apr 1993 15:00)
Now you have 1 tasks in the list.
```

## Marking a task

Example: `mark 2`

Expected outcome:
```
Nice! I've marked this task as done:
  [D][X] submit report (by: 20 Apr 1993 19:00)
```

## Unmarking a task

Example: `unmark 2`

Expected outcome:
```
OK, I've marked this task as not done yet:
  [D][ ] submit report (by: 20 Apr 1993 19:00)
```

## Deleting a task

Example: `delete 1`

Expected outcome:
```
Noted. I've removed this task:
  [T][ ] buy groceries
Now you have 1 tasks in the list.
```

## Finding tasks

Example: `find report`

Expected outcome:
```
Here are the matching tasks in your list:
1. [D][ ] submit report (by: 20 Apr 1993 19:00)
```

## Exiting

Example: `bye`

Expected outcome:
```
Bye. Hope to see you again soon!
```
