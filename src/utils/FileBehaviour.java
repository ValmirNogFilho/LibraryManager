package utils;

import java.io.File;
import java.io.IOException;

public abstract class FileBehaviour {
    public static final File DIRETORIO = new File("cache");
    public static final String EXTENSAO = "kv";

    public static File gerarArquivo(String nomearquivo) {

        if (!DIRETORIO.exists())
            DIRETORIO.mkdir();

        File arquivo = new File(DIRETORIO.getName() + "/" + nomearquivo + EXTENSAO);

        if (!arquivo.exists())
            try{
                arquivo.createNewFile();
            } catch(Exception e){
                arquivo = null;
            }

        return arquivo;
    }

}
