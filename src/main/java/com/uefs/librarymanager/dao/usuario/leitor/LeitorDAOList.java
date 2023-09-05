package main.java.com.uefs.librarymanager.dao.usuario.leitor;

import main.java.com.uefs.librarymanager.model.Leitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorDAOList implements LeitorDAO{

    private Map<String, Leitor> leitores;

    public LeitorDAOList(){
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
        return leitores.values().stream().toList();
    }

    @Override
    public Leitor findById(String id) {
        return leitores.get(id);
    }
}
