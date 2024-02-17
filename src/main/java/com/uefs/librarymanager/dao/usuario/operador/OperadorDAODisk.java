package com.uefs.librarymanager.dao.usuario.operador;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Usuario;
import com.uefs.librarymanager.utils.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperadorDAODisk implements OperadorDAO{

    File arquivo;
    private static String NOMEARQUIVO = "operadores";
    public OperadorDAODisk(){
        arquivo = FileUtils.obterInstanciaArquivo(NOMEARQUIVO);
    }

    @Override
    public Usuario create(Usuario obj) {

        Map<String, Usuario> operadores = FileUtils.consultarArquivoMap(arquivo);
        operadores.put(obj.getId(), obj);
        deleteMany();

        FileUtils.sobreescreverArquivo(arquivo, operadores);
        return obj;

    }
    @Override
    public void delete(Usuario obj) {
        Map<String, Usuario> operadores = FileUtils.consultarArquivoMap(arquivo);
        operadores.remove(obj.getId());
        deleteMany();

        FileUtils.sobreescreverArquivo(arquivo, operadores);
    }

    @Override
    public void deleteMany() {
        FileUtils.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Usuario update(Usuario obj) {
        Map<String, Usuario> operadores = FileUtils.consultarArquivoMap(arquivo);
        operadores.remove(obj.getId());
        return create(obj);
    }

    @Override
    public List<Usuario> findMany() {
        Map<String, Usuario> map = FileUtils.consultarArquivoMap(arquivo);
        return new ArrayList<Usuario>(map.values());
    }

    @Override
    public Usuario findByPrimaryKey(String PrimaryKey) {
        Map<String, Usuario> map = FileUtils.consultarArquivoMap(arquivo);

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

    @Override
    public List<Usuario> findByName(String name){
        return findMany()
                .stream()
                .filter(user -> user.getNome().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

}
