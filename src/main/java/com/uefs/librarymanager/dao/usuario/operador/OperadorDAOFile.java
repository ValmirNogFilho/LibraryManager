package main.java.com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Usuario;
import main.java.com.uefs.librarymanager.utils.FileBehaviour;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperadorDAOFile implements OperadorDAO{

    File arquivo;
    private static String NOMEARQUIVO = "operadores";
    public OperadorDAOFile(){
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
    }

    @Override
    public Usuario create(Usuario obj) {

        Map<String, Usuario> operadores = FileBehaviour.consultarArquivo(arquivo);
        operadores.put(obj.getId(), obj);
        deleteMany();

        FileBehaviour.sobreescreverArquivo(arquivo, operadores);
        return obj;

    }
    @Override
    public void delete(Usuario obj) {
        Map<String, Usuario> operadores = FileBehaviour.consultarArquivo(arquivo);
        operadores.remove(obj.getId());
        deleteMany();

        FileBehaviour.sobreescreverArquivo(arquivo, operadores);
    }

    @Override
    public void deleteMany() {
        FileBehaviour.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Usuario update(Usuario obj) {
        Map<String, Usuario> operadores = FileBehaviour.consultarArquivo(arquivo);
        operadores.remove(obj.getId());
        return create(obj);
    }

    @Override
    public List<Usuario> findMany() {
        Map<String, Usuario> map = FileBehaviour.consultarArquivo(arquivo);
        return new ArrayList<Usuario>(map.values());
    }

    @Override
    public Usuario findByPrimaryKey(String PrimaryKey) {
        Map<String, Usuario> map = FileBehaviour.consultarArquivo(arquivo);

        return map.get(PrimaryKey);
    }

    @Override
    public Usuario findById(String id) throws UsuarioException {
        Usuario o = findByPrimaryKey(id);
        if (o == null)
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        else
            return o;
    }

}
