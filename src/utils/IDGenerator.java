package utils;

import java.time.Year;
import java.util.Calendar;
import java.util.UUID;

import static java.util.Calendar.YEAR;

public class IDGenerator {
    public static String geraID() {
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        ano %=  100;

        return ano + "";
    }
}
