class Programmer {

    private String name;

    // Getter method used to get the data
    public String getName() { 
        return name; 
        }

    // Setter method is used to set or modify the data
    public void setName(String name, int a) {
        if(a>0){//validation er example hishebe bebohar kora hoyeche
        //validation check kore variable er value set korar jonno e encapsulation bebohar kora hoy
            this.name = name;
        }
        else{
            System.out.println("Invalid Name");
        }        
    }
}

public class Main {

    public static void main(String[] args){
        
        Programmer p = new Programmer();
        p.setName("Niloy", 3);
        System.out.println("Name=> " + p.getName());
        p.setName("Niloy", -1);
        System.out.println("Name=> " + p.getName());
    }
}