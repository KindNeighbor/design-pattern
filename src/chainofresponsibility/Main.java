package chainofresponsibility;

public class Main {
    public static void main(String[] args) {
        Support kim = new NoSupport("Kim");
        Support lee = new LimitSupport("Lee", 100);
        Support park = new SpecialSupport("Park", 429);
        Support choi = new LimitSupport("Choi", 200);
        Support jung = new OddSupport("Jung");
        Support kang = new LimitSupport("Kang", 300);

        kim.setNext(lee).setNext(park).setNext(choi).setNext(jung).setNext(kang);

        for (int i = 0; i < 500; i+=33) {
            kim.support(new Trouble(i));
        }
    }
}
