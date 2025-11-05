/*
    Author: Jye Curtis-Smith
    Version: 1.0
    Date: 3/11/2025
    PCPartSales GUI class - Handles everything related to the GUI for the program
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PCSalesGUI extends JFrame implements ActionListener, ChangeListener
{
    private JLabel fullNameLbl = new JLabel("Full Name:");
    private JLabel emailLbl = new JLabel("Email Address:");
    private JTextField fullNameTF = new JTextField();
    private JTextField emailTF = new JTextField();
    private JButton signInBtn = new JButton("Sign In");

    // Main panels
    private JPanel customerDetailsPnl = new JPanel();
    private JSplitPane partsSplitPnl = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JPanel reviewOrderPnl = new JPanel();
    private JPanel totalSalesPnl = new JPanel();

    private JPanel availablePartsPnl = new JPanel();
    private JPanel selectedPartsPnl = new JPanel();
    private JPanel selectedPartsListPnl = new JPanel();

    private Map<JButton, PCPart> partAddButtons = new HashMap<JButton, PCPart>();

    private JButton confirmOrderBtn = new JButton("Confirm Order");
    private JLabel welcomeCustomerLbl = new JLabel("Welcome customer, please choose from our variety of pc parts for your order");
    private JLabel invalidEmailLbl = new JLabel("ERROR: Not a valid email address.");

    private JTabbedPane mainTabs =  new JTabbedPane();
    private JTabbedPane categoryTabs = new JTabbedPane();

    private ArrayList<Customer> customers;
    private ArrayList<Order> confirmedOrders;
    private ArrayList<PCPart> availableParts;

    private Customer currentCustomer;
    private Order currentOrder;

    private final int GUI_WIDTH = 1280;
    private final int GUI_HEIGHT = 720;

    PCSalesGUI() throws SQLException {
        super("PCPartSales");
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        // Create array lists to store objects
        customers = new ArrayList<>();
        confirmedOrders = new ArrayList<>();
        availableParts = new ArrayList<>();

        populateAvailableParts();

        mainTabs.addTab("Customer", customerDetailsPnl);
        mainTabs.addTab("Parts", partsSplitPnl);
        mainTabs.addTab("Review", reviewOrderPnl);
        mainTabs.addTab("Sales", totalSalesPnl);

        mainTabs.addChangeListener(this);
        confirmOrderBtn.addActionListener(this);

        // Setup customer details tab
        customerDetailsPnl.setLayout(new BorderLayout());
        signInBtn.setMaximumSize(new Dimension(50, 50));

        fullNameLbl.setFont(new Font("Arial", Font.BOLD, 20));
        emailLbl.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel customerDetailsInputsPnl = new JPanel(new GridLayout(2, 2, 50, 0));
        customerDetailsInputsPnl.add(fullNameLbl);
        customerDetailsInputsPnl.add(fullNameTF);
        customerDetailsInputsPnl.add(emailLbl);
        customerDetailsInputsPnl.add(emailTF);
        customerDetailsPnl.add(customerDetailsInputsPnl, BorderLayout.NORTH);

        customerDetailsPnl.setBorder(new EmptyBorder(200, 400, 200, 400));
        signInBtn.addActionListener(this);
        signInBtn.setPreferredSize(new Dimension(150, 30));
        JPanel invisCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        invisCenterPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        invisCenterPanel.add(signInBtn);
        customerDetailsPnl.add(invisCenterPanel);

        // Setup order review tab
        reviewOrderPnl.setLayout(new BoxLayout(reviewOrderPnl, BoxLayout.Y_AXIS));
        reviewOrderPnl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Setup total sales tab
        totalSalesPnl.setLayout(new GridBagLayout());


        // Setup pc parts tab

        availablePartsPnl.setLayout(new BorderLayout());
        availablePartsPnl.setBorder(new LineBorder(Color.BLACK));
        availablePartsPnl.add(categoryTabs, BorderLayout.CENTER);

        selectedPartsPnl.setLayout(new BorderLayout());
        selectedPartsListPnl.setLayout(new BoxLayout(selectedPartsListPnl, BoxLayout.PAGE_AXIS));
        selectedPartsPnl.add(selectedPartsListPnl, BorderLayout.CENTER);

        partsSplitPnl.setDividerLocation(GUI_WIDTH / 2);
        partsSplitPnl.setLeftComponent(availablePartsPnl);
        partsSplitPnl.setRightComponent(selectedPartsPnl);

        JLabel availablePartsLbl = new JLabel("Available Parts");
        availablePartsLbl.setFont(new Font(availablePartsLbl.getFont().getFontName(), Font.BOLD, 24));

        JLabel selectedPartsLbl = new JLabel("Selected Parts");
        selectedPartsLbl.setBorder(new EmptyBorder(10, 10, 5, 0));
        selectedPartsLbl.setFont(new Font(selectedPartsLbl.getFont().getFontName(), Font.BOLD, 24));

        JPanel availablePartsNorthPnl = new JPanel();
        availablePartsNorthPnl.setLayout(new BoxLayout(availablePartsNorthPnl, BoxLayout.PAGE_AXIS));
        availablePartsNorthPnl.setBorder(new EmptyBorder(10, 10, 10, 0));
        availablePartsNorthPnl.add(availablePartsLbl);
        availablePartsNorthPnl.add(welcomeCustomerLbl);

        selectedPartsPnl.add(selectedPartsLbl, BorderLayout.NORTH);

        availablePartsPnl.add(availablePartsNorthPnl, BorderLayout.NORTH);

        // Create a tab for each part category and create each part panel
        for (PartCategory category : PartCategory.values())
        {
            JPanel categoryPnl = new JPanel();
            categoryPnl.setLayout(new BoxLayout(categoryPnl, BoxLayout.Y_AXIS));

            for (PCPart p : availableParts)
            {
                if (p.GetCategory() == category)
                {
                    JPanel itemPnl = CreatePartPanel(p, true);

                    JButton addBtn = new JButton("Add");
                    itemPnl.add(addBtn);
                    addBtn.addActionListener(this);

                    partAddButtons.put(addBtn, p);

                    categoryPnl.add(itemPnl, BorderLayout.NORTH);
                }
            }

            String categoryName = category == PartCategory.StorageDrive ? "Storage Drive" : category.toString();

            categoryTabs.add(categoryName, categoryPnl);
        }

        // Disable other tabs so they're inaccessible until login
        mainTabs.setEnabledAt(1, false);
        mainTabs.setEnabledAt(2, false);
        mainTabs.setEnabledAt(3, false);

        add(mainTabs);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == signInBtn)
        {
            String fullName = fullNameTF.getText();
            String email = emailTF.getText();

            if (loginCustomer(fullName, email))
            {
                // Set enabled tabs
                mainTabs.setEnabledAt(0, false);
                mainTabs.setEnabledAt(1, true);
                mainTabs.setEnabledAt(2, true);
                mainTabs.setEnabledAt(3, true);

                // Reset selected parts panel
                selectedPartsListPnl.removeAll();

                // Switch to parts tab
                mainTabs.setSelectedIndex(1);
            }
        }

        for (JButton button : partAddButtons.keySet())
        {
            if (e.getSource() == button)
            {
                PCPart part = partAddButtons.get(button);
                currentOrder.AddPart(part);

                JPanel selectedPartPanel = CreatePartPanel(part, false);
                selectedPartsListPnl.add(selectedPartPanel);
                selectedPartsListPnl.revalidate();
            }
        }

        if (e.getSource() == confirmOrderBtn)
        {
            confirmedOrders.add(currentOrder);

            // Insert order into database
            DB db;
            try {
                db = new DB();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            int orderNm = currentOrder.GetOrderNumber();
            int customerID = currentOrder.GetCustomerID();
            String orderDate = currentOrder.GetOrderDate();
            String orderTime = currentOrder.GetOrderTime();

            String query = "INSERT INTO orders (OrderNumber, CustomerID, OrderDate, OrderTime) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement stmt = db.newPreparedStatement(query);
                stmt.setInt(1, orderNm);
                stmt.setInt(2, customerID);
                stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                stmt.setTime(4, new java.sql.Time(System.currentTimeMillis()));
                db.executeQuery(stmt);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ArrayList<PCPart> orderParts = currentOrder.GetParts();

            for (PCPart p : orderParts)
            {
                int productID = p.GetPartNumber();
                String productName = p.GetBrandName() + " " + p.GetProductName();
                String detailsQuery = "INSERT INTO orderdetails (OrderNumber, ProductID, ProductName) VALUES (?, ?, ?)";

                try {
                    PreparedStatement preparedStmt = db.newPreparedStatement(detailsQuery);
                    preparedStmt.setInt(1, orderNm);
                    preparedStmt.setInt(2, productID);
                    preparedStmt.setString(3, productName);
                    db.executeQuery(preparedStmt);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            // Disable parts list tab
            mainTabs.setEnabledAt(1, false);

            // Disable review order tab
            mainTabs.setEnabledAt(2, false);

            // Enable customer tab
            mainTabs.setEnabledAt(0, true);

            // Disable the confirm button and display success message
            confirmOrderBtn.setEnabled(false);
            JLabel orderPlacedLbl = new JLabel("Order placed. Thank you for using PC Part Sales.");
            orderPlacedLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewOrderPnl.add(orderPlacedLbl);

            // Clear customer details textfields
            fullNameTF.setText("");
            emailTF.setText("");

        }
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        if (e.getSource() == mainTabs)
        {
            if (mainTabs.getSelectedComponent() == reviewOrderPnl)
               reviewOrder(currentOrder, currentCustomer);

            if (mainTabs.getSelectedComponent() == totalSalesPnl)
                displayTotalSales();
        }
    }

    private boolean loginCustomer(String fullName, String emailAddress)
    {
        int customerID = 0;

        // Check if customer already exists
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                if (customer.GetName().equalsIgnoreCase(fullName) && customer.GetEmail().equalsIgnoreCase(emailAddress))
                {
                    customerID = customer.GetID();
                    currentCustomer = customer;
                    currentOrder = new Order(confirmedOrders.size() + 1, customerID);
                    return true;
                }
            }
        }

        // No existing customer found, prepare to create new one

        // Check if email is valid
        if (!emailAddress.contains("@"))
        {
            customerDetailsPnl.remove(invalidEmailLbl);
            customerDetailsPnl.add(invalidEmailLbl, BorderLayout.SOUTH);
            customerDetailsPnl.revalidate();
            return false;
        }

        // Create a new customer and add them to the registry
        customerID = customers.size() + 1;
        currentCustomer = new Customer(customerID, fullName, emailAddress);
        customers.add(currentCustomer);

        currentOrder = new Order(confirmedOrders.size() + 1, customerID);

        welcomeCustomerLbl.setText(String.format("Welcome %s, please choose from our variety of pc parts for your order", currentCustomer.GetName()));

        return true;
    }

    private void reviewOrder(Order order, Customer customer)
    {
        // Completely update review order panel
        reviewOrderPnl.removeAll();

        // Update order date and time
        LocalDateTime currentDateTime = java.time.LocalDateTime.now();
        order.SetOrderDate(currentDateTime.toLocalDate().toString());
        order.SetOrderTime(currentDateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString());

        JLabel orderReviewTitleLbl = new JLabel("--------- ORDER REVIEW ---------", SwingConstants.CENTER);
        orderReviewTitleLbl.setFont(new Font("Arial", Font.BOLD, 20));
        orderReviewTitleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewOrderPnl.add(orderReviewTitleLbl);

        String orderDetails = "";
        String orderMetadata = String.format("<html>Order #%d for %s (Customer ID: %d) at %s on %s<br>", order.GetOrderNumber(), customer.GetName(), customer.GetID(), order.GetOrderTime(), order.GetOrderDate());
        String orderedPartsList = "Ordered parts list:<br>";

        orderDetails += orderMetadata + orderedPartsList;

        // Display parts and calculate price simultaneously
        double totalPrice = 0.0f;
        for (PCPart part : order.GetParts())
        {
            orderDetails += String.format("- 1 x %s %s - $%.2f<br>", part.brandName, part.productName, part.price);
            totalPrice += part.GetPrice();
        }

        orderDetails += "</html>";

        JLabel orderDetailsLbl = new JLabel(orderDetails, SwingConstants.CENTER);
        orderDetailsLbl.setFont(new Font("Arial", Font.PLAIN, 20));
        orderDetailsLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        orderDetailsLbl.setBorder(new EmptyBorder(20, 0, 20, 0));
        reviewOrderPnl.add(orderDetailsLbl);

        JLabel totalPriceLbl = new JLabel(String.format("------ Total Price: $%.2f ------", totalPrice));
        totalPriceLbl.setFont(new Font("Arial", Font.BOLD, 20));
        totalPriceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewOrderPnl.add(totalPriceLbl);


        confirmOrderBtn.setEnabled(true);
        confirmOrderBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewOrderPnl.add(confirmOrderBtn);

        reviewOrderPnl.revalidate();
        reviewOrderPnl.repaint();
    }

    private void displayTotalSales()
    {
        double totalSales = 0.0f;
        for (Order order : confirmedOrders)
        {
            for (PCPart part : order.GetParts())
                totalSales += part.GetPrice();
        }

        totalSalesPnl.removeAll();
        JLabel totalSalesLbl = new JLabel(String.format("------ Total sales across %d orders for %d customers: $%.2f ------\n", confirmedOrders.size(), customers.size(), totalSales));
        totalSalesLbl.setFont(new Font("Arial", Font.BOLD, 20));
        totalSalesPnl.add(totalSalesLbl);

        totalSalesPnl.revalidate();
        totalSalesPnl.repaint();
    }

    private void populateAvailableParts() throws SQLException {
        DB db = new DB();

        for (PartCategory category : PartCategory.values())
        {
            String table = category.toString().trim().toLowerCase();
            String query = "SELECT * FROM `" + table + "`";

            ResultSet results = db.executeQuery(query);

            while (results.next()) {
                int productid = results.getInt("productid");
                String brandName = results.getString("brandname");
                String productName = results.getString("productname");
                double price = results.getDouble("price");

                switch (category) {
                    case CPU:
                        String arch = results.getString("arch");
                        int coreCount = results.getInt("corecount");
                        float coreFreq = results.getFloat("corefreq");
                        CPU cpu = new CPU(productid, brandName, productName, price, arch, coreCount, coreFreq);
                        availableParts.add(cpu);
                        break;
                    case Motherboard:
                        String socketType = results.getString("sockettype");
                        int ramSlots = results.getInt("ramslots");
                        Motherboard motherboard = new Motherboard(productid, brandName, productName, price, socketType, ramSlots);
                        availableParts.add(motherboard);
                        break;
                    case RAM:
                        int gigabytes = results.getInt("gigabytes");
                        int speed = results.getInt("speed");
                        RAM ram = new RAM(productid, brandName, productName, price, gigabytes, speed);
                        availableParts.add(ram);
                        break;
                    case StorageDrive:
                        String type = results.getString("type");
                        int space = results.getInt("space");
                        StorageDrive drive = new StorageDrive(productid, brandName, productName, price, type, space);
                        availableParts.add(drive);
                        break;
                    case Case:
                        String formFactor = results.getString("formfactor");
                        boolean hasRGB = results.getBoolean("hasRGB");
                        Case caseProduct = new Case(productid, brandName, productName, price, formFactor, hasRGB);
                        availableParts.add(caseProduct);
                        break;
                    case PSU:
                        int wattage = results.getInt("wattage");
                        String efficiency = results.getString("efficiency");
                        PSU psu = new PSU(productid, brandName, productName, price, wattage, efficiency);
                        availableParts.add(psu);
                        break;
                    case GPU:
                        int vram = results.getInt("vramMB");
                        int coreCountGPU = results.getInt("corecount");
                        GPU gpu = new GPU(productid, brandName, productName, price, vram, coreCountGPU);
                        availableParts.add(gpu);
                        break;
                }
            }
        }
    }

    private JPanel CreatePartPanel(PCPart part, boolean addDetails)
    {
        JPanel itemPnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 5));

        JPanel partDetailsPnl = new JPanel();
        partDetailsPnl.setLayout(new BoxLayout(partDetailsPnl, BoxLayout.Y_AXIS));
        JLabel partNameLbl = new JLabel(part.GetBrandName() + " " + part.GetProductName());
        partNameLbl.setFont(new Font(partNameLbl.getFont().getFontName(), Font.BOLD, 16));
        partDetailsPnl.add(partNameLbl);

        if (addDetails)
        {
            String details = part.GetDetails();
            String[] splitDetails = details.split("\n");

            for (String s : splitDetails)
                partDetailsPnl.add(new JLabel(s));

        }
        itemPnl.add(partDetailsPnl);

        return itemPnl;
    }

    public static void main(String[] args) throws SQLException {
        PCSalesGUI gui = new PCSalesGUI();
        gui.setVisible(true);
    }
}

