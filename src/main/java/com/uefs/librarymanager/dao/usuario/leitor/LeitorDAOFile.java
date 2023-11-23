package com.uefs.librarymanager.dao.usuario.leitor;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.utils.FileBehaviour;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeitorDAOFile implements LeitorDAO{

    File arquivo;

    public LeitorDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("leitores");
    }


    @Override
    public Leitor create(Leitor obj) {
        Map<String, Leitor> leitores = FileBehaviour.consultarArquivo(arquivo);
        leitores.put(obj.getId(),obj);
        deleteMany();

        FileBehaviour.sobreescreverArquivo(arquivo, leitores);
        return obj;

    }


    @Override
    public void delete(Leitor obj) {
        Map<String, Leitor> leitores = FileBehaviour.consultarArquivo(arquivo);
        leitores.remove(obj.getId());
        deleteMany();

        FileBehaviour.sobreescreverArquivo(arquivo, leitores);

    }

    @Override
    public void deleteMany() {
        FileBehaviour.apagarConteudoArquivo(arquivo);
    }

    @Override
    public Leitor update(Leitor obj) {
        Map<String, Leitor> leitores = FileBehaviour.consultarArquivo(arquivo);
        leitores.remove(obj.getId());
        return create(obj);

    }

    @Override
    public List<Leitor> findMany() {
        Map<String, Leitor> leitores = FileBehaviour.consultarArquivo(arquivo);
        return new ArrayList<Leitor>(leitores.values());
    }

    @Override
    public Leitor findByPrimaryKey(String PrimaryKey) {
        Map<String, Leitor> leitores = FileBehaviour.consultarArquivo(arquivo);
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
}
