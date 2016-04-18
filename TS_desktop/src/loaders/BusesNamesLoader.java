package loaders;

import models.Bus;

import java.util.List;

public class BusesNamesLoader {
    private static BusesNamesLoader ourInstance = new BusesNamesLoader();

    public static BusesNamesLoader getInstance() {
        return ourInstance;
    }

    private BusesNamesLoader() {}

    public static final String MAIN_URL = "http://mybuses.ru/moscow/";

    public static List<Bus> loadAllBuses() {
        String html = CashWorker.getInstance().getHtmlCodeFromMainPageTextFile();
        return HtmlWorker.getListBuses(html);

    }
}
