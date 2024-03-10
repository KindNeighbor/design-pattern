package builder;

public abstract class Builder {
    abstract void makeTitle(String title);
    abstract void makeString(String str);
    abstract void makeItems(String[] items);
    abstract void close();
}
