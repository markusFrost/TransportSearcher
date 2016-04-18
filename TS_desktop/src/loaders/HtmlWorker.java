package loaders;

import models.Bus;
import models.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.HelpUtils;

import java.util.ArrayList;
import java.util.List;

public class HtmlWorker {


    // по детальному урлу загружает информацию об автобусе
    // а конкретно о дате прибывания на ту или иную старцнию
    public static Pair<List<List<Long>>, List<List<Long>>> getListTransportTable(String html,
                                                                              final String query, int stationsCount){
        Document doc = Jsoup.parse(html);

        //final String query = "table#table1>tbody>tr>td";
        Elements list = doc.select(query);

        int index = 0;

        List<List<Long>> listTableWorkDay = new ArrayList<>();
        List<List<Long>> listTableHoliday = new ArrayList<>();
        List<Long> listTime = new ArrayList<>();

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
                listTime.add(HelpUtils.convertStringToLong(name)); //теккущий одномерный массив расписаний
                index++;
                if(index >= stationsCount){ //когда считали все время текуго маршрута
                    listTableWorkDay.add(listTime); //заносим его в таблицу
                    listTime = new ArrayList<>(); //обнуляем
                    index = 0; //и обнуляем счётчик
                }
            }
            else if(name.contains(":") && isHoliday){
                listTime.add(HelpUtils.convertStringToLong(name));
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

    public static List<Bus> getListBuses(String html){

        List<Bus> listBuses = new ArrayList<>();
        Bus bus = null;

        final String query = "div.list-group>a.list-group-item";

        Document doc = Jsoup.parse(html);

        Elements array = doc.select(query);

        for (Element link : array){
            bus = new Bus();

            bus.setName(link.text());
            bus.setUrl(
                    HelpUtils.changeUrl(link.attr("href") ));

            listBuses.add(bus);
        }

        return listBuses;
    }
}

/*

select distinct Bus.name as bus_name, Station.name as station_name from BusToStation, Bus, Station where (BusToStation.station_id = 314 or BusToStation.station_id = 320)
and BusToStation.station_id = Station._id

ищем автобусы, которые подъезжают к указанным станциям

 */
