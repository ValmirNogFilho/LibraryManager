package com.uefs.librarymanager.utils;

import com.uefs.librarymanager.dao.DAO;

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

    public static String geraID() {
        String id;
        do{
            id = numerosFinaisAnoCadastro() + sequenciaAleatoriaQuatroDigitos();
        }
        while(idJaUtilizado(id));
        return id;
    }

    private static Integer numerosFinaisAnoCadastro(){
        Calendar calendar = Calendar.getInstance();
         return calendar.get(Calendar.YEAR) %  100;
    }

    private static String sequenciaAleatoriaQuatroDigitos(){
        return String.valueOf(1000 + new Random().nextInt(8999));
    }

    private static boolean idJaUtilizado(String id){
        return (DAO.getLeitorDAO().findByPrimaryKey(id) != null
                || DAO.getOperadorDAO().findByPrimaryKey(id) != null);
    }
}
