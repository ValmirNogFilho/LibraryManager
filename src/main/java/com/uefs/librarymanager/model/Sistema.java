package com.uefs.librarymanager.model;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.utils.statusEmprestimo;
import com.uefs.librarymanager.utils.statusLeitor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * Esta classe é responsável por executar tarefas de atualização de informações que não seriam possíveis de serem feitas
 * até mesmo pelo administrador (cargo mais alto na hierarquia do sistema) e pela aba de login. As tarefas em questão são:
 * Atualizar multas;
 * Verificar possíveis multas;
 * Atualizar reservas.
 * @author: Valmir Alves Nogueira Filho
 * @author: Kevin Cordeiro Borges
 * @see: main.java.com.uefs.librarymanager.dao.DAO
 * @see: main.java.com.uefs.librarymanager.exceptions.UsuarioException
 * @see: main.java.com.uefs.librarymanager.utils.statusEmprestimo
 * @see: main.java.com.uefs.librarymanager.utils.statusLeitor
 * @see: java.time.LocaleDate
 * @see: java.time.temporal.ChronoUnit
 * @see: java.util.LinkedList
 * @see: java.util.List
 * @see: java.util.Map
 * @see: java.util.Objects
 */
public class Sistema {
    /**
     * Este método corresponde à tarefa de Atualizar multas.
     * O método verifica se dado leitor possui multa, caso ele esteja com a multa definida (já tenha devolvido o livro),
     * é decrementado a quantidade de dias passados desde a última execução do método. Caso o prazo da multa do leitor já estiver acabado,
     * o status do leitor é alterado de MULTADO para LIVRE.
     * Ao fim, é feito um update(atualização) da situação do leitor.
     */
    public static void atualizarMultas(LocalDate ultimoAcesso){
        for (Leitor leitor: DAO.getLeitorDAO().findMany()){
            int intervaloDias = Math.min(leitor.getPrazoMulta(), (int) ChronoUnit.DAYS.between(ultimoAcesso, LocalDate.now()));
            // contabiliza quantidade de dias passados desde a última execução do método
            if (leitor.getPrazoMulta() != 0)
                leitor.setPrazoMulta(leitor.getPrazoMulta()-intervaloDias);
            else if (leitor.getPrazoMulta() < 0)
                leitor.setPrazoMulta(0);

            for (Emprestimo emprestimo: DAO.getEmprestimoDAO().findByLeitor(leitor)){

                verificarPossivelMulta(emprestimo, leitor);

            }

            if (leitor.getPrazoMulta()==0 && leitor.getStatus().equals(statusLeitor.MULTADO))
                leitor.setStatus(statusLeitor.LIVRE);

            DAO.getLeitorDAO().update(leitor);
        }

    }

    /**
     * Este método corresponde à tarefa de verificar possíveis multas.
     * O metódo faz uma checagem para ver se se há a possibilidade de um possível atraso de devolução de livro, o que
     * culmina numa multa. É feita uma contagem do dia estabelecido para devolução do livro e o dia atual (da consulta),
     * o valor é armazenado numa variável "saldoAtraso". Caso o valor da variável seja maior do que zero, é indicativo de
     * que uma multa deve ser aplicada ao leitor. Caso o leitor tenha mais de um atraso, a multa é aplicada sobre a devolução
     * atrasada com maior tempo. A cada dia de atraso, é aplicada uma multa de dois dias. Além disso, é feita um verificação
     * que confere se o dia atual é igual ao dia estabelecido para a devolução do livro, caso seja, o empréstimo é dado
     * como concluído.
     * @param emprestimo
     * @param leitor
     * @return estado de atraso da devolução do livro
     */

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
        else if(LocalDate.now().isEqual(emprestimo.getDataFim())){
            emprestimo.setStatus(statusEmprestimo.CONCLUIDO);
        }
        DAO.getEmprestimoDAO().update(emprestimo);
        DAO.getLeitorDAO().update(leitor);
        return estaAtrasado;
    }

    /**
     * Este método é referente à tarefa de atualização de reservas.
     * O método faz a contagem de quantos exemplares por ISBN estão disponíveis e de quantas pessoas estão na espera pelo
     * livro. Caso o número de computado pela variável contadora "i" seja menor que a quantidade disponível de livros,
     * os usuários que estão aptos a pegar o livro ficam no topo da lista. Por exemplo:
     * Se cinco livros ficam disponíveis para empréstimo, os cinco primeiros da fila ficam aptos para pegar um exemplar do
     * livro.
     * Após o usuário ficar apto para empréstimo, é dada uma tolerância de três dias para ser feito o empréstimo e retirada
     * do livro, caso esse prazo se exceda, a reserva é deletada e o próximo leitor fica apto ao empréstimo.
     * Tomando o exemplo dado acima, o sexto leitor da fila ficaria apto ao empréstimo.
     */
    public static void atualizarReservas(){
        Map<String, LinkedList<Reserva>> reservas = DAO.getReservaDAO().findManyMap();
        for(String ISBN: reservas.keySet()) {
            int disponiveis = DAO.getLivroDAO().findByPrimaryKey(ISBN).getDisponiveis();
            for(int i = 0; i< reservas.get(ISBN).size(); i++){
                Reserva reserva = reservas.get(ISBN).get(i);
                if (i < disponiveis){
                    if(Objects.isNull(reserva.getDataFim())){
                        reserva.setDataFim(LocalDate.now().plusDays(3));
                        DAO.getReservaDAO().update(reserva);
                    }
                    else if(reserva.getDataFim().isBefore(LocalDate.now())){
                        DAO.getReservaDAO().delete(reserva);
                    }
                }
                else{
                    reserva.setDataFim(null);
                    DAO.getReservaDAO().update(reserva);
                }
            }
        }
    }

    /**
     * Este método é responsável pela aba do login no sistema. Nele é feita uma verificação para identificar se o
     * usuário é administrador, bibliotecário ou leitor. Ao usuário ser identificado a partir do seu ID, é feita uma
     * verificação de senha, caso a senha esteja incorreta, a exceção  SENHA_INVALIDA é ativada informando ao usuário
     * que a senha dele difere da senha feita no cadastro.
     * @param id
     * @param senha
     * @param cargo
     * @throws UsuarioException
     * @return obj que pode ser um leitor, administrador ou bibliotecário.
     */
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
