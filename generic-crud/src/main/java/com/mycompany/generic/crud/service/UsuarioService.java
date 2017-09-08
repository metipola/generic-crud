package com.mycompany.generic.crud.service;

import com.mycompany.generic.crud.entity.Usuario;
import com.mycompany.generic.crud.filter.UsuarioFilter;
import com.mycompany.generic.crud.enumeration.UsuarioMessageEnum;
import com.mycompany.generic.crud.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService extends GenericService<Usuario, Long, UsuarioMessageEnum, UsuarioFilter> {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected List<UsuarioMessageEnum> validate(Usuario usuario) {
        List<UsuarioMessageEnum> errors;

        if (usuario.getId() == null) {
            errors = validateNewUsuario(usuario);
        } else {
            errors = validateOldUsuario(usuario);
        }
  
        return errors;
    }

    private List<UsuarioMessageEnum> validateNewUsuario(Usuario usuario) {
        List<UsuarioMessageEnum> errors = new ArrayList<>();
        
        if(usuarioRepository.existsLoginForNewUser(usuario.getLogin())){
           errors.add(UsuarioMessageEnum.LOGIN_EXISTE);
        }
        
        if(!usuario.getSenha().equals(usuario.getConfirmaSenha())){
           errors.add(UsuarioMessageEnum.SENHA_DIFERENTE_CONFIRMA_SENHA); 
        }
        
        return errors;
        
    }

    private List<UsuarioMessageEnum> validateOldUsuario(Usuario usuario) {
        List<UsuarioMessageEnum> errors = new ArrayList<>();
        
        if(usuarioRepository.existsLoginForOldUser(usuario.getId(), usuario.getLogin())){
           errors.add(UsuarioMessageEnum.LOGIN_EXISTE); 
        }
        
        return errors;
    }

}
