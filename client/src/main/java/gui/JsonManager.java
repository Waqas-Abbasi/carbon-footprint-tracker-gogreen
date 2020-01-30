package gui;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;

public class JsonManager {
    /**
     * adds a date.
     * @param obj date to add
     * @return String
     */
    public String addDate(JSONObject obj) {
        LocalDate today = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String time = localTime.toString();
        time = time.substring(0, time.indexOf("."));

        obj.put("date", today);
        obj.put("time", time);
        return obj.toString();
    }
}
