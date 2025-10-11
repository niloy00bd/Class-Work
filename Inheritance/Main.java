class grandparents {
    int asset = 500;
}
class parents extends grandparents {
    int money = 1000;
}
class child extends parents {
    void display() {
        System.out.printf("total: %d", money + asset);
    }
}
public class Main {
    public static void main(String[] args) {
        child c = new child();
        System.out.printf("\n%d", c.asset);
        System.out.printf("\n%d", c.money);
    }
}