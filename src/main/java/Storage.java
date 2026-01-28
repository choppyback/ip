import java.io.*;
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
}
