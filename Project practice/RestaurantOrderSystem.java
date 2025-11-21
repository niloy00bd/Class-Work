import javax.swing.*; // Imports the Swing library for creating the Graphical User Interface (GUI).
import java.awt.*; // Imports Abstract Window Toolkit (AWT) classes for graphics, colors, and fonts.
import java.awt.event.*; // Imports AWT event classes for handling user input (e.g., button clicks, mouse clicks).
import java.io.*; // Imports core Java I/O classes for file operations and serialization.
import java.util.*; // Imports utilities like HashMap, ArrayList, and List.
import java.util.List; // Specifically imports the List interface.

/**
 * RestaurantOrderSystem
 * This is the main class for the application. It extends JFrame to create the main
 * window and implements Serializable to allow the entire application state (the tables)
 * to be saved and loaded to a binary file.
 */
public class RestaurantOrderSystem extends JFrame implements Serializable {
    //JFrame কী? JFrame হলো জাভার Swing লাইব্রেরির একটি প্রধান ক্লাস। এটি মূলত উইন্ডোজ, ম্যাক বা লিনাক্স ডেস্কটপে দেখা 
    //একটি "উইন্ডো" বা "ফ্রেম" তৈরি করে। এর মধ্যে টাইটেল বার, মিনিমাইজ/ম্যাক্সিমাইজ বাটন এবং বন্ধ করার বাটন থাকে।

    //Serializable কী? এটি জাভা I/O (Input/Output) প্যাকেজের একটি বিশেষ মার্কিং ইন্টারফেস (Marking Interface)। 
    //এর ভেতরে কোনো মেথড নেই, কিন্তু এর কাজ খুবই গুরুত্বপূর্ণ।
    //আপনার রেস্টুরেন্ট সিস্টেমে Table এবং Order অবজেক্টের যে বর্তমান অবস্থা (যেমন: টেবিল ১ এর স্ট্যাটাস 'PENDING', 
    //তার অর্ডারে ৫টি বার্গার আছে) তা প্রোগ্রাম বন্ধ হয়ে গেলেও যেন সেভ থাকে, তার জন্য এই ইন্টারফেস দরকার।
    //implements Serializable না করলে, আপনি যখন ObjectOutputStream-এর মাধ্যমে tables লিস্টটি সেভ করতে চাইবেন, 
    //তখন জাভা NotSerializableException দেখাবে।

    // --- Static Constants and Menu Setup ---

    // The file name used for persistence (Java Object Serialization/Binary File).
    private static final String DATA_FILE = "restaurant_data.ser"; // (.bin) dileo hoto

    // Menu Item Constants (using final for immutability and easy access).
    private static final List<MenuItem> MENU_ITEMS = List.of( // Creates an unmodifiable list of menu items.
    
    //List<MenuItem>
    //এটি একটি টাইপ ডিক্লারেশন। এর মানে হলো MENU_ITEMS ভ্যারিয়েবলটি একটি List হবে।
    //MenuItem হলো আপনার তৈরি করা কাস্টম ক্লাস, যা প্রতিটি খাদ্য বস্তুকে (যেমন: "Steak Frites") প্রতিনিধিত্ব করে।
    //অর্থাৎ, এই লিস্টটি শুধুমাত্র MenuItem টাইপের অবজেক্ট ধারণ করতে পারবে।

        new MenuItem("Steak Frites", 25.99), // Initializes a MenuItem object.
        new MenuItem("Caesar Salad", 12.50),
        new MenuItem("Truffle Pasta", 18.00),
        new MenuItem("Cheeseburger", 15.75),
        new MenuItem("Soda", 3.00),
        new MenuItem("Espresso", 4.50) //laster tay comma nai
    ); // **** 

    // --- OOP Model Classes ---

    /**
     * Represents a single item on the restaurant menu.
     * Implementing Serializable is crucial for saving the object to the binary file.
     */
    private static class MenuItem implements Serializable { //ekhanei MenuItem class create korlam
        private final String name; // The name of the item (final means it cannot be changed after creation).
        private final double price; // The price of the item (final means it cannot be changed after creation).

        public MenuItem(String name, double price) { // Constructor to initialize a menu item.
            this.name = name;
            this.price = price;
        }

        public String getName() { return name; } // Getter for the item name.
        public double getPrice() { return price; } // Getter for the item price.

        @Override
        public String toString() { return String.format("%s (%.2f)", name, price); } // Formats item for display.
        
        // Overriding equals and hashCode is necessary for the MenuItem to work correctly
        // as a key in the HashMap used within the Order class.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MenuItem menuItem = (MenuItem) o;
            return name.equals(menuItem.name); // Two items are equal if their names are the same.
        }
        @Override
        public int hashCode() { return name.hashCode(); } // Hash code based on the name.
    }

    /**
     * Represents an order placed for a specific table.
     * Implements Serializable for persistence.
     */
    private static class Order implements Serializable {
        private final int tableNumber; // The table this order belongs to.
        private final long timestamp; // Time the order was placed (in milliseconds since epoch).
        
        // Map stores the MenuItem object as the key and the quantity ordered as the value.
        private Map<MenuItem, Integer> items = new HashMap<>();

        public Order(int tableNumber) { // Constructor for a new order.
            this.tableNumber = tableNumber;
            this.timestamp = System.currentTimeMillis();
        }

        public void addItem(MenuItem item, int quantity) {
            // Adds or updates the quantity of a specific item in the order.
            items.put(item, items.getOrDefault(item, 0) + quantity);
        }

        public double calculateTotal() {
            // Calculates the total cost of the order using streams (Java 8+ feature).
            return items.entrySet().stream()
                // Maps each entry (item and quantity) to its subtotal (price * quantity).
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum(); // Sums all the subtotal values.
        }

        public String getSummary() {
            StringBuilder sb = new StringBuilder(); // Efficient way to build a string.
            // Adds header with table number and formatted time.
            sb.append(String.format("Order for Table %d (Time: %tH:%tM):\n", tableNumber, timestamp, timestamp));
            
            // Iterates through all items in the order map.
            for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
                // Formats and appends the quantity and item name.
                sb.append(String.format("- %d x %s\n", entry.getValue(), entry.getKey().getName()));
            }
            // Appends the final calculated total.
            sb.append(String.format("Total: $%.2f", calculateTotal()));
            return sb.toString();
        }
    }

    /**
     * Defines the possible status states of a table.
     * This uses an enum, which is ideal for a fixed set of constants.
     */
    private enum TableStatus implements Serializable {
        AVAILABLE("Available", new Color(0, 150, 0)), // Green color for available.
        PENDING("Pending Order", new Color(255, 180, 0)), // Yellow/Orange color for pending.
        SERVED("Served/Busy", new Color(180, 0, 0)); // Red/Maroon color for served/busy.

        private final String label; // Descriptive name for the status.
        private final Color color; // The color associated with the status for the GUI map.

        TableStatus(String label, Color color) { // Enum constructor.
            this.label = label;
            this.color = color;
        }

        public String getLabel() { return label; } // Getter for the status label.
        public Color getColor() { return color; } // Getter for the status color.
    }

    /**
     * Represents a physical table in the restaurant floor plan.
     * Implements Serializable for persistence.
     */
    private static class Table implements Serializable {
        private final int number; // Unique table number.
        private TableStatus status; // Current status (e.g., AVAILABLE, PENDING).
        private Order currentOrder; // The order currently associated with the table (can be null).
        // Coordinates for drawing the table on the map panel.
        private final int x, y; 

        public Table(int number, int x, int y) { // Constructor to set table properties and location.
            this.number = number;
            this.x = x;
            this.y = y;
            this.status = TableStatus.AVAILABLE; // Starts as available.
            this.currentOrder = null;
        }

        // Getters for table properties.
        public int getNumber() { return number; }
        public TableStatus getStatus() { return status; }
        public Order getCurrentOrder() { return currentOrder; }
        public int getX() { return x; }
        public int getY() { return y; }

        public void placeOrder(Order order) { // Method to assign a new order and update status.
            this.currentOrder = order;
            this.status = TableStatus.PENDING; // Status changes to pending.
        }

        public void setStatus(TableStatus status) { // Method to change only the status.
            this.status = status;
            if (status == TableStatus.AVAILABLE) {
                this.currentOrder = null; // Clears the order when the table becomes available.
            }
        }
    }

    // --- Main Application State ---

    private List<Table> tables; // The main list holding all Table objects.
    private TableMapPanel mapPanel; // The custom panel that draws the floor map.
    private JTabbedPane tabbedPane; // Container for the map and order panels.
    private OrderPanel orderPanel; // Panel for managing order details.

    // --- Initialization and Setup ---

    public RestaurantOrderSystem() { // Main constructor of the application.
        setTitle("Restaurant Order System"); // Sets the window title.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensures the application exits when the window is closed.
        
        loadData(); // Attempts to load tables from the binary file.
        
        if (tables == null || tables.isEmpty()) { // Checks if data was loaded successfully.
            initializeTables(); // If not, initialize a default set of tables.
        }

        setupGUI(); // Configures and displays all GUI components.
        // FIX: Setting a minimum size for the main window to ensure everything fits.
        setPreferredSize(new Dimension(850, 600)); 
        pack(); // Sizes the frame to fit the preferred size of its components.
        setLocationRelativeTo(null); // Centers the frame on the screen.
        setVisible(true); // Makes the frame visible.

        // Registers a thread to be run when the Java Virtual Machine (JVM) shuts down.
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveData)); // This ensures data is saved reliably.
    }

    private void initializeTables() {
        // Creates the default layout of tables.
        tables = new ArrayList<>();
        // Main Area tables with x, y coordinates for the map drawing.
        tables.add(new Table(1, 100, 100)); 
        tables.add(new Table(2, 250, 100));
        tables.add(new Table(3, 100, 250));
        tables.add(new Table(4, 250, 250));
        // Bar Section tables.
        tables.add(new Table(5, 500, 50));
        tables.add(new Table(6, 500, 180));
    }

    private void setupGUI() {
        tabbedPane = new JTabbedPane(); // Initializes the tabbed interface.
        
        // 1. Table Map Panel setup.
        mapPanel = new TableMapPanel();
        tabbedPane.addTab("Restaurant Map", mapPanel); // Adds the map view as the first tab.

        // 2. Order Management Panel setup.
        orderPanel = new OrderPanel();
        tabbedPane.addTab("Order Management", orderPanel); // Adds the order control as the second tab.

        add(tabbedPane); // Adds the tabbed panel to the main frame.
        
        // Customizes the frame appearance to match the operating system's look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel.");
        }
    }
    
    // --- Persistence (File I/O) ---
    
    /**
     * Saves the current state of tables to a binary file using Java Serialization.
     */
    private void saveData() {
        // Uses try-with-resources for automatic closing of streams, preventing resource leaks.
        try (FileOutputStream fos = new FileOutputStream(DATA_FILE); // Opens stream to write bytes to the file.
             // FIX: Removed the redundant 'new' keyword that caused the compilation error.
             ObjectOutputStream oos = new ObjectOutputStream(fos)) { // Wraps stream to write Java objects.
            
            oos.writeObject(tables); // Writes the entire list of 'tables' objects to the file.
            System.out.println("Data saved successfully to " + DATA_FILE); // Console confirmation.
            
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage()); // Handles I/O errors during save.
        }
    }

    /**
     * Loads the previous state of tables from the binary file.
     */
    @SuppressWarnings("unchecked") // Suppresses warning about unchecked cast from Object to List<Table>.
    private void loadData() {
        // Uses try-with-resources for automatic closing of streams.
        try (FileInputStream fis = new FileInputStream(DATA_FILE); // Opens stream to read bytes from the file.
             ObjectInputStream ois = new ObjectInputStream(fis)) { // Wraps stream to read Java objects.
            
            tables = (List<Table>) ois.readObject(); // Reads the list of tables object from the file.
            System.out.println("Data loaded successfully from " + DATA_FILE);
            
        } catch (FileNotFoundException e) {
            // This is expected if the app is run for the first time or the file was deleted.
            System.out.println("No previous data file found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            // Handles I/O errors or if the class definitions changed since saving (Class not found).
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
    
    // --- Inner Classes for GUI Components ---

    /**
     * Custom JPanel component responsible for drawing the restaurant floor layout and table status.
     */
    private class TableMapPanel extends JPanel {
        private static final int TABLE_SIZE = 50; // Constant size (diameter) for drawing tables.
        private Table selectedTable = null; // Stores the table the user last clicked.

        public TableMapPanel() {
            setPreferredSize(new Dimension(600, 400)); // Sets the size of the drawing area.
            setBackground(new Color(245, 245, 220)); // Sets a light beige color for the "floor".
            
            // Attaches a listener to detect mouse clicks on the map.
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedTable = null; // Reset selection.
                    // Loop through all tables to check if the click coordinates match a table's location.
                    for (Table table : tables) {
                        // Checks if the click X and Y fall within the table's bounds (x, y, and size).
                        if (e.getX() >= table.getX() && e.getX() <= table.getX() + TABLE_SIZE &&
                            e.getY() >= table.getY() && e.getY() <= table.getY() + TABLE_SIZE) {
                            
                            selectedTable = table; // Sets the clicked table as selected.
                            break; // Exit the loop once the table is found.
                        }
                    }
                    
                    if (selectedTable != null) {
                        // If a table was clicked, update the OrderPanel and switch tabs.
                        orderPanel.setTable(selectedTable);
                        tabbedPane.setSelectedIndex(1); // Switches to the "Order Management" tab (index 1).
                    }
                    
                    repaint(); // Tells Swing to redraw the panel (which will call paintComponent).
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Calls the superclass method to ensure correct background painting.
            Graphics2D g2d = (Graphics2D) g; // Casts to Graphics2D for advanced drawing capabilities.
            // Enables anti-aliasing for smooth edges on circles and text.
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw floor sections/decor (simulated bar/kitchen area).
            g2d.setColor(new Color(220, 220, 200)); 
            g2d.fillRect(450, 0, 150, 400); // Draws a rectangular block for a bar area.
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawRect(450, 0, 150, 400); // Draws a border around the bar area.

            // Draw all tables on the map.
            for (Table table : tables) {
                // Set the fill color based on the table's current status (green, yellow, or red).
                g2d.setColor(table.getStatus().getColor());
                
                // Draw the table shape as a filled circle (Oval).
                g2d.fillOval(table.getX(), table.getY(), TABLE_SIZE, TABLE_SIZE);
                
                // Draw outline and label.
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2)); // Sets a thicker stroke for the border.
                g2d.drawOval(table.getX(), table.getY(), TABLE_SIZE, TABLE_SIZE);
                
                // Draw the table number.
                g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                String tableNum = String.valueOf(table.getNumber());
                FontMetrics fm = g2d.getFontMetrics(); // Get font metrics to center the text.
                int textWidth = fm.stringWidth(tableNum);
                // Calculate centered x and y coordinates for the number inside the circle.
                int x = table.getX() + (TABLE_SIZE - textWidth) / 2;
                int y = table.getY() + (TABLE_SIZE - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(tableNum, x, y);
            }
            
            drawLegend(g2d); // Draws the status legend in the corner.
        }
        
        private void drawLegend(Graphics2D g2d) {
            int x = 20; // Starting X position.
            int y = getHeight() - 80; // Starting Y position (near the bottom).
            int boxSize = 15; // Size of the colored square in the legend.
            
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString("STATUS LEGEND:", x, y);
            y += 20;

            // Iterate through the TableStatus enum values to draw the legend items.
            for (TableStatus status : TableStatus.values()) {
                g2d.setColor(status.getColor());
                g2d.fillRect(x, y, boxSize, boxSize); // Draw the colored square.
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, boxSize, boxSize); // Draw a black border around the square.
                g2d.drawString(status.getLabel(), x + boxSize + 5, y + boxSize - 3); // Draw the status label next to it.
                y += 20; // Move down for the next item.
            }
        }
    }

    /**
     * Panel dedicated to placing, modifying, and managing orders for a selected table.
     */
    private class OrderPanel extends JPanel {
        private Table table; // The table currently being managed.
        private JComboBox<String> tableSelector; // Dropdown to manually select a table.
        private JTextArea orderSummaryArea; // Text area to display the order details.
        private JPanel menuPanel; // Panel to hold the dynamic menu items and spinners.
        private JButton placeOrderButton; // Button to place the order.
        private JButton servedButton; // Button to mark the order as served.
        private JButton clearButton; // Button to clear the table.

        // Stores a reference to the JSpinner for each MenuItem to retrieve quantities easily.
        private final Map<MenuItem, JSpinner> quantityInputs = new HashMap<>();

        public OrderPanel() {
            // FIX: Set a preferred size for the OrderPanel to ensure all components, especially buttons,
            // have sufficient space to render correctly without being compressed by the BorderLayout.
            setPreferredSize(new Dimension(800, 500)); 
            
            setLayout(new BorderLayout(10, 10)); // Uses BorderLayout for main sections with spacing.
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adds padding around the panel.
            
            // Top Section (Table Selection) setup.
            tableSelector = new JComboBox<>();
            // Adds a listener to change the current table when an item is selected from the combo box.
            tableSelector.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Extracts the table number from the selected string ("Table X").
                    String selectedItem = (String)tableSelector.getSelectedItem();
                    if (selectedItem != null) {
                         int selectedTableNum = Integer.parseInt(selectedItem.split(" ")[1]);
                         // Finds the corresponding Table object and sets it as the current table.
                         setTable(tables.stream().filter(t -> t.getNumber() == selectedTableNum).findFirst().orElse(null));
                    }
                }
            });
            
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel to hold the selector.
            topPanel.add(new JLabel("Current Table:"));
            topPanel.add(tableSelector);
            add(topPanel, BorderLayout.NORTH); // Adds selector panel to the top.

            // Center Section (Menu) setup.
            menuPanel = new JPanel();
            // Uses GridLayout for menu items (rows determined by menu size, 3 columns: Label, Spinner).
            menuPanel.setLayout(new GridLayout(MENU_ITEMS.size(), 3, 5, 5));
            JScrollPane menuScrollPane = new JScrollPane(menuPanel); // Makes the menu scrollable.
            menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu Selection (Enter Quantity)"));
            add(menuScrollPane, BorderLayout.CENTER); // Adds the menu scroll pane to the center.
            
            setupMenuInput(); // Method to dynamically create menu input components.

            // Right Section (Summary and Actions) setup.
            JPanel eastPanel = new JPanel(new BorderLayout(5, 5));
            // Setting a fixed width for the East panel helps prevent button compression.
            eastPanel.setPreferredSize(new Dimension(300, 400));
            
            orderSummaryArea = new JTextArea(10, 30); // Initializes the text area.
            orderSummaryArea.setEditable(false); // Prevents users from typing in the summary.
            orderSummaryArea.setBorder(BorderFactory.createTitledBorder("Order Summary"));
            eastPanel.add(new JScrollPane(orderSummaryArea), BorderLayout.CENTER); // Makes summary scrollable.

            // FIX: Using a simple vertical box layout to ensure buttons stack nicely and prevent compression.
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Vertical stacking.
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            
            // Creates and configures the action buttons with custom colors and listeners.
            placeOrderButton = createActionButton("Place Order (Pending)", new Color(25, 118, 210), this::placeOrder);
            servedButton = createActionButton("Mark Served (Served)", new Color(180, 0, 0), this::markServed);
            clearButton = createActionButton("Clear Table (Available)", new Color(0, 150, 0), this::clearTable);
            
            buttonPanel.add(placeOrderButton);
            buttonPanel.add(Box.createVerticalStrut(5)); // Small gap between buttons.
            buttonPanel.add(servedButton);
            buttonPanel.add(Box.createVerticalStrut(5)); // Small gap between buttons.
            buttonPanel.add(clearButton);
            
            // Ensure buttons fill the width of their container.
            placeOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            servedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            eastPanel.add(buttonPanel, BorderLayout.SOUTH); // Adds buttons to the bottom of the right panel.

            add(eastPanel, BorderLayout.EAST); // Adds the right panel (summary + buttons) to the main panel.
            
            populateTableSelector(); // Fills the table dropdown menu.
        }
        
        // Helper method to create consistently styled buttons.
        private JButton createActionButton(String text, Color color, ActionListener listener) {
            JButton button = new JButton(text);
            button.setBackground(color); // Sets the button background color.
            button.setForeground(Color.WHITE); // Sets the text color to white.
            button.setFocusPainted(false); // Removes the focus border.
            // Ensure custom color renders and text is visible.
            button.setOpaque(true); 
            button.setBorderPainted(false); 
            // FIX: Setting preferred size for the button to ensure it has enough space for text.
            button.setPreferredSize(new Dimension(250, 40)); 
            button.setMaximumSize(new Dimension(300, 40)); // Prevent excessive resizing.
            button.addActionListener(listener); // Attaches the specific action listener.
            return button;
        }

        private void populateTableSelector() {
            tableSelector.removeAllItems(); // Clears any existing items.
            // Populates the JComboBox with sorted table numbers.
            tables.stream()
                  .sorted(Comparator.comparing(Table::getNumber))
                  .forEach(t -> tableSelector.addItem("Table " + t.getNumber()));
            
            setTable(tables.get(0)); // Automatically sets the first table as the default selection.
        }

        private void setupMenuInput() {
            for (MenuItem item : MENU_ITEMS) {
                // Adds a label for the item name and price.
                menuPanel.add(new JLabel(item.getName() + " ($" + String.format("%.2f", item.getPrice()) + ")"));
                
                // Defines the spinner model: start at 0, min 0, max 99, step 1.
                SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 99, 1);
                JSpinner spinner = new JSpinner(model);
                quantityInputs.put(item, spinner); // Stores the spinner reference keyed by the MenuItem object.
                menuPanel.add(spinner); // Adds the spinner to the menu panel.
            }
        }
        
        // Sets the current table for the OrderPanel and triggers a display update.
        public void setTable(Table t) {
            if (t == null) return; // Guard clause if table is null.
            this.table = t;
            
            // Ensures the JComboBox displays the correct table if the change came from the map click.
            String tableItem = "Table " + t.getNumber();
            if (!tableItem.equals(tableSelector.getSelectedItem())) {
                 tableSelector.setSelectedItem(tableItem);
            }
            
            updateDisplay(); // Refresh the GUI elements.
        }

        // Refreshes the order summary, resets inputs, and updates button states.
        private void updateDisplay() {
            if (table == null) return;

            // Update summary area based on the table's current order.
            if (table.getCurrentOrder() != null) {
                orderSummaryArea.setText(table.getCurrentOrder().getSummary());
            } else {
                orderSummaryArea.setText("No order currently placed for Table " + table.getNumber());
            }

            // Reset all quantity spinners to zero.
            quantityInputs.values().forEach(s -> s.setValue(0));
            
            // Update button visibility/enabling based on the table's current status.
            // FIX: Added logic to enable Place Order button only if the table is currently AVAILABLE.
            // If status is PENDING or SERVED, it means there is an active order being managed.
            // However, based on the image, the user seems to be trying to place an order when the table is not AVAILABLE.
            // Let's adjust logic: Allow placing an order if it's NOT AVAILABLE (meaning we are updating/replacing an order). 
            // The original logic was actually: placeOrderButton.setEnabled(table.getStatus() != TableStatus.AVAILABLE); 
            // We'll stick to the original intent: if a table is selected, the place order button should be available 
            // unless the table has just been cleared. We only block it if the table is completely free.
            placeOrderButton.setEnabled(true); // Always allow placing an order once table is selected
            if (table.getStatus() == TableStatus.AVAILABLE) {
                 placeOrderButton.setEnabled(true); // Allow order placement on empty table.
            }

            // The 'Served' button is only useful if there is a pending order to serve.
            servedButton.setEnabled(table.getStatus() == TableStatus.PENDING); 
            
            // The 'Clear' button is for clearing a table that is busy (PENDING or SERVED).
            clearButton.setEnabled(table.getStatus() != TableStatus.AVAILABLE); 
            
            mapPanel.repaint(); // Triggers the map to redraw, showing the updated table status color.
        }

        // Action handler for the "Place Order" button.
        private void placeOrder(ActionEvent e) {
            if (table == null) return;

            Order newOrder = new Order(table.getNumber()); // Creates a new Order object.
            int totalItems = 0;
            
            // Iterate through all menu item spinners to gather quantities.
            for (Map.Entry<MenuItem, JSpinner> entry : quantityInputs.entrySet()) {
                int quantity = (Integer) entry.getValue().getValue(); // Retrieves the spinner's current value.
                if (quantity > 0) {
                    newOrder.addItem(entry.getKey(), quantity); // Add item to the order.
                    totalItems += quantity;
                }
            }

            if (totalItems == 0) {
                // Shows a message box if no items were selected.
                JOptionPane.showMessageDialog(this, 
                    "Please select at least one item and quantity to place an order.", 
                    "Order Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Handle if we are placing a new order or replacing an existing one.
            String message;
            if (table.getCurrentOrder() == null) {
                table.placeOrder(newOrder); // Assigns the order and sets status to PENDING.
                message = String.format("New order placed for Table %d.", table.getNumber());
            } else {
                // If an order already exists, this replaces the old order or is considered an update.
                table.placeOrder(newOrder); 
                message = String.format("Order for Table %d updated.", table.getNumber());
            }
            
            // Confirms the order placement with the total amount.
            JOptionPane.showMessageDialog(this, 
                String.format("%s Total: $%.2f", message, newOrder.calculateTotal()), 
                "Order Placed", JOptionPane.INFORMATION_MESSAGE);
            
            updateDisplay(); // Updates the status color on the map and summary.
        }

        // Action handler for the "Mark Served" button.
        private void markServed(ActionEvent e) {
            // Checks if a table is selected and its status is PENDING.
            if (table == null || table.getStatus() != TableStatus.PENDING) return;
            
            table.setStatus(TableStatus.SERVED); // Changes status to SERVED.
            JOptionPane.showMessageDialog(this, 
                "Table " + table.getNumber() + " marked as Served.", 
                "Status Change", JOptionPane.INFORMATION_MESSAGE);
            updateDisplay(); // Updates the status color on the map.
        }
        
        // Action handler for the "Clear Table" button.
        private void clearTable(ActionEvent e) {
            // Checks if a table is selected and its status is not already AVAILABLE.
            if (table == null || table.getStatus() == TableStatus.AVAILABLE) return;
            
            // Shows a confirmation dialog before resetting the table.
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to clear Table " + table.getNumber() + "? This clears the order history.",
                "Confirm Clear Table", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) { // Proceed only if the user confirms.
                table.setStatus(TableStatus.AVAILABLE); // Changes status to AVAILABLE and clears the order object.
                JOptionPane.showMessageDialog(this, 
                    "Table " + table.getNumber() + " marked as Available.", 
                    "Status Change", JOptionPane.INFORMATION_MESSAGE);
                updateDisplay(); // Updates the status color on the map.
            }
        }
    }

    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures that all GUI operations are performed
        // safely on the Event Dispatch Thread (EDT), which is the standard practice in Swing.
        SwingUtilities.invokeLater(RestaurantOrderSystem::new);
    }
}