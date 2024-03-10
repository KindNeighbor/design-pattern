package factorymethod;

import factorymethod.framework.Factory;
import factorymethod.framework.Product;
import factorymethod.idcard.IDCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("Kim");
        System.out.println();
        Product card2 = factory.create("Lee");
        System.out.println();
        Product card3 = factory.create("Choi");
        System.out.println();

        card1.use();
        card2.use();
        card3.use();
    }
}
