package main.java.com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Usuario;
import utils.FileBehaviour;

import java.io.File;
import java.util.List;

public class OperadorDAOFile implements OperadorDAO{

    File arquivo;

    public OperadorDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("operadores");
    }

    @Override
    public Usuario create(Usuario obj) {
        return null;
    }

    @Override
    public void delete(Usuario obj) {

    }

    @Override
    public void deleteMany() {

    }

    @Override
    public Usuario update(Usuario obj) {
        return null;
    }

    @Override
    public List<Usuario> findMany() {
        return null;
    }

    @Override
    public Usuario findByPrimaryKey(String PrimaryKey) {
        return null;
    }

    @Override
    public Usuario findById(String id) throws UsuarioException {
        return null;
    }
}
