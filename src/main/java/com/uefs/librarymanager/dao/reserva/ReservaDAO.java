package com.uefs.librarymanager.dao.reserva;

import main.java.com.uefs.librarymanager.dao.CRUD;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Emprestimo;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Reserva;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Esta interface contém todas as ações referentes às reservas da biblioteca, tais como:
 * Verificar se a fila está vazia;
 * Registrar uma nova reserva;
 * Cancelar reserva;
 * Registrar empréstimo por reserva.
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see main.java.com.uefs.librarymanager.dao.CRUD
 * @see LivroException
 * @see UsuarioException
 * @see Emprestimo
 * @see Leitor
 * @see Livro
 * @see Reserva
 * @see java.util.LinkedList
 * @see java.util.List
 * @see java.util.Map
 */
public interface ReservaDAO extends CRUD<Reserva>{
    /**
     * Este método incrementa a variável proximoID.
     * @return o ID sucessor do ID atual que está sendo incrementado.
     */
    public int proximoID();

    /**
     * Este método cria um HashMap contendo todas as reservas.
     * @return reservas
     */
    Map<String, LinkedList<Reserva>> findManyMap();

    /**
     * Este método verifica se dada fila para empréstimo de um livro está vazia, caso a fila não esteja vazia, a exceção
     * FILA_NÃO_VAZIA é ativada
     * @param ISBN
     * @return True caso a fila esteja vazia ou uma exceção caso a fila não esteja vazia.
     * @throws LivroException
     */
    public boolean filaVazia(String ISBN) throws LivroException;

    /**
     * Este método registra uma reserva a partir da calidação de algumas informações:
     * Verifica se o leitor pode pegar livro;
     * Verifica se o leior pode fazer mais reservas;
     * Verifica se o leitor possui algum livro com o mesmo ISBN do que ele está tentando fazer o empréstimo.
     * Caso esteja tudo nos conformes, uma nova reserva é criada, é incrementada uma reserva na conta do leito e o ID
     * dele é atribuído ao "proximoID" para que ele possa ter sua vez quando a contagem sucessiva da variável começar.
     * @param leitor
     * @param livro
     * @return nova reserva ou null.
     * @throws UsuarioException
     * @throws LivroException
     */
    public Reserva registrarReserva(Leitor leitor, Livro livro) throws UsuarioException, LivroException;

    /**
     * Este método cancela uma reserva.
     * @param leitor
     * @param livro
     */
    public void cancelarReserva(Leitor leitor, Livro livro);

    /**
     * Este método é responsável procurar uma reserva com o ID especificado dentro de uma coleção de reservas, que é
     * obtida através da função "findMany()"
     * @param Id
     * @return
     */

    public Reserva findById(int Id);

    /**
     * Este método verifica a quantidade disponível de dado livro e indica os usuários que estão aptos para pegar o livro.
     * A aptdão em questão é a ordem de cada usuário na fila, por exemplo:
     * Se dois livros ficaram disponíveis, os dois primeiros da fila de reservas ficam aptos para pegar os dois livros.
     * @param ISBN
     * @return lista de objetos "reserva" que representam os usuários aptos para o empréstimo de um livro com o
     * ISBN especificado.
     */
    public List<Reserva> usuariosAptosParaEmprestimo(String ISBN);

    /**
     * O método registra um empréstimo a partir de uma reserva feita previamente pelo leitor. Para que o empréstimo seja
     * efetuado são checadas algumas condições:
     * Verifica se o leitor pode pegar livros;
     * Verifica se o leitor pode fazer mais empréstimos;
     * Verifica se o leitor não possui atrasos em sua conta;
     * Verifica se há livros disponíveis;
     * Verifica se o leitor já não possui um livro de mesmo ISBN.
     * Se todas as condições forem satisfeitas, um novo empréstimo é registrado, porém se alguma das condições não for
     * satisfeita, é retornado um "null" e o empréstimo não é efetuado.
     * @param reserva
     * @return novo empréstimo ou null
     * @throws LivroException
     * @throws UsuarioException
     */
    public Emprestimo registrarEmprestimoPorReserva(Reserva reserva) throws LivroException, UsuarioException;
}
