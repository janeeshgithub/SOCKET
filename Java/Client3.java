import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client3 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 65432;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("python programming");
            System.out.println("Client 3: String sent to server: python programming");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
