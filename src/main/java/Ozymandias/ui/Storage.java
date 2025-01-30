package Ozymandias.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public TaskList load() {
        // TODO: scanner or smth idk see first
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
