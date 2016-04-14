package loaders;

import database.SaveDbHelper;

import java.util.List;

public class TimeLoader {
    public static void loadTimeInfo(List<List<Long>> listTableWorkDay){
        final int WORK_DAY = 1;

        for(List<Long> listWorkDay : listTableWorkDay){
            for(Long timeDepart : listWorkDay){
                int timeDepartId = SaveDbHelper.fillTimeDepartmentTable(timeDepart, WORK_DAY);
            }
        }
    }
}
