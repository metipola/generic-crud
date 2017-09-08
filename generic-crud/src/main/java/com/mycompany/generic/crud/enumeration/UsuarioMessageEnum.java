package com.mycompany.generic.crud.enumeration;


public enum UsuarioMessageEnum implements MessageEnum{
    
    USUARIO_SALVO_SUCESSO("USUARIO_SALVO_SUCESSO"),
    USUARIO_EXCLUIDO_SUCESSO("USUARIO_EXCLUIDO_SUCESSO"),
    LOGIN_EXISTE("LOGIN_EXISTE"),
    SENHA_DIFERENTE_CONFIRMA_SENHA("SENHA_DIFERENTE_CONFIRMACAO");
    
    private final String message;

    private UsuarioMessageEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
    
    
}
