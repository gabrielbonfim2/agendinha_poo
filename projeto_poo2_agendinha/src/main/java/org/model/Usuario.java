package org.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data

public class Usuario {
    @GeneratedValue
    @Id
    private long id;
    private String nome;
    private String email;
    private String senha;






}
