package studytrack.model;

import java.time.LocalDate;

public class AssignmentTask extends AcademicTask {

    private String subject;

    public AssignmentTask(String title, String subject, LocalDate dueDate) {
        super(title, dueDate);
        setSubject(subject);
    }
    public AssignmentTask(
        String id,
        String title,
        String subject,
        LocalDate dueDate) {

    super(id, title, dueDate);
    setSubject(subject);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject cannot be empty.");
        }
        this.subject = subject.trim();
    }

    @Override
    public String getTaskType() {
        return "Assignment";
    }

    @Override
    public String getSummary() {
        return super.getSummary() + " | Subject: " + subject;
    }
}