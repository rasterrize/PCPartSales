import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class PCSales
{
    private static ArrayList<Customer> customers;
    private static ArrayList<Order> orders;
    private static ArrayList<PCPart> availableParts;

    private static boolean runProgram = true;
    private static MenuState menuState = MenuState.DisplayMainMenu;

    private static Scanner input;

    private static Order customerOrder;

    private enum MenuState
    {
        DisplayMainMenu,
        DisplayParts,
        DisplayTotalSales,
        ReviewOrder,
    }

    public static void main(String[] args)
    {
        // Init variables
        input = new Scanner(System.in);
        customers = new ArrayList<Customer>();
        orders = new ArrayList<Order>();
        availableParts = new ArrayList<PCPart>();

        // Prepare available PC parts
        prepareParts();

        while (runProgram)
        {
            // Force reset scanner (due to weird bug that causes it to break)
            input = new Scanner(System.in);

            // Get customer full name and email address
            System.out.println("Welcome to PC Part Sales.");
            System.out.print("Please enter your full name: ");
            String fullName = input.nextLine();

            // Check if customer already exists
            boolean foundExistingCustomer = false;
            if (!customers.isEmpty()) {
                for (Customer customer : customers) {
                    if (customer.GetName().equalsIgnoreCase(fullName))
                    {
                        foundExistingCustomer = true;
                        System.out.println("ERROR: This customer already exists, please use another name.");
                        break;
                    }
                }
            }

            if (foundExistingCustomer)
                continue;

            System.out.print("Please enter your email address: ");
            String emailAddress = input.nextLine();

            if (!emailAddress.contains("@"))
            {
                System.out.println("ERROR: Not a valid email address.");
                continue;
            }

            // Create a new customer and add them to the registry
            int newCustomerID = customers.size() + 1;
            Customer customer = new Customer(newCustomerID, fullName, emailAddress);
            customers.add(customer);

            // Create a new order for the customer
            customerOrder = new Order(orders.size() + 1, newCustomerID);

            System.out.println();
            System.out.printf("Welcome %s, please choose from our variety of pc parts for your order.\n", fullName);

            // Ensure correct default menu state
            menuState = MenuState.DisplayMainMenu;

            // Display menu (categories and options)
            boolean selectingParts = true;
            PartCategory selectedCategory = PartCategory.Invalid;
            while (selectingParts)
            {
                switch (menuState)
                {
                    case DisplayMainMenu:
                        showMainMenu();

                        int selectedNumber;
                        try
                        {
                            selectedNumber = Integer.parseInt(input.nextLine());
                        }
                        catch (RuntimeException e)
                        {
                            System.out.println("ERROR: Category or option must be an integer number");
                            continue;
                        }

                        switch (selectedNumber)
                        {
                            case 1:
                                // Show CPU products
                                selectedCategory = PartCategory.CPU;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 2:
                                // Show Motherboard products
                                selectedCategory = PartCategory.Motherboard;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 3:
                                // Show RAM products
                                selectedCategory = PartCategory.RAM;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 4:
                                // Show Storage products
                                selectedCategory = PartCategory.StorageDrive;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 5:
                                // Show Case products
                                selectedCategory = PartCategory.Case;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 6:
                                // Show PSU products
                                selectedCategory = PartCategory.PSU;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 7:
                                // Show GPU products
                                selectedCategory = PartCategory.GPU;
                                menuState = MenuState.DisplayParts;
                                continue;
                            case 66:
                                // Check if order is valid (minimum 1 part)
                                if (customerOrder.GetParts().isEmpty())
                                {
                                    System.out.println("ERROR: You must select at least one part");
                                    continue;
                                }

                                // Confirm order details and exit
                                if (confirmOrder(customerOrder, customer))
                                    selectingParts = false;

                                break;
                            case 100:
                                // Reset order parts
                                customerOrder.GetParts().clear();
                                continue; // NOTE: unsure of how this skips display parts menu state
                            case 101:
                                // Cancel order
                                selectingParts = false;
                                break;
                            case 999:
                                // Display total sales and exit
                                displayTotalSales();
                                selectingParts = false;
                                runProgram = false;
                                break;
                            default:
                                System.out.println("ERROR: Invalid selection. Please try again.");
                                continue;
                        }
                        break;
                    case DisplayParts:
                        showPartsForCategory(selectedCategory);
                        break;
                }
            }
        }

        System.out.println("Program will exit now. Goodbye!");
    }

    private static void prepareParts()
    {
        int nextPartNumber = 1;
        
        // ----------------
        // CPUS
        // ----------------
        CPU cpu1 = new CPU("x86", 8, 3.4f);
        cpu1.SetPartNumber(nextPartNumber++);
        cpu1.SetBrandName("AMD");
        cpu1.SetProductName("Ryzen 7 5700X");
        cpu1.SetPrice(264.0f);
        availableParts.add(cpu1);

        CPU cpu2 = new CPU("x86", 16, 4.3f);
        cpu2.SetPartNumber(nextPartNumber++);
        cpu2.SetBrandName("AMD");
        cpu2.SetProductName("Ryzen 9 9950X3D");
        cpu2.SetPrice(1149.0f);
        availableParts.add(cpu2);

        CPU cpu3 = new CPU("x86", 6, 3.8f);
        cpu3.SetPartNumber(nextPartNumber++);
        cpu3.SetBrandName("AMD");
        cpu3.SetProductName("Ryzen 5 7600");
        cpu3.SetPrice(309.0f);
        availableParts.add(cpu3);

        // ----------------
        // Motherboards
        // ----------------
        Motherboard motherboard1 = new Motherboard("AM5", 4);
        motherboard1.SetPartNumber(nextPartNumber++);
        motherboard1.SetBrandName("MSI");
        motherboard1.SetProductName("MAG B650 TOMAHAWK WIFI");
        motherboard1.SetPrice(299.0f);
        availableParts.add(motherboard1);

        Motherboard motherboard2 = new Motherboard("LGA1700", 4);
        motherboard2.SetPartNumber(nextPartNumber++);
        motherboard2.SetBrandName("ASUS");
        motherboard2.SetProductName("ROG MAXIMUS Z790 DARK HERO");
        motherboard2.SetPrice(999.0f);
        availableParts.add(motherboard2);

        Motherboard motherboard3 = new Motherboard("AM4", 2);
        motherboard3.SetPartNumber(nextPartNumber++);
        motherboard3.SetBrandName("MSI");
        motherboard3.SetProductName("B450M-A PRO MAX II");
        motherboard3.SetPrice(87.0f);
        availableParts.add(motherboard3);

        // ----------------
        // RAM
        // ----------------
        RAM ram1 = new RAM(32, 6000);
        ram1.SetPartNumber(nextPartNumber++);
        ram1.SetBrandName("Corsair");
        ram1.SetProductName("Vengeance RGB 32 GB");
        ram1.SetPrice(164.0f);
        availableParts.add(ram1);

        RAM ram2 = new RAM(64, 5200);
        ram2.SetPartNumber(nextPartNumber++);
        ram2.SetBrandName("Corsair");
        ram2.SetProductName("Vengeance 64 GB");
        ram2.SetPrice(259.0f);
        availableParts.add(ram2);

        RAM ram3 = new RAM(16, 3200);
        ram3.SetPartNumber(nextPartNumber++);
        ram3.SetBrandName("Corsair");
        ram3.SetProductName("Vengeance LPX 16 GB");
        ram3.SetPrice(164.0f);
        availableParts.add(ram3);

        // ----------------
        // Storage Drives
        // ----------------
        StorageDrive storageDrive1 = new StorageDrive("NVME SSD", 1000);
        storageDrive1.SetPartNumber(nextPartNumber++);
        storageDrive1.SetBrandName("Crucial");
        storageDrive1.SetProductName("P3 Plus");
        storageDrive1.SetPrice(92.0f);
        availableParts.add(storageDrive1);

        StorageDrive storageDrive2 = new StorageDrive("NVME SSD", 4000);
        storageDrive2.SetPartNumber(nextPartNumber++);
        storageDrive2.SetBrandName("Western Digital");
        storageDrive2.SetProductName("WD_BLACK SN8100");
        storageDrive2.SetPrice(759.0f);
        availableParts.add(storageDrive2);

        StorageDrive storageDrive3 = new StorageDrive("SATA SSD", 480);
        storageDrive3.SetPartNumber(nextPartNumber++);
        storageDrive3.SetBrandName("Samsung");
        storageDrive3.SetProductName("870 Evo");
        storageDrive3.SetPrice(69.0f);
        availableParts.add(storageDrive3);

        // ----------------
        // Cases
        // ----------------
        Case case1 = new Case("ATX Mid Tower", false);
        case1.SetPartNumber(nextPartNumber++);
        case1.SetBrandName("Corsair");
        case1.SetProductName("4000D Airflow");
        case1.SetPrice(159.0f);
        availableParts.add(case1);

        Case case2 = new Case("ATX Mid Tower", true);
        case2.SetPartNumber(nextPartNumber++);
        case2.SetBrandName("HYTE");
        case2.SetProductName("Y70 Touch Infinite");
        case2.SetPrice(639.0f);
        availableParts.add(case2);

        Case case3 = new Case("ATX Full Tower", true);
        case3.SetPartNumber(nextPartNumber++);
        case3.SetBrandName("be quiet!");
        case3.SetProductName("Light Base 900 DX");
        case3.SetPrice(171.0f);
        availableParts.add(case3);

        // ----------------
        // Power Supplies
        // ----------------
        PSU psu1 = new PSU(750, "80+ Gold");
        psu1.SetPartNumber(nextPartNumber++);
        psu1.SetBrandName("MSI");
        psu1.SetProductName("MAG A750GL PCIE5");
        psu1.SetPrice(127.0f);
        availableParts.add(psu1);

        PSU psu2 = new PSU(1600, "80+ Titanium");
        psu2.SetPartNumber(nextPartNumber++);
        psu2.SetBrandName("ASUS");
        psu2.SetProductName("ROG THOR 1600T Gaming");
        psu2.SetPrice(899.0f);
        availableParts.add(psu2);

        PSU psu3 = new PSU(550, "80+ Bronze");
        psu3.SetPartNumber(nextPartNumber++);
        psu3.SetBrandName("Corsair");
        psu3.SetProductName("CX (2023)");
        psu3.SetPrice(84.0f);
        availableParts.add(psu3);

        // ----------------
        // Graphics Cards
        // ----------------
        GPU gpu1 = new GPU(12000, 3584);
        gpu1.SetPartNumber(nextPartNumber++);
        gpu1.SetBrandName("MSI");
        gpu1.SetProductName("GeForce RTX 3060 Ventus 2X 12GB");
        gpu1.SetPrice(419.0f);
        availableParts.add(gpu1);

        GPU gpu2 = new GPU(32000, 21760);
        gpu2.SetPartNumber(nextPartNumber++);
        gpu2.SetBrandName("ASUS");
        gpu2.SetProductName("ROG Astral OC GeForce RTX 5090");
        gpu2.SetPrice(5295.0f);
        availableParts.add(gpu2);

        GPU gpu3 = new GPU(16000, 4096);
        gpu3.SetPartNumber(nextPartNumber++);
        gpu3.SetBrandName("ASUS");
        gpu3.SetProductName("Prime OC Radeon RX 9070 XT");
        gpu3.SetPrice(1199.0f);
        availableParts.add(gpu3);
    }

    private static void showMainMenu()
    {
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
        menuState = MenuState.DisplayParts;

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
                menuState = MenuState.DisplayMainMenu;
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
                System.out.println("This product ID does not match the current category you are viewing. Add product anyway? (Y/N)");
                if (!input.nextLine().equalsIgnoreCase("y"))
                    continue;
            }

            // Add part to order
            customerOrder.AddPart(selectedPart);

            // Return to main menu
            menuState = MenuState.DisplayMainMenu;
            System.out.println();
            break;
        }
    }

    private static boolean confirmOrder(Order order, Customer customer)
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

        menuState = MenuState.DisplayMainMenu;
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