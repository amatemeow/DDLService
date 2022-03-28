package uni.diploma.ddlservice.processing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileBuilder {
    private static final String DIRECTORY = System.getProperty("user.dir") + "/userdata/files";
    private final File file;

    public FileBuilder(String DDL, String id) throws IOException {
        File directory = new File(DIRECTORY);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        file = new File(DIRECTORY + "/" + id + ".sql");

        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(DDL);
        writer.close();
    }

    public File getFile() {
        return file;
    }
}
