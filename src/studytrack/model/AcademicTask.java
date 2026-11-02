package studytrack.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class AcademicTask {

    private final String id;
    private String title;
    private LocalDate dueDate;
    private boolean completed;

    public AcademicTask(String title, LocalDate dueDate) {
    this(UUID.randomUUID().toString(), title, dueDate);
    }

    public AcademicTask(String id, String title, LocalDate dueDate) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be empty.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }

    this.id = id;
    setTitle(title);
    setDueDate(dueDate);
    this.completed = false;
}

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        this.title = title.trim();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markPending() {
        this.completed = false;
    }

    public boolean isOverdue() {
        return !completed && dueDate.isBefore(LocalDate.now());
    }

    public abstract String getTaskType();

    public String getSummary() {
        return "[" + getTaskType() + "] " + title
                + " | Due: " + dueDate
                + " | Status: " + (completed ? "Completed" : "Pending");
    }
}