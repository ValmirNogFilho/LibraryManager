package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import utils.statusEmprestimo;
import utils.statusLeitor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Sistema {

    public static void atualizarMultas(){

        for (Emprestimo emprestimo: DAO.getEmprestimoDAO().findMany()){
            Leitor leitor = DAO.getLeitorDAO().findByPrimaryKey(emprestimo.getUsuarioId());

            verificarPossivelMulta(emprestimo, leitor);
            verificarPagamentoDeMulta(emprestimo, leitor);

            DAO.getEmprestimoDAO().update(emprestimo);
            DAO.getLeitorDAO().update(leitor);
        }
    }

    public static void verificarPagamentoDeMulta(Emprestimo emprestimo, Leitor leitor) {
        int atraso = emprestimo.getAtraso();

        if(atraso > 0){
            emprestimo.setAtraso(atraso-1);
            if(emprestimo.getAtraso() == 0){
                emprestimo.setStatus(statusEmprestimo.CONCLUIDO);
                DAO.getEmprestimoDAO().update(emprestimo);
                if(DAO.getEmprestimoDAO().maiorAtraso(leitor) == 0)
                    leitor.setStatus(statusLeitor.LIVRE);
            }
        }
    }

    public static boolean verificarPossivelMulta(Emprestimo emprestimo, Leitor leitor) {
        int saldoAtraso = (int) ChronoUnit.DAYS.between(emprestimo.getDataFim(), LocalDate.now());

        boolean estaAtrasado = saldoAtraso > 0;

        if(estaAtrasado) {

            int maiorMulta = Math.max(saldoAtraso, DAO.getEmprestimoDAO().maiorAtraso(leitor));
            emprestimo.setAtraso(2 * maiorMulta);
            emprestimo.setStatus(statusEmprestimo.MULTADO);
            leitor.setStatus(statusLeitor.MULTADO);


        }
        return estaAtrasado;
    }


}
