# Task-X

TaskX is a Java console application demonstrating all four OOP pillars: Inheritance, Polymorphism, Encapsulation, and Abstraction. It uses an object hierarchy for task management and leverages file I/O for essential data persistence.

## Key Features

* **Task Management:** Create and track four types of records: Assignment, Quiz, Exam, and Reminder.
* **Inheritance:** Uses an abstract base class (`Record`) which is extended by all specific task types.
* **File Persistence:** Data is automatically saved and loaded from a file named `SCNZ.txt`.
* **Custom Utilities:** Includes an enum for **Priority** (LOW, MEDIUM, HIGH, CRITICAL) and a utility class (`RecordUtil`) for defining sorting logic based on course or deadline.

## Getting Started

### 1. File Structure

All classes are in the default Java package, so they must be placed in the same directory (e.g., `Task X/`):

Task X/
├── Assignment.java
├── Exam.java
├── Priority.java
├── Quiz.java
├── Record.java
├── RecordUtil.java
├── Reminder.java
└── TaskX.java  <-- Main executable class

### 2. Compilation

Navigate to the `Task X` directory in your terminal and compile all Java source files:

javac *.java

### 3. Execution

Run the main application class:

java TaskX

## Code Overview

The project architecture is structured around the `Record` hierarchy:

| File Name         | Description         | Key Elements                                                                 |
| :---------------- | :----------------- | :-------------------------------------------------------------------------- |
| `Record.java`     | Abstract Base Class | Defines essential properties (title, course, deadline, priority, total marks) and abstract methods (`display()`, `toFileString()`). |
| `Assignment.java` | Concrete Class      | Extends `Record`. Tracks if the assignment is a **Lab** or **Theory** task. |
| `Quiz.java`       | Concrete Class      | Extends `Assignment`. Also tracks if it's a **Viva** (oral exam).           |
| `Exam.java`       | Concrete Class      | Extends `Record`. Tracks the specific **Exam Type** (e.g., Mids/Finals).    |
| `Reminder.java`   | Concrete Class      | Extends `Record`. Stores an `ArrayList` of strings for **notes**.           |
| `Priority.java`   | Enum                | Defines the four standard priority levels.                                  |
| `RecordUtil.java` | Utilities           | Contains `Comparator` definitions (e.g., `deadlineComparator`) and a static `HashMap` (`coursePriority`) for fixed course priority/order. |
| `TaskX.java`      | Main App            | Handles the user menu, file loading/saving (`SCNZ.txt`), and task creation logic. |
