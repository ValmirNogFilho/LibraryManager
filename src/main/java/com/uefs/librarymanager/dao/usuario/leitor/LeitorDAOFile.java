package main.java.com.uefs.librarymanager.dao.usuario.leitor;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Leitor;
import main.java.com.uefs.librarymanager.model.Usuario;
import utils.FileBehaviour;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeitorDAOFile implements LeitorDAO{

    File arquivo;

    public LeitorDAOFile(){
        arquivo = FileBehaviour.gerarArquivo("leitores");
    }


    @Override
    public Leitor create(Leitor obj) {
        Map<String, Leitor> leitores = findManyMap();
        leitores.put(obj.getId(),obj);
        deleteMany();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(leitores);
        } catch (IOException e) {
            obj = null;
        }
        return obj;

    }


    @Override
    public void delete(Leitor obj) {
        Map<String, Leitor> leitores = findManyMap();
        leitores.remove(obj.getId());
        deleteMany();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(leitores);
        } catch (IOException e) {}


    }

    @Override
    public void deleteMany() {
        try {
            new FileOutputStream(arquivo).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Leitor update(Leitor obj) {
        Map<String, Leitor> leitores = findManyMap();
        leitores.remove(obj.getId());
        return create(obj);

    }

    @Override
    public List<Leitor> findMany() {
        return new ArrayList<Leitor>(findManyMap().values());
    }

    @Override
    public Leitor findByPrimaryKey(String PrimaryKey) {
        return findManyMap().get(PrimaryKey);
    }

    @Override
    public Leitor findById(String id) throws UsuarioException {
        Leitor o = findByPrimaryKey(id);
        if (o == null)
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        else
            return o;
    }

    private Map<String, Leitor> findManyMap() {
        Map<String, Leitor> leitores;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            leitores = (Map<String, Leitor>) in.readObject();

        } catch (IOException e){
            leitores = new HashMap<>();
        }
        catch (ClassNotFoundException e) {
            leitores = new HashMap<>();
        }
        return leitores;
    }
}
