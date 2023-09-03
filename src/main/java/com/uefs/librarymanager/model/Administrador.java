package main.java.com.uefs.librarymanager.model;

public class Administrador extends Usuario {

    private static Relatorio relatorio = new Relatorio();
    public Administrador(String nome, String endereco, String telefone, String senha) {
        super(nome, endereco, telefone, senha);
    }

}