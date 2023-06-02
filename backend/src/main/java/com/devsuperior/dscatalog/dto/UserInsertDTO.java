package com.devsuperior.dscatalog.dto;

import java.io.Serializable;

public class UserInsertDTO extends UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String password;

    // Utilizar o super caso tenha alguma logica no construtor vazio da super classe ele pega ela e executa.
    UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
