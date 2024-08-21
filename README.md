# Socket Programming

This repository contains basic examples of socket programming in Java and Python. The project includes implementations for both server and client applications using TCP and UDP protocols.

## Project Structure

- **Java:**
  - `Server.java`: A basic TCP/UDP server implementation.
  - `Client.java`: A basic TCP/UDP client implementation.

- **Python:**
  - `server.py`: A basic TCP/UDP server implementation.
  - `client.py`: A basic TCP/UDP client implementation.

## Getting Started

### Prerequisites

- **Java:**
  - Java Development Kit (JDK) 8 or higher.

- **Python:**
  - Python 3.x.

### Installation

1. **Java:**

    - Compile the Java files:

        ```bash
        javac Server.java
        javac Client.java
        ```

2. **Python:**

    - Ensure you have Python 3.x installed. No compilation needed.

### Usage

#### Java TCP Communication

1. **Start the Server:**

    ```bash
    java Server
    ```

2. **Run the Client:**

    ```bash
    java Client
    ```

#### Python TCP Communication

1. **Start the Server:**

    ```bash
    python server.py
    ```

2. **Run the Client:**

    ```bash
    python client.py
    ```

## Code Examples

### Java Server

```java
// Server.java
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server is listening on port 8080");
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message = in.readLine();
        System.out.println("Message from client: " + message);
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
