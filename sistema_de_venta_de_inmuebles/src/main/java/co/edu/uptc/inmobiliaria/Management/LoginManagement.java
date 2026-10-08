package co.edu.uptc.inmobiliaria.Management;

import co.edu.uptc.inmobiliaria.Enums.Rol;
import co.edu.uptc.inmobiliaria.Model.Administrador;
import co.edu.uptc.inmobiliaria.Model.Persona;

public class LoginManagement {

    public Persona iniciarSesion(int id, String clave, Rol rol) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'iniciarSesion'");

         // Temporal: cualquier dato entra como administrador de prueba
            Administrador admin = new Administrador();
            admin.setId(1);
            admin.setNombre("Admin de prueba");
            admin.setClave("123");
            admin.setRol(Rol.ADMINISTRADOR);
            return admin;
            
    }

    
    
}
