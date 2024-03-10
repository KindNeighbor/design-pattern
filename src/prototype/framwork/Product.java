package prototype.framwork;

public interface Product extends Cloneable {
    void use(String s);
    Product createCopy();
}
