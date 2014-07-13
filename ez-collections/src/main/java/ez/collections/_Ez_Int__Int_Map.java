package ez.collections;

public interface _Ez_Int__Int_Map {
    // TODO what to return if there is no value for a certain key?

    int size();

    boolean isEmpty();

    boolean containsKey(/*K*/int/*K*/ key);

    /*V*/int/*V*/ get(/*K*/int/*K*/ key);

    /*V*/int/*V*/ put(/*K*/int/*K*/ key, /*V*/int/*V*/ value);

    /*V*/int/*V*/ remove(/*K*/int/*K*/ key);

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
