package utils;

import main.java.com.uefs.librarymanager.model.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

}
