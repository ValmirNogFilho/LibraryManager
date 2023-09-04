package main.java.com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.model.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperadorDAOList implements OperadorDAO{
    private Map<String, Usuario> operadores;

    public OperadorDAOList(){
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
        return operadores.values().stream().toList();
    }

    @Override
    public Usuario findById(String id) {
        return operadores.get(Id);
    }
}
