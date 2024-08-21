package Java;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.CRC32;

public class CRCServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started and listening on port 12345");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read the data from the client
            String data = in.readLine();
            long clientChecksum = Long.parseLong(in.readLine());

            // Compute the CRC-32 checksum for the received data
            CRC32 crc32 = new CRC32();
            crc32.update(data.getBytes());
            long serverChecksum = crc32.getValue();

            // Compare checksums
            if (clientChecksum == serverChecksum) {
                out.println("Checksum valid");
            } else {
                out.println("Checksum invalid");
            }

            // Close connections
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
