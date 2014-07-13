package ez.collections;

public interface _Ez_Int__Int_SortedMap extends _Ez_Int__Int_Map {
    // TODO null issues, e.g. when there is no higher key

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean containsKey(/*K*/int/*K*/ key);

    @Override
    /*V*/int/*V*/ get(/*K*/int/*K*/ key);

    @Override
    /*V*/int/*V*/ put(/*K*/int/*K*/ key, /*V*/int/*V*/ value);

    @Override
    /*V*/int/*V*/ remove(/*K*/int/*K*/ key);

    @Override
    void clear();

    @Override
    /*K*/int/*K*/[] keys();

    @Override
    /*V*/int/*V*/[] values();

    @Override
    _Ez_Int__Int_MapIterator iterator();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();

    @Override
    String toString();

    /*K*/int/*K*/ getFirstKey();

    /*K*/int/*K*/ getLastKey();

    /*V*/int/*V*/ getFirstValue();

    /*V*/int/*V*/ getLastValue();

    void removeFirst();

    void removeLast();

    /*K*/int/*K*/ floorKey(/*K*/int/*K*/ key);

    /*K*/int/*K*/ ceilingKey(/*K*/int/*K*/ key);

    /*K*/int/*K*/ lowerKey(/*K*/int/*K*/ key);

    /*K*/int/*K*/ higherKey(/*K*/int/*K*/ key);
}
