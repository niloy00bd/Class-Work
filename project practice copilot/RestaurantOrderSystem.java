// Import Swing classes for GUI components (JFrame, JPanel, JButton, JComboBox, etc.)
import java.awt.*; // GUI toolkit for desktop apps
import java.awt.event.*; // Low-level drawing and component layout
import java.io.*; // ActionListener, MouseAdapter, MouseEvent
import javax.swing.*; // FileInputStream, FileOutputStream, DataInputStream, DataOutputStream, IOException, EOFException

// Main application window class; extends JFrame to create the app window
public class RestaurantOrderSystem extends JFrame { // Application class

    // ===== Fixed menu constants (final to ensure they don't change at runtime) =====
    public static final String[] MENU_NAMES = { "Coffee", "Tea", "Burger", "Pizza", "Pasta", "Salad", "Juice" }; // Menu item names
    public static final double[] MENU_PRICES = { 120.0, 100.0, 350.0, 550.0, 400.0, 250.0, 150.0 }; // Menu item prices

    // ===== Status color constants for drawing tables =====
    public static final Color COLOR_AVAILABLE = Color.GREEN; // Available = green
    public static final Color COLOR_PENDING = Color.YELLOW;  // Pending = yellow
    public static final Color COLOR_SERVED = Color.RED;      // Served = red

    // ===== Binary data file name =====
    private static final String DATA_FILE = "restaurant_orders.bin"; // File for persistence

    // ===== Floor layout and tables =====
    private Table[] tables; // Array holding all tables on the floor. Table টাইপের array. Table class ami niche create korechi
    private int rows = 2;   // Number of rows on the floor (editable)
    private int cols = 5;   // Number of columns on the floor (editable)

    // ===== GUI components =====
    private FloorPanel floorPanel;        // Custom panel to draw the floor and tables
    //Floorpanel class ta niche create korechi eta JPanel ke extend kore
    private JComboBox<String> menuCombo;  // Dropdown to select a menu item
    //JComboBox একটি Swing GUI component. এটি একটি ড্রপডাউন মেনু তৈরি করে যা ব্যবহারকারীদের একটি তালিকা থেকে একটি বিকল্প নির্বাচন করতে দেয়।
    private JSpinner qtySpinner;          // Spinner to set quantity
    private JComboBox<Integer> tableCombo;// Dropdown to select a table ID
    private JButton addOrderBtn;          // Button to add an order
    private JButton serveBtn;             // Button to mark a table served
    private JButton clearBtn;             // Button to clear a table
    private JButton totalBtn;            // Button to show total bill
    private JTextArea orderArea;          // Text area to show summary of orders
    // JTextArea একটি Swing GUI component — যা একটি multi-line text box তৈরি করে।
    // এটি ব্যবহারকারীদের বা প্রোগ্রামকে একাধিক লাইন টেক্সট প্রদর্শন বা সম্পাদনা করতে দেয়।
    //এখানে  orderArea হলো সেই text box-এর reference, যেটা আমরা রেস্টুরেন্টের অর্ডার summary দেখানোর জন্য ব্যবহার করছি।
    // এতে প্রতিটি টেবিলের অর্ডার, status, এবং bill summary দেখানো হয়।

    // Constructor: builds UI, initializes tables, loads data, wires events
    public RestaurantOrderSystem() { // Window constructor
        super("Restaurant Order System"); // Set window title
        //এটি একটি constructor call। এখানে super মানে হলো parent class-এর constructor কে কল করা।
        //আমাদের কোডে RestaurantOrderSystem ক্লাসটি JFrame কে extend করেছে, তাই JFrame হলো parent class।

        //• 	JFrame এর একটি constructor আছে যেটি একটি String argument নেয়।
        //• 	সেই String হলো window-এর title।
        //• 	তাই super("Restaurant Order System"); দিলে JFrame window-এর উপরে title bar-এ  লেখা দেখা যাবে।


        tables = new Table[rows * cols]; // Create array for all tables
        //• 	এখানে - tables হলো একটা ১-ডাইমেনশনাল অ্যারে।
        //• 	আমরা - rows * cols সংখ্যক টেবিল বানাচ্ছি।
        //• 	প্রতিটি টেবিলকে index দিয়ে access করি:
        //• 	সুবিধা: সহজে loop চালানো যায়, serialization (binary file save/load) সহজ হয়।
        //• 	অসুবিধা: row/col আলাদা করে বোঝা যায় না, সবকিছু index দিয়ে হিসাব করতে হয়।

        for (int i = 0; i < tables.length; i++) { // Initialize each table
            tables[i] = new Table(i + 1); // Assign human-friendly IDs starting at 1
        }

        floorPanel = new FloorPanel(); // Create drawing panel
        //• 	এখানে একটি custom JPanel subclass (FloorPanel) এর object তৈরি হচ্ছে।
        //• 	এর কাজ হলো রেস্টুরেন্টের টেবিল ম্যাপ আঁকা (circle, color, label ইত্যাদি)।
        //• 	পরে এটাকে -  add(floorPanel, BorderLayout.CENTER); দিয়ে main window-তে বসানো হয়

        menuCombo = new JComboBox<>(MENU_NAMES); // Menu dropdown with names
        // এখানে একটি dropdown list (JComboBox) তৈরি হচ্ছে।
        // 	MENU_NAMES হলো একটি String array (যেমন: "Coffee", "Tea", "Pizza")।
        // 	এই dropdown থেকে ইউজার মেনু আইটেম বেছে নিতে পারবে।

        qtySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1)); // Quantity spinner: start 0, min 0, max 20, step 1
        //• 	শুরু হবে 0 থেকে
        //• 	সর্বনিম্ন মান 0
        //• 	সর্বোচ্চ মান 20
        //• 	প্রতি ধাপে 1 করে বাড়বে/কমবে

        tableCombo = new JComboBox<>(); // Table selection dropdown
        for (Table t : tables) { // Populate table combo with table IDs
            tableCombo.addItem(t.tableId); // Add each table's ID
        }
        //• 	এখানে  array-এর প্রতিটি  object loop করা হচ্ছে।
        //• 	প্রতিটি টেবিলের  dropdown-এ যোগ করা হচ্ছে।
        //• 	ফলে dropdown-এ T1, T2, T3… এরকম টেবিল নম্বর দেখা যাবে।
        //• 	ইউজার এখান থেকে নির্দিষ্ট টেবিল বেছে নিতে পারবে।

        addOrderBtn = new JButton("Add Order"); // Button to add order
        serveBtn = new JButton("Mark Served");  // Button to mark served
        clearBtn = new JButton("Clear Table");  // Button to clear a table
        totalBtn = new JButton("Show Total Bill"); //button to show bill
        orderArea = new JTextArea(10, 30);      // Text area for summary
        //• 	- JTextArea হলো Swing-এর একটি GUI কম্পোনেন্ট, যেটা multi-line text box তৈরি করে।
        //• 	এখানে - (10, 30) মানে হলো:
        //• 	10 rows (মানে 10 লাইন টেক্সট দেখাতে পারবে)
        //Item 10 ta rakshi tai 10 line er beshi hobe na. Item barale row o barabo
        //• 	30 columns (মানে প্রতি লাইনে আনুমানিক 30 character জায়গা থাকবে)

        orderArea.setEditable(false);           // Make summary read-only
        //• 	ডিফল্টভাবে JTextArea ইউজারকে টেক্সট লিখতে দেয়।
        //• 	কিন্তু এখানে আমরা চাই ইউজার শুধু অর্ডার summary পড়ুক, লিখতে না পারুক।
        //• 	তাই setEditable(false) দিয়ে এটাকে read-only করা হয়েছে।


        setLayout(new BorderLayout()); // Use BorderLayout for main layout
        //• 	এখানে মূল JFrame-এর layout manager হিসেবে BorderLayout সেট করা হচ্ছে।
        //• 	BorderLayout মানে হলো window-কে ৫টা region-এ ভাগ করা যায়: NORTH, SOUTH, EAST, WEST, CENTER।
        //• 	পরে আমরা control panel-কে NORTH-এ, floorPanel-কে CENTER-এ, summary panel-কে EAST-এ boshabo।


        JPanel controlPanel = new JPanel(); // Panel to hold controls
        //• 	এখানে একটি নতুন JPanel তৈরি হচ্ছে।
        //• 	এই panel-এর কাজ হলো সব control (dropdown, spinner, button ইত্যাদি) একসাথে রাখা।
        //• 	Panel হলো ছোট container, যেটা main window-এর ভিতরে বসে।

        controlPanel.setLayout(new GridBagLayout()); // Flexible grid layout
        //• 	এই panel-এর layout manager হিসেবে GridBagLayout ব্যবহার করা হচ্ছে।
        //• 	GridBagLayout হলো খুব flexible layout — এখানে row/column coordinate দিয়ে component বসানো যায়।
        //• 	প্রতিটি component-এর জন্য আলাদা constraint দেওয়া যায় (যেমন padding, alignment, position)।
        GridBagConstraints gbc = new GridBagConstraints(); // Constraints for GridBag
        //• 	এখানে একটি GridBagConstraints object তৈরি হচ্ছে।
        //• 	এর মাধ্যমে আমরা প্রতিটি component-এর অবস্থান (x, y), padding, alignment ইত্যাদি নির্ধারণ করি।

        gbc.insets = new Insets(5, 5, 5, 5); // Padding around controls
        //• 	Insets মানে হলো component-এর চারপাশে margin/padding।
        //• 	এখানে প্রতিটি component-এর চারপাশে ৫ পিক্সেল জায়গা রাখা হচ্ছে।
        gbc.anchor = GridBagConstraints.WEST; // Left-align labels/controls
        //• 	Anchor মানে হলো component কোথায় align হবে।
        //• 	এখানে WEST মানে হলো বাম দিকে align করা হবে।

        gbc.gridx = 0; gbc.gridy = 0; controlPanel.add(new JLabel("Menu:"), gbc); // Menu label
        //• 	এখানে একটি JLabel ("Menu:") panel-এ বসানো হচ্ছে।
        //• 	এখানে ("Menu:") হলো সেই লেখা যা ইউজারকে দেখানো হবে।
        //• 	JLabel শুধু টেক্সট বা আইকন প্রদর্শন করে, ইউজার এটাতে কিছু লিখতে বা পরিবর্তন করতে পারে না।
        //• 	তাই এটা মূলত নাম/লেবেল হিসেবে কাজ করে — যেমন "Menu:", "Qty:", "Table:" ইত্যাদি।
        //• 	gbc.gridx = 0; → column 0
        //• 	gbc.gridy = 0; → row 0
        //• 	মানে grid-এর প্রথম cell-এ বসানো হলো।

        gbc.gridx = 1; controlPanel.add(menuCombo, gbc); // Menu dropdown
        //• 	এখানে menuCombo (dropdown) বসানো হচ্ছে।
        //• 	Column 1, Row 0 → label-এর পাশে বসানো হলো।
        gbc.gridx = 2; controlPanel.add(new JLabel("Qty:"), gbc); // Quantity label
        //• 	এখানে "Qty:" label বসানো হচ্ছে।
        //• 	Column 2, Row 0 → menu dropdown-এর পাশে।
        gbc.gridx = 3; controlPanel.add(qtySpinner, gbc); // Quantity spinner
        //• 	এখানে qtySpinner বসানো হচ্ছে।
        //• 	Column 3, Row 0 → quantity label-এর পাশে।

        gbc.gridx = 0; gbc.gridy = 1; controlPanel.add(new JLabel("Table:"), gbc); // Table label (Row=1, Column=0)
        //gbc.gridy = 1; ar dewa lagbena jotokkhon porjonto amra abar set na kori
        gbc.gridx = 1; controlPanel.add(tableCombo, gbc); // Table dropdown
        //automatic gbc.gridy er value 1 e thakbe

        gbc.gridx = 0; gbc.gridy = 2; controlPanel.add(addOrderBtn, gbc); // Add order button
        gbc.gridx = 1; controlPanel.add(serveBtn, gbc);                   // Mark served button
        gbc.gridx = 2; controlPanel.add(clearBtn, gbc);                   // Clear table button
        gbc.gridx = 3; controlPanel.add(totalBtn, gbc);                   // Place next to other buttons

        JPanel rightPanel = new JPanel(new BorderLayout()); // Right side panel. etake amra pore right side e place korbo
        //• 	- new JPanel(new BorderLayout()) → একটি panel তৈরি হলো, যার ভিতরে component বসানো যাবে BorderLayout অনুযায়ী (NORTH, SOUTH, EAST, WEST, CENTER)।
        //• 	কিন্তু এই লাইন একা panel-কে right side-এ বসায় না।
        //• 	Panel-কে main JFrame-এ বসাতে হবে এভাবে: add(rightPanel, BorderLayout.EAST);
        // eta amra pore korbo. ekhane shudhu ei rightPanel er kon pashe ki thakbe egulo set korchi

        rightPanel.add(new JLabel("Orders Summary:"), BorderLayout.NORTH); // Summary label
        rightPanel.add(new JScrollPane(orderArea), BorderLayout.CENTER);   // Scrollable text area

        add(controlPanel, BorderLayout.NORTH);  // Place controls at the top
        add(floorPanel, BorderLayout.CENTER);   // Center drawing panel
        add(rightPanel, BorderLayout.EAST);     // Summary on the right

        addOrderBtn.addActionListener(e -> { // Add order button handler
            int tableId = (Integer) tableCombo.getSelectedItem(); // Get selected table ID
            //	tableCombo হলো dropdown যেখানে টেবিল নম্বর থাকে।
            //  যদি ইউজার T3 বেছে নেয়, তাহলে tableId = 3 হবে।
            int qty = (Integer) qtySpinner.getValue();            // Get quantity
            int menuIndex = menuCombo.getSelectedIndex();         // Get selected menu index
            String name = MENU_NAMES[menuIndex];                  // Resolve item name
            double price = MENU_PRICES[menuIndex];                // Resolve item price
            //• 	menuCombo হলো dropdown যেখানে খাবারের নাম থাকে।
            //• 	ইউজার কোন খাবার বেছে নিয়েছে তার index বের করা হচ্ছে।
            //• 	সেই index দিয়ে MENU_NAMES[menuIndex]; থেকে নাম এবং MENU_PRICES থেকে দাম বের করা হচ্ছে।


            Table t = getTableById(tableId);                      // Find table by ID
            if (t == null) return;                                // Safety: if not found, exit
            //• 	এখানে নির্দিষ্ট table object খুঁজে বের করা হচ্ছে।
            //• 	যদি টেবিল না পাওয়া যায় (null হয়), তাহলে কোড থেমে যাবে।
            //  getTableById method ta niche create korechi
            // etar kaj holo tableId diye oi table object ta ber kora

            MenuItem item = new MenuItem(name, price);            // Create menu item
            Order order = new Order(item, qty);                   // Create order
            // MenuItem এবং Order class গুলোও নিচে create করেchi
            if (qty != 0) {                                         // If table has orders
                t.orders.append(order);                            // Append to table's order list
                t.status = TableStatus.PENDING;                   // Set table status to pending
                //• 	সেই অর্ডারকে টেবিলের linked list-এ যোগ করা হচ্ছে।
                //• 	টেবিলের status পরিবর্তন করে  করা হচ্ছে (মানে অর্ডার এসেছে, এখনো serve হয়নি)।
                saveAll();                                        // Save state to binary file
                refreshUI();                                      // Refresh UI and summary
            } else {
                JOptionPane.showMessageDialog(this, "No orders for this table."); // Info message
            }
        });

        serveBtn.addActionListener(e -> { // Mark served handler
            int tableId = (Integer) tableCombo.getSelectedItem(); // Get selected table ID
            Table t = getTableById(tableId);                      // Find table
            if (t == null) return;                                // Safety

            if (!t.orders.isEmpty()) {                            // If table has orders
                t.status = TableStatus.SERVED;                    // Mark as served
                saveAll();                                        // Persist
                refreshUI();                                      // Refresh UI
            } else {
                JOptionPane.showMessageDialog(this, "No orders for this table."); // Info message
            }
        });

        clearBtn.addActionListener(e -> { // Clear table handler
            int tableId = (Integer) tableCombo.getSelectedItem(); // Get selected table ID
            Table t = getTableById(tableId);                      // Find table
            if (t == null) return;                                // Safety

            t.orders.clear();                                     // Remove all orders
            t.status = TableStatus.AVAILABLE;                     // Reset status to available
            saveAll();                                            // Persist
            refreshUI();                                          // Refresh UI
        });

        totalBtn.addActionListener(e -> { // Total bill handler
            int tableId = (Integer) tableCombo.getSelectedItem(); // Get selected table
            Table t = getTableById(tableId); // Find table object
            if (t == null || t.orders.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No orders for this table.");
                return;
            }

            double total = 0.0;
            DoublyLinkedList.Node<Order> cur = t.orders.head; // Traverse order list
            //	t.orders.head মানে হলো সেই লিস্টের প্রথম অর্ডার।
            //• cur ভ্যারিয়েবল দিয়ে আমরা লিস্টের শুরু থেকে traversal শুরু করছি।


            // • 	এখানে একটি loop চলছে যতক্ষণ পর্যন্ত লিস্টে অর্ডার আছে (cur != null)।
            // • 	প্রতিটি node থেকে Order object বের করা হচ্ছে (cur.data)।
            // • 	তারপর সেই অর্ডারের দাম হিসাব করা হচ্ছে:
            // • 	o.item.price → খাবারের দাম
            // • 	o.quantity → কতগুলো খাবার
            // • 	গুণ করে মোট যোগ করা হচ্ছে total এ।
            // • 	শেষে cur = cur.next; দিয়ে পরের অর্ডারে চলে যাচ্ছে।


            while (cur != null) {
                Order o = cur.data;
                total += o.item.price * o.quantity; // Add item total
                cur = cur.next;
            }

            JOptionPane.showMessageDialog(this,
                "Total bill for Table " + tableId + " is ৳" + total,
                "Bill Summary", JOptionPane.INFORMATION_MESSAGE);
                // • 	সব অর্ডারের দাম যোগ হয়ে গেলে একটি dialog box দেখানো হচ্ছে।
                // • 	- JOptionPane.showMessageDialog GUI-তে একটি ছোট popup window তৈরি করে।
                // • 	এতে লেখা থাকবে:
                // • 	"Total bill for Table X is ৳Y"
                //      যেখানে X হলো টেবিল নম্বর, আর Y হলো মোট বিল।
                // • 	- "Bill Summary" হলো popup-এর শিরোনাম।
                // • 	- INFORMATION_MESSAGE মানে popup-এ info আইকন দেখাবে।

        });

        loadAll(); // Load persisted data on startup

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when window closes
        setSize(1000, 600);                             // Window size
        setLocationRelativeTo(null);                    // Center window
        //এটি JFrame (বা অন্য Swing window) এর একটি মেথড।
        //এর কাজ হলো window-এর অবস্থান নির্ধারণ করা।
        //যখন null দেওয়া হয়, তখন window টি স্ক্রিনের মাঝখানে অবস্থান করে। null মানে হলো screen-এর center।
        setVisible(true);                               // Show window
    } // End of class constructor

    // Helper to find a table object by its ID
    private Table getTableById(int id) { // Linear search by ID
        for (Table t : tables) { // Iterate tables
            if (t.tableId == id) return t; // Return matched table object from array list of tables(table object gula tables array te ache)
        }
        return null; // Not found
    }

    // Refresh drawing and text summary
    private void refreshUI() { // Central refresh method
        floorPanel.repaint();                 // Redraw floor and tables
        orderArea.setText(buildSummaryText()); // Update order summary text
    }

    // Build a human-readable summary of all tables and their orders
    private String buildSummaryText() { // Compose summary
        StringBuilder sb = new StringBuilder(); // Efficient string builder
        for (Table t : tables) { // For each table
            sb.append("Table ").append(t.tableId) // Table header
              .append(" — Status: ").append(t.status) // Status
              .append("\n"); // Newline
            if (t.orders.isEmpty()) { // If no orders
                sb.append("  (No orders)\n"); // Show empty info
            } else {
                // Traverse linked list using the correct static nested type reference
                DoublyLinkedList.Node<Order> cur = t.orders.head; // Start at head
                while (cur != null) { // Traverse nodes
                    Order o = cur.data; // Extract order object
                    sb.append("  - ")   // Bullet
                      .append(o.item.name) // Item name
                      .append(" x").append(o.quantity) // Quantity
                      .append(" @ ").append(o.item.price) // Unit price
                      .append("\n"); // Newline
                    cur = cur.next; // Move to next
                }
            }
            sb.append("\n"); // Blank line between tables
        }
        return sb.toString(); // Return summary
    }

    // Save all tables and orders to a binary file
    private void saveAll() { // Persistence writer
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(DATA_FILE))) { // Open output stream
            dos.writeInt(rows);            // Write row count
            dos.writeInt(cols);            // Write column count
            dos.writeInt(tables.length);   // Write total tables
            for (Table t : tables) {       // For each table
                dos.writeInt(t.tableId);                 // Write table ID
                dos.writeInt(t.status.ordinal());        // Write status ordinal (int)
// • 	dos → এটি একটি DataOutputStream object। এর কাজ হলো binary ফাইলে primitive ডেটা লিখে রাখা।
// • 	t.status → প্রতিটি টেবিলের status (TableStatus  enum) যেমন -  AVAILABLE, PENDING, SERVED ।
// • 	- .ordinal() → enum-এর অবস্থান (index) বের করে।
// • 	উদাহরণ:
// • 	- AVAILABLE.ordinal() → 0
// • 	- PENDING.ordinal() → 1
// • 	- SERVED.ordinal()  → 2
// • 	- dos.writeInt(...) → সেই integer মান binary ফাইলে লিখে রাখা হচ্ছে।


// 🧠 কিভাবে কাজ করে?
// - ধরো t.status = TableStatus.PENDING;
// - t.status.ordinal() → 1 রিটার্ন করবে।
// - dos.writeInt(1); → binary ফাইলে 1 লিখে দেবে।
// - পরে যখন ফাইল পড়া হবে:
// int statusOrdinal = dis.readInt();
// TableStatus status = TableStatus.values()[statusOrdinal];
// - → আবার enum status পাওয়া যাবে (PENDING)।

                dos.writeInt(t.orders.size());           // Write number of orders
                DoublyLinkedList.Node<Order> cur = t.orders.head; // Traverse orders
                while (cur != null) {                    // Loop through each order
                    Order o = cur.data;                  // Access order
                    dos.writeUTF(o.item.name);           // Write item name
                    dos.writeDouble(o.item.price);       // Write item price
                    dos.writeInt(o.quantity);            // Write quantity
                    cur = cur.next;                      // Next order
                }
            }//end of for. mane protita table er jonno barbar loop cholbe r same vabe data write korbe
        } catch (IOException e) { // Handle IO errors
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage()); // Show error
        }
    }

    // Load tables and orders from the binary file
    private void loadAll() { // Persistence reader
        File f = new File(DATA_FILE); // Create file object to check existence
        if (!f.exists()) {            // If file doesn't exist
            refreshUI();              // Show empty layout
            return;                   // Exit load
        }
        try (DataInputStream dis = new DataInputStream(new FileInputStream(DATA_FILE))) { // Open input stream
            rows = dis.readInt();              // Read row count
            cols = dis.readInt();              // Read column count
            int total = dis.readInt();         // Read total tables
            if (total != tables.length) {      // If saved layout size differs
                tables = new Table[total];     // Resize tables array
                for (int i = 0; i < total; i++) { // Create tables
                    tables[i] = new Table(i + 1);  // Assign IDs
                }
                tableCombo.removeAllItems();   // Refresh table combo list
                for (Table t : tables) {       // Repopulate table IDs
                    tableCombo.addItem(t.tableId); // Add ID
                }
            }
            //• 	যদি ফাইলে saved টেবিল সংখ্যা বর্তমান array-এর সাথে না মেলে → নতুন array বানানো হচ্ছে।
            //• 	প্রতিটি টেবিলকে নতুন ID দেওয়া হচ্ছে।
            //• 	তারপর -  tableCombo dropdown refresh করে সব টেবিল ID আবার যোগ করা হচ্ছে।

            for (int i = 0; i < tables.length; i++) { // Read table data
                int id = dis.readInt();                        // Read table ID
                //যখন আমরা -  saveAll() মেথডে টেবিলের ডেটা ফাইলে লিখি, তখন প্রথমেই প্রতিটি টেবিলের ID লিখে রাখি:
                // - মানে ফাইলে প্রথম integer হিসেবে টেবিলের ID রাখা হয়।
                //- যেহেতু ফাইলে প্রথমে টেবিলের ID লেখা হয়েছিলো, তাই পড়ার সময়ও প্রথমে সেই ID-ই পাওয়া যায়।

// - Save করার সময় যে ক্রমে ডেটা লেখা হয়, Load করার সময় সেই একই ক্রমে পড়তে হয়।
// - যদি এখানে অন্য কিছু বসানো হতো (যেমন status বা order count), তাহলে ডেটা mismatch হয়ে যেতো।
// - তাই এই integer সবসময় টেবিলের ID হিসেবেই বসে।


                int statusOrd = dis.readInt();                 // Read status ordinal
                int orderCount = dis.readInt();                // Read order count
                Table t = getTableById(id);                    // Get table object by ID
                if (t == null) {                               // If not found
                    t = new Table(id);                         // Create new table
                    tables[i] = t;                             // Place in array
                }
                t.status = TableStatus.values()[statusOrd];    // Restore status
                t.orders.clear();                              // Clear previous orders
                // - প্রতিটি টেবিলের জন্য:
                // - ID পড়া হচ্ছে
                // - Status ordinal পড়া হচ্ছে (enum restore করার জন্য)
                // - কতগুলো order আছে সেটা পড়া হচ্ছে
                // - তারপর সেই টেবিল object পাওয়া যাচ্ছে বা নতুন বানানো হচ্ছে।
                // - Status restore হচ্ছে, পুরনো orders clear হচ্ছে।
                //  প্রতিটি order restore করা হচ্ছে
                for (int k = 0; k < orderCount; k++) {         // Read each order
                    String name = dis.readUTF();               // Read item name
                    double price = dis.readDouble();           // Read item price
                    int qty = dis.readInt();                   // Read quantity
                    MenuItem item = new MenuItem(name, price); // Recreate menu item
                    Order order = new Order(item, qty);        // Recreate order
                    t.orders.append(order);                    // Append to list
                }
            }
            refreshUI(); // Update UI after load
        } catch (IOException e) { // Handle IO errors
            JOptionPane.showMessageDialog(this, "Error loading: " + e.getMessage()); // Show error
        }
    }

    // Custom panel that draws the restaurant tables on the floor
    private class FloorPanel extends JPanel { // Drawing panel
        public FloorPanel() { // Panel constructor
            setPreferredSize(new Dimension(600, 400)); // Preferred size for drawing area
            setBackground(Color.WHITE);                // White background

            addMouseListener(new MouseAdapter() { // Add mouse click handler
                @Override
                public void mouseClicked(MouseEvent e) { // On mouse click
                    int x = e.getX(); // Click X coordinate
                    int y = e.getY(); // Click Y coordinate
                    int index = hitTestTable(x, y); // Determine clicked table index
                    //method ta niche create korechi
                    if (index != -1) { // If a table was clicked
                        tableCombo.setSelectedItem(tables[index].tableId); // Select it in dropdown
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) { // Paint callback for drawing
            super.paintComponent(g); // Clear background
            //	Panel-এর পুরনো আঁকা মুছে ফেলে নতুন করে আঁকার জন্য background clear করে।

            int padding = 20; // Space around edges
            //Padding মানে হলো কোনো জিনিসের চারপাশে অতিরিক্ত ফাঁকা জায়গা রাখা।
            //GUI বা graphics আঁকার সময় padding ব্যবহার করা হয় যাতে component বা আঁকা জিনিসগুলো 
            //সরাসরি edge-এ না লেগে যায়, বরং চারপাশে একটু space থাকে।
            //	এখানে panel-এর চারপাশে 20 pixels ফাঁকা জায়গা রাখা হচ্ছে।
            int cellW = (getWidth() - 2 * padding) / cols;  // Cell width per column
            //	Panel-এর মোট width/height থেকে padding বাদ দিয়ে প্রতিটি cell-এর আকার বের করা হচ্ছে।
            int cellH = (getHeight() - 2 * padding) / rows; // Cell height per row
            int tableSize = Math.min(cellW, cellH) - 30;    // Table circle size
            //  কী হচ্ছে এখানে?
            // • 	cellW = প্রতিটি column-এর cell width
            // • 	cellH = প্রতিটি row-এর cell height
            // • 	Math.min(cellW, cellH) → দুইটার মধ্যে যেটা ছোট, সেটি নেওয়া হচ্ছে।
            // • 	তারপর সেই মান থেকে 30 বাদ দেওয়া হচ্ছে।
            // • 	ফলাফল tableSize → প্রতিটি টেবিলের circle-এর diameter (আকার)।

            //কেন -30 করা হলো?
            // • 	Circle পুরো cell-এর ভেতরে আঁকা হলে edge-এ লেগে যাবে।
            // • 	তাই একটু ছোট করা হয়েছে (30 pixel কমানো হয়েছে) → যাতে চারপাশে gap থাকে।
            // • 	এই gap-এর কারণে circle সুন্দরভাবে cell-এর মধ্যে বসে।



            for (int r = 0; r < rows; r++) { // For each row
                for (int c = 0; c < cols; c++) { // For each column
                    int index = r * cols + c;          // Compute table index
                    //• 	প্রতিটি row এবং column traverse করা হচ্ছে।
                    // • 	index = r * cols + c; দিয়ে টেবিল array-এর index বের করা হচ্ছে।
                    // • 	সেই index থেকে টেবিল object পাওয়া যাচ্ছে।

                    if (index >= tables.length) break; // Guard mismatches
                    Table t = tables[index];           // Get table

                    Color fill = COLOR_AVAILABLE; // Default green set hobe
                    //er por if else if check hobe. condition match na korle default color e thakbe.
                    if (t.status == TableStatus.PENDING) fill = COLOR_PENDING; // Pending = yellow
                    else if (t.status == TableStatus.SERVED) fill = COLOR_SERVED; // Served = red

                    int cx = padding + c * cellW + cellW / 2; // Center X of cell
                    int cy = padding + r * cellH + cellH / 2; // Center Y of cell
                    // cx এবং cy হলো প্রতিটি cell-এর center point-এর coordinate।
                    // • 	padding + c * cellW → cell-এর বাম দিকের edge থেকে শুরু করে cell-এর right edge পর্যন্ত দূরত্ব।
                    // • 	তারপর + cellW / 2 → cell-এর মাঝখানে যেতে হবে।
                    // • 	একইভাবে cy হিসাব করা হচ্ছে row অনুযায়ী।
                    int x = cx - tableSize / 2;               // Top-left X of circle
                    int y = cy - tableSize / 2;               // Top-left Y of circle
                    // x এবং y হলো circle-এর top-left corner-এর coordinate।
                    // • 	cx - tableSize / 2 → circle-এর center(cx) থেকে তার radius (tableSize / 2) বাদ দিলে top-left corner পাওয়া যায়।

                    g.setColor(fill); // Set fill color
                    g.fillOval(x, y, tableSize, tableSize); // Draw filled circle
                    // circle আঁকা হচ্ছে। ebong circle-এর ভিতর রঙ করা হচ্ছে।

                    g.setColor(Color.DARK_GRAY); // Outline color
                    g.drawOval(x, y, tableSize, tableSize); // Draw outline
                    // circle-এর বাইরের রেখা আঁকা হচ্ছে।

                    g.setColor(Color.BLACK); // Text color
                    String label = "TABLE : " + t.tableId; // Table label text
                    FontMetrics fm = g.getFontMetrics(); // Measure text
                    int tx = cx - fm.stringWidth(label) / 2; // Center X for text
                    int ty = cy + fm.getAscent() / 2 - 2;    // Center Y for text
                    // - টেবিলের ID দিয়ে label বানানো হচ্ছে (যেমন "TABLE : 3")।
                    // - FontMetrics দিয়ে টেক্সটের width/height মাপা হচ্ছে।
                    // - Center position হিসাব করে টেক্সট আঁকা হচ্ছে circle-এর মাঝখানে।
                    g.drawString(label, tx, ty); // Draw label
                }
            }
        }

        // Determine which table (if any) contains the clicked point
        private int hitTestTable(int x, int y) { // Returns table index or -1
            int padding = 20; // Same padding as paint
            int cellW = (getWidth() - 2 * padding) / cols; // Cell width
            int cellH = (getHeight() - 2 * padding) / rows; // Cell height
            int tableSize = Math.min(cellW, cellH) - 30; // Circle size
            int radius = tableSize / 2; // Circle radius

            for (int r = 0; r < rows; r++) { // Iterate rows
                for (int c = 0; c < cols; c++) { // Iterate cols
                    int index = r * cols + c; // Index in array
                    if (index >= tables.length) break; // Guard mismatch
                    int cx = padding + c * cellW + cellW / 2; // Center X
                    int cy = padding + r * cellH + cellH / 2; // Center Y
                    int dx = x - cx; // X delta from center
                    int dy = y - cy; // Y delta from center
                    //	ক্লিক করা point (x,y) থেকে circle center (cx,cy) পর্যন্ত horizontal ও vertical দূরত্ব বের করা হচ্ছে।
                    if (dx * dx + dy * dy <= radius * radius) { // Point inside circle?
                        return index; // Return table index
                    }
                }
            }
            return -1; // No hit
        }
    }

    // Model class for a table
    private static class Table { // Holds status and orders
        int tableId;                            // Unique table ID
        TableStatus status = TableStatus.AVAILABLE; // Default status
        DoublyLinkedList<Order> orders = new DoublyLinkedList<>(); // Order list per table

        Table(int id) { // Constructor
            this.tableId = id; // Set ID
        }
    }

    // Enum representing table statuses
    //- Enum (Enumeration) হলো Java-তে একটি বিশেষ data type।
    // - এর মাধ্যমে তুমি একটি নির্দিষ্ট সেটের constant মান একসাথে সংজ্ঞায়িত করতে পারো।
    // - উদাহরণ: সপ্তাহের দিন, ট্রাফিক লাইটের রঙ, টেবিলের status ইত্যাদি।
    // 👉 Enum ব্যবহার করলে কোডে fixed মানগুলোকে নাম দিয়ে ব্যবহার করা যায়, সংখ্যা বা string দিয়ে নয়। এতে কোড পড়া ও বোঝা সহজ হয়।

    // - এখানে TableStatus নামে একটি enum বানানো হলো।
    // - এর মধ্যে তিনটি constant আছে: AVAILABLE, PENDING, SERVED।
    // - এখন কোনো টেবিলের status সেট করতে চাইলে এভাবে লিখতে পারো:
    // TableStatus status = TableStatus.PENDING;



    // 🧠 কিভাবে কাজ করে?
    // - Enum তৈরি করা → তুমি একটি নির্দিষ্ট সেটের নাম দাও।
    // - Enum ব্যবহার করা → কোডে সেই নামগুলো দিয়ে মান সেট করা হয়।
    // - Ordinal → প্রতিটি enum constant-এর একটি index থাকে (0 থেকে শুরু)।
    // - AVAILABLE.ordinal() → 0
    // - PENDING.ordinal() → 1
    // - SERVED.ordinal() → 2
    // - values() → সব enum constant array আকারে পাওয়া যায়।
    // for (TableStatus s : TableStatus.values()) {
    //     System.out.println(s);
    // }
    // - → AVAILABLE, PENDING, SERVED প্রিন্ট হবে।
    private enum TableStatus { // Used for state and persistence via ordinal
        AVAILABLE, // No active orders
        PENDING,   // Orders placed, not served
        SERVED     // Orders served
    }

    // Model class for menu item
    private static class MenuItem { // Immutable menu item
        final String name;  // Name of the item
        final double price; // Price of the item

        MenuItem(String name, double price) { // Constructor
            this.name = name;  // Set name
            this.price = price; // Set price
        }
    }

    // Model class for an order (item + quantity)
    private static class Order { // Immutable order
        final MenuItem item; // Ordered item
        final int quantity;  // Quantity ordered

        Order(MenuItem item, int quantity) { // Constructor
            this.item = item;     // Set item
            this.quantity = quantity; // Set quantity
        }
    }

    // Generic doubly linked list implementation for storing orders
    private static class DoublyLinkedList<T> { // Minimal generic list
        // Static nested node class; use a separate generic type parameter to avoid capture issues
        //🔍 <T> কী?
        // • 	<T> হলো Generic Type Parameter।
        // • 	Java-তে Generics ব্যবহার করা হয় যাতে একই ক্লাস বা মেথড বিভিন্ন ধরনের ডেটার সাথে কাজ করতে পারে।
        // • 	এখানে T মানে হলো Type placeholder।
        // • 	যখন তুমি ক্লাস ব্যবহার করবে, তখন T-এর জায়গায় আসল টাইপ বসবে।

        //• 	এখানে <T> মানে হলো লিস্টে কী ধরনের ডেটা থাকবে।
        // • 	যদি তুমি -  DoublyLinkedList<Order>  বানাও → তাহলে -  T = Order  হবে।
        // • 	ফলে -  append(Order data) কাজ করবে।
        // • 	আবার যদি -  DoublyLinkedList<String> বানাও → তাহলে -  T = String হবে।


        static class Node<E> { // Linked list node
            E data;        // Payload data
            Node<E> prev;  // Previous node
            Node<E> next;  // Next node
            Node(E d) { this.data = d; } // Node constructor
        }

        Node<T> head; // Head (first) node reference
        Node<T> tail; // Tail (last) node reference
        int size = 0; // Count of nodes

        void append(T data) { // Add new element to the end
            Node<T> n = new Node<>(data); // Create a node carrying data
            if (head == null) {           // If list is empty
                head = tail = n;          // Head and tail point to the new node
            } else {
                tail.next = n;            // Link old tail to new node
                n.prev = tail;            // Back-link new node to old tail
                tail = n;                 // Move tail to the new node
            }
            size++; // Increase size
        }
        //• 	নতুন node তৈরি হয়।
        // • 	যদি লিস্ট খালি থাকে → head এবং tail দুটোই নতুন node হবে।
        // • 	যদি লিস্টে কিছু থাকে → পুরনো tail-এর সাথে নতুন node link হয়, তারপর tail নতুন node-এ চলে যায়।
        // • 	শেষে size বাড়ে।
        // 👉 মানে, নতুন element সবসময় লিস্টের শেষে যোগ হয়।


        boolean isEmpty() { // Check if list has no elements
            return size == 0; // True when size is zero
        }

        int size() { // Return number of elements
            return size; // Current size
        }

        void clear() { // Remove all elements
            head = tail = null; // Drop references so GC can collect
            size = 0;           // Reset size
        }
    }

    // Application entry point sets up UI on the Event Dispatch Thread
    public static void main(String[] args) { // Main method
        SwingUtilities.invokeLater(RestaurantOrderSystem::new); // Create and show app
    }
}
//🔍 কী হচ্ছে এখানে?
// • 	- SwingUtilities.invokeLater(...) → এটি একটি utility মেথড, যা কোনো কাজকে Event Dispatch Thread (EDT)-এ রান করায়।
// • 	- RestaurantOrderSystem::new → এটি একটি method reference (constructor reference)। মানে, 
//      যখন invokeLater রান হবে তখন নতুন -  RestaurantOrderSystem object তৈরি হবে।
// • 	ফলে GUI অ্যাপ্লিকেশন সঠিকভাবে তৈরি ও দেখানো হবে।

// ✅ কেন দরকার?
// • 	Swing হলো single-threaded GUI toolkit।
// • 	সব GUI update এবং event handling Event Dispatch Thread (EDT)-এ করতে হয়।
// • 	যদি GUI constructor বা update main thread-এ করা হয় → deadlock বা UI freeze হতে পারে।
// • 	তাই -  invokeLater() ব্যবহার করে নিশ্চিত করা হয় যে GUI creation EDT-তে হবে।

// 🧠 কিভাবে কাজ করে?
// 1. 	প্রোগ্রাম main thread থেকে শুরু হয়।
// 2. 	যখন এই লাইন রান হয়:
// SwingUtilities.invokeLater(RestaurantOrderSystem::new);
// • 	RestaurantOrderSystem::new মানে হলো → constructor call {new RestaurantOrderSystem()}।
// • 	কিন্তু সেটা সাথে সাথে রান হয় না, বরং queue-তে জমা হয়।
// 3. 	Event Dispatch Thread (EDT) যখন ফ্রি হয়, তখন সেই কাজ রান করে।
// 4. 	ফলে GUI তৈরি হয় এবং সঠিকভাবে screen-এ দেখায়।
