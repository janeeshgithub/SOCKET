import random
import time
import threading

class Sender:
    def __init__(self):
        self.packet_number = 0
        self.lock = threading.Lock()
        self.condition = threading.Condition()
        self.finished_sending = False  # Flag to indicate if sending is finished

    def send_packets(self):
        while self.packet_number < StopAndWaitProtocol.MAX_PACKETS:
            print(f"Sender: Sending packet {self.packet_number}")
            # Simulate packet transmission time
            time.sleep(1)  # 1 second delay for sending the packet
            # Wait for acknowledgment
            self.wait_for_ack()
            self.packet_number += 1
        
        # Set finished flag and notify the receiver if all packets are sent
        with self.condition:
            self.finished_sending = True
            self.condition.notify_all()  # Notify receiver that sending is finished

    def wait_for_ack(self):
        with self.condition:
            self.condition.wait()  # Wait for acknowledgment from the receiver

    def receive_ack(self):
        with self.condition:
            if self.packet_number < StopAndWaitProtocol.MAX_PACKETS:
                print(f"Sender: Acknowledgment received for packet {self.packet_number}")
                self.condition.notify()  # Notify the sender that ACK has been received

class Receiver(threading.Thread):
    def __init__(self, sender):
        super().__init__()
        self.sender = sender

    def run(self):
        while True:
            # Simulate processing time for receiving a packet
            time.sleep(random.randint(1, 3))  # 1-3 seconds delay
            if self.sender.packet_number < StopAndWaitProtocol.MAX_PACKETS:
                print(f"Receiver: Acknowledging packet {self.sender.packet_number}")
                self.sender.receive_ack()
            if self.sender.finished_sending:  # Check if sender has finished sending
                break

class StopAndWaitProtocol:
    MAX_PACKETS = 7

    @staticmethod
    def main():
        sender = Sender()
        receiver = Receiver(sender)

        # Start the receiver thread
        receiver.start()
        
        # Start sending packets
        sender.send_packets()

if __name__ == "__main__":
    StopAndWaitProtocol.main()
