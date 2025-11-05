/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    PCSales class - Console based version of the program, serving as an alternative to the GUI
 */

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class PCSales
{
    private static ArrayList<Customer> customers;
    private static ArrayList<Order> orders;
    private static ArrayList<PCPart> availableParts;

    private static Scanner input;

    private static Order customerOrder;

    private enum MenuState
    {
        MainMenu,
        DisplayParts,
        DisplayTotalSales,
        ReviewOrder,
    }

    public static void main(String[] args)
    {
        // Init variables
        input = new Scanner(System.in);
        customers = new ArrayList<>();
        orders = new ArrayList<>();
        availableParts = new ArrayList<>();

        // Prepare available PC parts
        prepareParts();

        boolean runProgram = true;
        while (runProgram)
        {
            // Force reset scanner (due to weird bug that causes it to break)
            input = new Scanner(System.in);

            // Get customer full name and email address
            System.out.println("Welcome to PC Part Sales.");
            System.out.print("Please enter your full name: ");
            String fullName = input.nextLine();

            int customerID = 0;
            Customer currentCustomer = null;

            // Check if customer already exists
            boolean foundExistingCustomer = false;
            if (!customers.isEmpty()) {
                for (Customer customer : customers) {
                    if (customer.GetName().equalsIgnoreCase(fullName))
                    {
                        foundExistingCustomer = true;
                        customerID = customer.GetID();
                        currentCustomer = customer;
                        System.out.println("Found existing customer " + fullName + ", welcome back!");
                        input.nextLine();
                        break;
                    }
                }
            }

            if (!foundExistingCustomer)
            {
                System.out.print("Please enter your email address: ");
                String emailAddress = input.nextLine();

                if (!emailAddress.contains("@"))
                {
                    System.out.println("ERROR: Not a valid email address.");
                    continue;
                }

                // Create a new customer and add them to the registry
                customerID = customers.size() + 1;
                currentCustomer = new Customer(customerID, fullName, emailAddress);
                customers.add(currentCustomer);
            }

            // Create a new order for the customer
            customerOrder = new Order(orders.size() + 1, customerID);

            // Ensure correct default menu state
            MenuState menuState = MenuState.MainMenu;

            // Display menu (categories and options)
            boolean selectingParts = true;
            PartCategory selectedCategory = PartCategory.CPU;
            while (selectingParts)
            {
                switch (menuState)
                {
                    case MainMenu:
                        showMainMenu(fullName);

                        int selectedNumber;
                        try
                        {
                            selectedNumber = Integer.parseInt(input.nextLine());
                        }
                        catch (RuntimeException e)
                        {
                            System.out.println("ERROR: Category or option must be an integer number");
                            break;
                        }

                        switch (selectedNumber)
                        {
                            case 1:
                                // Show CPU products
                                selectedCategory = PartCategory.CPU;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 2:
                                // Show Motherboard products
                                selectedCategory = PartCategory.Motherboard;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 3:
                                // Show RAM products
                                selectedCategory = PartCategory.RAM;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 4:
                                // Show Storage products
                                selectedCategory = PartCategory.StorageDrive;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 5:
                                // Show Case products
                                selectedCategory = PartCategory.Case;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 6:
                                // Show PSU products
                                selectedCategory = PartCategory.PSU;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 7:
                                // Show GPU products
                                selectedCategory = PartCategory.GPU;
                                menuState = MenuState.DisplayParts;
                                break;
                            case 66:
                                // Review order
                                // Check if order is valid (minimum 1 part)
                                if (customerOrder.GetParts().isEmpty())
                                {
                                    System.out.println("ERROR: You must select at least one part");
                                    break;
                                }

                                menuState = MenuState.ReviewOrder;
                                break;
                            case 100:
                                // Reset order parts
                                customerOrder.GetParts().clear();
                                break;
                            case 101:
                                // Cancel order
                                selectingParts = false;
                                break;
                            case 999:
                                // Display total sales
                                menuState = MenuState.DisplayTotalSales;
                                break;
                            default:
                                System.out.println("ERROR: Invalid selection. Please try again.");
                                break;
                        }
                        break;
                    case DisplayParts:
                        showPartsForCategory(selectedCategory);
                        menuState = MenuState.MainMenu;
                        break;
                    case DisplayTotalSales:
                        // Remove the last customer since they can't make an order beyond this point anyway
                        customers.remove(customers.size() - 1);

                        // Display total sales and exit
                        displayTotalSales();
                        selectingParts = false;
                        runProgram = false;
                        break;
                    case ReviewOrder:
                        // Confirm order details and exit
                        if (reviewOrder(customerOrder, currentCustomer))
                            selectingParts = false;

                        menuState = MenuState.MainMenu;
                        break;
                }
            }
        }

        System.out.println("Program will exit now. Goodbye!");
        input.nextLine();
    }

    private static void prepareParts()
    {
        int nextPartNumber = 1;
        
        // ----------------
        // CPUS
        // ----------------
        CPU cpu1 = new CPU(
                nextPartNumber++,
                "AMD",
                "Ryzen 7 5700X",
                264.0f,
                "x86",
                8,
                3.4f
        );
        availableParts.add(cpu1);

        CPU cpu2 = new CPU(
                nextPartNumber++,
                "AMD",
                "Ryzen 9 9950X3D",
                1149.0f,
                "x86",
                16,
                4.3f
        );
        availableParts.add(cpu2);

        CPU cpu3 = new CPU(
                nextPartNumber++,
                "AMD",
                "Ryzen 5 7600",
                309.0f,
                "x86",
                6,
                3.8f
        );
        availableParts.add(cpu3);

        // ----------------
        // Motherboards
        // ----------------
        Motherboard motherboard1 = new Motherboard(
                nextPartNumber++,
                "MSI",
                "MAG B650 TOMAHAWK WIFI",
                299.0f,
                "AM5",
                4
        );
        availableParts.add(motherboard1);

        Motherboard motherboard2 = new Motherboard(
                nextPartNumber++,
                "ASUS",
                "ROG MAXIMUS Z790 DARK HERO",
                999.0f,
                "LGA1700",
                4
        );
        availableParts.add(motherboard2);

        Motherboard motherboard3 = new Motherboard(
                nextPartNumber++,
                "MSI",
                "B450M-A PRO MAX II",
                87.0f,
                "AM4",
                2
        );
        availableParts.add(motherboard3);

        // ----------------
        // RAM
        // ----------------
        RAM ram1 = new RAM(
                nextPartNumber++,
                "Corsair",
                "Vengeance RGB 32 GB",
                164.0f,
                32,
                6000
        );
        availableParts.add(ram1);

        RAM ram2 = new RAM(
                nextPartNumber++,
                "Corsair",
                "Vengeance 64 GB",
                259.0f,
                64,
                5200
        );
        availableParts.add(ram2);

        RAM ram3 = new RAM(
                nextPartNumber++,
                "Corsair",
                "Vengeance LPX 16 GB",
                79.0f,
                16,
                3200
        );
        availableParts.add(ram3);

        // ----------------
        // Storage Drives
        // ----------------
        StorageDrive storageDrive1 = new StorageDrive(
                nextPartNumber++,
                "Crucial",
                "P3 Plus",
                92.0f,
                "NVME SSD",
                1000
        );
        availableParts.add(storageDrive1);

        StorageDrive storageDrive2 = new StorageDrive(
                nextPartNumber++,
                "Western Digital",
                "WD_BLACK SN8100",
                759.0f,
                "NVME SSD",
                4000
        );
        availableParts.add(storageDrive2);

        StorageDrive storageDrive3 = new StorageDrive(
                nextPartNumber++,
                "Samsung",
                "870 Evo",
                69.0f,
                "SATA SSD",
                480
        );
        availableParts.add(storageDrive3);

        // ----------------
        // Cases
        // ----------------
        Case case1 = new Case(
                nextPartNumber++,
                "Corsair",
                "4000D Airflow",
                159.0f,
                "ATX Mid Tower",
                false
        );
        availableParts.add(case1);

        Case case2 = new Case(
                nextPartNumber++,
                "HYTE",
                "Y70 Touch Infinite",
                639.0f,
                "ATX Mid Tower",
                true
        );
        availableParts.add(case2);

        Case case3 = new Case(
                nextPartNumber++,
                "be quiet!",
                "Light Base 900 DX",
                171.0f,
                "ATX Full Tower",
                true
        );
        availableParts.add(case3);

        // ----------------
        // Power Supplies
        // ----------------
        PSU psu1 = new PSU(
                nextPartNumber++,
                "MSI",
                "MAG A750GL PCIE5",
                127.0f,
                750,
                "80+ Gold"
        );
        availableParts.add(psu1);

        PSU psu2 = new PSU(
                nextPartNumber++,
                "ASUS",
                "ROG THOR 1600T Gaming",
                899.0f,
                1600,
                "80+ Titanium"
        );
        availableParts.add(psu2);

        PSU psu3 = new PSU(
                nextPartNumber++,
                "Corsair",
                "CX (2023)",
                84.0f,
                550,
                "80+ Bronze"
        );
        availableParts.add(psu3);

        // ----------------
        // Graphics Cards
        // ----------------
        GPU gpu1 = new GPU(
                nextPartNumber++,
                "MSI",
                "GeForce RTX 3060 Ventus 2X 12GB",
                419.0f,
                12000,
                3584
        );
        availableParts.add(gpu1);

        GPU gpu2 = new GPU(
                nextPartNumber++,
                "ASUS",
                "ROG Astral OC GeForce RTX 5090",
                5295.0f,
                32000,
                21760
        );
        availableParts.add(gpu2);

        GPU gpu3 = new GPU(
                nextPartNumber++,
                "ASUS",
                "Prime OC Radeon RX 9070 XT",
                1199.0f,
                16000,
                4096
        );
        availableParts.add(gpu3);
    }

    private static void showMainMenu(String customerName)
    {
        System.out.println();
        System.out.printf("Welcome %s, please choose from our variety of pc parts for your order.\n", customerName);
        System.out.printf("You currently have %d parts selected.\n", customerOrder.GetParts().size());
        System.out.println("--- CATEGORIES ---");
        System.out.println("[1] CPU");
        System.out.println("[2] Motherboards");
        System.out.println("[3] RAM");
        System.out.println("[4] Storage Drives");
        System.out.println("[5] Cases");
        System.out.println("[6] Power Supplies");
        System.out.println("[7] Graphics Cards");
        System.out.println("--- OPTIONS ---");
        System.out.println("[66] Review order and confirm");
        System.out.println("[100] Reset selected parts");
        System.out.println("[101] Cancel order");
        System.out.println("[999] Exit program and display total sales");
        System.out.println();
        System.out.print("Enter number to select: ");
    }

    private static void showPartsForCategory(PartCategory category)
    {
        // List available parts for selected category
        System.out.printf("--- %s PRODUCTS ---\n", category.toString().toUpperCase());
        for (PCPart part : availableParts)
        {
            if (part.GetCategory() != category)
                continue;

            System.out.printf("- [%d] %s %s ($%.2f):\n", part.partNumber, part.brandName, part.productName, part.price);
            System.out.print(part.GetDetails());
        }
        System.out.println();

        // Loop until user enters a valid option
        while (true)
        {
            System.out.print("Enter a product ID to select, or leave empty to go back: ");
            String answer = input.nextLine();

            // Check if input is left empty, if so, return to main menu
            if (answer.isEmpty())
            {
                System.out.println();
                break;
            }

            // Check if input is an integer
            int selectedProductID;
            try
            {
                selectedProductID = Integer.parseInt(answer);
            }
            catch (RuntimeException e)
            {
                System.out.println("ERROR: Product ID must be an integer number");
                continue;
            }

            // Check the product registry for the inputted ID and retrieve associated PCPart
            boolean foundProduct = false;
            PCPart selectedPart = null;
            for (PCPart part : availableParts)
            {
                if (selectedProductID == part.GetPartNumber())
                {
                    foundProduct = true;
                    selectedPart = part;
                    break;
                }
            }

            if (!foundProduct)
            {
                System.out.println("ERROR: Product ID was not found");
                continue;
            }

            // Check if the found product is of the correct category (for user QoL)
            if (selectedPart.category != category)
            {
                System.out.print("This product ID does not match the current category you are viewing. Add product anyway? (Y/N): ");
                if (!input.nextLine().equalsIgnoreCase("y"))
                    continue;
            }

            // Add part to order
            customerOrder.AddPart(selectedPart);
            break;
        }
    }

    private static boolean reviewOrder(Order order, Customer customer)
    {
        // Set order date and time to current time
        // Calculate price for order
        // DISPLAY:
        // Order #{orderNm} for {custName} (Customer ID: {custID}) at {orderTime} on {orderDate}
        // {Order parts}
        // Type 'confirm' to confirm order

        // Update order to current date and time
        LocalDateTime currentDateTime = java.time.LocalDateTime.now();
        order.SetOrderDate(currentDateTime.toLocalDate().toString());
        order.SetOrderTime(currentDateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString());

        System.out.println("--------- ORDER REVIEW ---------");
        System.out.printf("Order #%d for %s (Customer ID: %d) at %s on %s\n", order.GetOrderNumber(), customer.GetName(), customer.GetID(), order.GetOrderTime(), order.GetOrderDate());
        System.out.println("Ordered parts list:");

        // Display parts and calculate price simultaneously
        double totalPrice = 0.0f;
        for (PCPart part : order.GetParts())
        {
            System.out.printf("- 1 x %s %s - $%.2f\n", part.brandName, part.productName, part.price);
            totalPrice += part.GetPrice();
        }

        System.out.printf("------ Total Price: $%.2f ------\n", totalPrice);
        System.out.print("Type 'confirm' to place order or leave blank to cancel and return to menu: ");

        Scanner input = new Scanner(System.in);
        String confirmation = input.nextLine();

        if (confirmation.equalsIgnoreCase("confirm"))
        {
            System.out.print("Order placed. Thank you for using PC Part Sales.");
            input.nextLine();

            System.out.println();

            // Add order to past orders
            orders.add(order);

            return true;
        }

        return false;
    }

    private static void displayTotalSales()
    {
        double totalSales = 0.0f;
        for (Order order : orders)
        {
            for (PCPart part : order.GetParts())
                totalSales += part.GetPrice();
        }

        System.out.printf("------ Total sales across %d orders for %d customers: $%.2f ------\n", orders.size(), customers.size(), totalSales);
    }
}