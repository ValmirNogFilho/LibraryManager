package com.uefs.librarymanager.utils;

import com.uefs.librarymanager.dao.DAO;
import com.uefs.librarymanager.exceptions.LivroException;
import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Administrador;
import com.uefs.librarymanager.model.Bibliotecario;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Livro;


import java.util.ArrayList;
import java.util.List;


public class DataInitialFeeder {


    private static void povoarDadosDeLivros() {

        ArrayList<Livro> listaLivros = new ArrayList<>();

        listaLivros.add(new Livro("Dom Casmurro", "Machado de Assis", "Livraria Garnier",
                "9788574803999", 1899, "", "Romance", 10));

        listaLivros.add(new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis",
                "Livraria Garnier", "9788574803982", 1881, "", "Romance", 15));

        listaLivros.add(new Livro("O Guarani", "José de Alencar", "Editora Magalhães",
                "9788574803975", 1857, "", "Romance", 8));

        listaLivros.add(new Livro("Quincas Borba", "Machado de Assis", "Livraria Garnier",
                "9788574803968", 1891, "", "Romance", 12));

        listaLivros.add(new Livro("Iracema", "José de Alencar", "Editora Magalhães",
                "9788574803951", 1865, "", "Romance", 20));

        listaLivros.add(new Livro("Senhora", "José de Alencar", "Editora Magalhães",
                "9788574803944", 1875, "", "Romance", 18));

        listaLivros.add(new Livro("O Alienista", "Machado de Assis", "Livraria Garnier",
                "9788574803937", 1882, "", "Conto", 7));

        listaLivros.add(new Livro("O Cortiço", "Aluísio Azevedo", "Editora Magalhães",
                "9788574803920", 1890, "", "Romance Naturalista", 14));

        listaLivros.add(new Livro("Memórias de um Sargento de Milícias", "Manuel Antônio de Almeida",
                "Livraria Garnier", "9788574803913", 1854, "", "Romance", 11));

        listaLivros.add(new Livro("A Moreninha", "Joaquim Manuel de Macedo", "Editora Magalhães",
                "9788574803906", 1844, "", "Romance", 25));

        for(Livro livro: listaLivros){
            DAO.getLivroDAO().create(livro);
        }
        
    }

    private static void povoarDadosDeLeitores() throws UsuarioException {
        ArrayList<Leitor> listaLeitores = new ArrayList<>();

        listaLeitores.add(new Leitor("Valmir", "Amargosa", "(75) 91234-5678"));
        listaLeitores.add(new Leitor("Alice", "Cidade XYZ", "(123) 456-7890"));
        listaLeitores.add(new Leitor("Carlos", "Rua ABC", "(987) 654-3210"));
        listaLeitores.add(new Leitor("Juliana", "Avenida 123", "(111) 222-3333"));
        listaLeitores.add(new Leitor("Ricardo", "Praça Central", "(555) 666-7777"));
        listaLeitores.add(new Leitor("Fernanda", "Bairro PQR", "(444) 333-2222"));
        listaLeitores.add(new Leitor("Gustavo", "Rua XYZ", "(777) 888-9999"));
        listaLeitores.add(new Leitor("Ana", "Avenida ABC", "(333) 222-1111"));
        listaLeitores.add(new Leitor("Pedro", "Cidade 789", "(999) 888-7777"));
        listaLeitores.add(new Leitor("Mariana", "Rua 456", "(666) 555-4444"));


        for(Leitor leitor: listaLeitores){
            leitor.setSenha("1234");
            DAO.getLeitorDAO().create(leitor);
        }

    }

    private static void povoarDadosDeOperadores() throws UsuarioException {
        ArrayList<Administrador> listaAdministradores = new ArrayList<>();

        listaAdministradores.add(new Administrador("João", "Rua 123", "(111) 222-3333"));
        listaAdministradores.add(new Administrador("Maria", "Avenida XYZ", "(444) 555-6666"));
        listaAdministradores.add(new Administrador("Carlos", "Praça ABC", "(777) 888-9999"));

        for(Administrador admin: listaAdministradores){
            admin.setSenha("1234");
            DAO.getOperadorDAO().create(admin);
        }

        ArrayList<Bibliotecario> listaBibliotecarios = new ArrayList<>();

        listaBibliotecarios.add(new Bibliotecario("Luciana", "Rua 456", "(333) 444-5555"));
        listaBibliotecarios.add(new Bibliotecario("Antônio", "Avenida ABC", "(666) 777-8888"));
        listaBibliotecarios.add(new Bibliotecario("Isabela", "Praça XYZ", "(999) 111-2222"));
        listaBibliotecarios.add(new Bibliotecario("Rafael", "Avenida 789", "(222) 333-4444"));
        listaBibliotecarios.add(new Bibliotecario("Camila", "Rua DEF", "(555) 666-7777"));
        listaBibliotecarios.add(new Bibliotecario("Roberto", "Praça GHI", "(888) 999-0000"));
        listaBibliotecarios.add(new Bibliotecario("Mariana", "Avenida JKL", "(123) 456-7890"));

        for(Bibliotecario bib: listaBibliotecarios){
            bib.setSenha("1234");
            DAO.getOperadorDAO().create(bib);
        }

    }

    private static void povoarDadosDeEmprestimos() throws LivroException, UsuarioException {
        DAO.getReservaDAO().deleteMany();

        List<Leitor> leitores = DAO.getLeitorDAO().findMany();
        List<Livro> livros = DAO.getLivroDAO().findMany();
        int i = 0;

        while (i < livros.size() && i < leitores.size()){
            Leitor leitor = leitores.get(i);
            Livro livro = livros.get(i);
            DAO.getEmprestimoDAO().registrarEmprestimo(leitor, livro);
            i++;
        }
    }

    private static void povoarDadosDeReservas() throws LivroException, UsuarioException {

        Livro livro1 = DAO.getLivroDAO().create( new Livro("Java: The Complete Reference", "Herbert Schildt", "McGraw-Hill",
                "978-0071606301", 2014, "Seção A", "Programação", 10));
        Livro livro2 = DAO.getLivroDAO().create( new Livro("Clean Code: A Handbook of Agile Software Craftsmanship",
                "Robert C. Martin", "Prentice Hall", "978-0132350884", 2008,
                "Seção B", "Desenvolvimento de Software", 5));


        Leitor leitor1 = new Leitor("João", "Rua A", "123456789");
        Leitor leitor2 = new Leitor("Maria", "Rua B", "987654321");

        leitor1.setSenha("1234");
        leitor2.setSenha("1234");

        DAO.getLeitorDAO().create(leitor1);
        DAO.getLeitorDAO().create(leitor2);


        DAO.getReservaDAO().registrarReserva(leitor1, livro1);
        DAO.getReservaDAO().registrarReserva(leitor2, livro2);

    }


    public static void main(String[] args) throws LivroException, UsuarioException {
        povoarDadosDeLivros();
        povoarDadosDeLeitores();
        povoarDadosDeOperadores();
        povoarDadosDeEmprestimos();
        povoarDadosDeReservas();
    }
}



