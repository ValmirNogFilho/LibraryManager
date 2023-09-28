package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import utils.statusLeitor;

public class Administrador extends Usuario {

    public Administrador(String nome, String endereco, String telefone) {
        super(nome, endereco, telefone, null);
    }


    public Leitor cadastrarLeitor(String nome, String endereco, String telefone, String senha) throws UsuarioException {
        Leitor l = new Leitor(nome, endereco, telefone);
        l.setSenha(senha);
        return DAO.getLeitorDAO().create(l);
    }

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

    public void bloquearLeitor(Leitor l) {
        l.setStatus(statusLeitor.BLOQUEADO);
        DAO.getLeitorDAO().update(l);
    }

    public void desbloquearLeitor(Leitor l){
        l.setStatus(statusLeitor.LIVRE);
        for (Emprestimo emprestimo: DAO.getEmprestimoDAO().findByLeitor(l)){

            Sistema.verificarPossivelMulta(emprestimo, l);
            DAO.getEmprestimoDAO().update(emprestimo);

        }
        DAO.getLeitorDAO().update(l);
    }

    public void removerLeitor(Leitor l){
        DAO.getLeitorDAO().delete(l);
    }

    public void removerOperador(Usuario o){
        DAO.getOperadorDAO().delete(o);
    }


}