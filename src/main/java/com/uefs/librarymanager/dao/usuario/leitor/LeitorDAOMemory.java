package com.uefs.librarymanager.dao.usuario.leitor;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;

import java.util.*;
import java.util.stream.Collectors;

public class LeitorDAOMemory implements LeitorDAO{

    private Map<String, Leitor> leitores;

    public LeitorDAOMemory(){
        leitores = new HashMap<String, Leitor>();
    }

    @Override
    public Leitor create(Leitor obj) {
        leitores.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public void delete(Leitor obj) {
        leitores.remove(obj.getId());
    }

    @Override
    public void deleteMany() {
        leitores = new HashMap<String, Leitor>();
    }

    @Override
    public Leitor update(Leitor obj) {
        leitores.remove(obj.getId());
        leitores.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public List<Leitor> findMany() {
        Collection<Leitor> valores = leitores.values();
        return new ArrayList<Leitor>(valores);
    }

    @Override
    public Leitor findByPrimaryKey(String id) {
        return leitores.get(id);
    }


    @Override
    public Leitor findById(String id) throws UsuarioException {
        Leitor l = findByPrimaryKey(id);
        if (l == null)
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        else
            return l;
    }

    @Override
    public List<Leitor> findByName(String name){
        return findMany()
                .stream()
                .filter(user -> user.getNome().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

}
