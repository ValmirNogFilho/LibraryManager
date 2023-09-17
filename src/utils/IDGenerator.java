package utils;

import java.time.Year;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import static java.util.Calendar.YEAR;

public class IDGenerator {
    public static LinkedList<String> IDsRegistrados = new LinkedList<String>();
    public static String geraID() {
        Calendar c = Calendar.getInstance();
        int ano = c.get(Calendar.YEAR);
        ano %=  100;
        Random random = new Random();
        Integer randint;
        String id;
        do{
            randint = 1000 + random.nextInt(9999);
            id = ano + randint.toString();
        }
        while(IDsRegistrados.contains(id));
        IDsRegistrados.add(id);
        return id;
    }
}
