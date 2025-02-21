package Ozymandias.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import Ozymandias.Tasks.Deadlines;
import Ozymandias.Tasks.Events;
import Ozymandias.Tasks.Task;
import Ozymandias.Tasks.ToDos;
import Ozymandias.ui.TaskList;

public class Storage {
    /**
     * prints out the task details in the txt file
     *
     * @return The tasks in the txt file
     */
    public static TaskList load() {
        TaskList taskList = new TaskList();
        File f = new File("./data/ozymandias.txt");
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                Task parsedTask = parseLine(line);
                if (parsedTask != null) {
                    taskList.addTask(parsedTask);
                }
            }
        } catch (FileNotFoundException e) {
            return new TaskList();
        }
        return taskList;
    }

    private static Task parseLine(String line) {
        int bracketIndex = line.indexOf('[');
        assert bracketIndex != -1: "wrong format for storage";

        String trimmed = line.substring(bracketIndex).trim();
        assert trimmed.length() > 3: "wrong format for storage";
        char type = trimmed.charAt(1); // T, D, E

        int secondBracketStart = trimmed.indexOf('[', 3);
        if (secondBracketStart == -1) { return null; }
        int secondBracketEnd = trimmed.indexOf(']', secondBracketStart);
        if (secondBracketEnd == -1) { return null; }
        String doneMark = trimmed.substring(secondBracketStart + 1, secondBracketEnd).trim();
        boolean isDone = doneMark.equals("X");

        String descriptionPart = trimmed.substring(secondBracketEnd + 1).trim();
        return switch (type) {
            case 'T' -> buildToDo(descriptionPart, isDone);
            case 'D' -> buildDeadline(descriptionPart, isDone);
            case 'E' -> buildEvent(descriptionPart, isDone);
            default -> null;
        };
    }

    private static Task buildToDo(String descriptionPart, boolean isDone) {
        Task t = new ToDos(descriptionPart);
        if (isDone) {
            t.toggleIsDone();
        }
        return t;
    }

    private static Task buildDeadline(String descriptionPart, boolean isDone) {
        String desc = descriptionPart;
        String dueDate = "";
        int parenStart = descriptionPart.indexOf("(");
        if (parenStart != -1) {
            desc = descriptionPart.substring(0, parenStart).trim();
            String parenthetical = descriptionPart.substring(parenStart).trim();
            if (parenthetical.startsWith("(") && parenthetical.endsWith(")")) {
                parenthetical = parenthetical.substring(1, parenthetical.length() - 1).trim();
            }
            if (parenthetical.startsWith("by:")) {
                dueDate = parenthetical.substring(3).trim(); // => "2025-01-28"
            }
        }

        Task t = new Deadlines(desc, dueDate);
        if (isDone) {
            t.toggleIsDone();
        }

        return t;
    }

    private static Task buildEvent(String descriptionPart, boolean isDone) {
        String desc = descriptionPart;
        String fromDate = "";
        String toDate = "";
        int parenStart = descriptionPart.indexOf("(");

        if (parenStart != -1) {
            desc = descriptionPart.substring(0, parenStart).trim();
            String parenthetical = descriptionPart.substring(parenStart).trim();
            if (parenthetical.startsWith("(") && parenthetical.endsWith(")")) {
                parenthetical = parenthetical.substring(1, parenthetical.length() - 1).trim();
            }
            if (parenthetical.contains("from:")) {
                parenthetical = parenthetical.replaceFirst("from:", "").trim();
                if (parenthetical.contains("to:")) {
                    String[] fromTo = parenthetical.split("to:");
                    if (fromTo.length == 2) {
                        fromDate = fromTo[0].trim();
                        toDate = fromTo[1].trim();
                    }
                }
            }
        }

        Task t = new Events(desc, fromDate, toDate);
        if (isDone) {
            t.toggleIsDone();
        }
        return t;
    }


    /**
     * Saves the tasklist into a txt file
     *
     * @param taskList Tasklist to be saved
     */
    public static void save(TaskList taskList) {
        // Always use this file path.
        String filePath = "./data/ozymandias.txt";

        try {
            File f = new File(filePath);
            // Create parent directories if they don't exist
            f.getParentFile().mkdirs();

            // Use FileWriter in overwrite mode
            FileWriter fw = new FileWriter(f, false);

            for (Map.Entry<Integer, Task> entry : taskList.getAllTasks().entrySet()) {
                Integer id = entry.getKey();
                Task t = entry.getValue();
                // Write the task ID, type, status, and toString() content
                fw.write(id + ". " + t.getTaskType() + "[" + t.getStatusIcon() + "] "
                        + t + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException while saving tasks: " + e.getMessage());
        }
    }
}
