package model;

import java.util.HashMap;

public class DatosGlobales {

    public static HashMap<String, String> usuariosGuardados = new HashMap<>();
    
    static {
        usuariosGuardados.put("emiliano", "1234");
    }
}