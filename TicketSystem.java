import java.util.Scanner;

public class TicketSystem {
    private Configuration configuration;
    private TicketPool ticketPool;
    private Vendor vendor;
    private Customer customer;
    private Thread vendorThread;
    private Thread customerThread;

    public static void main(String[] args) {
        TicketSystem ticketSystem = new TicketSystem();
        ticketSystem.initialize();
        ticketSystem.run();
    }

    public void initialize() {
        // Step 1: Setup Configuration
        configuration = new Configuration();
        configuration.configureSystem();

        // Step 2: Initialize Ticket Pool
        ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), new Logger("ticket_transactions.log"));

        // Step 3: Initialize Vendor and Customer
        vendor = new Vendor(ticketPool, configuration.getTicketReleaseRate());
        customer = new Customer(ticketPool, configuration.getCustomerRetrievalRate());

        System.out.println("Ticket system initialized successfully!");
    }

    public void run() {
        // Step 4: Start Command Loop
        System.out.println("Starting ticketing system...");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Commands: start, stop, status, exit");
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "start":
                    startOperation();
                    break;
                case "stop":
                    stopOperation();
                    break;
                case "status":
                    displayStatus();
                    break;
                case "exit":
                    stopOperation();
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private void startOperation() {
        // Start Vendor Thread
        if (vendorThread == null || !vendorThread.isAlive()) {
            vendorThread = new Thread(vendor, "Vendor");
            vendorThread.start();
            System.out.println("Vendor thread started.");
        }

        // Start Customer Thread
        if (customerThread == null || !customerThread.isAlive()) {
            customerThread = new Thread(customer, "Customer");
            customerThread.start();
            System.out.println("Customer thread started.");
        }
    }

    private void stopOperation() {
        // Interrupt Vendor Thread
        if (vendorThread != null && vendorThread.isAlive()) {
            vendorThread.interrupt();
            System.out.println("Vendor thread stopped.");
        }

        // Interrupt Customer Thread
        if (customerThread != null && customerThread.isAlive()) {
            customerThread.interrupt();
            System.out.println("Customer thread stopped.");
        }
    }

    private void displayStatus() {
        System.out.println("Current ticket pool size: " + ticketPool.getCurrentSize());
        System.out.println("Max ticket capacity: " + ticketPool.getMaxCapacity());
    }
}

