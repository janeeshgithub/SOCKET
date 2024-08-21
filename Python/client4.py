import socket

def send_string_to_server(s, host='localhost', port=65432):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client_socket:
        client_socket.connect((host, port))
        client_socket.sendall(s.encode('utf-8'))
        print("Client 4: String sent to server:", s)

if __name__ == "__main__":
    send_string_to_server("multithreading example")
