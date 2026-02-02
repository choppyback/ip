package woody.storage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import woody.task.Task;

/**
 * Handles saving and loading of task data to and from local storage.
 */
public class Storage {
    private static final String DIR = "data";
    private static final String FILE_PATH = "data/woody.txt";

    /**
     * Ensures that the data directory and storage file exist.
     * Creates them if they do not already exist.
     *
     * @throws IOException If an I/O error occurs while creating the directory or file.
     */
    public static void ensureFileExist() throws IOException {
        File dir = new File(DIR);
        if(!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(FILE_PATH);
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Saves the given list of tasks to the storage file.
     *
     * @param tasks List of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            ensureFileExist();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Task task : tasks) {
                    writer.write(task.toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return List of tasks loaded from storage.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public ArrayList<Task> load() throws IOException {
        ensureFileExist();
        Path filePath = Paths.get(FILE_PATH);

        ArrayList<Task> list = new ArrayList<>();
    
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            Task task = Task.fileToTask(line);
            if (task != null) {
                list.add(task);
            }
        }
        return list;
    }

}
