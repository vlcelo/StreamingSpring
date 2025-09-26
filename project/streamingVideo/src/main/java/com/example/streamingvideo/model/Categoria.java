package com.example.streamingvideo.model;
import jakarta.persistence.*;
@Entity
@Table(name = "categoria", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=255, unique=true)
    private String nome;

    public Categoria() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
