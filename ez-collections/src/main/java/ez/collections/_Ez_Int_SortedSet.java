package ez.collections;

public interface _Ez_Int_SortedSet extends _Ez_Int_Set {
    // TODO null issues, e.g. when there is no higher element - consider returning special 'null value'

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean contains(/*C*/int/*C*/ element);

    @Override
    _Ez_Int_Iterator iterator();

    @Override
    /*C*/int/*C*/[] toArray();

    @Override
    boolean add(/*C*/int/*C*/ element);

    @Override
    boolean remove(/*C*/int/*C*/ element);

    @Override
    void clear();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();

    @Override
    String toString();

    /*C*/int/*C*/ getFirst();

    /*C*/int/*C*/ getLast();

    /*C*/int/*C*/ removeFirst();

    /*C*/int/*C*/ removeLast();

    /*C*/int/*C*/ floor(/*C*/int/*C*/ element);

    /*C*/int/*C*/ ceiling(/*C*/int/*C*/ element);

    /*C*/int/*C*/ lower(/*C*/int/*C*/ element);

    /*C*/int/*C*/ higher(/*C*/int/*C*/ element);

    boolean wasCorrectValueReturned();
}
