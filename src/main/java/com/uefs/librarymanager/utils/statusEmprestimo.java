package main.java.com.uefs.librarymanager.utils;

/**
 * Esta essa classe é responsável por armazenar os possíveis estados de um empréstimo:
 * Concluído: Indica que o livro já foi devolvido e o empréstimo finalizado;
 * Andamento: Indica que o livro ainda não foi entregue porque ainda está dentro do prazo de empréstimo;
 * Multado: Indica que o livro ainda não foi entregue, embora o prazo de devolução já tenha se excedido, o que culmina
 * numa multa pro leitor, por isso o nome "MULTADO".
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 */
public enum statusEmprestimo {
    CONCLUIDO, ANDAMENTO, MULTADO;
}
