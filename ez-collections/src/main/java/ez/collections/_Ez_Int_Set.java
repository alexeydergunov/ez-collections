package ez.collections;

public interface _Ez_Int_Set extends _Ez_Int_Collection {
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
}
