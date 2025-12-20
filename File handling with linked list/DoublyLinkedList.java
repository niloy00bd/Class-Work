import java.io.*; // Import Java's Input/Output classes for reading/writing files
import java.util.Scanner; // Import Scanner class for reading user input
import java.util.ArrayList; // Optional: useful if we want to store nodes temporarily in a list

// Node class represents each element in the doubly linked list
class Node {
    int data;       // Holds the actual value of the node
    Node prev;      // Points to the previous node (null if it's the first node)
    Node next;      // Points to the next node (null if it's the last node)

    // Constructor initializes a node with given data and sets prev/next to null
    Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

// Main class to manage the doubly linked list and file operations
public class DoublyLinkedList {
    Node head; // Points to the first node in the list (null if list is empty)

    // Adds a new node with given data at the end of the list
    public void append(int data) {
        Node newNode = new Node(data); // Create a new node with the given data

        if (head == null) { // If list is empty, make newNode the head
            head = newNode;
            return; // Exit since we've added the first node
        }

        // Traverse to the last node
        Node current = head;
        while (current.next != null) {
            current = current.next; // Move forward until we reach the end
        }

        // Link the new node at the end
        current.next = newNode;     // Set current node's next to newNode
        newNode.prev = current;     // Set newNode's prev to current node
    }

    // Prints the list from head to tail (forward direction)
    public void printForward() {
        Node current = head; // Start from the head
        System.out.print("Forward: ");
        while (current != null) {


            System.out.print(current.data + " <=> "); // Print current node's data
            current = current.next; // Move to the next node
        }
        System.out.println("null"); // Indicates end of the list
    }

    // Saves the current list to a binary file so it can be reused later
    public void saveToFile(String filename) {
        try (
            // Create a binary output stream to write data to file
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))
        ) {
            Node current = head; // Start from the head
            while (current != null) {
                dos.writeInt(current.data); // Write each node's data as binary integer
                current = current.next;     // Move to the next node
            }
        } catch (IOException e) {
            // If any error occurs during writing, print the error message
            //তুমি যদি catch না লেখো, তাহলে প্রোগ্রাম হঠাৎ বন্ধ হয়ে যাবে। কিন্তু catch লিখলে, JVM বলে:
            //"আচ্ছা, সমস্যা হয়েছে, কিন্তু আমি তোমার catch ব্লকে গিয়ে সেটা হ্যান্ডেল করে ফেলছি।"

            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Loads data from a binary file and reconstructs the linked list
    public void loadFromFile(String filename) {
        try (
            // Create a binary input stream to read data from file
            DataInputStream dis = new DataInputStream(new FileInputStream(filename))
        ) {
            while (true) {
                int data = dis.readInt(); // Read one integer from the file
                append(data);             // Add it to the list
            }
        } catch (EOFException e) {
            // End of file reached — this is expected when all data is read
        } catch (IOException e) {
            // If any error occurs during reading, print the error message
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    // Main method — entry point of the program
//     public static void main(String[] args) throws IOException {
//         DoublyLinkedList list = new DoublyLinkedList(); // Create a new empty list
//         String filename = "linkedlist.bin";             // Name of the binary file to store data

//         list.loadFromFile(filename); // Load existing data from file into the list

//         list.printForward();         // Print the current list (before adding new data)

//         // Ask user to enter new data to add to the list
//         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Read input from console
//         System.out.print("Enter new data to append: ");
//         int newData = Integer.parseInt(reader.readLine()); // Convert input string to integer

//         list.append(newData);        // Add the new data to the end of the list

//         list.saveToFile(filename);   // Save the updated list back to the binary file

//         list.printForward();         // Print the updated list to show changes
//     }
// }


public static void main(String[] args) {
    DoublyLinkedList list = new DoublyLinkedList();
    String filename = "linkedlist.bin";

    list.loadFromFile(filename);
    list.printForward();

    Scanner sc = new Scanner(System.in);
    System.out.print("Enter new data to append: ");
    int newData = sc.nextInt(); // সরাসরি int ইনপুট নেয়
    list.append(newData);
    list.saveToFile(filename);
    list.printForward();
    sc.close(); // Scanner বন্ধ করা ভালো অভ্যাস
}
}