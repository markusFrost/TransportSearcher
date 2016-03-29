package loaders;

import database.DbHelper;
import models.Bus;
import models.Pair;
import models.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.HelpUtils;

import java.util.ArrayList;
import java.util.List;

public class HtmlWorker {
    //этот метод получает на вход html детальной страницы автобуса
    // и в зависимсти от введённыъ параметров читает станции
    // после этого автобус сохраняется
    //станции сохраняются
    //и их ид сохраняются в таблице
    //за счёт добавление индексов уникальности данные
    //будь то автобус, станция или их ид - не дублируются
    // этом случае идёт проверка на существование
    // если есть - возвращается ид и с ним таким же образом
    // производится запист в таблицу
    public static int loadBusInfo(
            final String query,
            final String html, final Bus bus){

        // читаем таблицу со списком станций
        // в зависимость от query это либо в одном направлении
        // либо в другом
        InfoLoader infoLoader = InfoLoader.getInstance();
//        final String query1 = "table#table1>thead>tr>th>a";
        //получаем массив станций
        List<Station> listStations = infoLoader.getListStations(html, query);

        DbHelper dbHelper = DbHelper.getInstance();

        int busId = dbHelper.addBus(bus); //добавляем автобус в таблицу
        // и получаем его ид

        //проверок на ложь буду делать по мере необходимости
       // System.out.println("busId = " + busId);

        int weight = 1;

        for (Station station : listStations) {
            int stationId =   dbHelper.addStation(station); //добавляем каждую станцию в БД

            if(busId > 0 && stationId > 0) {
                int bs_id = dbHelper.addBusToStation(busId, stationId); // и готовые ид добавляем в сответствующую таблицу

                //dbHelper.addBusToStationInfo(bs_id, weight, 1);
                weight++;

               // System.out.println("bsToStId = " + bs_id);
            }
        }
        return listStations.size();
    }

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
}

/*

select distinct Bus.name as bus_name, Station.name as station_name from BusToStation, Bus, Station where (BusToStation.station_id = 314 or BusToStation.station_id = 320)
and BusToStation.station_id = Station._id

ищем автобусы, которые подъезжают к указанным станциям

 */
