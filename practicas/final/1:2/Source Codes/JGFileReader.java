import java.io.FileReader;
import java.io.IOException;

public class JGFileReader {
    private String file = "";
    private FileReader fr;
    JGFileReader(String newFile) {
        file = newFile;
        try {
            fr = new FileReader(file);
        }
        catch(IOException e) {
            System.out.print(e.getMessage());
        }
    }
    public String getFileContents() {
        String retVal = "";
        try {
            int readCharacter = fr.read();
            while (readCharacter != -1) {
                retVal += (char)readCharacter;
                readCharacter = fr.read();
            }
        }
        catch(IOException e) {
            retVal += e.getMessage();
        }
        return retVal;
    }
}
