package main.java.com.uefs.librarymanager.utils;

/**
 * Esta classe é responsável por armazenar os possíveis estados (status) de um leitor:
 * Livre: Indica que o leitor não possui multas e está apto à fazer reservas e empréstimos;
 * Multado: Indica que o leitor possui multa e está impossibilitado de fazer empréstimos e/ou reservas por tempo determinado;
 * Bloqueado: Indica que o leitor está impossibilitado de fazer quaisquer coisas em sua conta por tempo indeterminado.
 */
public enum statusLeitor {
    LIVRE, MULTADO, BLOQUEADO;
}
