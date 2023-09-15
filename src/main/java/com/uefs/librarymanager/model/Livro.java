package main.java.com.uefs.librarymanager.model;

import main.java.com.uefs.librarymanager.exceptions.LivroException;

import java.util.List;
import java.util.Objects;

public class Livro {
    private String titulo;
    private String autor;
    private String editora;
    private String ISBN;
    private int anoDePublicacao;
    private String localizacao;
    private String categoria;
    private int disponiveis;

    public Livro(String titulo, String autor, String editora, String ISBN, int anoDePublicacao, String localizacao, String categoria, int disponiveis) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ISBN = ISBN;
        this.anoDePublicacao = anoDePublicacao;
        this.localizacao = localizacao;
        this.categoria = categoria;
        this.disponiveis = disponiveis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public void setAnoDePublicacao(int anoDePublicacao) {
        this.anoDePublicacao = anoDePublicacao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getDisponiveis() {
        return disponiveis;
    }

    public void setDisponiveis(int disponiveis) {
        this.disponiveis = disponiveis;
    }

    public boolean existemDisponiveis() throws LivroException {
        if (getDisponiveis()> 0)
            return true;
        else throw new LivroException(LivroException.SEM_EXEMPLARES);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(ISBN, livro.ISBN);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", anoDePublicacao=" + anoDePublicacao +
                ", localizacao='" + localizacao + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
