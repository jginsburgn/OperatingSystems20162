import java.io.*;
import java.net.Socket;

public class JGNetClient {
    private Socket socket;
    private PrintWriter out;

    JGNetClient () throws IOException {
        socket = new Socket("127.0.0.1", 9090);
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void finish() {
        try {
            socket.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void write(String string) {
        out.println(string);
    }
}
