package main.java.com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Usuario;

import java.util.*;

public class OperadorDAOList implements OperadorDAO {
    private Map<String, Usuario> operadores;

    public OperadorDAOList() {
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

}


