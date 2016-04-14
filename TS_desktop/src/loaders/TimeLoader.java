package loaders;

import database.SaveDbHelper;

import java.util.List;

public class TimeLoader {
    public static void loadTimeInfo(List<List<Long>> listTableWorkDay, List<Integer> listBusStopIds, int day_type){

        for(List<Long> listWorkDay : listTableWorkDay){
            for(int i = 0; i < listWorkDay.size(); i++){
                int timeDepartId =
                        SaveDbHelper.fillTimeDepartmentTable(listWorkDay.get(i), day_type);

                int busStopId = listBusStopIds.get(i);

                int stationTimeDepartId =
                        SaveDbHelper.fillStationTimeDepartTable(busStopId, timeDepartId);
            }
        }
    }
}
