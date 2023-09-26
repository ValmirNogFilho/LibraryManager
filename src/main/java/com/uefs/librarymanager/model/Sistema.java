package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.statusEmprestimo;
import utils.statusLeitor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Map;

public class Sistema {

    public static void atualizarMultas(){

        for (Leitor leitor: DAO.getLeitorDAO().findMany()){
            if (leitor.getPrazoMulta() > 0)
                leitor.setPrazoMulta(leitor.getPrazoMulta()-1);

            for (Emprestimo emprestimo: DAO.getEmprestimoDAO().findByLeitor(leitor)){

                verificarPossivelMulta(emprestimo, leitor);
                DAO.getEmprestimoDAO().update(emprestimo);
            }

            if (leitor.getPrazoMulta()==0 && leitor.getStatus().equals(statusLeitor.MULTADO))
                leitor.setStatus(statusLeitor.LIVRE);

            DAO.getLeitorDAO().update(leitor);
        }

    }

    public static boolean verificarPossivelMulta(Emprestimo emprestimo, Leitor leitor) {
        int saldoAtraso = (int) ChronoUnit.DAYS.between(emprestimo.getDataFim(), LocalDate.now());

        boolean estaAtrasado = saldoAtraso > 0;
        int maiorMulta = Math.max(saldoAtraso, DAO.getEmprestimoDAO().maiorAtraso(leitor));

        if(estaAtrasado && !(emprestimo.getStatus().equals(statusEmprestimo.MULTADO)) &&
        maiorMulta == saldoAtraso) {

            leitor.setInicioMulta(LocalDate.now());
            leitor.setPrazoMulta(2* maiorMulta);
            emprestimo.setAtraso(saldoAtraso);
            emprestimo.setStatus(statusEmprestimo.MULTADO);
            leitor.setStatus(statusLeitor.MULTADO);

        }
        return estaAtrasado;
    }

    public static void atualizarReservas(){
        Map<String, LinkedList<Reserva>> reservas = DAO.getReservaDAO().findManyMap();
        for(String ISBN: reservas.keySet()){
            Reserva primeiroFila = DAO.getReservaDAO().findByPrimaryKey(ISBN);
            //faltando a verificação de exemplares disponíveis para próximos da fila
            if(primeiroFila != null && DAO.getLivroDAO().findByPrimaryKey(ISBN).getDisponiveis() > 0){
                primeiroFila.setPrazo(primeiroFila.getPrazo()-1);
                DAO.getReservaDAO().update(primeiroFila);
                if(primeiroFila.getPrazo() == 0){
                    DAO.getReservaDAO().popFila(ISBN, 0);
                }
            }
        }
    }

    public static void gerirPrazoReservas(){
        Map<String, LinkedList<Reserva>> reservas = DAO.getReservaDAO().findManyMap();
        for(String ISBN: reservas.keySet()){
            LinkedList<Reserva> reservasISBN = reservas.get(ISBN);
            if(!reservasISBN.isEmpty()){
                int disponiveis = DAO.getLivroDAO().findByPrimaryKey(ISBN).getDisponiveis();
                int disponiveisParaFila = Math.min(disponiveis, reservas.get(ISBN).size());

                for(int i = 0; i < disponiveisParaFila; i++){
                    Reserva leitorDaFila =  reservasISBN.get(i);
                    leitorDaFila.setPrazo(leitorDaFila.getPrazo()-1);
                    DAO.getReservaDAO().update(leitorDaFila);
                    if(leitorDaFila.getPrazo() == 0){
                        DAO.getReservaDAO().delete(leitorDaFila);
                        i--;
                    }
                }
            }

        }
    }

    public static Usuario login(String id, String senha, String cargo) throws UsuarioException {
        Usuario obj = null;
        if(cargo.equals("Administrador") || cargo.equals("Bibliotecario")){
            obj = DAO.getOperadorDAO().findById(id);
        } else if (cargo.equals("Leitor")) {
            obj = (Leitor) DAO.getLeitorDAO().findById(id);
        }
        if(obj.getSenha().equals(senha)){
            return obj;
        }
        else throw new UsuarioException(UsuarioException.SENHA_INVALIDA);
    }

}
