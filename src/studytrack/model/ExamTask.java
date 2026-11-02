package studytrack.model;

import java.time.LocalDate;

public class ExamTask extends AcademicTask {

    private String subject;
    private String examType;

    public ExamTask(String title, String subject, String examType,
                    LocalDate dueDate) {

        super(title, dueDate);
        setSubject(subject);
        setExamType(examType);
    }
    public ExamTask(
        String id,
        String title,
        String subject,
        String examType,
        LocalDate dueDate) {

    super(id, title, dueDate);
    setSubject(subject);
    setExamType(examType);
}

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException(
                    "Subject cannot be empty.");
        }

        this.subject = subject.trim();
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        if (examType == null || examType.isBlank()) {
            throw new IllegalArgumentException(
                    "Exam type cannot be empty.");
        }

        this.examType = examType.trim();
    }

    @Override
    public String getTaskType() {
        return "Examination";
    }

    @Override
    public String getSummary() {
        return super.getSummary()
                + " | Subject: " + subject
                + " | Exam Type: " + examType;
    }
}