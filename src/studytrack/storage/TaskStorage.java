package studytrack.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import studytrack.model.AcademicTask;
import studytrack.model.AssignmentTask;
import studytrack.model.ExamTask;

public class TaskStorage {

    private final Path filePath;

    public TaskStorage(String fileName) {
        this.filePath = Path.of(fileName);
    }

    // Convert text into a safe format for storing in a delimited file.
    private String encode(String value) {
        return Base64.getEncoder().encodeToString(
                value.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String value) {
        return new String(
                Base64.getDecoder().decode(value),
                StandardCharsets.UTF_8);
    }

    // Save all tasks to the file.
    public void saveTasks(List<AcademicTask> tasks) throws IOException {

        Path parent = filePath.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        List<String> lines = new ArrayList<>();

        for (AcademicTask task : tasks) {

            String line;

            if (task instanceof AssignmentTask assignment) {

                line = "ASSIGNMENT"
                        + "|" + task.getId()
                        + "|" + task.getDueDate()
                        + "|" + task.isCompleted()
                        + "|" + encode(task.getTitle())
                        + "|" + encode(assignment.getSubject());

            } else if (task instanceof ExamTask exam) {

                line = "EXAM"
                        + "|" + task.getId()
                        + "|" + task.getDueDate()
                        + "|" + task.isCompleted()
                        + "|" + encode(task.getTitle())
                        + "|" + encode(exam.getSubject())
                        + "|" + encode(exam.getExamType());

            } else {
                continue;
            }

            lines.add(line);
        }

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    // Load tasks from the file.
    public List<AcademicTask> loadTasks() throws IOException {

        List<AcademicTask> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(
                filePath, StandardCharsets.UTF_8);

        for (String line : lines) {

            if (line.isBlank()) {
                continue;
            }

            try {
                String[] parts = line.split("\\|", -1);

                String type = parts[0];
                String id = parts[1];
                LocalDate dueDate = LocalDate.parse(parts[2]);
                boolean completed = Boolean.parseBoolean(parts[3]);
                String title = decode(parts[4]);

                AcademicTask task;

                if (type.equals("ASSIGNMENT") && parts.length == 6) {

                    String subject = decode(parts[5]);

                    task = new AssignmentTask(
                        id, title, subject, dueDate);

                } else if (type.equals("EXAM") && parts.length == 7) {

                    String subject = decode(parts[5]);
                    String examType = decode(parts[6]);

                    task = new ExamTask(
                        id, title, subject, examType, dueDate);

                } else {
                    throw new IllegalArgumentException(
                        "Invalid task record.");
                    }

                if (completed) {
                    task.markCompleted();
                }  

                tasks.add(task);

            } catch (RuntimeException exception) {
                throw new IOException(
                        "Could not read task record: " + line,
                        exception);
            }
        }

        return tasks;
    }
}