package com.mycompany.generic.crud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;


@Entity
@Table(name = "USUARIO")
public class Usuario extends BaseEntity<Long>{   
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceUsuario")
    @SequenceGenerator(name = "sequenceUsuario", sequenceName = "SQ_USUARIO", allocationSize = 1, initialValue = 1000)
    private Long id;
    
    @Setter
    @Getter
    @Size(min = 1, message = "{CAMPO_NOME_VAZIO}")
    @NotNull(message = "{CAMPO_NOME_VAZIO}")
    private String nome;

    @Setter
    @Getter
    @Size(min = 1, message = "{CAMPO_LOGIN_VAZIO}")
    @NotNull(message = "{CAMPO_LOGIN_VAZIO}")
    private String login;

    @Setter
    @Getter
    @Email(message = "{EMAIL_INVALIDO}")
    @NotNull(message = "{EMAIL_VAZIO}")
    private String email;

    @Setter
    @Getter
    @Size(min = 8, message = "{TAMANHO_SENHA_INVALIDO}")
    @NotNull(message = "{TAMANHO_SENHA_INVALIDO}")
    private String senha;

    @Setter
    @Getter
    @Transient
    private String confirmaSenha;

    @Override
    public Long getId() {
        return id;
    }

}
