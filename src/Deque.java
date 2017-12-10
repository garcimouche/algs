import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    public Deque() {
        // construct an empty deque
    }

    public boolean isEmpty() {
        // is the deque empty?
        return first == null;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null)
            throw new IllegalArgumentException();
        size++;
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null)
            oldFirst.previous = first;
        if (last == null)
            last = first;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null)
            throw new IllegalArgumentException();
        size++;
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        if (oldLast != null)
            oldLast.next = last;
        if (first == null)
            first = last;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (this.isEmpty())
            throw new NoSuchElementException();
        size--;

        Node oldFirst = first;

        first = first.next;

        if (first != null)
            first.previous = null;

        if (size <= 1)
            last = first;

        return oldFirst.item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (this.isEmpty())
            throw new NoSuchElementException();
        size--;

        Node oldLast = last;

        last = oldLast.previous;

        if (last != null)
            last.next = null;

        if (size <= 1)
            first = last;

        return oldLast.item;

    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new Iterator<Item>() {

            @Override
            public boolean hasNext() {
                return !Deque.this.isEmpty();
            }

            @Override
            public Item next() {
                if (Deque.this.isEmpty())
                    throw new NoSuchElementException();
                return Deque.this.removeFirst();
            }

        };
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<String> deque = new Deque<String>();

        deque.addFirst("second");
        deque.addFirst("first");
        deque.addLast("third");

        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());

        deque.addLast("first");
        deque.addLast("second");
        deque.addLast("third");

        for (String item : deque) {
            System.out.println(item);
        }

        deque.addLast("first");
        deque.addLast("second");
        deque.addFirst("zero");
        deque.addLast("third");

        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
    }
}
