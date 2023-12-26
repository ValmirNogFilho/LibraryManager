package com.uefs.librarymanager.dao.usuario.operador;

import com.uefs.librarymanager.exceptions.UsuarioException;
import com.uefs.librarymanager.model.Leitor;
import com.uefs.librarymanager.model.Usuario;

import java.util.*;
import java.util.stream.Collectors;

public class OperadorDAOMemory implements OperadorDAO {
    private Map<String, Usuario> operadores;

    public OperadorDAOMemory() {
        operadores = new HashMap<String, Usuario>();
    }

    @Override
    public Usuario create(Usuario obj) {
        operadores.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public void delete(Usuario obj) {
        operadores.remove(obj.getId());
    }

    @Override
    public void deleteMany() {
        operadores = new HashMap<String, Usuario>();
    }

    @Override
    public Usuario update(Usuario obj) {
        operadores.remove(obj.getId());
        operadores.put(obj.getId(), obj);
        return obj;
    }

    @Override
    public List<Usuario> findMany() {
        Collection<Usuario> valores = operadores.values();
        return new ArrayList<Usuario>(valores);
    }

    @Override
    public Usuario findByPrimaryKey(String id) {
        return operadores.get(id);
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


