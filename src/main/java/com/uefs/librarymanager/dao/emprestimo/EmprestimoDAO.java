package com.uefs.librarymanager.dao.emprestimo;

import com.uefs.librarymanager.dao.CRUD;
import com.uefs.librarymanager.exceptions.EmprestimoException;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.*;

import java.util.List;

/**
 * Esta interface contém tudo que possui relação com os empréstimos, desde criar uma lista de objetos empréstimos, até
 * fazer verificações nas contas dos leitores para checar se eles podem fazer empréstimos, por exemplo.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see CRUD
 * @see EmprestimoException
 * @see LivroException
 * @see UsuarioException
 * @see com.uefs.librarymanager.model
 * @see  java.util.List
 */

public interface EmprestimoDAO extends CRUD<Emprestimo> {

    /**
     * Este método incrementa o integer proximoID, fazendo assim que haja uma sucessão crescente de IDs
     * @return próximo ID com relação ao anterior
     */
    public int getProximoID();

    /**
     * Este método adiciona um novo empréstimo à conta do leitor.
     * @param leitor
     * @return lista de empréstimos de dado leitor atualizada
     */
    public List<Emprestimo> findByLeitor(Leitor leitor);

    /**
     * Este método verifica quantos empréstimos o leitor tem em status de ANDAMENTO.
     * Uma variiável incrementadora "count" registra a ocorrência de empréstimos em andamento.
     * @param leitor
     * @return quantidade de empréstimos em andamento de dado leitor
     */
    public int qtdEmprestimosEmAndamentoDe(Leitor leitor);

    /**
     * Este método verifica se o leitor pode fazer mais empréstimos. Caso o usuário possua uma quantia menor que três
     * de empréstimos em andamento, ele pode sim fazer um novo empréstimo. Caso o número seja três, uma exceção é ativada.
     * @param leitor
     * @return True, caso o leitor possua menos de três empréstimos ou uma exceção caso o leitor já possua três empréstimos
     * registrados.
     * @throws UsuarioException
     */
    public boolean podeFazerMaisEmprestimos(Leitor leitor) throws UsuarioException;

    /**
     * Este método verifica se o leitor já possui algum livro de mesmo ISBN que ele está tentando fazer o empréstimo,
     * caso ele possua, uma exceção é ativada informando que ele já possui um livro em mãos de mesmo ISBN.
     * @param leitor
     * @param ISBN
     * @return exceção informando que o leitor já possui um livro de mesmo ISBN em mãos.
     * @throws LivroException
     */
    public boolean usuarioNaoTemISBN(Leitor leitor, String ISBN) throws LivroException;

    /**
     * Este método registra um novo empréstmo na conta de dado leitor. Algumas verificações são feitas, como por exemplo:
     * Verfica se o leitor pode pegar livros;
     * Verfica se o leitor pode fazer mais empréstimos;
     * Verfifica se há livros disponíveis;
     * Verfica fila de espera para empréstimo do livro;
     * Verifica se o leitor não possui livro de mesmo ISBN já em mãos.
     * Caso todas as verificações estejam nos conformes, o empréstimo é registrado na dada atual e é estipulada uma dada
     * com distância de sete dias para a devolução do livro.
     * @param objleitor
     * @param objlivro
     * @return novo empréstimo ou null
     * @throws UsuarioException
     * @throws LivroException
     */
    public Emprestimo registrarEmprestimo(Leitor objleitor, Livro objlivro) throws UsuarioException,
            LivroException;

    /**
     * Este método verifica se o leitor pode fazer uma renovação de empréstimo. Ele  verifica por exemplo se a fila para
     * empréstimo do livro etá vazia. Se as verificações estiverem nos conformes, a renovação é feita com acréscimo de
     * mais sete dias com o livro.
     * @param leitor
     * @param livro
     * @return renovação do empréstimo ou null
     * @throws EmprestimoException
     * @throws LivroException
     */
    public Emprestimo renovarEmprestimo(Leitor leitor, Livro livro) throws EmprestimoException, LivroException;

    /**
     * Este método verifica qual o maior atraso de dado leitor para que a multa seja aplicada sobre o maior débito. Por
     * exemplo: Se o leitor possuir dois atrasos, um de um dia e outro de três dias, a multa deve ser aplicada sobre
     * o atraso de três dias.
     * @param leitor
     * @return atarso maior
     */
    public int maiorAtraso(Leitor leitor);

    /**
     * Este método simula a devolução do livro. O método incrementa +1 aos livros disponíveis, além de verificar possíveis
     * multas por atraso do leitor. Ademais, o status do empréstimo é alterado para CONCLUÍDO.
     * @param emprestimo
     * @return livro
     * @throws LivroException
     * @throws UsuarioException
     */
    public Livro devolverLivro(Emprestimo emprestimo) throws LivroException, UsuarioException;

    /**
     * Este método verifica se o leitor possui ou não atarasos.
     * @param leitor
     * @return True ou False
     * @throws UsuarioException
     */
    public boolean leitorSemAtrasos(Leitor leitor) throws UsuarioException;

    public List<Leitor> emprestadoresDoLivro(Livro livro);

    public Emprestimo findEmprestimo(Leitor leitor, Livro livro);
}
