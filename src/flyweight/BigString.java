package flyweight;

public class BigString {
    private BigChar[] bigChars;

    public BigString(String string, boolean shared) {
        if (shared) {
            initShared(string);
        } else {
            initUnshared(string);
        }
    }

    private void initShared(String string) {
        BigCharFactory factory = BigCharFactory.getInstance();
        bigChars = new BigChar[string.length()];
        for (int i = 0; i < bigChars.length; i++) {
            bigChars[i] = factory.getBigChar(string.charAt(i));
        }
    }

    private void initUnshared(String string) {
        bigChars = new BigChar[string.length()];
        for (int i = 0; i < bigChars.length; i++) {
            bigChars[i] = new BigChar(string.charAt(i));
        }
    }

    public void print() {
        for (BigChar bc : bigChars) {
            bc.print();
        }
    }
}
