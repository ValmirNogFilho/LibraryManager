package com.uefs.librarymanager.model;

import com.uefs.librarymanager.exceptions.LivroException;

import java.io.Serializable;
import java.util.Objects;

/**
 * Esta classe é responsável por criar livros, contendo:
 * Título;
 * Autor;
 * ISBN;
 * Ano de publicação;
 * Onde o livro fica localizado;
 * Categoria a qual o livro pertence;
 * Quantidade de exemplares disponíveis.
 * Exemplo de uso:
 * Livro livro = new Livro("titulo","autor","editora","ISBN","anoDePublicacao","categoria","disponiveis")
 * @author Valmir ALves Nogueira Filho
 * @author Kevin Cordeiro Borges
 * @see LivroException
 * @see java.util.List
 * @see java.util.Objects
 */
public class Livro implements Serializable {
    private String titulo;
    private String autor;
    private String editora;
    private String ISBN;
    private int anoDePublicacao;
    private String localizacao;
    private String categoria;
    private int disponiveis;
    private String sinopse;
    private String imagemUrl;

    public static final String BOOK_COVERS_DIRECTORY = "img/book-covers/";
    public static final String TEMPLATE_COVER = "template.jpg";

    public Livro(String titulo, String autor, String editora, String ISBN, int anoDePublicacao,
                 String localizacao, String categoria, int disponiveis, String sinopse) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ISBN = ISBN;
        this.anoDePublicacao = anoDePublicacao;
        this.localizacao = localizacao;
        this.categoria = categoria;
        this.disponiveis = disponiveis;
        this.sinopse = sinopse;
        this.imagemUrl = BOOK_COVERS_DIRECTORY + TEMPLATE_COVER;
    }

    public Livro(String titulo, String autor, String editora, String ISBN, int anoDePublicacao,
                 String localizacao, String categoria, int disponiveis, String sinopse, String imagemUrl) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ISBN = ISBN;
        this.anoDePublicacao = anoDePublicacao;
        this.localizacao = localizacao;
        this.categoria = categoria;
        this.disponiveis = disponiveis;
        this.sinopse = sinopse;
        this.imagemUrl = BOOK_COVERS_DIRECTORY + imagemUrl;
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

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    /**
     * Este método compara dois ISBNs e informa por meio de valor booleano se eles são iguais ou não.
     * Para ISBNs iguais, True;
     * Para ISBNs distintos, False;
     * @param o
     * @return True or False
     */
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
