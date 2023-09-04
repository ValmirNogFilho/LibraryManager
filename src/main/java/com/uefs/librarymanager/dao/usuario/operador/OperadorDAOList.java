package main.java.com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperadorDAOList implements OperadorDAO{
    private Map<String, Usuario> operadores = new HashMap<String, Usuario>();

    @Override
    public Usuario create(Usuario obj) {
        operadores.put(obj.getId(), obj);
        return obj;
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
    public Usuario findById(String id) {
        return null;
    }
}
