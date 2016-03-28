package loaders;

import database.DbHelper;
import models.Bus;
import models.Station;

import java.util.List;

public class HtmlWorker {
    //этот метод получает на вход html детальной страницы автобуса
    // и в зависимсти от введённыъ параметров читает станции
    // после этого автобус сохраняется
    //станции сохраняются
    //и их ид сохраняются в таблице
    //за счёт добавление индексов уникальности данные
    //будь то автобус, станция или их ид - не дублируются
    public static void loadBusInfo(
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
        System.out.println(busId);

        for (Station station : listStations) {
            int stationId =   dbHelper.addStation(station); //добавляем каждую станцию в БД

            if(busId > 0 && stationId > 0) {
                int bs_id = dbHelper.addBusToStation(busId, stationId); // и готовые ид добавляем в сответствующую таблицу
                System.out.println(bs_id);
            }
        }

    }
}
