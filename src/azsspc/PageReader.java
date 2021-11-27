package azsspc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PageReader {
    public String getPage(String page_name) {
        try {
            return Files.readString(Path.of(getClass().getResource("/azsspc/pages/" + page_name + ".html").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
