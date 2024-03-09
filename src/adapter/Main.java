package adapter;

public class Main {

    public static void main(String[] args) {
        Print p = new PrintBanner("Hello1");
        p.printWeak();
        p.printStrong();

        System.out.println();

        Print2 p2 = new PrintBanner2("Hello2");
        p2.printWeak();
        p2.printStrong();
    }
}
