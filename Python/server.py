import socket
import threading

def process_to_upper_case(s):
    return s.upper()

def handle_client(client_socket):
    try:
        data = client_socket.recv(1024).decode('utf-8')
        if data:
            print("Received from client:", data)
            upper_case_str = process_to_upper_case(data)
            print("Upper Case:", upper_case_str)
    except Exception as e:
        print(f"Error: {e}")
    finally:
        client_socket.close()

def start_server(host='localhost', port=65432):
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((host, port))
    server_socket.listen()

    print(f"Server listening on {host}:{port}")

    while True:
        client_socket, addr = server_socket.accept()
        print(f"Accepted connection from {addr}")
        client_handler = threading.Thread(target=handle_client, args=(client_socket,))
        client_handler.start()

if __name__ == "__main__":
    start_server()
