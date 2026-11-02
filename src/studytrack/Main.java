package studytrack;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import studytrack.model.AcademicTask;
import studytrack.model.AssignmentTask;
import studytrack.model.ExamTask;
import studytrack.service.TaskManager;
import studytrack.storage.TaskStorage;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();
        TaskStorage storage = new TaskStorage("data/tasks.txt");

        // Load previously saved tasks when the application starts.
        try {
            List<AcademicTask> savedTasks = storage.loadTasks();
            manager.loadTasks(savedTasks);

            System.out.println(
                    "Loaded " + savedTasks.size() + " saved tasks.");

        } catch (IOException exception) {
            System.out.println(
                    "Could not load tasks: " + exception.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n========== STUDYTRACK ==========");
            System.out.println("1. View all tasks");
            System.out.println("2. Add assignment");
            System.out.println("3. Add examination");
            System.out.println("4. Mark task as completed");
            System.out.println("5. Delete task");
            System.out.println("6. View statistics");
            System.out.println("7. Exit");
            System.out.println("================================");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            try {

                switch (choice) {

                    // View all tasks.
                    case "1" -> {
                        List<AcademicTask> tasks =
                                manager.getAllTasks();

                        if (tasks.isEmpty()) {
                            System.out.println("No tasks found.");
                        } else {
                            System.out.println("\n===== ALL TASKS =====");

                            for (int i = 0; i < tasks.size(); i++) {
                                AcademicTask task = tasks.get(i);

                                System.out.println(
                                        "\n" + (i + 1) + ". "
                                        + task.getSummary());

                                System.out.println(
                                        "   Task ID: " + task.getId());

                                if (task.isOverdue()) {
                                    System.out.println("   OVERDUE!");
                                }
                            }
                        }
                    }

                    // Add a new assignment.
                    case "2" -> {
                        System.out.print("Assignment title: ");
                        String title = scanner.nextLine();

                        System.out.print("Subject: ");
                        String subject = scanner.nextLine();

                        System.out.print("Due date (YYYY-MM-DD): ");
                        LocalDate dueDate = LocalDate.parse(
                                scanner.nextLine().trim());

                        AcademicTask assignment =
                                new AssignmentTask(
                                        title, subject, dueDate);

                        manager.addTask(assignment);

                        storage.saveTasks(manager.getAllTasks());

                        System.out.println(
                                "Assignment added and saved successfully.");
                    }

                    // Add a new examination.
                    case "3" -> {
                        System.out.print("Examination title: ");
                        String title = scanner.nextLine();

                        System.out.print("Subject: ");
                        String subject = scanner.nextLine();

                        System.out.print(
                                "Exam type (Minor/Major): ");
                        String examType = scanner.nextLine();

                        System.out.print(
                                "Exam date (YYYY-MM-DD): ");
                        LocalDate dueDate = LocalDate.parse(
                                scanner.nextLine().trim());

                        AcademicTask exam = new ExamTask(
                                title,
                                subject,
                                examType,
                                dueDate
                        );

                        manager.addTask(exam);

                        storage.saveTasks(manager.getAllTasks());

                        System.out.println(
                                "Examination added and saved successfully.");
                    }

                    // Mark a task as completed using its serial number.
                    case "4" -> {
                        AcademicTask task =
                                selectTask(manager, scanner);

                        if (task != null) {

                            if (task.isCompleted()) {
                                System.out.println(
                                        "This task is already completed.");

                            } else if (manager.completeTask(task.getId())) {

                                storage.saveTasks(
                                        manager.getAllTasks());

                                System.out.println(
                                        "Task marked as completed and saved.");

                            } else {
                                System.out.println(
                                        "Could not complete the task.");
                            }
                        }
                    }

                    // Delete a task using its serial number.
                    case "5" -> {
                        AcademicTask task =
                                selectTask(manager, scanner);

                        if (task != null) {

                            System.out.print(
                                    "Delete \"" + task.getTitle()
                                    + "\"? (yes/no): ");

                            String confirmation =
                                    scanner.nextLine().trim();

                            if (confirmation.equalsIgnoreCase("yes")) {

                                if (manager.deleteTask(task.getId())) {

                                    storage.saveTasks(
                                            manager.getAllTasks());

                                    System.out.println(
                                            "Task deleted and changes saved.");

                                } else {
                                    System.out.println(
                                            "Could not delete the task.");
                                }

                            } else {
                                System.out.println(
                                        "Deletion cancelled.");
                            }
                        }
                    }

                    // Display task statistics.
                    case "6" -> {
                        System.out.println(
                                "\n===== TASK STATISTICS =====");

                        System.out.println(
                                "Total tasks: "
                                + manager.getAllTasks().size());

                        System.out.println(
                                "Completed tasks: "
                                + manager.getCompletedTaskCount());

                        System.out.println(
                                "Pending tasks: "
                                + manager.getPendingTaskCount());

                        System.out.println(
                                "Overdue tasks: "
                                + manager.getOverdueTasks().size());
                    }

                    // Exit the application.
                    case "7" -> {
                        running = false;
                        System.out.println(
                                "Thank you for using StudyTrack!");
                    }

                    default -> System.out.println(
                            "Invalid option. Please choose 1-7.");
                }

            } catch (IOException exception) {
                System.out.println(
                        "File operation failed: "
                        + exception.getMessage());

            } catch (IllegalArgumentException exception) {
                System.out.println(
                        "Invalid input: "
                        + exception.getMessage());
            }
        }

        scanner.close();
    }

    // Display tasks and let the user select one by serial number.
    private static AcademicTask selectTask(
            TaskManager manager,
            Scanner scanner) {

        List<AcademicTask> tasks = manager.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return null;
        }

        System.out.println("\n===== SELECT A TASK =====");

        for (int i = 0; i < tasks.size(); i++) {
            AcademicTask task = tasks.get(i);

            System.out.println(
                    (i + 1) + ". " + task.getSummary());
        }

        System.out.print("Enter task number: ");
        String input = scanner.nextLine().trim();

        try {
            int choice = Integer.parseInt(input);

            if (choice < 1 || choice > tasks.size()) {
                System.out.println(
                        "Invalid task number. Please try again.");
                return null;
            }

            return tasks.get(choice - 1);

        } catch (NumberFormatException exception) {
            System.out.println(
                    "Invalid input. Please enter a number.");
            return null;
        }
    }
}