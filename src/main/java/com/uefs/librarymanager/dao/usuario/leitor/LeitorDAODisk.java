package com.uefs.librarymanager.dao.usuario.leitor;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.utils.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeitorDAODisk implements LeitorDAO{

    File arquivo;

    public LeitorDAODisk(){
        arquivo = FileUtils.obterInstanciaArquivo("leitores");
    }


    @Override
    public Leitor create(Leitor obj) {
        Map<String, Leitor> leitores = FileUtils.consultarArquivoMap(arquivo);
        leitores.put(obj.getId(),obj);
        deleteMany();

        FileUtils.sobreescreverArquivo(arquivo, leitores);
        return obj;

    }


    @Override
    public void delete(Leitor obj) {
        Map<String, Leitor> leitores = FileUtils.consultarArquivoMap(arquivo);
        leitores.remove(obj.getId());
        deleteMany();

        FileUtils.sobreescreverArquivo(arquivo, leitores);

    }

    @Override
    public void deleteMany() {
        FileUtils.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Leitor update(Leitor obj) {
        Map<String, Leitor> leitores = FileUtils.consultarArquivoMap(arquivo);
        leitores.remove(obj.getId());
        return create(obj);

    }

    @Override
    public List<Leitor> findMany() {
        Map<String, Leitor> leitores = FileUtils.consultarArquivoMap(arquivo);
        return new ArrayList<Leitor>(leitores.values());
    }

    @Override
    public Leitor findByPrimaryKey(String PrimaryKey) {
        Map<String, Leitor> leitores = FileUtils.consultarArquivoMap(arquivo);
        return leitores.get(PrimaryKey);
    }

    @Override
    public Leitor findById(String id) throws UsuarioException {
        Leitor o = findByPrimaryKey(id);
        if (o == null)
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        else
            return o;
    }

    @Override
    public List<Leitor> findByName(String name){
        return findMany()
                .stream()
                .filter(user -> user.getNome().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}
