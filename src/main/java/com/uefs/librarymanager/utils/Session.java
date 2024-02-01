package com.uefs.librarymanager.utils;

import com.uefs.librarymanager.model.Usuario;

import java.util.HashMap;

public abstract class Session {
    private static HashMap<String, Usuario> session = new HashMap<>();

    public static Usuario getUserInSession(){
        return session.get("inSession");
    }
    public static void loginUser(Usuario user){
        session.put("inSession", user);
    }

    public static void logoutUser(){
        session.clear();
    }

}
