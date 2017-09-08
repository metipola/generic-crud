package com.mycompany.generic.crud.filter;

import lombok.Getter;
import lombok.Setter;


public class UsuarioFilter extends GenericFilter{
    
    @Setter
    @Getter
    private String nome;

    @Setter
    @Getter
    private String login;

    @Setter
    @Getter
    private String email;
}
