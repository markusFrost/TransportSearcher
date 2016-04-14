package models;

public class Pair<U,V> {
    private U mListTableWorkDay;
    private V mListTableHoliday;

    public Pair(U item1, V item2) {
        mListTableWorkDay = item1;
        mListTableHoliday = item2;
    }

    public U getListTableWorkDay() {
        return mListTableWorkDay;
    }

    public V getListTableHoliday() {
        return mListTableHoliday;
    }
}
