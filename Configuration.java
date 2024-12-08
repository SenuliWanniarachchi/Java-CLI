import com.google.gson.Gson;
import java.io.*;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Configure the system and save settings
    public void configureSystem() {

        // Configuration input
        System.out.println("Welcome to the Ticketing System Configuration!");

        Scanner scanner = new Scanner(System.in);

        String saveLog = getValidYesNoInput(scanner, "Would you like to save the tickets log before starting configuration? (y/n): ");
        if ("y".equalsIgnoreCase(saveLog)) {
            try (FileWriter writer = new FileWriter("tickets_log.json")) {
                writer.write("{ \"message\": \"Tickets log saved successfully.\" }");
                System.out.println("Log saved as tickets_log.json.");
            } catch (IOException e) {
                System.out.println("Failed to save log: " + e.getMessage());
            }
        }

        // If user selects "no", load configuration from file
        if ("n".equalsIgnoreCase(saveLog)) {
            loadConfigurationFromJSON();
        }

        // Prompt for the configuration settings
        totalTickets = getValidInput(scanner, "Enter total number of tickets: ");
        ticketReleaseRate = getValidInput(scanner, "Enter ticket release rate: ");
        customerRetrievalRate = getValidInput(scanner, "Enter customer retrieval rate: ");
        maxTicketCapacity = getValidInput(scanner, "Enter max ticket capacity: ");

        // Save configuration to JSON
        saveConfigurationToJSON();

        System.out.println("Configuration complete!");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
    }

    private void saveConfigurationToJSON() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("configuration.json")) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved to configuration.json.");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    private void loadConfigurationFromJSON() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("configuration.json")) {
            Configuration config = gson.fromJson(reader, Configuration.class);
            if (config != null) {
                this.totalTickets = config.totalTickets;
                this.ticketReleaseRate = config.ticketReleaseRate;
                this.customerRetrievalRate = config.customerRetrievalRate;
                this.maxTicketCapacity = config.maxTicketCapacity;
                System.out.println("Loaded configuration from configuration.json.");
                System.out.println("Total Tickets: " + totalTickets);
                System.out.println("Ticket Release Rate: " + ticketReleaseRate);
                System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
                System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
            } else {
                System.out.println("No configuration found in the file.");
            }
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    private int getValidInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) break;
                else System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    private static String getValidYesNoInput(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase();
            if ("y".equals(input) || "n".equals(input)) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
        return input;
    }

    // Getter methods
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}
