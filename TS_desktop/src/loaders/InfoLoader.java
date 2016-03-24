package loaders;

import models.Bus;
import models.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InfoLoader {
    private InfoLoader(){}
    private static InfoLoader sInfoLoader;
    private String mUrl;

    public static InfoLoader getInstance(){
        if (sInfoLoader == null){
            sInfoLoader = new InfoLoader();
        }
        return sInfoLoader;
    }

    public String getHtmlCodyByUrl(String url){

        StringBuilder sb = new StringBuilder();

        try {
            URL object = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(object.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }

            in.close();

        }catch (Exception e){}

        return sb.toString();
    }

    // по детальному урлу загружает информацию об автобусе
    // а конкретно о дате прибывания на ту или иную старцнию
    public Pair<List<List<String>>, List<List<String>>> getListTransportTable(String html,
                                                                              final String query, int stationsCount){
        Document doc = Jsoup.parse(html);

        //final String query = "table#table1>tbody>tr>td";
        Elements list = doc.select(query);

        int index = 0;

        List<List<String>> listTableWorkDay = new ArrayList<>();
        List<List<String>> listTableHoliday = new ArrayList<>();
        List<String> listTime = new ArrayList<>();

        boolean isHoliday = false;

        for (Element link : list){
            String name = link.text();

            if(name.equals("выходные")){
                isHoliday = true;
            }
            else if(name.equals("будни")){
                isHoliday = false;
            }
            if(name.contains(":") && !isHoliday){
                listTime.add(name); //теккущий одномерный массив расписаний
                index++;
                if(index >= stationsCount){ //когда считали все время текуго маршрута
                    listTableWorkDay.add(listTime); //заносим его в таблицу
                    listTime = new ArrayList<>(); //обнуляем
                    index = 0; //и обнуляем счётчик
                }
            }
            else if(name.contains(":") && isHoliday){
                listTime.add(name);
                index++;
                if(index >= stationsCount){
                    listTableHoliday.add(listTime);
                    listTime = new ArrayList<>();
                    index = 0;
                }
            }
        }

        return new Pair<>(listTableWorkDay, listTableHoliday);

    }

    public List<String> getListStations(String html, final String query){
        List<String> listStations = new ArrayList<>();

        Document doc = Jsoup.parse(html);

       // final String query = "table#table1>thead>tr>th>a";
        Elements list = doc.select(query);

        for (Element link : list){
            String name = link.text();
            listStations.add(name);
        }

        return listStations;
    }

    public List<Bus> getListBuses(String html){

        List<Bus> listBuses = new ArrayList<>();
        Bus bus = null;

        final String query = "div.list-group>a.list-group-item";

        Document doc = Jsoup.parse(html);

        Elements array = doc.select(query);

        for (Element link : array){
            bus = new Bus();

            bus.setName(link.text());
            bus.setUrl(link.attr("href"));

            listBuses.add(bus);
        }

        return listBuses;

    }


}
