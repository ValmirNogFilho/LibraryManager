package main.java.com.uefs.librarymanager.dao.usuario.leitor;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import utils.FileBehaviour;

import java.io.File;
import java.util.List;

public class LeitorDAOFile implements LeitorDAO{

    File arquivo;

    public LeitorDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("leitores");
    }

    @Override
    public Leitor create(Leitor obj) {
        return null;
    }

    @Override
    public void delete(Leitor obj) {

    }

    @Override
    public void deleteMany() {

    }

    @Override
    public Leitor update(Leitor obj) {
        return null;
    }

    @Override
    public List<Leitor> findMany() {
        return null;
    }

    @Override
    public Leitor findByPrimaryKey(String PrimaryKey) {
        return null;
    }

    @Override
    public Leitor findById(String id) throws UsuarioException {
        return null;
    }
}
