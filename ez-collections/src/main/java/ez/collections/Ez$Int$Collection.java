package ez.collections;

public interface Ez$Int$Collection {
    int size();

    boolean isEmpty();

    boolean contains(/*T*/int/*T*/ element);

    Ez$Int$Iterator iterator();

    /*T*/int/*T*/[] toArray();

    boolean add(/*T*/int/*T*/ element);

    boolean remove(/*T*/int/*T*/ element);

    void clear();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();
}
