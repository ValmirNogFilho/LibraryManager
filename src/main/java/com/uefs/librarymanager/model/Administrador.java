package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.dao.DAO;
import main.java.com.uefs.librarymanager.dao.usuario.SenhaInvalidaException;
import utils.statusLeitor;

import java.util.HashMap;
import java.util.Map;

public class Administrador extends Usuario {

    private static Relatorio relatorio = new Relatorio();
    public Administrador(String nome, String endereco, String telefone, String senha) {
        super(nome, endereco, telefone, senha);
    }


    public Leitor cadastrarLeitor(String nome, String endereco, String telefone, String senha) throws SenhaInvalidaException {
        Leitor l = new Leitor(nome, endereco, telefone, null, null, 0, 0, statusLeitor.LIVRE);
        l.setSenha(senha);
        return DAO.getLeitorDAO().create(l);
    }

    public Usuario cadastrarOperador(String nome, String endereco, String telefone, String senha, String ocupacao) throws SenhaInvalidaException{
        Usuario obj = null;
        switch (ocupacao){
            case "Administrador":
                obj = new Administrador(nome, endereco, telefone, null);
                break;
            case "Bibliotecário":
                obj = new Bibliotecario(nome, endereco, telefone, null);
                break;
        }
        obj.setSenha(senha);
        return DAO.getOperadorDAO().create(obj);
    }

    public void bloquearLeitor(Leitor l) {
        l.setStatus(statusLeitor.BLOQUEADO);
        DAO.getLeitorDAO().update(l);
    }

    public void removerLeitor(Leitor l){
        DAO.getLeitorDAO().delete(l);
    }

    public void removerOperador(Usuario o){
        DAO.getOperadorDAO().delete(o);
    }


}