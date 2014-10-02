package ez.collections;

public interface _Ez_Int__Int_Map {
    // TODO null issues, e.g. when there is no value for a certain key - consider returning special 'null value'

    int size();

    boolean isEmpty();

    boolean containsKey(/*K*/int/*K*/ key);

    /*V*/int/*V*/ get(/*K*/int/*K*/ key);

    /*V*/int/*V*/ put(/*K*/int/*K*/ key, /*V*/int/*V*/ value);

    /*V*/int/*V*/ remove(/*K*/int/*K*/ key);

    boolean returnedNull();

    void clear();

    /*K*/int/*K*/[] keys();

    /*V*/int/*V*/[] values();

    _Ez_Int__Int_MapIterator iterator();

    @Override
    boolean equals(Object object);

    @Override
    int hashCode();

    @Override
    String toString();
}
