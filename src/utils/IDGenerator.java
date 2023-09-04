package utils;

import java.time.Year;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import static java.util.Calendar.YEAR;

public class IDGenerator {
    public static String geraID() {
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        ano %=  100;
        Random random = new Random();
        Integer randint = 1000 + random.nextInt(9999);
        return ano + randint.toString();
    }
}
