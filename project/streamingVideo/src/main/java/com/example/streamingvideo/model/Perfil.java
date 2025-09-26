package com.example.streamingvideo.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "perfil", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome_perfil"})
})
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome_perfil", nullable=false, length=255)
    private String nomePerfil;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable=false)
    private Usuario usuario;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visualizacao> visualizacoes;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    public Perfil() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomePerfil() { return nomePerfil; }
    public void setNomePerfil(String nomePerfil) { this.nomePerfil = nomePerfil; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Visualizacao> getVisualizacoes() { return visualizacoes; }
    public void setVisualizacoes(List<Visualizacao> visualizacoes) { this.visualizacoes = visualizacoes; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }
}
