package Ozymandias.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    public Scanner s;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void save(TaskList taskList) {
        try {
            File f = new File(filePath);
            f.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(f);

            for (Map.Entry<Integer, Task> entry : taskList.getAllTasks().entrySet()) {
                Integer id = entry.getKey();
                Task t = entry.getValue();
                fw.write(id + ". " + t.getTaskType() + "[" + t.getStatusIcon() + "] " + t + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
