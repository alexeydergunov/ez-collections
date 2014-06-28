package ez.collections;

public interface EzIntCollection {
    int size();

    boolean isEmpty();

    boolean contains(int element);

    EzIntIterator iterator();

    int[] toArray();

    boolean add(int element);

    boolean remove(int element);

    void clear();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
}
