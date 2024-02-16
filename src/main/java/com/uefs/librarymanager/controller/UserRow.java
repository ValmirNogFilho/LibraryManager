package com.uefs.librarymanager.controller;

import com.uefs.librarymanager.model.Livro;
import com.uefs.librarymanager.model.Usuario;

public interface UserRow {
    void setUser(Usuario user);
    void setBook(Livro book);
}
