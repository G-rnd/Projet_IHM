package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Unused.
 */
public class Date {
    final static String format = "yyyy-MM-dd";

    public static boolean isValide(String date) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValide(int[] date) {
        try {
            return isValide(dateToString(date));
        } catch (Exception e) {
            return false;
        }
    }

    public static int[] stringToDate(String s) throws Exception {
        if (isValide(s)) {
            int[] date = new int[3];
            String[] tab = s.split("-");
            for (int i = 0; i < 3; i++) {
                date[i] = Integer.parseInt(tab[i]);
            }
            return date;
        } else {
            throw new Exception("Error date format");
        }
    }

    public static String dateToString(int[] date) throws Exception {
        String ret = date[0] + "-" + date[1] + "-" + date[2];
        if (isValide(ret))
            return ret;
        else
            throw new Exception("Error date format");
    }

    public static boolean datesEgales(int[] d1, int[] d2) {
        if (isValide(d1) && isValide(d2)) {
            return (d1[0] == d2[0]) && (d1[1] == d2[1]) && (d1[2] == d2[2]);
        }
        return false;
    }
}
