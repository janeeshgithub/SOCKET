import java.util.Random;

public class Receiver extends Thread {
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
