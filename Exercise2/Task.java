import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private String description;
    private String startTime; // Format: HH:mm
    private String endTime;   // Format: HH:mm
    private String priority;
    private boolean isCompleted;

    public Task(String description, String startTime, String endTime, String priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.isCompleted = false; // Task starts as incomplete
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getPriority() {
        return priority;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        String status = isCompleted ? "Completed" : "Pending";
        return startTime + " - " + endTime + ": " + description + " [" + priority + "] - " + status;
    }
}
