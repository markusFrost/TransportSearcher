package loaders;

import models.Bus;

public class CashWorker {
    private static CashWorker ourInstance = new CashWorker();

    public static CashWorker getInstance() {
        return ourInstance;
    }

    private CashWorker() {}

    public void saveAllBusesPage(){
        final String url = "http://mybuses.ru/moscow/";

        String html = InfoLoader.getInstance().getHtmlCodeByUrl(url);
        FileLoader.getInstance().writeToFileHtmlCodeFromBusesListPage(html);
    }

    public void saveBusPage(Bus bus){
        final String url = bus.getUrl();

        String html = InfoLoader.getInstance().getHtmlCodeByUrl(url);
        bus.setHtmlCode(html);

        FileLoader.getInstance().writeToFileHtmlCodeFromBusPage(bus);
    }

    public String getHtmlCodeFromMainPageTextFile(){
        return FileLoader.getInstance().getHtmlCodeFromMainPageTextFile();
    }

    public String getHtmlCodeFromBusPageTextFile(Bus bus){
        return FileLoader.getInstance().getHtmlCodeFromBusPageTextFile(bus);
    }
}
