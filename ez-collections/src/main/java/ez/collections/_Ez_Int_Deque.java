package ez.collections;

public interface _Ez_Int_Deque extends _Ez_Int_Queue, _Ez_Int_Stack {
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

    void addFirst(/*T*/int/*T*/ element);

    void addLast(/*T*/int/*T*/ element);

    /*T*/int/*T*/ getFirst();

    /*T*/int/*T*/ getLast();

    /*T*/int/*T*/ removeFirst();

    /*T*/int/*T*/ removeLast();
}
