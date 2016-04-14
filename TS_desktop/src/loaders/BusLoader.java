package loaders;

import database.SaveDbHelper;
import models.Bus;
import models.Station;

import java.util.List;

public class BusLoader {
    public static List<Integer> loadBusInfo(
            final Bus bus,
            final List<Station> listStations,
            int directionType){

        int busId = SaveDbHelper.fillBusTable(bus);

        int routeId = SaveDbHelper.fillRoutTable(busId, directionType);

        List<Integer> listStationsIds =
                SaveDbHelper.fillStationTable(listStations);

        List<Integer> listBusToStationIds =
                SaveDbHelper.fillBusToStationTable(busId, listStationsIds);

        List<Integer> listBusStopIds =
                SaveDbHelper.fillBusStopListTable(listBusToStationIds, routeId);

        return listBusStopIds;
    }

    public static List<Station> loadStations(
            final String html,
            final String query){
        InfoLoader infoLoader = InfoLoader.getInstance();

        List<Station> listStations = infoLoader.getListStations(html, query);

        return listStations;
    }
}
