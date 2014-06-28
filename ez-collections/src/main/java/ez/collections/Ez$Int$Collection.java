package ez.collections;

public interface Ez$Int$Collection {
    int size();

    boolean isEmpty();

    boolean contains(int element);

    Ez$Int$Iterator iterator();

    int[] toArray();

    boolean add(int element);

    boolean remove(int element);

    void clear();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
}
