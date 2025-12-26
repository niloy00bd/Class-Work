// Import Swing classes like JFrame, JPanel, JButton
import javax.swing.*;

// Import AWT classes like Graphics and Color
import java.awt.*;

// Import event classes like ActionListener and ActionEvent
import java.awt.event.*;

// Import Random class to generate random positions
import java.util.Random;

// Main class that extends JFrame (the main window)
public class SimpleCircleDrawer extends JFrame {
    // Declare two buttons
    private JButton drawButton;   // Button to draw a circle
    private JButton clearButton;  // Button to clear the panel

    // Declare the drawing panel
    private JPanel drawingPanel;  // Panel where circle will be drawn

    // Variables for circle position and size
    private int x = -1, y = -1;   // Coordinates of circle
    private int radius = 50;      // Circle radius (size)
    private boolean hasCircle = false; // Flag to check if circle exists

    // Random object to generate random positions
    private Random random = new Random();

    // Constructor: sets up the window and components
    public SimpleCircleDrawer() {
        setTitle("Simple Circle Drawer"); // Set window title
        setSize(400, 400);                // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close program when window closes

        // Create the "Draw Circle" button
        drawButton = new JButton("Draw Circle");

        // Create the "Clear" button
        clearButton = new JButton("Clear");

        // Create the drawing panel (anonymous inner class)
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Clear background before drawing
                if (hasCircle) {         // Only draw if circle exists
                    g.setColor(Color.RED); // Set circle color to red
                    g.fillOval(x, y, radius, radius); // Draw filled circle at (x,y)
                }
            }
        };

        // Add event handling for "Draw Circle" button
        drawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get panel width and height
                int w = drawingPanel.getWidth();
                int h = drawingPanel.getHeight();

                // Generate random x and y so circle fits inside panel
                x = random.nextInt(w - radius);
                y = random.nextInt(h - radius);

                hasCircle = true;           // Set flag to true (circle exists)
                drawingPanel.repaint();     // Refresh panel to show circle
            }
        });

        // Add event handling for "Clear" button
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hasCircle = false;          // Remove circle (flag false)
                drawingPanel.repaint();     // Refresh panel to clear drawing
            }
        });

        // Create a panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(drawButton); // Add "Draw Circle" button
        buttonPanel.add(clearButton); // Add "Clear" button

        // Add drawing panel to center of window
        add(drawingPanel, BorderLayout.CENTER);

        // Add button panel to bottom of window
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Main method: program entry point
    public static void main(String[] args) {
        // Run GUI creation on Event Dispatch Thread (safe practice in Swing)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleCircleDrawer().setVisible(true); // Create window and show it
            }
        });
    }
}