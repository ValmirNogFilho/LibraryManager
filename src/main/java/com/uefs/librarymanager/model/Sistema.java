package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import utils.statusEmprestimo;
import utils.statusLeitor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Sistema {

    public static void atualizarMultas(){

        for (Leitor leitor: DAO.getLeitorDAO().findMany()){
            if (leitor.getPrazoMulta() > 0)
                leitor.setPrazoMulta(leitor.getPrazoMulta()-1);

            for (Emprestimo emprestimo: DAO.getEmprestimoDAO().findByLeitor(leitor)){

                verificarPossivelMulta(emprestimo, leitor);
                verificarPagamentoDeMulta(emprestimo);

                DAO.getEmprestimoDAO().update(emprestimo);
            }

            if (leitor.getPrazoMulta()==0 && leitor.getStatus().equals(statusLeitor.MULTADO))
                leitor.setStatus(statusLeitor.LIVRE);

            DAO.getLeitorDAO().update(leitor);
        }

    }

    public static void verificarPagamentoDeMulta(Emprestimo emprestimo) {
        boolean estaMultado = LocalDate.now().isAfter(emprestimo.getDataFim());
        if(!estaMultado && emprestimo.getStatus().equals(statusEmprestimo.MULTADO)){
            emprestimo.setStatus(statusEmprestimo.CONCLUIDO);
        }
    }

    public static boolean verificarPossivelMulta(Emprestimo emprestimo, Leitor leitor) {
        int saldoAtraso = (int) ChronoUnit.DAYS.between(emprestimo.getDataFim(), LocalDate.now());

        boolean estaAtrasado = saldoAtraso > 0;
        int maiorMulta = Math.max(saldoAtraso, DAO.getEmprestimoDAO().maiorAtraso(leitor));

        if(estaAtrasado && !(emprestimo.getStatus().equals(statusEmprestimo.MULTADO)) &&
        maiorMulta <= saldoAtraso) {

            leitor.setInicioMulta(LocalDate.now());
            leitor.setPrazoMulta(2* maiorMulta);
            emprestimo.setAtraso(saldoAtraso);
            emprestimo.setStatus(statusEmprestimo.MULTADO);
            leitor.setStatus(statusLeitor.MULTADO);


        }
        return estaAtrasado;
    }


}
