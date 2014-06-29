package ez.collections.list;

import ez.collections.Ez$Int$Collection;
import ez.collections.Ez$Int$Iterator;

public interface Ez$Int$List extends Ez$Int$Collection {
    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean contains(/*T*/int/*T*/ element);

    @Override
    Ez$Int$Iterator iterator();

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

    /*T*/int/*T*/ get(int index);

    /*T*/int/*T*/ set(int index, /*T*/int/*T*/ element);

    void insert(int index, /*T*/int/*T*/ element);

    /*T*/int/*T*/ removeAt(int index);

    int indexOf(/*T*/int/*T*/ element);

    int lastIndexOf(/*T*/int/*T*/ element);
}
