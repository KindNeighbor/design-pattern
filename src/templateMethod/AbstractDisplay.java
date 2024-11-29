package templateMethod;

public abstract class AbstractDisplay {
    // open, print, close 는 하위 클래스에 구현을 맡기는 추상 메소드
    public abstract void open();
    public abstract void print();
    public abstract void close();

    // display 는 AbstractDisplay 에서 구현하는 메소드
    public final void display() { // 여기서 final 을 쓰는 이유 -> display 메소드는 오버라이드 하지 않게 하려고
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}
