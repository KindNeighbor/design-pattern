## Iterator 패턴

- iterator 라는 말 그대로 반복해서 전체를 검색한 후 출력하는 데 사용되는 패턴이다. 반복문 중 for문을 보면 i 값에 따라서 다음으로 진행하는데, 이 i 를 추상화 해서 일반화 시킨 패턴이라고 보면 된다.

![Iterator pattern.png](src/images/Iterator%20pattern.png)

우선 위와 같은 구조로 Iterator 패턴을 구현한다. Itrable<T> 와 Itrator<E> 는 java.lang 패키지에 이미 선언되어 있으므로, 직접 구현할 필요없이 가져다가 쓰면 된다.

```java
public class Book {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

우선 iterator 를 돌렸을 때 그 하나 하나의 요소가 될 Book 클래스를 만든다.

```java
public class BookShelf implements Iterable<Book> {

    private Book[] books;
    private int last = 0;

    public BookShelf(int maxsize) {
        this.books = new Book[maxsize];
    }

    public Book getBookAt(int index) {
        return books[index];
    }

    public void appendBook(Book book) {
        this.books[last] = book;
        last++;
    }

    public int getLength() {
        return last;
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}
```

BookShelf 는 UML 에서 보았듯이 Book 의 집합체를 의미하는 클래스이다. 집합체를 만들기 위해서 Iterable<Book> 인터페이스를 구현하고, 구현을 위해서 Iterator<Book> 을 오버라이드 했다.

Iterable<T> 인터페이스는 집합체를 나타내는 인터페이스로, Iterator 메소드가 선언되어 있다. 이 메소드는 집합체에 대응하는 iterator 를 만들기 위함으로, 반복을 어떻게 할건지 구현해 주어야 한다.

일단 배열로 Book 을 받았는데, 이 경우 처음 지정한 사이즈 이상으로는 book 을 넣을 수 없으므로, 초기 사이즈 설정에 국한되고 싶지 않다면 List 로 구현하면 된다.

```java
public class BookShelfIterator implements Iterator<Book> {
    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}
```

Iterator 인터페이스를 구현하고 있는데, 이를 위해 next 와 hasNext 메소드를 구현했다. hasNext 의 경우 처음 설정한 bookShelf 의 사이즈를 넘어가면 더 이상 반복하지 않도록 구현했고, next 의 경우 현재 탐색한 값을 반환하고, 다음으로 넘기도록 구현했다.

```java
public class Main {

    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);
        bookShelf.appendBook(new Book("Around the World in 80 Days"));
        bookShelf.appendBook(new Book("Bible"));
        bookShelf.appendBook(new Book("Cinderella"));
        bookShelf.appendBook(new Book("Daddy-Long-Legs"));

        Iterator<Book> it = bookShelf.iterator();
        while (it.hasNext()) {
            Book book = it.next();
            System.out.println(book.getName());
        }
        System.out.println();

        for (Book book : bookShelf) {
            System.out.println(book.getName());
        }
        System.out.println();
    }
}
```

책을 4권 새로 만들어서 bookshelf 에 넣은 뒤, 반복을 통해서 각 책의 제목을 출력했다. 물론 아래의 향상된 for문 처럼 기존에 있는 반복문을 사용하면 굳이 Iterator 패턴을 구현하지 않아도 집합체를 반복해서 탐색 후 출력할 수 있다.

그럼에도 불구하고 Iterator 패턴을 구현해서 사용하는 이유는, bookShelf 구현에 의존하지 않는다. 아래의 향상된 for문을 보면, bookShelf 에서 book 을 뽑아서 사용하는데, 만약 bookShelf 가 바뀌면 for문 내부도 바꾸어야 하는 상황이 생길 수 있다. 하지만 Iterator 패턴을 사용한 부분을 보면 반복문 내부에서 bookShelf 가 등장하지 않는다.

![iterator.png](src/images/iterator.png)

앞서 Book, BookShelf, BookShelfIterator 로 만들어본 Iterator 패턴을 일반화 한 UML 은 다음과 같다. ConcreateAggregate 가 구체적인 집합체이고, ConcreteIterator 가 그 집합체를 반복 탐색하는 부분이다.

Aggregate 는 집합체를 의미하는데, Iterable 을 생각하면 된다. 결국 집합체 인터페이스와 그 집합체를 반복 - 탐색하는 인터페이스가 있는데, 그걸 각각 구체화 하는 클래스가 있다고 생각하면 된다.

참고로 delete Iterator 는 필요가 없다. 사용되지 않는 인스턴스는 GC 가 자동으로 삭제해주기 때문이다.

Iterator<Book> it = bookShelf.iterator(); 이 부분에서 볼 수 있듯이 Iterator 가 되는 Concrete Iterator 를 직접 선언 후 할당 해주는데, Concrete Iterator 를 여러개 만들면 하나의 ConcreteAggregate 에 대해서 복수의 Concrete Iterator 를 만들어놓고 사용할 수 있다.