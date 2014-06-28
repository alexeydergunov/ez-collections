package ez.collections.list;

import ez.collections.EzIntCollection;
import ez.collections.EzIntIterator;

public interface EzIntList extends EzIntCollection {
    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean contains(int element);

    @Override
    EzIntIterator iterator();

    @Override
    int[] toArray();

    @Override
    boolean add(int element);

    @Override
    boolean remove(int element);

    @Override
    void clear();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();

    int get(int index);

    int set(int index, int element);

    void insert(int index, int element);

    int removeAt(int index);

    int indexOf(int element);

    int lastIndexOf(int element);
}
