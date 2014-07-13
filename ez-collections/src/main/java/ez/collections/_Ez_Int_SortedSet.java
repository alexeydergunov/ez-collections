package ez.collections;

public interface _Ez_Int_SortedSet extends _Ez_Int_Set {
    // TODO null issues, e.g. when there is no higher element

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean contains(/*T*/int/*T*/ element);

    @Override
    _Ez_Int_Iterator iterator();

    @Override
    /*T*/int/*T*/[] toArray();

    @Override
    boolean add(/*T*/int/*T*/ element);

    @Override
    boolean remove(/*T*/int/*T*/ element);

    @Override
    void clear();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();

    @Override
    String toString();

    /*T*/int/*T*/ getFirst();

    /*T*/int/*T*/ getLast();

    /*T*/int/*T*/ removeFirst();

    /*T*/int/*T*/ removeLast();

    /*T*/int/*T*/ floor(/*T*/int/*T*/ element);

    /*T*/int/*T*/ ceiling(/*T*/int/*T*/ element);

    /*T*/int/*T*/ lower(/*T*/int/*T*/ element);

    /*T*/int/*T*/ higher(/*T*/int/*T*/ element);
}
