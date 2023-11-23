package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.utils.statusLeitor;

/**
 * Esta classe é responsável por registrar um administrador no sistema da biblioteca.
 * Dentre os usários do sistema, o administrador é está no topo da hierarquia podendo executar algumas funções:
 * Cadastrar leitor;
 * Cadastarar operador (podendo ser bibliotecário ou outro administrador);
 * Bloquear leitor;
 * Desbloquear leitor;
 * Deletar a conta de um leitor;
 * Deletar a conta de um operador (podendo ser bibliotecário ou outro administrador).
 * Esta classe é uma extensão da classe "Usuario"
 * @author Valmir Alves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see main.java.com.uefs.librarymanager.dao.DAO
 * @see main.java.com.uefs.librarymanager.exceptions.UsuarioException
 * @see statusLeitor
 */
public class Administrador extends Usuario {

    public Administrador(String nome, String endereco, String telefone) {
        super(nome, endereco, telefone, null);
    }

    /**
     * Este método cadastra um novo leitor no sistema da biblioteca
     * @param nome
     * @param endereco
     * @param telefone
     * @param senha
     * @return Novo leitor
     * @throws UsuarioException
     */
    public Leitor cadastrarLeitor(String nome, String endereco, String telefone, String senha) throws UsuarioException {
        Leitor l = new Leitor(nome, endereco, telefone);
        l.setSenha(senha);
        return DAO.getLeitorDAO().create(l);
    }

    /**
     * Este método cadastra um novo operador, como o operador pode ser um administrador ou um bibliotecário, a ocupação do
     * operário também é passada como parâmentro para avaliar se o caso é de um registro de novo bibliotecário ou de novo
     * administrador.
     * @param nome
     * @param endereco
     * @param telefone
     * @param senha
     * @param ocupacao
     * @return Novo operador (administrador ou bibliotecário)
     * @throws UsuarioException
     */
    public Usuario cadastrarOperador(String nome, String endereco, String telefone, String senha, String ocupacao) throws UsuarioException{
        Usuario obj = null;
        switch (ocupacao){
            case "Administrador":
                obj = new Administrador(nome, endereco, telefone);
                break;
            case "Bibliotecário":
                obj = new Bibliotecario(nome, endereco, telefone);
                break;
        }
        obj.setSenha(senha);
        return DAO.getOperadorDAO().create(obj);
    }

    /**
     * Este método permite que o status de dado leitor "l" seja modificado para "BLOQUEADO" impossibilitando que ele
     * faça empréstimos e reservas como forma de penalidade por ataso de devolução de livro, por exemplo.
     * @param l
     */
    public void bloquearLeitor(Leitor l) {
        l.setStatus(statusLeitor.BLOQUEADO);
        DAO.getLeitorDAO().update(l);
    }

    /**
     * Este método permite que o status de dado leitor "l" seja modificado para "LIVRE", informando que a multa dele
     * acabou, possibilitando que ele faça empréstimos e reservas de livros.
     * @param l
     */
    public void desbloquearLeitor(Leitor l){
        l.setStatus(statusLeitor.LIVRE);
        for (Emprestimo emprestimo: DAO.getEmprestimoDAO().findByLeitor(l)){

            Sistema.verificarPossivelMulta(emprestimo, l);
            DAO.getEmprestimoDAO().update(emprestimo);

        }
        DAO.getLeitorDAO().update(l);
    }

    /**
     * Este método deleta a conta de dado leitor "l".
     * @param l
     */
    public void removerLeitor(Leitor l){
        DAO.getLeitorDAO().delete(l);
    }

    /**
     * Este método deleta a conta de dado operador "o".
     * @param o
     */
    public void removerOperador(Usuario o){
        DAO.getOperadorDAO().delete(o);
    }


}