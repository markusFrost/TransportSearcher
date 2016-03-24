package models;

public class Pair<U,V> {
    private U mItem1;
    private V mItem2;

    public Pair(U item1, V item2) {
        mItem1 = item1;
        mItem2 = item2;
    }

    public U getItem1() {
        return mItem1;
    }

    public V getItem2() {
        return mItem2;
    }
}
