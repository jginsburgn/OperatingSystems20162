import java.io.PrintWriter;
import java.io.IOException;

public class JGFileWriter {
    private String path = System.getProperty("user.dir");
    private PrintWriter pw;
    JGFileWriter(String fileName) {
        path += "/" + fileName;
        try {
            pw = new PrintWriter(path);
        }
        catch(IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void printAndClose(String text) {
        pw.print(text);
        pw.close();
    }

}
