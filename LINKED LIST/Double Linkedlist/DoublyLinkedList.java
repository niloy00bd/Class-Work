// Define a Node class for the doubly linked list
class Node {
    int data;       // Stores the value of the node
    Node prev;      // Reference to the previous node
    Node next;      // Reference to the next node

    // Constructor to initialize node with data
    Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

// Define the DoublyLinkedList class
public class DoublyLinkedList {
    Node head;  // Reference to the first node in the list

    // Method to add a node at the end of the list
    public void append(int data) {
        Node newNode = new Node(data);  // Create a new node with given data

        // If the list is empty, set head to the new node
        if (head == null) {
            head = newNode;
            return;
        }

        // Traverse to the last node
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        // Link the new node at the end
        current.next = newNode;     // Set current node's next to new node
        newNode.prev = current;     // Set new node's prev to current node
    }

    // Method to print the list from head to tail
    public void printForward() {
        Node current = head;        // Start from head
        System.out.print("Forward: ");
        while (current != null) {
            System.out.print(current.data + " ⇄ ");  // Print current node's data
            current = current.next;                 // Move to next node
        }
        System.out.println("null");  // End of list
    }

    // Method to print the list from tail to head
    public void printBackward() {
        Node current = head;        // Start from head

        // Move to the last node
        if (current == null) return;
        while (current.next != null) {
            current = current.next;
        }

        System.out.print("Backward: ");
        while (current != null) {
            System.out.print(current.data + " ⇄ ");  // Print current node's data
            current = current.prev;                 // Move to previous node
        }
        System.out.println("null");  // Start of list
    }

    // Main method to test the doubly linked list
    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();  // Create a new list

        list.append(10);  // Add node with data 10
        list.append(20);  // Add node with data 20
        list.append(30);  // Add node with data 30

        list.printForward();   // Print list from head to tail
        list.printBackward();  // Print list from tail to head
    }
}