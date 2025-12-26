import java.awt.*;          // AWT (Abstract Window Toolkit) এর Graphics ও Color ক্লাস ব্যবহার করার জন্য ইমপোর্ট
import javax.swing.*;       // Swing লাইব্রেরি ইমপোর্ট, JFrame ও JPanel ব্যবহার করার জন্য

public class BasicGraphics extends JPanel {   // BasicGraphics নামে একটি ক্লাস তৈরি, যা JPanel কে extend করছে

    @Override
    protected void paintComponent(Graphics g) {   // JPanel এর paintComponent মেথড ওভাররাইড করা হচ্ছে
        super.paintComponent(g);                  // প্যারেন্ট ক্লাসের paintComponent কল করা হচ্ছে (background পরিষ্কার করার জন্য)

        // লাইন আঁকা
        g.drawLine(20, 20, 100, 20);              // (20,20) থেকে (100,20) পর্যন্ত একটি লাইন আঁকবে

        // আয়তক্ষেত্র আঁকা
        g.drawRect(20, 40, 80, 40);               // (20,40) থেকে 80x40 সাইজের একটি ফাঁকা আয়তক্ষেত্র আঁকবে
        

        // রঙ সেট করা (হালকা নীল)
        g.setColor(Color.CYAN);
        // ভরাট আয়তক্ষেত্র আঁকা
        g.fillRect(120, 40, 80, 40);     // (120,40) থেকে 80x40 সাইজের একটি হালকা নীল ভরাট আয়তক্ষেত্র আঁকবে

        // আয়তক্ষেত্রের চারপাশে বর্ডার আঁকা
        g.setColor(Color.BLACK);
        g.drawRect(120, 40, 80, 40);

        // টেক্সটের রঙ সেট করা
        g.setColor(Color.RED);
        // টেক্সট লেখা (আয়তক্ষেত্রের ভেতরে)
        g.drawString("RECTANGLE", 120, 65);

        

        // বৃত্ত আঁকা
        g.setColor(Color.RED);                    // রঙকে লাল করা হচ্ছে
        g.drawOval(20, 100, 80, 80);              // (20,100) থেকে 80x80 সাইজের একটি ফাঁকা বৃত্ত আঁকবে

        // ভরাট বৃত্ত
        g.setColor(Color.GREEN);                  // রঙকে সবুজ করা হচ্ছে
        g.fillOval(120, 100, 80, 80);             // (120,100) থেকে 80x80 সাইজের একটি সবুজ ভরাট বৃত্ত আঁকবে

        // টেক্সট লেখা
        g.setColor(Color.BLACK);                  // রঙকে কালো করা হচ্ছে
        g.drawString("Hello Graphics!", 20, 220); // (20,220) পজিশনে "Hello Graphics!" লেখা দেখাবে
    }

    public static void main(String[] args) {
        System.out.println("Starting Basic Graphics Example"); // কনসোলে একটি বার্তা প্রিন্ট করবে

        JFrame frame = new JFrame("Basic Graphics Example");   // একটি JFrame উইন্ডো তৈরি করবে, টাইটেল হবে "Basic Graphics Example"
        BasicGraphics panel = new BasicGraphics();             // BasicGraphics ক্লাসের একটি অবজেক্ট তৈরি করবে (যেখানে আঁকা হবে)

        frame.add(panel);                                      // panel কে JFrame এ যোগ করা হচ্ছে
        frame.setSize(300, 300);                               // JFrame এর সাইজ 300x300 সেট করা হচ্ছে
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // উইন্ডো বন্ধ করলে প্রোগ্রামও বন্ধ হবে
        frame.setVisible(true);                                // JFrame কে দৃশ্যমান করা হচ্ছে
    }
}