package loaders;

import models.Bus;
import models.Pair;
import models.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.HelpUtils;

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


    public List<Station> getListStations(String html, final String query){
        List<Station> listStations = new ArrayList<>();
        Station item = null;

        Document doc = Jsoup.parse(html);

       // final String query = "table#table1>thead>tr>th>a";
        Elements list = doc.select(query);

        for (Element link : list){
            String name = link.text();

            item = new Station();
            item.setName(name);
            listStations.add(item);
        }

        return listStations;
    }

    public List<Bus> getListBuses(String html, String url){

        List<Bus> listBuses = new ArrayList<>();
        Bus bus = null;

        final String query = "div.list-group>a.list-group-item";

        Document doc = Jsoup.parse(html);

        Elements array = doc.select(query);

        for (Element link : array){
            bus = new Bus();

            bus.setName(link.text());
            bus.setUrl(
                    HelpUtils.changeUrl(url, link.attr("href") ));

            listBuses.add(bus);
        }

        return listBuses;

    }


}
