import java.util.Scanner;

public class MultipleLineInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // একটি StringBuilder ব্যবহার করা হচ্ছে সব লাইনগুলো দক্ষতার সাথে একত্রিত করার জন্য
        StringBuilder multiLineInput = new StringBuilder(); 
        String line;

        System.out.println("Write multiple lines of input. ");
        System.out.println("for finishing input press enter twice (i.e., submit an empty line).");

        // while লুপ দিয়ে ইনপুট নেওয়া
        while (scanner.hasNextLine()) {//while (true) {  dileo same kaj hobe
            // ১. nextLine() দিয়ে একটি সম্পূর্ণ লাইন ইনপুট নেওয়া
            line = scanner.nextLine(); 
            
            // ২. খালি লাইন কিনা তা চেক করা (ইনপুট শেষ করার শর্ত)
            if (line.isEmpty()) {
                break; // যদি খালি হয়, লুপ ব্রেক করা ber hoye jabe
            }
            
            // ৩. লাইনটি StringBuilder-এ যোগ করা এবং নতুন লাইন যোগ করা
            multiLineInput.append(line).append("\n"); 
        }

        // আউটপুট দেখানো
        System.out.println("\nYour Total Input:\n---");
        System.out.println(multiLineInput.toString());
        System.out.println("---");
        
        scanner.close();
    }
}
