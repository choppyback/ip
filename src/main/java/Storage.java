import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final String DIR = "data";
    private static final String FILE_PATH = "data/woody.txt";

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

    public static void save(List<Task> tasks) throws IOException {
        ensureFileExist();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        }
    }

    public static ArrayList<Task> load() throws IOException {
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
