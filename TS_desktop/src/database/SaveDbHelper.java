package database;

import models.Bus;
import models.Station;

import java.util.ArrayList;
import java.util.List;

public class SaveDbHelper {

    public static int fillBusTable(Bus bus){
        DbHelper dbHelper = DbHelper.getInstance();
        return dbHelper.addBus(bus);
    }
    public static List<Integer> fillBusToStationTable(int busId, List<Integer> listStationsIds){

        List<Integer> listBusToStationsIds = new ArrayList<>();

        DbHelper dbHelper = DbHelper.getInstance();

        for (int stationId : listStationsIds) {
            if(busId > 0 && stationId > 0) {
                final int busToStationId =
                        dbHelper.addBusToStation(busId, stationId);
                listBusToStationsIds.add(busToStationId);
            }
        }
        return listBusToStationsIds;
    }

    public static List<Integer> fillStationTable(List<Station> listStations){

        List<Integer> listStationsIds = new ArrayList<>();

        DbHelper dbHelper = DbHelper.getInstance();

        for (Station station : listStations) {
            final int stationId = dbHelper.addStation(station);
            listStationsIds.add(stationId);
        }
        return listStationsIds;
    }

}
