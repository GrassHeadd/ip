package Ozymandias.Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import Ozymandias.Tasks.Task;
import Ozymandias.ui.TaskList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * prints out the task details in the txt file
     *
     * @return The tasks in the txt file
     */
    public TaskList load() {
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f); // create a Scanner using the File as the source
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no file you moron!");
        }
        return new TaskList();
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
