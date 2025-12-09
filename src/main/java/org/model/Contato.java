package org.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contatos")
@Data                 // Cria Getters e Setters automático
@NoArgsConstructor    // Obrigatório para o Hibernate
@AllArgsConstructor   // Cria construtor com todos os dados
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String telefone;
    private String email;
}