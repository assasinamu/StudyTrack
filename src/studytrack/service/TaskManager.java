package studytrack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import studytrack.model.AcademicTask;

public class TaskManager {

    private final List<AcademicTask> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    // Add a task
    public void addTask(AcademicTask task) {
        if (task == null) {
            throw new IllegalArgumentException(
                    "Task cannot be null.");
        }

        tasks.add(task);
    }

    // Return all tasks
    public List<AcademicTask> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // Find a task using its ID
    public Optional<AcademicTask> findTaskById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }

        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    // Mark a task as completed
    public boolean completeTask(String id) {
        Optional<AcademicTask> task = findTaskById(id);

        if (task.isEmpty()) {
            return false;
        }

        task.get().markCompleted();
        return true;
    }

    // Delete a task
    public boolean deleteTask(String id) {
        Optional<AcademicTask> task = findTaskById(id);

        if (task.isEmpty()) {
            return false;
        }

        tasks.remove(task.get());
        return true;
    }

    // Return only overdue tasks
    public List<AcademicTask> getOverdueTasks() {
        List<AcademicTask> overdueTasks = new ArrayList<>();

        for (AcademicTask task : tasks) {
            if (task.isOverdue()) {
                overdueTasks.add(task);
            }
        }

        return overdueTasks;
    }

    // Count completed tasks
    public long getCompletedTaskCount() {
        return tasks.stream()
                .filter(AcademicTask::isCompleted)
                .count();
    }

    // Count pending tasks
    public long getPendingTaskCount() {
        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .count();
    }
    // Load tasks from storage into the manager.
    public void loadTasks(List<AcademicTask> loadedTasks) {
        tasks.clear();
        tasks.addAll(loadedTasks);
    }
}