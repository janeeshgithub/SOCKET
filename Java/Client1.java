import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client1 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 65432;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("hello world");
            System.out.println("Client 1: String sent to server: hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
