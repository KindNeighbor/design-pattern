## Chain Of Responsibility 패턴

- 요청을 처음 받은 객체가 처리할 수 없으면, 연결되어있는 다른 객체에게 그 책임을 떠넘기는 패턴이다. 요청이 계속 넘어가다가 처리할 수 있는 객체를 만나면 처리하게 된다.

<br>

<div align="center">
 <img src="../images/Chain%20of%20Responsibility.png" alt="Chain of Responsibility">
</div>

<br>

우선 문제를 처리하는 추상클래스 Support 가 있고, 이를 구체화 하는 각각의 하위 클래스가 존재한다. 하나의 클래스에서 처리하지 못하면 다음으로 책임을 넘기는 구조를 가지고 있다.

이번예시에서는 번호의 홀/짝 이나 제한을 걸거나, 어떤 특정한 값일 때만 처리한다거나 등 각각의 처리방법을 가진 하위클래스들이 존재한다.

<br>

```java
public class Trouble {
    private int number;

    public Trouble(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "[Trouble " + number + "]";
    }
}
```

<br>

처리해 달라고 보낼 문제 상황이다. 이걸 Main 클래스를 통해서 Support 로 문제를 해결해 달라고 요청을 보낸다.

<br>

```java
public abstract class Support {
    private String name;
    private Support next;

    public Support(String name) {
        this.name = name;
        this.next = null;
    }

    public Support setNext(Support next) {
        this.next = next;
        return next;
    }

    public void support(Trouble trouble) {
        if (resolve(trouble)) {
            done(trouble);
        } else if (next != null) {
            next.support(trouble);
        } else {
            fail(trouble);
        }
    }

    @Override
    public String toString() {
        return "[" + name + "]";
    }

    protected abstract boolean resolve(Trouble trouble);

    protected void done(Trouble trouble) {
        System.out.println(trouble + " is resolved by " + this + ".");
    }

    protected void fail(Trouble trouble) {
        System.out.println(trouble + " cannot be resolved");
    }
}
```

<br>

문제를 처리할 추상 클래스이다. 여기서는 책임을 떠넘기는 setNext 메소드와, 문제를 해결 할 수 있는지 없는지를 판단하는 support 메소드가 있다. support 메소드에서는 resolve 메소드에서 false 값을 반환하게 되면 다음 클래스로 책임을 넘긴다. 만약 더 이상 넘길 대상이 없다면 fail 메소드를 호출하게 된다.

<br>

```java
public class NoSupport extends Support {

    public NoSupport(String name) {
        super(name);
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        return false;
    }
}
======================================================
public class OddSupport extends Support {

    public OddSupport(String name) {
        super(name);
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if (trouble.getNumber() % 2 == 1) {
            return true;
        } else {
            return false;
        }
    }
}
======================================================
public class LimitSupport extends Support {
    private int limit;

    public LimitSupport(String name, int limit) {
        super(name);
        this.limit = limit;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if (trouble.getNumber() < limit) {
            return true;
        } else {
            return false;
        }
    }
}
======================================================
public class SpecialSupport extends Support {
    private int number;

    public SpecialSupport(String name, int number) {
        super(name);
        this.number = number;
    }

    @Override
    protected boolean resolve(Trouble trouble) {
        if (trouble.getNumber() == number) {
            return true;
        } else {
            return false;
        }
    }
}
```

<br>

각각의 처리 방법을 나타내는 하위 클래스들이다.

<br>

```java
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
=================================================================================
[Trouble 0] is resolved by [Lee].
[Trouble 33] is resolved by [Lee].
[Trouble 66] is resolved by [Lee].
[Trouble 99] is resolved by [Lee].
[Trouble 132] is resolved by [Choi].
[Trouble 165] is resolved by [Choi].
[Trouble 198] is resolved by [Choi].
[Trouble 231] is resolved by [Jung].
[Trouble 264] is resolved by [Kang].
[Trouble 297] is resolved by [Jung].
[Trouble 330] cannot be resolved
[Trouble 363] is resolved by [Jung].
[Trouble 396] cannot be resolved
[Trouble 429] is resolved by [Park].
[Trouble 462] cannot be resolved
[Trouble 495] is resolved by [Jung].
```

<br>

위의 for 문을 보면 kim 을 시작으로 각 사람이 각각의 처리 방법을 가지고 책임을 넘겨 받게 된다. 만약 모두다 요청을 처리할 수 없는 경우에는 [Trouble 330] cannot be resolve] 와 같이 출력하게 된다.

<br>

<div align="center">
 <img src="../images/Chain%20of%20Responsibility%20example.png" alt="Chain of Responsibility example">
</div>

<br>

Chain of Responsibility 패턴을 사용하면, 요청한 사람과 그걸 처리하는 사람간의 연결 관계가 느슨해진다. 어떤 요청에는 대응되는 어떤 사람이 맡아야 한다는 법이 없기 때문에, 요청하는 사람은 문제의 요청 자체만 신경쓰면 된다.

또한 책임을 떠넘기는 순서도 동적으로 바꿔줄 수 있고, 각 요청을 처리하는 클래스들은 자기의 처리방식에만 신경을 쓰면 된다. 처리를 못하면 책임을 넘기면 되기 때문이다.

단점으로는 요청을 누군가가 처리할 때 까지 책임을 떠넘기면서 기다리게 된다. 만약 빠르게 요청을 누군가가 처리해야 하는 상황이라면 요청을 처리할 누군가를 확실하게 지정해주는 편이 좋다.

GUI 앱에서 이번 패턴을 볼 수 있다. 만약 자식 컴포넌트에서 이벤트 처리가 되지 않으면 부모 컴포넌트로 이벤트를 넘긴다.