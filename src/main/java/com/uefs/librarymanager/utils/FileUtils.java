package com.uefs.librarymanager.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public abstract class FileUtils {
    public static final File DIRETORIO = new File("cache");
    public static final String EXTENSAO = ".kv";

    public static File obterInstanciaArquivo(String nomearquivo) {

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

    public static <V> Map<String, V> consultarArquivoMap(File file){
        Map<String, V> map;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            map = (Map<String, V>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            map = new HashMap<>();
        }
        return map;
    }

    public static <V> List<V> consultarArquivoList(File file){
        List<V> list;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            list = (List<V>) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static int consultarArquivoIDs(File file){
        int value;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            value = (int) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            value = 0;
        }
        return value;
    }

    public static boolean sobreescreverArquivo(File arquivo, Object obj){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(obj);
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

    public static LocalDate consultarArquivoLocalDate(File file){
        LocalDate localDate;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            localDate = (LocalDate) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            localDate = LocalDate.now();
        }
        return localDate;
    }

    public static void copiarImagemPara(File imgOriginal, String directoryPath) throws IOException {
        File diretorio = new File(directoryPath);
        if(!diretorio.exists())
            diretorio.mkdirs();

        File arquivoImg = new File(diretorio + "/" + imgOriginal.getName());
        arquivoImg.createNewFile();

        BufferedImage in = ImageIO.read(imgOriginal);
        ImageIO.write(in, "png", arquivoImg);
    }
}
