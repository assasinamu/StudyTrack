# StudyTrack — Student Academic Planner

StudyTrack is a console-based student academic planner built with Java and Object-Oriented Programming (OOP). It helps students manage academic tasks such as assignments and examinations, track completion status, and review upcoming or overdue work.

This project was developed as an academic Java OOP project and is designed with a modular structure that separates task models, task-management logic, storage, and the user interface.

## Features

- **Manage assignments:** Add assignments with a title, subject, and due date.
- **Manage examinations:** Add examinations with a title, subject, exam type, and due date.
- **View tasks:** Display saved academic tasks and their details.
- **Track completion:** Mark tasks as completed.
- **Delete tasks:** Remove tasks when they are no longer needed.
- **Task statistics:** View total, completed, pending, and overdue task counts.
- **Overdue detection:** Identify incomplete tasks whose due dates have passed.
- **Persistent storage:** Save tasks to a local text file and load them when the application starts.
- **Input validation:** Validate required fields such as task titles, subjects, and due dates.

## Technologies Used

- Java
- Java Collections Framework (`List`, `ArrayList`)
- Java Date and Time API (`LocalDate`)
- File I/O and exception handling
- Base64 encoding for text fields stored in the data file
- Git and GitHub for version control and project hosting

## Object-Oriented Programming Concepts

StudyTrack demonstrates the following OOP concepts:

- **Encapsulation:** Task fields are private and are accessed or modified through methods with validation.
- **Abstraction:** `AcademicTask` is an abstract base class that defines common task behaviour.
- **Inheritance:** `AssignmentTask` and `ExamTask` extend `AcademicTask`.
- **Polymorphism:** Different task types are managed through the common `AcademicTask` type, with task-specific behaviour provided by overridden methods.
- **Separation of responsibilities:** Model classes represent tasks, `TaskManager` handles task operations, `TaskStorage` manages persistence, and `Main` handles the console workflow.

## Project Structure

```text
OOPS_PROJECT/
├── src/
│   └── studytrack/
│       ├── Main.java
│       ├── model/
│       │   ├── AcademicTask.java
│       │   ├── AssignmentTask.java
│       │   └── ExamTask.java
│       ├── service/
│       │   └── TaskManager.java
│       └── storage/
│           └── TaskStorage.java
├── data/
│   └── tasks.txt
├── out/                 # Compiled class files; normally not committed
└── README.md
```

The structure above describes the console version. If you have additional source files, such as a GUI class, keep them in the repository if they are part of the version you intend to publish.

## Requirements

- A Java Development Kit (JDK) installed.
- A terminal or Java IDE such as Visual Studio Code.

Check that Java and the Java compiler are available:

```bash
java -version
javac -version
```

## How to Compile and Run

Run these commands from the project root directory (the directory containing `src/`):

```bash
javac -d out $(find src -name "*.java")
java -cp out studytrack.Main
```

The first command compiles the Java source files and places the compiled classes in `out/`. The second command starts the application.

## Data Storage

StudyTrack stores task records locally in `data/tasks.txt`. The application loads saved tasks at startup and saves changes to the file.

- Keep the data file if you want to retain your local test tasks.
- Do not commit personal or sensitive information.
- If you change the storage format during development, back up existing data before testing the new format.

## Future Enhancements

The following features are planned but are **not yet implemented**:

- **Search tasks:** Search by task title or subject.
- **Sort by due date:** Display tasks from the earliest deadline to the latest.
- **Filter tasks:** Show all, pending, completed, or overdue tasks.
- Potential future improvements include a graphical interface, automated tests, and additional reporting.

## Current Status

The console-based application supports creating assignments and examinations, viewing tasks, marking tasks completed, deleting tasks, viewing statistics, detecting overdue tasks, and saving/loading task data.

## Author

Developed as a Java Object-Oriented Programming academic project.

---

If you clone this repository, compile and run it using the instructions above.
