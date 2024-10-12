import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Sender {
    private int packetNumber = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean finishedSending = false; // Flag to indicate if sending is finished
    public static final int MAX_PACKETS = 5;

    public void sendPackets() {
        while (packetNumber < MAX_PACKETS) {
            System.out.println("Sender: Sending packet " + packetNumber);
            // Simulate packet transmission time
            try {
                Thread.sleep(1000); // 1 second delay for sending the packet
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Wait for acknowledgment
            waitForAck();
            packetNumber++;
        }

        // Set finished flag and notify the receiver if all packets are sent
        lock.lock();
        try {
            finishedSending = true;
            condition.signalAll(); // Notify receiver that sending is finished
        } finally {
            lock.unlock();
        }
    }

    public void waitForAck() {
        lock.lock();
        try {
            condition.await(); // Wait for acknowledgment from the receiver
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void receiveAck() {
        lock.lock();
        try {
            if (packetNumber < MAX_PACKETS) {
                System.out.println("Sender: Acknowledgment received for packet " + packetNumber);
                condition.signal(); // Notify the sender that ACK has been received
            }
        } finally {
            lock.unlock();
        }
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public boolean isFinishedSending() {
        return finishedSending;
    }
}

class Receiver extends Thread {
    private final Sender sender;
    private final Random random = new Random();

    public Receiver(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while (true) {
            // Simulate processing time for receiving a packet
            try {
                Thread.sleep(random.nextInt(2000) + 1000); // 1-3 seconds delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (sender.getPacketNumber() < Sender.MAX_PACKETS) {
                System.out.println("Receiver: Acknowledging packet " + sender.getPacketNumber());
                sender.receiveAck();
            }
            if (sender.isFinishedSending()) { // Check if sender has finished sending
                break;
            }
        }
    }
}

public class StopAndWaitProtocol {
    public static void main(String[] args) {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);

        // Start the receiver thread
        receiver.start();
        
        // Start sending packets
        sender.sendPackets();
    }
}
