## Factory Method 패턴

- Template Method 를 인스턴스 생성에 적용한 것이 Factory Method 이다.
- 인스턴스 생성 방법을 상위 클래스에서 결정하지만, 그 인스턴스의 구체적인 정보는 모두 하위 클래스에서 구현한다.

<br>

<div align="center">
  <img src="../images/Factory%20Method%20example.png" alt="Factory Method example">
</div>

<br>


위의 UML 클래스 다이어그램 예시는 id 카드를 만드는 factory 를 의미한다. Factory 에서 인스턴스를 만들 때, id card 에 관한 부분은 하위 클래스에서 상속해서 구현하는 것을 볼 수 있다.

바깥쪽의 큰 테두리는 각각 패키지를 의미한다. 패턴 구현 시 패키지를 분리해서 작성하고, framwork 패키지에 있는 클래스를 idcard 패키지에 있는 클래스가 상속받는 형태로 구현한다.

<br>

```java
package factorymethod.framework;

public abstract class Factory {
    public final Product create(String owner) {
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }

    protected abstract Product createProduct(String owner);
    protected abstract void registerProduct(Product product);
}
```

<br>

우선 다이어그램에서 Framework 에 해당하는 부분의 패키지를 만든 뒤, 그 하위 패키지에 추상 클래스를 만든다.  Factory 라는 이름 처럼 무언가를 만드는 create 메소드를 정의하고, 구체적인 설정은 하위 클래스로 넘긴다.

<br>

```java
package factorymethod.framework;

public abstract class Product {
    public abstract void use();
}
```

<br>

Product 도 Factory 의 경우처럼 사용한다는 메소드만 정의하고, 어떻게 할건지는 하위 클래스로 넘긴다.

<br>

```java
package factorymethod.idcard;

import factorymethod.framework.Product;

public class IDCard extends Product {
    private String owner;

    IDCard(String owner) {
        System.out.println(owner + "의 카드를 만듭니다.");
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println(this + "을 사용합니다.");
    }

    @Override
    public String toString() {
        return "[IDCard:" + owner + "]";
    }

    public String getOwner() {
        return owner;
    }
}
```

<br>

일단 Product 를 상속받았으므로, Product 를 어떻게 구체적으로 사용하는 건지에 대해서 작성한다.

생성자의 경우 public 을 붙이지 않았는데, 이는 IDCard 인스턴스를 생성하기 위해서는 IDCardFactory 를 경유해야 하도록 만들기 위함이다. public 으로 하면 IDCardFactory 없이도 new 로 선언 후 할당이 가능해지기 때문이다.

<br>

```java
package factorymethod.idcard;

import factorymethod.framework.Factory;
import factorymethod.framework.Product;

public class IDCardFactory extends Factory {

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        System.out.println(product + "을 등록했습니다.");
    }
}
```

<br>

Factory 에서 추상 메소드로 만들었던 부분을 자세히 구현한다.

<br>

```java
package factorymethod;

import factorymethod.framework.Factory;
import factorymethod.framework.Product;
import factorymethod.idcard.IDCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("Kim");
        Product card2 = factory.create("Lee");
        Product card3 = factory.create("Choi");

        System.out.println();

        card1.use();
        card2.use();
        card3.use();
    }
}
====================================================
Kim의 카드를 만듭니다.
[IDCard:Kim]을 등록했습니다.
Lee의 카드를 만듭니다.
[IDCard:Lee]을 등록했습니다.
Choi의 카드를 만듭니다.
[IDCard:Choi]을 등록했습니다.

[IDCard:Kim]을 사용합니다.
[IDCard:Lee]을 사용합니다.
[IDCard:Choi]을 사용합니다.
```

<br>

Main 함수에서의 실행 결과이다. 처음 Factory factory = new IDCardFactory(); 이 부분 이후에는 IDCard, IDCardFactory 를 작성하지 않아도 Factory 클래스와 Product 클래스만 가지고 생성과 사용이 가능하다.

IDCard 의 인스턴스 생성을 new IDCard 가 아니라, factory 클래스의 create 메소드를 사용해서 생성한다. 이렇게 인스턴스 생성을 Template Method 패턴을 이용하는 것이 Factory Method 패턴이다.

<br>

<div align="center">
  <img src="../images/Factory%20Method.png" alt="Factory Method">
</div>

<br>

일반화 된 클래스 다이어그램도 앞서 살펴본 예시와 똑같다. framework 부분에서 추상화를 하고, 하위 클래스에서 구체화 한다. 위의 다이어그램의 경우 Concrete 를 하나만 작성해 놓았는데, 이 부분은 여러가지가 될 수 있다.

framwork 부분은 구체적인 제품이나 factory 의 이름이 있는 것은 아니기 때문에, 하위 클래스에서 수정이 일어난다 하더라도 전혀 수정이 필요가 없다. 이렇게 영향을 받지 않는 상황을 의존하지 않는다고 표현한다.

<br>

```java
SecureRadom random = SecureRandom.getInstance("Hello");

List<String> list = List.of("Kim", "Lee", "Choi");

String s = String.valueOf('A');
```

<br>

Factory Method 패턴에서는 인스턴스 생성을 직접 하는 것이 아니라 경유에서 하는 것을 보았다. 이런점을 활용해서 메소드에 static 을 붙여서 어느 곳에서나 인스턴스 생성을 메소드로 하게 만든 함수들이 있는데, 이런 함수들을 보통 static Factory Method, 정적 펙토리 메소드라고 흔히 말한다.

위의 예시들도 .getInstance(), .of() 등을 통해서 인스턴스 생성 후 변수에 할당을 한다.