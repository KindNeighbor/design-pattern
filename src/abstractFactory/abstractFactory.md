## Abstract Factory 패턴

- Template Method 에서 하위 클래스에서의 구체화나, Factory Method 에서 인스턴스를 추상화하고 하위클래스에서 구현하는 등 추상 - 구현의 구조를 전부 집약해 놓은 패턴이다.

<br>

<div align="center">
 <img src="../images/Abstract%20Factory%20example.png" alt="Abstract Factory example">
</div>

<br>

보기에는 복잡해보이는데 사실 추상 - 구체화 구조가 다양하게 있을 뿐이다. 추상화 한 단위도 하나의 제품이었다면, 이제는 부품도 나누고, 그걸로 조합해서 부품도 만들고, 그걸 만드는 공장도 전부 추상화 해서 만들어 놓고 전부 구체화를 하는 것이다.

위의 예시에서는 Link 와 Tray 라는 부품을 통해 Page 를 형성하는 Factory 구조를 전부 추상화를 했다. 이렇게 Main 함수에서 사용하면 Main 함수에서는 구체적인 동작을 모른다. 이런 전체적인 구조를 전부 추상화하고, 구체화는 또 전체적인 구조에서 구체화 하는 것이 Abstract Factory 패턴이다.

위의 클래스 다이어그램에는 ListFactory 하나만 있는데, 이런 Factory 가 여러개 있어도 되는데, 사실 그럴려고 이런 복잡한 구조에도 불구하고 Abstract Factory 패턴을 사용하는 것이다.

<br>

```java
package abstractfactory.factory;

public abstract class Item {
    protected String caption;

    public Item(String caption) {
        this.caption = caption;
    }

    public abstract String makeHTML();
}
================================================
package abstractfactory.factory;

public abstract class Link extends Item {
    protected String url;

    public Link(String caption, String url) {
        super(caption);
        this.url = url;
    }
}
================================================
package abstractfactory.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class Tray extends Item {
    protected List<Item> tray = new ArrayList<>();

    public Tray(String caption) {
        super(caption);
    }

    public void add(Item item) {
        tray.add(item);
    }
}
```

<br>

우선 부품을 구성하는 추상 클래스들을 만든다. Link 는 말그대로 링크를 만드는 클래스이고, Tray 는 여러 List 나 Tray 를 하나로 모으는 역할을 한다. 따라서 List 를 사용해서 add 로 List 에 추가하는 부분을 볼 수 있다.

<br>

```java
package abstractfactory.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public abstract class Page {
    protected String title;
    protected String author;
    protected List<Item> content = new ArrayList<>();

    public Page(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void add(Item item) {
        content.add(item);
    }

    public void output(String filename) {
        try {
            Files.writeString(Path.of(filename), makeHTML(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
            System.out.println(filename + " 파일을 작성했습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String makeHTML();
}
```

<br>

부품을 모아서 만들어지는 제품인 Page 클래스이다. 보면 Item 을 받아서 합치는 것을 볼 수 있다.

<br>

```java
package abstractfactory.factory;

public abstract class Factory {
    public static Factory getFactory(String classname) {
        Factory factory = null;
        try {
            factory = (Factory) Class.forName(classname).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println(classname + " 클래스가 발견되지 않았습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    public abstract Link createLink(String caption, String url);
    public abstract Tray createTray(String caption);
    public abstract Page createPage(String title, String author);
}
```

<br>

추상 구조의 마지막인 Factory 추상 클래스이다. 여기서는 자바 Reflection 을 사용했다. 구체화된 공장이 여럿인 경우에 하나의 구체적인 공장을 선택해서 주입해줄 수 있는 방법이다.

<br>

```java
public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main filename.html class.name.of.ConcreteFactory");
            System.out.println("Example 1: java Main list.html listfactory.ListFactory");
            System.out.println("Example 2: java Main div.html divfactory.DivFactory");
            System.exit(0);
        }

        String filename = args[0];
        String classname = args[1];

        Factory factory = Factory.getFactory(classname);

        Link blog1 = factory.createLink("Blog 1", "https://example.com/blog1");
        Link blog2 = factory.createLink("Blog 2", "https://example.com/blog2");
        Link blog3 = factory.createLink("Blog 3", "https://example.com/blog3");

        Tray blogTray = factory.createTray("Blog Site");
        blogTray.add(blog1);
        blogTray.add(blog2);
        blogTray.add(blog3);

        Link news1 = factory.createLink("News 1", "https://example.com/news1");
        Link news2 = factory.createLink("News 2", "https://example.com/news2");
        Tray news3 = factory.createTray("News 3");
        news3.add(factory.createLink("News 3 (US)", "https://example.com/news3us"));
        news3.add(factory.createLink("News 3 (Korea)", "https://example.com/news3kr"));

        Tray newsTray = factory.createTray("News Site");
        newsTray.add(news1);
        newsTray.add(news2);
        newsTray.add(news3);

        Page page = factory.createPage("Blog and News", "TEST.com");
        page.add(blogTray);
        page.add(newsTray);

        page.output(filename);
    }
}
```

<br>

classname 에는 패키지명 + 클래스이름을 넣어주어서 리플렉션으로 생성자를 가지고 와서 인스턴스를 생성한다. 그 이후에는 뭔가 많은 것 같지만 원하는 대로 Link 와 Tray 를 구성해서 Page 에다가 넘겨주고, 그 Page 클래스에서 받은 부품들을 가지고 file 만드는 것이다.

<br>

<div align="center">
 <img src="../images/Abstract%20Factory%20page.jpg" alt="Abstract Factory page">
</div>

<br>

Main 클래스로부터 만들어진 html 파일을 브라우저에서 열어보면 다음과 같이 나오는 것을 볼 수 있다. (ListFactory 부분까지 넣으면 너무 길어지므로 생략)

<br>

<div align="center">
 <img src="../images/Abstract%20Factory%20page2.jpg" alt="Abstract Factory page2">
</div>

<br>

ListFactory 가 아닌 DivFactory 를 만들어서 Main 함수에 넣어주면 이런 결과도 바로 얻을 수 있다. Template Method 에서 하위클래스 하나씩 동적으로 주입되던 것이 거대한 구조가 동적으로 주입된다고 보면 된다.

<br>

<div align="center">
 <img src="../images/Abstract%20Factory.png" alt="Abstract Factory">
</div>

<br>

일반화한 클래스 다이어그램도 예시로 본 것과 똑같다. Product 부분을 각각의 제품으로 두던, 부품으로 만들어서 하나의 제품으로 합치던, 더 많은 수의 부품을 두던 아무상관이 없다. 그저 하나의 커다란 제작 과정이 있는 구조를 전부다 추상화를 시키고, 그 전체를 구체화하는 concreteFactory 가 하위에 있을 뿐이다. 또한 이 concreteFactory 가 여러개 있어도 된다.

다만 이렇게 복잡한 구조를 가지는 만큼, 만약 factory 에 새로운 Product 가 추가되는 상황이라면, 꾀나 수정이 번거로워질 수 있다. 이에 대응하는 concreteFactory 를 전부 수정해야하기 때문이다.