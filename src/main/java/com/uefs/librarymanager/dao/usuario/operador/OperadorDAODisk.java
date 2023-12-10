package com.uefs.librarymanager.dao.usuario.operador;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.FileBehaviour;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperadorDAODisk implements OperadorDAO{

    File arquivo;
    private static String NOMEARQUIVO = "operadores";
    public OperadorDAODisk(){
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
    }

    @Override
    public Usuario create(Usuario obj) {

        Map<String, Usuario> operadores = FileBehaviour.consultarArquivoMap(arquivo);
        operadores.put(obj.getId(), obj);
        deleteMany();

        FileBehaviour.sobreescreverArquivo(arquivo, operadores);
        return obj;

    }
    @Override
    public void delete(Usuario obj) {
        Map<String, Usuario> operadores = FileBehaviour.consultarArquivoMap(arquivo);
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
        Map<String, Usuario> operadores = FileBehaviour.consultarArquivoMap(arquivo);
        operadores.remove(obj.getId());
        return create(obj);
    }

    @Override
    public List<Usuario> findMany() {
        Map<String, Usuario> map = FileBehaviour.consultarArquivoMap(arquivo);
        return new ArrayList<Usuario>(map.values());
    }

    @Override
    public Usuario findByPrimaryKey(String PrimaryKey) {
        Map<String, Usuario> map = FileBehaviour.consultarArquivoMap(arquivo);

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
