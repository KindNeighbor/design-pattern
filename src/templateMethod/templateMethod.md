## Template Method 패턴

- 템플릿이란 원래 문자 모양대로 구멍이 난 플라스틱 판으로, 그 구멍 모양에 맞춰서 그리면 문자를 그릴 수 있다. 문자는 판을 보면 알 수 있지만, 그 문자가 어떤 색깔이나 굵기로 적힐지는 사용하는 연필, 펜, 색연필 등 도구에 따라 달라진다.
- 이런 실제 템플릿과 같이 템플릿 메소드 패턴은 상위 클래스에서 템플릿이 추상메소드로 되어있고, 하위에서 구체적으로 구현을 한다.

<br>

<div align="center">
  <img src="../images/Template%20Method%20example.png" alt="Template Method example">
</div>

<br>

AbstractDisplay 가 template 이 된다. CharDisplay 와 StringDisplay 는 template 를 이용해서 문자를 작성할 연필, 색연필, 싸인펜에 해당하는 부분이 된다.

<br>

```java
public abstract class AbstractDisplay {
    // open, print, close 는 하위 클래스에 구현을 맡기는 추상 메소드
    public abstract void open();
    public abstract void print();
    public abstract void close();

    // display 는 AbstractDisplay 에서 구현하는 메소드
    public final void display() {
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}
```

<br>

AbstractDisplay 클래스에서는 display 메소드만 구현되어있고, 나머지 메소드들은 추상 메소드로 되어있다. 템플릿을 보면 display 부분을 보고 무언가 출력한다는 것은 알 수 있지만, 정작 뭘 출력하는지는 상속 받을 클래스들을 봐야 알 수 있다.

display 메소드에 **final** 을 넣었는데, 그 이유는 display 메소드는 이미 구현되어있으니 하위 클래스에서 다시 오버라이드 하지 말라는 의미이다.

<br>

```java
public class CharDisplay extends AbstractDisplay {
    private char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    @Override
    public void open() {
        System.out.print("<<");
    }

    @Override
    public void print() {
        System.out.print(ch);
    }

    @Override
    public void close() {
        System.out.println(">>");
    }
}
```

<br>

템플릿을 사용할 도구를 구현하는 부분이다. AbstractDisplay 를 상속받고, 그 내용을 구현한다.

<br>

```java
public class StringDisplay extends AbstractDisplay {

    private String string;
    private int width;

    public StringDisplay(String string) {
        this.string = string;
        this.width = string.length();
    }

    @Override
    public void open() {
        printLine();
    }

    @Override
    public void print() {
        System.out.println("|" + string + "|");
    }

    @Override
    public void close() {
        printLine();
    }

    private void printLine() {
        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
```

<br>

CharDisplay 과 마찬가지로 AbstractDisplay 를 상속받고 템플릿을 어떻게 활용할 것인지 구현한다.

<br>

```java
public class Main {

    public static void main(String[] args) {
        AbstractDisplay d1 = new CharDisplay('H');
        AbstractDisplay d2 = new StringDisplay("Hello, World!");

        d1.display();
        d2.display();
    }
}
==================================================================
<<HHHHH>>
+-------------+
|Hello, World!|
|Hello, World!|
|Hello, World!|
|Hello, World!|
|Hello, World!|
+-------------+
```

<br>

메인 메소드에서 실행하면 각각 템플릿을 사용한 도구에 따라서 다른 출력값을 볼 수 있다.

<br>

<div align="center">
  <img src="../images/Template%20Method.png" alt="Template Method">
</div>

<br>

Template Method 패턴을 사용하면 상위 템플릿 메소드에 알고리즘을 일단 작성해 놓으면, 하위 클래스 쪽에 일일이 하나씩 따로 기술할 필요가 없어진다.

게다가 만약 Template Method 패턴을 사용하지 않고 각각의 ConcreteClass 에 일일이 같은 알고리즘을 작성을 해놓았을 때, 버그를 찾는다면 모든 ConcreteClass 를 일일이 다 수정해야한다.

하지만 Template Method 패턴을 사용하면 AbstractClass 하나만 수정하면 된다.