package com.example.streamingvideo.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "video", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo"})
})
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=255)
    private String titulo;

    @Column(length=1000)
    private String descricao;

    @Column(nullable=false)
    private Integer duracao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable=false)
    private Categoria categoria;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visualizacao> visualizacoes;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    public Video() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getDuracao() { return duracao; }
    public void setDuracao(Integer duracao) { this.duracao = duracao; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public List<Visualizacao> getVisualizacoes() { return visualizacoes; }
    public void setVisualizacoes(List<Visualizacao> visualizacoes) { this.visualizacoes = visualizacoes; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) { this.avaliacoes = avaliacoes; }
}
