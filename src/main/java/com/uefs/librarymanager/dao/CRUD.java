package main.java.com.uefs.librarymanager.dao;

import java.util.List;

public interface CRUD<T> {
    /**
     * Cria novo objeto T
     *
     * @param obj
     * @return
     */
    public T create (T obj);

    /**
     * remove objeto T
     * @param obj
     */
    public void delete(T obj);

    /**
     * remove alguns objetos
     * @param obj
     */
    public void deleteMany(T obj);

    /**
     * atualiza objeto T na sua coleção
     * @param obj
     * @return
     */
    public T update(T obj);

    /**
     * consulta coleção de objetos T
     * @return
     */
    public List<T> findMany();

    /**
     * consulta objeto T na coleção pelo ID
     * @param id
     * @return
     */
    public T findById(int id);
}
