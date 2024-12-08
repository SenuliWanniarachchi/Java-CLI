import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Integer> tickets = new LinkedList<>();
    private final int maxCapacity;
    private final Logger logger;

    public TicketPool(int maxCapacity, Logger logger) {
        this.maxCapacity = maxCapacity;
        this.logger = logger;
    }

    public synchronized void addTickets(int quantity) {
        while (tickets.size() + quantity > maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted.");
            }
        }
        for (int i = 0; i < quantity; i++) {
            tickets.add(1); // Each ticket is represented as 1
        }
        String logMessage = Thread.currentThread().getName() + " added " + quantity + " tickets. Current pool size: " + tickets.size();
        logger.log(logMessage);
        System.out.println(logMessage);
        notifyAll();
    }

    public synchronized void retrieveTickets(int quantity) {
        while (tickets.size() < quantity) {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting for tickets.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted.");
            }
        }
        for (int i = 0; i < quantity; i++) {
            tickets.poll();
        }
        String logMessage = Thread.currentThread().getName() + " purchased " + quantity + " tickets. Current pool size: " + tickets.size();
        logger.log(logMessage);
        System.out.println(logMessage);
        notifyAll();
    }

    public int getCurrentSize() {
        return tickets.size();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
