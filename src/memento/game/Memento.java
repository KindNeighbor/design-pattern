package memento.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Memento {
    private int money;
    private List<String> fruits;

    public int getMoney() {
        return money;
    }

    // 생성자는 default 로 한다.
    // Main 함수에서 new Memento 와 같이 호출 불가능 하기 위함
    Memento(int money) {
        this.money = money;
        this.fruits = new ArrayList<>();
    }

    void addFruits(String fruit) {
        fruits.add(fruit);
    }

    List<String> getFruits() {
        return new ArrayList<>(fruits);
    }

    public static boolean saveToFile(String filename, Memento memento) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%d", memento.money));
        sb.append("\n");

        for (String f : memento.getFruits()) {
            sb.append(f);
            sb.append("\n");
        }

        try {
            Files.writeString(Path.of(filename), sb,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    public static Memento loadFromFile(String filename) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filename));
            if (lines.size() == 0) {
                System.out.println("Empty file");
                return null;
            }

            int money = 0;
            try {
                money = Integer.parseInt(lines.get(0));
            } catch (NumberFormatException e) {
                System.out.println("Format error: " + e);
                return null;
            }

            Memento memento = new Memento(money);

            for (int i = 0; i < lines.size(); i++) {
                memento.addFruits(lines.get(i));
            }
            return memento;
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}
