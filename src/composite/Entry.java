package composite;

public abstract class Entry {
    private Entry parent;

    protected void setParent(Entry parent) {
        this.parent = parent;
    }

    public abstract String getName();

    public abstract int getSize();

    public void printList() {
        printList("");
    }

    protected abstract void printList(String prefix);

    @Override
    public String toString() {
        return getName() + "(" + getSize() + ")";
    }

    public String getFullName() {
        StringBuilder fullname = new StringBuilder();
        Entry entry = this;
        do {
            fullname.insert(0, entry.getName());
            fullname.insert(0, "/");
            entry = entry.parent;
        } while (entry != null);
        return fullname.toString();
    }
}
