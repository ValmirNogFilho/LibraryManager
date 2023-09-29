package utils;

import java.time.Year;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import static java.util.Calendar.YEAR;

/**
 * Esta classe é responsável por gerar o ID de cada usuário da biblioteca. O ID é formado por seis dígitos, sendo dois
 * os dois últimos números do ano, exemplo: 2023, o ID vai conter o número 23 + quatro números aleatórios.
 * Exemplo de um possível ID: 232113.
 * @author  Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see java.time.Year
 * @see java.util.Calendar
 * @see java.util.LinkedList
 * @see java.util.Random
 * @see java.util.UUID
 */
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
