import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class AstronautScheduleOrganizer {
    private static final Logger logger = Logger.getLogger(AstronautScheduleOrganizer.class.getName());

    public static void main(String[] args) {
        ScheduleManager manager = ScheduleManager.getInstance();
        Scanner scanner = new Scanner(System.in);

        logger.info("Astronaut Schedule Organizer started.");
        System.out.println("Welcome to the Astronaut Daily Schedule Organizer!");
        System.out.println("Enter command (add/view/remove/edit/mark/priority):");
        String command = scanner.nextLine().toLowerCase();

        switch (command) {
            case "add":
                System.out.println("Enter task description, start time (HH:mm), end time (HH:mm), and priority:");
                String description = scanner.nextLine();
                String startTime = scanner.nextLine();
                String endTime = scanner.nextLine();
                String priority = scanner.nextLine();

                Task newTask = TaskFactory.createTask(description, startTime, endTime, priority);
                if (manager.addTask(newTask)) {
                    System.out.println("Task added successfully.");
                } else {
                    System.out.println("Failed to add task due to conflicts.");
                }
                break;

            case "view":
                List<Task> tasks = manager.getTasks();
                if (tasks.isEmpty()) {
                    System.out.println("No tasks scheduled for the day.");
                } else {
                    for (Task task : tasks) {
                        System.out.println(task);
                    }
                }
                break;

            case "remove":
                System.out.println("Enter task description to remove:");
                String removeDescription = scanner.nextLine();
                if (manager.removeTask(removeDescription)) {
                    System.out.println("Task removed successfully.");
                } else {
                    System.out.println("Error: Task not found.");
                }
                break;

            case "edit":
                System.out.println("Enter existing task description to edit:");
                String oldDescription = scanner.nextLine();
                System.out.println("Enter new task description, start time (HH:mm), end time (HH:mm), and priority:");
                String newDescription = scanner.nextLine();
                String newStartTime = scanner.nextLine();
                String newEndTime = scanner.nextLine();
                String newPriority = scanner.nextLine();

                Task updatedTask = TaskFactory.createTask(newDescription, newStartTime, newEndTime, newPriority);
                if (manager.removeTask(oldDescription) && manager.addTask(updatedTask)) {
                    System.out.println("Task updated successfully.");
                } else {
                    System.out.println("Failed to update task.");
                }
                break;

            case "mark":
                System.out.println("Enter task description to mark as completed:");
                String taskToMark = scanner.nextLine();
                for (Task task : manager.getTasks()) {
                    if (task.getDescription().equals(taskToMark)) {
                        task.markAsCompleted();
                        System.out.println("Task marked as completed.");
                    }
                }
                break;

            case "priority":
                System.out.println("Enter priority level to view tasks:");
                String priorityLevel = scanner.nextLine();
                manager.getTasks().stream()
                        .filter(task -> task.getPriority().equalsIgnoreCase(priorityLevel))
                        .forEach(System.out::println);
                break;

            default:
                logger.warning("Invalid command entered: " + command);
                System.out.println("Invalid command. Please try again.");
        }

        scanner.close();
        logger.info("Astronaut Schedule Organizer terminated.");
    }
}
