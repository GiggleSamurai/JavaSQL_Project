/**
 * @class DisplayTime.java
 * @author Louis Wong
 */

package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DisplayTime {
    private static ObservableList<String> allHours = FXCollections.observableArrayList();
    private static ObservableList<String> allMinutes = FXCollections.observableArrayList();
    private static ObservableList<String> theAMorPM = FXCollections.observableArrayList();

    /**
     *
     * @return list of all the hours in 12 hour system
     */
    public static ObservableList<String> getAllHours() {
        allHours.clear();
        allHours.addAll( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11","12");
        return allHours;
    }

    /**
     *
     * @return list of all the minutes available for appointment
     */
    public static ObservableList<String> getAllMinutes() {
        allMinutes.clear();
        allMinutes.addAll("00", "15", "30", "45");
        return allMinutes;
    }

    /**
     *
     * @return list of just AM & PM
     */
    public static ObservableList<String> getAMorPM() {
        theAMorPM.clear();
        theAMorPM.addAll( "AM", "PM");
        return theAMorPM;
    }

    public static Integer getMinuteInt(String minute) {
        Integer minValue = Integer.parseInt(minute);
        return minValue;
    }

    /**
     *
     * @param hour hour to convert
     * @param AmOrPm if AM or PM to convert
     * @return the 12 hour formatted integer
     */
    public static Integer getHourInt(String hour, String AmOrPm) {

        if (AmOrPm.equals("AM") && Integer.parseInt(hour) != 12){
            return Integer.parseInt(hour);
        } else if (Integer.parseInt(hour) == 12 && AmOrPm.equals("PM")) {
            return 12;
        } else {
            Integer thisHour = Integer.parseInt(hour) + 12;
            if (thisHour == 24) {
                return 0;
            } else {
                return thisHour;
            }

        }
    }

    /**
     *
     * @param userTime user time
     * @return converted UTC time
     */
    public static LocalDateTime userTime2UTC(LocalDateTime userTime) {
        ZonedDateTime userZoneTime = ZonedDateTime.of(userTime, ZoneId.systemDefault());
        ZonedDateTime utcZoneTime = userZoneTime.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime utcTime = utcZoneTime.toLocalDateTime();
        return utcTime;
    }

    /**
     *
     * @param UTCTime utc time
     * @return converted ET time
     */
    public static LocalDateTime UTCTime2EST(LocalDateTime UTCTime) {
        ZonedDateTime utcZoneTime = ZonedDateTime.of(UTCTime, ZoneOffset.UTC);
        ZonedDateTime etZoneTime = utcZoneTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
        LocalDateTime etTime = etZoneTime.toLocalDateTime();
        return etTime;
    }

    /**
     *
     * @param StartUTCDateTime the appointment start UTC time
     * @param EndUTCDateTime the appointment end UTC time
     * @return boolean statement if is in business hour or not
     */
    public static boolean inBusinessHours(LocalDateTime StartUTCDateTime, LocalDateTime EndUTCDateTime) {

        int StartHour = UTCTime2EST(StartUTCDateTime).getHour();
        int EndHour = UTCTime2EST(EndUTCDateTime).getHour();
        int OpenESTHour = 8 + 0; //0=AM
        int CloseESTHour = 10 + 12; //12=PM

        int EndHourMin = UTCTime2EST(EndUTCDateTime).getMinute();
        if (StartHour>=OpenESTHour && StartHour<=CloseESTHour && EndHour>=OpenESTHour && EndHour<=CloseESTHour) {
            if (EndHour==CloseESTHour && EndHourMin != 0){
                return false;
            } else {
                //In Business Hour
                return true;
            }
        } else {
            return false;
        }
    }
}
