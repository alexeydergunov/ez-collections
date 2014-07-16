package ez.collections;

public interface _Ez_Int__Int_SortedMap extends _Ez_Int__Int_Map {
    // TODO null issues, e.g. when there is no higher key

    @Override
    int size();

    @Override
    boolean isEmpty();

    @Override
    boolean containsKey(/*KC*/int/*KC*/ key);

    @Override
    /*V*/int/*V*/ get(/*KC*/int/*KC*/ key);

    @Override
    /*V*/int/*V*/ put(/*KC*/int/*KC*/ key, /*V*/int/*V*/ value);

    @Override
    /*V*/int/*V*/ remove(/*KC*/int/*KC*/ key);

    @Override
    void clear();

    @Override
    /*KC*/int/*KC*/[] keys();

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

    /*KC*/int/*KC*/ getFirstKey();

    /*KC*/int/*KC*/ getLastKey();

    /*V*/int/*V*/ getFirstValue();

    /*V*/int/*V*/ getLastValue();

    void removeFirst();

    void removeLast();

    /*KC*/int/*KC*/ floorKey(/*KC*/int/*KC*/ key);

    /*KC*/int/*KC*/ ceilingKey(/*KC*/int/*KC*/ key);

    /*KC*/int/*KC*/ lowerKey(/*KC*/int/*KC*/ key);

    /*KC*/int/*KC*/ higherKey(/*KC*/int/*KC*/ key);
}
