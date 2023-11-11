package main.java.com.uefs.librarymanager.dao.usuario.operador;

import main.java.com.uefs.librarymanager.exceptions.UsuarioException;
import main.java.com.uefs.librarymanager.model.Usuario;
import utils.FileBehaviour;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperadorDAOFile implements OperadorDAO{

    File arquivo;
    private static String NOMEARQUIVO = "operadores";
    public OperadorDAOFile(){
        arquivo = FileBehaviour.gerarArquivo(NOMEARQUIVO);
    }

    @Override
    public Usuario create(Usuario obj) {

        Map<String, Usuario> operadores = findManyMap();
        operadores.put(obj.getId(), obj);
        deleteMany();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
           out.writeObject(operadores);
        } catch (IOException e) {
            obj = null;
        }
        return obj;

    }
    @Override
    public void delete(Usuario obj) {
        Map<String, Usuario> operadores = findManyMap();
        operadores.remove(obj.getId());
        deleteMany();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(operadores);
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
    public Usuario update(Usuario obj) {
        Map<String, Usuario> operadores = findManyMap();
        operadores.remove(obj.getId());
        return create(obj);
    }

    @Override
    public List<Usuario> findMany() {
        return new ArrayList<Usuario>(findManyMap().values());
    }

    @Override
    public Usuario findByPrimaryKey(String PrimaryKey) {
        return findManyMap().get(PrimaryKey);
    }

    @Override
    public Usuario findById(String id) throws UsuarioException {
        Usuario o = findByPrimaryKey(id);
        if (o == null)
            throw new UsuarioException(UsuarioException.NAO_EXISTENTE);
        else
            return o;
    }

    private Map<String, Usuario> findManyMap() {
        Map<String, Usuario> operadores;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            operadores = (Map<String, Usuario>) in.readObject();

        } catch (IOException e){
            operadores = new HashMap<>();
        }
        catch (ClassNotFoundException e) {
            operadores = new HashMap<>();
        }
        return operadores;

    }
}
