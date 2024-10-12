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
