import javax.swing.*;      // GUI উপাদান (JFrame, JButton, JPanel ইত্যাদি) এর জন্য Swing লাইব্রেরি।
import java.awt.*;         // গ্রাফিক্স, রঙ, ফন্ট এবং লেআউট ম্যানেজারের জন্য AWT লাইব্রেরি।
import java.awt.event.*;   // মাউস ক্লিক, বাটন ক্লিক ইত্যাদি ইভেন্ট হ্যান্ডেল করার জন্য।
import java.io.*;          // ফাইল সেভ (Serialization) এবং লোড করার জন্য।
import java.util.*;        // লিস্ট, ম্যাপ (HashMap), ArrayList ইত্যাদি ডেটা স্ট্রাকচারের জন্য।
import java.util.List;     // List ইন্টারফেসটিকে বিশেষভাবে ইম্পোর্ট করা হলো।

/**
 * এই ক্লাসটি রেস্টুরেন্ট অর্ডার সিস্টেম অ্যাপ্লিকেশনের প্রধান অংশ।
 * এটি JFrame থেকে উত্তরাধিকার (extends) নেয়, তাই এটি একটি উইন্ডো তৈরি করতে পারে।
 * এটি Serializable ইন্টারফেস বাস্তবায়ন (implements) করে, তাই এই ক্লাসের
 * অবজেক্টের অবস্থা ফাইলে সেভ করা যায়।
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


    // --- স্ট্যাটিক কনস্ট্যান্টস এবং মেনু সেটআপ ---

    // ডেটা ফাইলটির নাম। এই ফাইলে বাইনারি ফরম্যাটে ডেটা সেভ হবে।
    private static final String DATA_FILE = "restaurant_data.ser"; // (.bin) dileo hoto

    // মেনুর আইটেমগুলোর অপরিবর্তনশীল (Immutable) লিস্ট তৈরি করা হলো।
    private static final List<MenuItem> MENU_ITEMS;
    
    //List<MenuItem>
    //এটি একটি টাইপ ডিক্লারেশন। এর মানে হলো MENU_ITEMS ভ্যারিয়েবলটি একটি List হবে।
    //MenuItem হলো আপনার তৈরি করা কাস্টম ক্লাস, যা প্রতিটি খাদ্য বস্তুকে (যেমন: "Steak Frites") প্রতিনিধিত্ব করে।
    //অর্থাৎ, এই লিস্টটি শুধুমাত্র MenuItem টাইপের অবজেক্ট ধারণ করতে পারবে।

    // স্ট্যাটিক ব্লক ব্যবহার করা হলো, যা ক্লাস লোড হওয়ার সময় একবারই চলে।
    static {
        // মেনু আইটেমগুলো ArrayList এ তৈরি করা হচ্ছে, যাতে List.of() এর চেয়ে সহজবোধ্য হয়।
        List<MenuItem> tempMenu = new ArrayList<>();
        tempMenu.add(new MenuItem("Steak Frites", 25.99)); // নতুন MenuItem অবজেক্ট তৈরি করে যোগ করা হলো।
        tempMenu.add(new MenuItem("Caesar Salad", 12.50));
        tempMenu.add(new MenuItem("Truffle Pasta", 18.00));
        tempMenu.add(new MenuItem("Cheeseburger", 15.75));
        tempMenu.add(new MenuItem("Soda", 3.00));
        tempMenu.add(new MenuItem("Espresso", 4.50));
        // তৈরি করা লিস্টটিকে অপরিবর্তনশীল (Immutable) লিস্টে পরিণত করা হলো।
        MENU_ITEMS = Collections.unmodifiableList(tempMenu);
    }

    // --- অবজেক্ট-অরিয়েন্টেড মডেল (OOP Model) ক্লাসসমূহ ---

    /**
     * রেস্টুরেন্টের মেনুর একক আইটেমকে উপস্থাপন করে।
     * Serializable ইমপ্লিমেন্ট করা আবশ্যক।
     */
    private static class MenuItem implements Serializable {
        private final String name;  // আইটেমের নাম।
        private final double price; // আইটেমের দাম।

        public MenuItem(String name, double price) { // MenuItem কনস্ট্রাক্টর।
            this.name = name;
            this.price = price;
        }

        public String getName() { return name; }    // নাম পাওয়ার জন্য Getter মেথড।
        public double getPrice() { return price; }  // দাম পাওয়ার জন্য Getter মেথড।

        // আইটেমকে পড়ার উপযোগী ফরম্যাটে দেখানোর জন্য।
        @Override
        public String toString() { return name + " ($" + String.format("%.2f", price) + ")"; } 
        
        // HashMap-এর জন্য equals() এবং hashCode() ব্যবহার করা হলো।
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MenuItem menuItem = (MenuItem) o;
            return name.equals(menuItem.name); // দুটি আইটেমের নাম সমান হলে তারা সমান।
        }
        @Override
        public int hashCode() { return name.hashCode(); } 
    }

    /**
     * একটি নির্দিষ্ট টেবিলের জন্য প্লেস করা অর্ডারকে উপস্থাপন করে।
     */
    private static class Order implements Serializable {
        private final int tableNumber;  // অর্ডারটি কোন টেবিলের জন্য।
        private final long timestamp;   // কখন অর্ডারটি প্লেস করা হয়েছে।
        
        // Key: MenuItem, Value: Quantity (সংখ্যা)।
        private Map<MenuItem, Integer> items = new HashMap<>(); 

        public Order(int tableNumber) { 
            this.tableNumber = tableNumber;
            this.timestamp = System.currentTimeMillis();
        }

        // আইটেম এবং তার সংখ্যা অর্ডারে যোগ করার মেথড।
        public void addItem(MenuItem item, int quantity) {
            // যদি আইটেমটি ইতিমধ্যেই ম্যাপে থাকে, তবে নতুন সংখ্যা যোগ করা হবে।
            if (items.containsKey(item)) {
                int currentQuantity = items.get(item);
                items.put(item, currentQuantity + quantity);
            } else {
                // যদি নতুন হয়, তবে সরাসরি যোগ করা হবে।
                items.put(item, quantity);
            }
        }

        // মোট বিল গণনা করার মেথড, এবার সহজ 'for loop' ব্যবহার করা হলো।
        public double calculateTotal() {
            double total = 0.0;
            // ম্যাপের প্রতিটি এন্ট্রির ওপর লুপ চালানো হলো।
            for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
                MenuItem item = entry.getKey();
                int quantity = entry.getValue();
                // (দাম * সংখ্যা) যোগ করা হলো।
                total += item.getPrice() * quantity;
            }
            return total;
        }

        // অর্ডারের সংক্ষিপ্ত বিবরণ (Summary) স্ট্রিং হিসেবে তৈরি করে।
        public String getSummary() {
            StringBuilder sb = new StringBuilder(); 
            // টেবিল নম্বর এবং সময় যুক্ত করা হলো (SimpleDateFormat ব্যবহার করে সহজ ফরম্যাট)।
            String time = new java.text.SimpleDateFormat("HH:mm").format(new Date(timestamp));
            sb.append("Order for Table " + tableNumber + " (Time: " + time + "):\n");
            
            // সব আইটেমের ওপর লুপ চালানো হলো।
            for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
                // সংখ্যা, আইটেমের নাম ফরম্যাট করে যোগ করা হলো।
                sb.append("- " + entry.getValue() + " x " + entry.getKey().getName() + "\n");
            }
            // মোট বিল যোগ করা হলো।
            sb.append("Total: $" + String.format("%.2f", calculateTotal()));
            return sb.toString();
        }
    }

    /**
     * টেবিলের স্ট্যাটাসগুলো Enum হিসেবে নির্ধারণ করা হয়েছে।
     */
    private enum TableStatus implements Serializable {
        AVAILABLE("Available", new Color(0, 150, 0)),        // সবুজ রঙ: টেবিল খালি।
        PENDING("Pending Order", new Color(255, 180, 0)),    // হলুদ/কমলা রঙ: অর্ডার অপেক্ষমাণ।
        SERVED("Served/Busy", new Color(180, 0, 0));         // লাল রঙ: খাবার সার্ভ হয়েছে।

        private final String label;  
        private final Color color;   

        TableStatus(String label, Color color) { // Enum কনস্ট্রাক্টর।
            this.label = label;
            this.color = color;
        }

        public String getLabel() { return label; } 
        public Color getColor() { return color; } 
    }

    /**
     * রেস্টুরেন্টের একটি ফিজিক্যাল টেবিলকে উপস্থাপন করে।
     */
    private static class Table implements Serializable {
        private final int number;        
        private TableStatus status;      
        private Order currentOrder;      
        private final int x, y;          

        public Table(int number, int x, int y) { // Table কনস্ট্রাক্টর।
            this.number = number;
            this.x = x;
            this.y = y;
            this.status = TableStatus.AVAILABLE; 
            this.currentOrder = null;
        }

        // গুরুত্বপূর্ণ গেটার মেথডসমূহ।
        public int getNumber() { return number; }
        public TableStatus getStatus() { return status; }
        public Order getCurrentOrder() { return currentOrder; }
        public int getX() { return x; }
        public int getY() { return y; }

        // একটি নতুন অর্ডার টেবিলের সাথে যুক্ত করার মেথড।
        public void placeOrder(Order order) { 
            this.currentOrder = order;
            this.status = TableStatus.PENDING; // স্ট্যাটাস 'PENDING' এ পরিবর্তিত হয়।
        }

        // টেবিলের স্ট্যাটাস পরিবর্তন করার মেথড।
        public void setStatus(TableStatus status) {
            this.status = status;
            // if স্টেটমেন্ট ব্যবহার করে লজিক।
            if (status == TableStatus.AVAILABLE) {
                this.currentOrder = null; // টেবিল খালি হলে অর্ডার মুছে ফেলা হয়।
            }
        }
    }

    // --- প্রধান অ্যাপ্লিকেশন স্টেট ভ্যারিয়েবলসমূহ ---

    private List<Table> tables;     // সমস্ত Table অবজেক্ট এই লিস্টে থাকে।
    private TableMapPanel mapPanel; 
    private JTabbedPane tabbedPane; 
    private OrderPanel orderPanel;   

    // --- ক্লাসের কনস্ট্রাক্টর: প্রোগ্রাম শুরুর মূল লজিক ---

    public RestaurantOrderSystem() { 
        setTitle("Restaurant Order System"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        loadData(); // আগের ডেটা লোড করার চেষ্টা।
        
        // যদি ডেটা লোড না হয়, তবে ডিফল্ট টেবিল তৈরি করা হয়।
        if (tables == null || tables.isEmpty()) { 
            initializeTables(); 
        }

        setupGUI(); // GUI সেটআপ করা হয়।
        setPreferredSize(new Dimension(850, 600)); // উইন্ডোর আকার নির্ধারণ।
        pack(); // ফ্রেমের আকার তার কম্পোনেন্ট অনুযায়ী সেট করা।
        setLocationRelativeTo(null); // উইন্ডোকে স্ক্রিনের মাঝখানে আনা।
        setVisible(true); // উইন্ডোটি দৃশ্যমান করা।

        // শাটডাউন হুক সেট করা: প্রোগ্রাম বন্ধ হওয়ার আগে saveData() কল হবে।
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveData())); 
    }

    // টেবিলগুলো তৈরি করে তাদের স্থানাঙ্ক সহ সেট করে।
    private void initializeTables() {
        tables = new ArrayList<>();
        tables.add(new Table(1, 100, 100)); 
        tables.add(new Table(2, 250, 100));
        tables.add(new Table(3, 100, 250));
        tables.add(new Table(4, 250, 250));
        tables.add(new Table(5, 500, 50));
        tables.add(new Table(6, 500, 180));
    }

    // প্রধান GUI কাঠামো সেটআপ করে।
    private void setupGUI() {
        tabbedPane = new JTabbedPane(); 
        
        // ১. ম্যাপ প্যানেল তৈরি এবং ট্যাবে যোগ করা।
        mapPanel = new TableMapPanel();
        tabbedPane.addTab("Restaurant Map", mapPanel); 

        // ২. অর্ডার প্যানেল তৈরি এবং ট্যাবে যোগ করা।
        orderPanel = new OrderPanel();
        tabbedPane.addTab("Order Management", orderPanel); 

        add(tabbedPane); 
        
        // লুক অ্যান্ড ফিল সেট করা।
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel.");
        }
    }
    
    // --- ডেটা সংরক্ষণ (Persistence: File I/O) ---
    
    /**
     * Java Serialization ব্যবহার করে টেবিলের বর্তমান অবস্থা বাইনারি ফাইলে সেভ করে।
     */
    private void saveData() {
        // try-catch ব্লক ব্যবহার করে I/O ত্রুটি হ্যান্ডেল করা হলো।
        try (FileOutputStream fos = new FileOutputStream(DATA_FILE); 
             ObjectOutputStream oos = new ObjectOutputStream(fos)) { 
            
            oos.writeObject(tables); // 'tables' লিস্টটিকে ফাইলে লেখা হলো।
            System.out.println("Data saved successfully to " + DATA_FILE); 
            
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage()); 
        }
    }

    /**
     * বাইনারি ফাইল থেকে টেবিলের আগের অবস্থা লোড করে।
     */
    @SuppressWarnings("unchecked") 
    private void loadData() {
        try (FileInputStream fis = new FileInputStream(DATA_FILE); 
             ObjectInputStream ois = new ObjectInputStream(fis)) { 
            
            tables = (List<Table>) ois.readObject(); // অবজেক্টটি পড়া হলো।
            System.out.println("Data loaded successfully from " + DATA_FILE);
            
        } catch (FileNotFoundException e) {
            System.out.println("No previous data file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading data (I/O error): " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading data (Class definition changed): " + e.getMessage());
        }
    }
    
    // --- কাস্টম GUI কম্পোনেন্টের ইনার ক্লাসসমূহ ---

    /**
     * টেবিলের ম্যাপ এবং স্ট্যাটাস আঁকার জন্য কাস্টম JPanel ক্লাস।
     */
    private class TableMapPanel extends JPanel {
        private static final int TABLE_SIZE = 50; 
        private Table selectedTable = null;      

        public TableMapPanel() {
            setPreferredSize(new Dimension(600, 400)); 
            setBackground(new Color(245, 245, 220)); 
            
            // মাউস ক্লিক ধরার জন্য লিসেনার যোগ করা হলো।
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedTable = null; 
                    
                    // সহজ for লুপ ব্যবহার করে ক্লিক করা টেবিল খুঁজে বের করা হলো।
                    for (int i = 0; i < tables.size(); i++) {
                        Table table = tables.get(i);
                        // ক্লিকটি টেবিলের স্থানাঙ্কের মধ্যে পড়েছে কিনা পরীক্ষা করা হলো।
                        if (e.getX() >= table.getX() && e.getX() <= table.getX() + TABLE_SIZE &&
                            e.getY() >= table.getY() && e.getY() <= table.getY() + TABLE_SIZE) {
                            
                            selectedTable = table; 
                            break; 
                        }
                    }
                    
                    // if স্টেটমেন্ট ব্যবহার করে লজিক।
                    if (selectedTable != null) {
                        orderPanel.setTable(selectedTable);
                        tabbedPane.setSelectedIndex(1); // "Order Management" ট্যাবে সুইচ করা হলো।
                    }
                    
                    repaint(); // প্যানেলটি আবার আঁকার জন্য অনুরোধ।
                }
            });
        }

        // গ্রাফিক্স আঁকার মূল মেথড।
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            Graphics2D g2d = (Graphics2D) g; 
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // বার বা কিচেন এলাকা চিহ্নিত করা।
            g2d.setColor(new Color(220, 220, 200)); 
            g2d.fillRect(450, 0, 150, 400); 
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawRect(450, 0, 150, 400); 

            // সমস্ত টেবিল আঁকা হলো।
            for (int i = 0; i < tables.size(); i++) {
                Table table = tables.get(i);
                
                // টেবিলের স্ট্যাটাস অনুযায়ী রং সেট করা হলো।
                g2d.setColor(table.getStatus().getColor());
                
                // টেবিলটিকে একটি ভরা বৃত্ত হিসেবে আঁকা হলো।
                g2d.fillOval(table.getX(), table.getY(), TABLE_SIZE, TABLE_SIZE);
                
                // টেবিলের চারপাশে বর্ডার আঁকা হলো।
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2)); 
                g2d.drawOval(table.getX(), table.getY(), TABLE_SIZE, TABLE_SIZE);
                
                // টেবিল নম্বর লেখা হলো।
                g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
                String tableNum = String.valueOf(table.getNumber());
                FontMetrics fm = g2d.getFontMetrics(); 
                int textWidth = fm.stringWidth(tableNum);
                int x = table.getX() + (TABLE_SIZE - textWidth) / 2;
                int y = table.getY() + (TABLE_SIZE - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(tableNum, x, y);
            }
            
            drawLegend(g2d); 
        }
        
        // স্ট্যাটাস লেজেন্ড আঁকার মেথড।
        private void drawLegend(Graphics2D g2d) {
            int x = 20; 
            int y = getHeight() - 80; 
            int boxSize = 15; 
            
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            g2d.drawString("STATUS LEGEND:", x, y);
            y += 20;

            // Enum-এর প্রতিটি স্ট্যাটাস ধরে লেজেন্ড তৈরি করা হলো।
            TableStatus[] statuses = TableStatus.values();
            for (int i = 0; i < statuses.length; i++) {
                TableStatus status = statuses[i];
                g2d.setColor(status.getColor());
                g2d.fillRect(x, y, boxSize, boxSize); 
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, boxSize, boxSize); 
                g2d.drawString(status.getLabel(), x + boxSize + 5, y + boxSize - 3); 
                y += 20; 
            }
        }
    }

    /**
     * অর্ডার প্লেস করা এবং স্ট্যাটাস পরিবর্তন করার জন্য JPanel ক্লাস।
     */
    private class OrderPanel extends JPanel {
        private Table table; 
        private JComboBox<String> tableSelector; 
        private JTextArea orderSummaryArea;    
        private JPanel menuPanel;             
        private JButton placeOrderButton;      
        private JButton servedButton;          
        private JButton clearButton;           

        // MenuItem অবজেক্টের সাথে সংশ্লিষ্ট JSpinner-এর রেফারেন্স রাখার জন্য ম্যাপ।
        private final Map<MenuItem, JSpinner> quantityInputs = new HashMap<>();

        public OrderPanel() {
            setPreferredSize(new Dimension(800, 500)); 
            setLayout(new BorderLayout(10, 10)); 
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
            
            // --- ১. উপরের অংশ: টেবিল নির্বাচন ---
            tableSelector = new JComboBox<>();
            // সহজ ইভেন্ট হ্যান্ডেলিং এর জন্য ActionListener ব্যবহার করা হলো।
            tableSelector.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedItem = (String)tableSelector.getSelectedItem();
                    if (selectedItem != null) {
                         int selectedTableNum = Integer.parseInt(selectedItem.split(" ")[1]);
                         Table selectedTable = findTableByNumber(selectedTableNum); // সহজ মেথড ব্যবহার করা হলো।
                         setTable(selectedTable);
                    }
                }
            });
            
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
            topPanel.add(new JLabel("Current Table:"));
            topPanel.add(tableSelector);
            add(topPanel, BorderLayout.NORTH); 

            // --- ২. মাঝের অংশ: মেনু নির্বাচন ---
            menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(MENU_ITEMS.size(), 3, 5, 5));
            JScrollPane menuScrollPane = new JScrollPane(menuPanel); 
            menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu Selection (Enter Quantity)"));
            add(menuScrollPane, BorderLayout.CENTER); 
            
            setupMenuInput(); 

            // --- ৩. ডান পাশের অংশ: সামারি এবং বাটন ---
            JPanel eastPanel = new JPanel(new BorderLayout(5, 5));
            eastPanel.setPreferredSize(new Dimension(300, 400)); 
            
            orderSummaryArea = new JTextArea(10, 30); 
            orderSummaryArea.setEditable(false); 
            orderSummaryArea.setBorder(BorderFactory.createTitledBorder("Order Summary"));
            eastPanel.add(new JScrollPane(orderSummaryArea), BorderLayout.CENTER); 

            // বাটনগুলো উল্লম্বভাবে সাজানোর জন্য প্যানেল।
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); 
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            
            // বাটন তৈরি এবং অ্যাকশন লিসেনার তৈরি করা হলো।
            placeOrderButton = createActionButton("Place Order (Pending)", new Color(25, 118, 210), new ActionListener() {
                public void actionPerformed(ActionEvent e) { placeOrder(e); }
            });
            servedButton = createActionButton("Mark Served (Served)", new Color(180, 0, 0), new ActionListener() {
                public void actionPerformed(ActionEvent e) { markServed(e); }
            });
            clearButton = createActionButton("Clear Table (Available)", new Color(0, 150, 0), new ActionListener() {
                public void actionPerformed(ActionEvent e) { clearTable(e); }
            });
            
            buttonPanel.add(placeOrderButton);
            buttonPanel.add(Box.createVerticalStrut(5)); 
            buttonPanel.add(servedButton);
            buttonPanel.add(Box.createVerticalStrut(5)); 
            buttonPanel.add(clearButton);
            
            placeOrderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            servedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            eastPanel.add(buttonPanel, BorderLayout.SOUTH); 
            add(eastPanel, BorderLayout.EAST); 
            
            populateTableSelector(); 
        }
        
        // নম্বর দিয়ে টেবিল খুঁজে বের করার সহজ মেথড।
        private Table findTableByNumber(int tableNumber) {
            for (int i = 0; i < tables.size(); i++) {
                Table t = tables.get(i);
                if (t.getNumber() == tableNumber) {
                    return t;
                }
            }
            return null;
        }

        // বাটন তৈরির সহায়ক মেথড।
        private JButton createActionButton(String text, Color color, ActionListener listener) {
            JButton button = new JButton(text);
            button.setBackground(color); 
            button.setForeground(Color.WHITE); 
            button.setFocusPainted(false); 
            button.setOpaque(true); 
            button.setBorderPainted(false); 
            button.setPreferredSize(new Dimension(250, 40)); 
            button.setMaximumSize(new Dimension(300, 40)); 
            button.addActionListener(listener); 
            return button;
        }

        // টেবিল সিলেক্টর ড্রপডাউনটি পূরণ করার মেথড।
        private void populateTableSelector() {
            tableSelector.removeAllItems(); 
            // ArrayList দিয়ে টেবিল নম্বরগুলো সাজানো হলো।
            List<Table> sortedTables = new ArrayList<>(tables);
            // Collections.sort ব্যবহার করে সাজানো হলো।
            Collections.sort(sortedTables, new Comparator<Table>() {
                @Override
                public int compare(Table t1, Table t2) {
                    return Integer.compare(t1.getNumber(), t2.getNumber());
                }
            });

            for (int i = 0; i < sortedTables.size(); i++) {
                tableSelector.addItem("Table " + sortedTables.get(i).getNumber());
            }
            
            if (!tables.isEmpty()) {
                setTable(tables.get(0)); 
            }
        }

        // মেনু আইটেমগুলির জন্য ইনপুট স্পিনার তৈরি করা হলো।
        private void setupMenuInput() {
            for (int i = 0; i < MENU_ITEMS.size(); i++) {
                MenuItem item = MENU_ITEMS.get(i);
                menuPanel.add(new JLabel(item.toString())); // MenuItem এর toString() ব্যবহার।
                
                SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 99, 1);
                JSpinner spinner = new JSpinner(model);
                quantityInputs.put(item, spinner); 
                menuPanel.add(spinner); 
            }
        }
        
        // বর্তমানে নির্বাচিত টেবিল পরিবর্তন করে GUI আপডেট করার মেথড।
        public void setTable(Table t) {
            if (t == null) return; 
            this.table = t;
            
            String tableItem = "Table " + t.getNumber();
            // JComboBox এর মাধ্যমে টেবিলটি সিলেক্ট করা হলো।
            if (!tableItem.equals(tableSelector.getSelectedItem())) {
                 tableSelector.setSelectedItem(tableItem);
            }
            
            updateDisplay(); 
        }

        // GUI উপাদান (সামারি, স্পিনার, বাটন) আপডেট করার মেথড।
        private void updateDisplay() {
            if (table == null) return;

            // অর্ডারের সামারি আপডেট করা হলো।
            if (table.getCurrentOrder() != null) {
                orderSummaryArea.setText(table.getCurrentOrder().getSummary());
            } else {
                orderSummaryArea.setText("No order currently placed for Table " + table.getNumber());
            }

            // সমস্ত স্পিনারকে আবার 0 তে সেট করা হলো।
            for (JSpinner spinner : quantityInputs.values()) {
                spinner.setValue(0);
            }
            
            // বাটনগুলো এনাবল/ডিসাবল করার জন্য if-else লজিক।
            // Place Order বাটন সবসময় এনাবল থাকে যখন টেবিল সিলেক্ট করা থাকে।
            placeOrderButton.setEnabled(true); 
            
            if (table.getStatus() == TableStatus.PENDING) {
                servedButton.setEnabled(true);
                clearButton.setEnabled(true);
            } else if (table.getStatus() == TableStatus.SERVED) {
                servedButton.setEnabled(false); // একবার সার্ভড হলে আবার সার্ভ করা যায় না।
                clearButton.setEnabled(true);
            } else { // TableStatus.AVAILABLE
                servedButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
            
            mapPanel.repaint(); 
        }

        // "Place Order" বাটনের অ্যাকশন হ্যান্ডেলার।
        private void placeOrder(ActionEvent e) {
            if (table == null) return;

            Order newOrder = new Order(table.getNumber()); 
            int totalItems = 0;
            
            // সহজ for লুপ ব্যবহার করে স্পিনার থেকে সংখ্যাগুলো নিয়ে অর্ডারে যোগ করা হলো।
            for (Map.Entry<MenuItem, JSpinner> entry : quantityInputs.entrySet()) {
                MenuItem item = entry.getKey();
                JSpinner spinner = entry.getValue();
                int quantity = (Integer) spinner.getValue(); 
                
                if (quantity > 0) {
                    newOrder.addItem(item, quantity); 
                    totalItems += quantity;
                }
            }

            // যদি কোনো আইটেম সিলেক্ট না হয়।
            if (totalItems == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Please select at least one item and quantity to place an order.", 
                    "Order Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // নতুন অর্ডার নাকি আপডেট, তা চেক করা হলো।
            String message;
            if (table.getCurrentOrder() == null) {
                message = "New order placed for Table " + table.getNumber() + ".";
            } else {
                message = "Order for Table " + table.getNumber() + " updated.";
            }
            
            table.placeOrder(newOrder); // অর্ডারটি টেবিলে সেট করা হলো (স্ট্যাটাস PENDING হবে)।
            
            JOptionPane.showMessageDialog(this, 
                message + " Total: $" + String.format("%.2f", newOrder.calculateTotal()), 
                "Order Placed", JOptionPane.INFORMATION_MESSAGE);
            
            updateDisplay(); 
        }

        // "Mark Served" বাটনের অ্যাকশন হ্যান্ডেলার।
        private void markServed(ActionEvent e) {
            if (table == null || table.getStatus() != TableStatus.PENDING) return;
            
            table.setStatus(TableStatus.SERVED); // স্ট্যাটাস SERVED করা হলো (রং লাল হবে)।
            JOptionPane.showMessageDialog(this, 
                "Table " + table.getNumber() + " marked as Served.", 
                "Status Change", JOptionPane.INFORMATION_MESSAGE);
            updateDisplay(); 
        }
        
        // "Clear Table" বাটনের অ্যাকশন হ্যান্ডেলার।
        private void clearTable(ActionEvent e) {
            if (table == null || table.getStatus() == TableStatus.AVAILABLE) return;
            
            // কনফার্মেশন ডায়ালগ বক্স।
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to clear Table " + table.getNumber() + "? This clears the order history.",
                "Confirm Clear Table", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) { 
                table.setStatus(TableStatus.AVAILABLE); // স্ট্যাটাস AVAILABLE করা হলো (রং সবুজ হবে)।
                JOptionPane.showMessageDialog(this, 
                    "Table " + table.getNumber() + " marked as Available.", 
                    "Status Change", JOptionPane.INFORMATION_MESSAGE);
                updateDisplay(); 
            }
        }
    }

    // --- Main মেথড ---

    public static void main(String[] args) {
        // GUI অপারেশন যেন সঠিক থ্রেডে চলে, তা নিশ্চিত করা হলো।
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RestaurantOrderSystem();
            }
        });
    }
}