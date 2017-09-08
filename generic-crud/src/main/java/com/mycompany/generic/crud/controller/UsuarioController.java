package com.mycompany.generic.crud.controller;

import com.mycompany.generic.crud.entity.Usuario;
import com.mycompany.generic.crud.filter.UsuarioFilter;
import com.mycompany.generic.crud.service.UsuarioService;
import com.mycompany.generic.crud.enumeration.UsuarioMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("viewScoped")
public class UsuarioController extends GenericController<Usuario, Long, UsuarioMessageEnum, UsuarioFilter>{
    
    
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
        this.usuarioService = usuarioService;
    }

    @Override
    public UsuarioMessageEnum successMessage() {
        return UsuarioMessageEnum.USUARIO_SALVO_SUCESSO;
    }
    
    @Override
    public UsuarioMessageEnum deleteMessage() {
        return UsuarioMessageEnum.USUARIO_EXCLUIDO_SUCESSO;
    }

    @Override
    public Usuario createEntityInstance() {
        return new Usuario();
    }

    @Override
    public UsuarioFilter createFilterInstance() {
        return new UsuarioFilter();
    }
}
