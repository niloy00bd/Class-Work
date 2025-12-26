// Import necessary Swing and AWT classes
import javax.swing.*;   // Provides JFrame, JPanel, JButton, SwingUtilities, etc.
import java.awt.*;      // Provides Graphics, Color, BorderLayout
import java.awt.event.*; // Provides ActionListener and ActionEvent for event handling
import java.util.Random; // Provides Random class for generating random positions

// Main class that extends JFrame (the main window)
public class Main extends JFrame {
    // Declare components: drawing panel and buttons
    private DrawingPanel drawingPanel;
    private JButton drawButton, clearButton;

    // Constructor: sets up the GUI
    public Main() {
        setTitle("Circle Drawer"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close program when window closes
        setSize(500, 500); // Set window size (width=500, height=500)
        setLayout(new BorderLayout()); // Use BorderLayout for arranging components

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel(); // Simple container for buttons
        drawButton = new JButton("Draw Circle"); // Button to draw circle
        clearButton = new JButton("Clear"); // Button to clear panel

        // Add buttons to the button panel
        buttonPanel.add(drawButton);
        buttonPanel.add(clearButton);

        // Create custom drawing panel
        drawingPanel = new DrawingPanel();

        // Add components to the frame
        add(buttonPanel, BorderLayout.SOUTH); // Place button panel at bottom
        add(drawingPanel, BorderLayout.CENTER); // Place drawing panel in center

        // Event handling for "Draw Circle" button
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.drawRandomCircle(); // Call method to draw circle
            }
        });

        // Event handling for "Clear" button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clear(); // Call method to clear panel
            }
        });
    }

    // Inner class: custom panel for drawing
    private static class DrawingPanel extends JPanel {
        // Variables to store circle position and size
        private int x = -1, y = -1, radius = 50; // Default radius = 50
        private boolean hasCircle = false; // Flag to check if circle should be drawn
        private Random random = new Random(); // Random generator for positions

        // Method to generate random circle position
        public void drawRandomCircle() {
            int panelWidth = getWidth();   // Get current panel width
            int panelHeight = getHeight(); // Get current panel height

            // Generate random x, y ensuring circle fits inside panel
            x = random.nextInt(panelWidth - radius);
            y = random.nextInt(panelHeight - radius);

            hasCircle = true; // Set flag to true (circle exists)
            repaint(); // Request panel redraw
        }

        // Method to clear the panel
        public void clear() {
            hasCircle = false; // Reset flag (no circle)
            repaint(); // Request panel redraw
        }

        // Override paintComponent to perform custom drawing
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Call parent method to clear background
            if (hasCircle) { // Only draw if circle exists
                g.setColor(Color.RED); // Set drawing color to red
                g.fillOval(x, y, radius, radius); // Draw filled circle at (x,y)
            }
        }
    }

    // Main method: entry point of program
    public static void main(String[] args) {
        // Run GUI creation on Event Dispatch Thread (safe practice in Swing)
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main(); // Create main window
            frame.setVisible(true); // Make window visible
        });
    }
}