import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];
    private int size;

    public RandomizedQueue() {
        // construct an empty randomized queue
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return size;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        // add the item
        if (size == a.length)
            resize(2 * a.length);
        a[size++] = item;
    }

    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        // remove and return a random item
        Item item = getRandomly(true);
        size--;
        if (size > 0 && size == a.length / 4)
            resize(a.length / 2);
        return item;
    }

    private Item getRandomly(boolean init) {
        Item item = null;
        int i;
        do {
            i = StdRandom.uniform(a.length);
            item = a[i];
        }
        while (item == null);
        if (init)
            a[i] = null;
        return item;
    }

    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        // return a random item (but do not remove it)
        return getRandomly(false);
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new Iterator<Item>() {
            private int index = 0;
            private Item ia[] = (Item[]) new Object[RandomizedQueue.this.a.length];
            {
                for (int i = 0; i < ia.length; i++)
                    ia[i] = a[i];
                StdRandom.shuffle(ia);
            }

            @Override
            public boolean hasNext() {
                return index != ia.length;
            }

            @Override
            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                Item item;
                do {
                    item = ia[index++];
                }
                while (item == null);

                return item;
            }
        };
    }

    private void resize(int max) { // Move stack of size N <= max to a new array of size max.
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0, j = 0; i < a.length; i++)
            if (a[i] != null)
                temp[j++] = a[i];
        a = temp;
    }

    public static void main(String[] args) {
        // unit testing (optional)
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");
        rq.enqueue("f");
        rq.enqueue("g");

        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.sample());
        System.out.println(rq.dequeue());

        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");
        rq.enqueue("f");
        rq.enqueue("g");

        System.out.println("iterator");
        for (String item : rq) {
            System.out.println(item);
        }

    }
}