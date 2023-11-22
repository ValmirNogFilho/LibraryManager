package utils;

import main.java.com.uefs.librarymanager.model.Usuario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public abstract class FileBehaviour {
    public static final File DIRETORIO = new File("cache");
    public static final String EXTENSAO = ".kv";

    public static File gerarArquivo(String nomearquivo) {

        if (!DIRETORIO.exists())
            DIRETORIO.mkdir();
        File arquivo = new File(DIRETORIO.getName() + "/" + nomearquivo + EXTENSAO);

        if (!arquivo.exists()){
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return arquivo;
    }

    public static <V> Map<String, V> consultarArquivo(File file){
        Map<String, V> map;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            map = (Map<String, V>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            map = new HashMap<>();
        }
        return map;
    }

    public static boolean sobreescreverArquivo(File arquivo, Map map){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(map);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void apagarConteudoArquivo(File arquivo) {
        try {
            new FileOutputStream(arquivo).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
