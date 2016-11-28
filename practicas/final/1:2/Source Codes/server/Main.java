import java.util.*;
import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("Listening on port 9090:");
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        String input = in.readLine();
                        if (input == null || input.equals(".")) {
                            break;
                        }
                        System.out.println(input);
                    }
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}
