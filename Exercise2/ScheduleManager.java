import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;
    private static final String FILE_NAME = "tasks.dat";
    private static final Logger logger = Logger.getLogger(ScheduleManager.class.getName());

    private ScheduleManager() {
        tasks = new ArrayList<>();
        loadTasksFromFile();
        logger.info("Schedule Manager initialized.");
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    public boolean addTask(Task task) {
        logger.log(Level.INFO, "Attempting to add task: {0}", task.getDescription());
        if (validateTask(task)) {
            tasks.add(task);
            Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
            saveTasksToFile(); // Save tasks after adding
            logger.info("Task added successfully: " + task.getDescription());
            return true;
        } else {
            logger.warning("Failed to add task due to time conflict: " + task.getDescription());
            return false;
        }
    }

    public boolean removeTask(String description) {
        logger.log(Level.INFO, "Attempting to remove task: {0}", description);
        boolean removed = tasks.removeIf(task -> task.getDescription().equals(description));
        if (removed) {
            saveTasksToFile(); // Save tasks after removal
            logger.info("Task removed successfully: " + description);
        } else {
            logger.warning("Failed to remove task. Task not found: " + description);
        }
        return removed;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    private boolean validateTask(Task newTask) {
        for (Task task : tasks) {
            if (isTimeConflict(newTask, task)) {
                logger.warning("Task conflict detected between: " + newTask.getDescription() + " and " + task.getDescription());
                System.out.println("Error: Task conflicts with existing task: " + task.getDescription());
                return false;
            }
        }
        return true;
    }

    private boolean isTimeConflict(Task newTask, Task existingTask) {
        return newTask.getStartTime().compareTo(existingTask.getEndTime()) < 0 &&
               newTask.getEndTime().compareTo(existingTask.getStartTime()) > 0;
    }

    // Save tasks to file
    private void saveTasksToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(tasks);
            logger.info("Tasks saved to file.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving tasks to file", e);
        }
    }

    // Load tasks from file
    private void loadTasksFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tasks = (List<Task>) in.readObject();
            logger.info("Tasks loaded from file.");
        } catch (FileNotFoundException e) {
            logger.warning("No existing task file found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error loading tasks from file", e);
        }
    }
}
