package Java;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.zip.CRC32;

public class CRCClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Connected to server");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = "Hello, Server!";

            // Compute the CRC-32 checksum for the data
            CRC32 crc32 = new CRC32();
            crc32.update(data.getBytes());
            long checksum = crc32.getValue();

            // Send data and checksum to the server
            out.println(data);
            out.println(checksum);

            // Read server response
            String response = in.readLine();
            System.out.println("Server response: " + response);

            // Close connections
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
