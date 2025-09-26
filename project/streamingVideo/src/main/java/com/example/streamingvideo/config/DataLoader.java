package com.example.streamingvideo.config;

import com.example.streamingvideo.model.*;
import com.example.streamingvideo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;

@Configuration
@Component
@Order(1)
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final CategoriaRepository categoriaRepository;
    private final VideoRepository videoRepository;
    private final VisualizacaoRepository visualizacaoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public DataLoader(UsuarioRepository usuarioRepository,
                      PerfilRepository perfilRepository,
                      CategoriaRepository categoriaRepository,
                      VideoRepository videoRepository,
                      VisualizacaoRepository visualizacaoRepository,
                      AvaliacaoRepository avaliacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.categoriaRepository = categoriaRepository;
        this.videoRepository = videoRepository;
        this.visualizacaoRepository = visualizacaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    public void run(String... args) {
        banner("INICIANDO SEED (H2)");

        // Categorias
        var catAcao  = saveCategoria("Ação");
        var catDrama = saveCategoria("Drama");
        var catAnimacao = saveCategoria("Animação");

        // Drama / Sci-fi
        var v1  = saveVideo("Interestelar", "Exploração espacial em busca de um novo lar", 169, catDrama);
        var v2  = saveVideo("Gravidade", "Astronautas tentando sobreviver após acidente em órbita", 91, catDrama);
        var v3  = saveVideo("Perdido em Marte", "Astronauta isolado precisa sobreviver em Marte", 144, catDrama);
        var v4  = saveVideo("A Chegada", "Contato linguístico com extraterrestres", 116, catDrama);
        var v5  = saveVideo("2001: Uma Odisseia no Espaço", "Clássico de Kubrick sobre evolução e tecnologia", 149, catDrama);

        // Ação
        var v6  = saveVideo("Matrix", "Um hacker descobre a verdade sobre a realidade", 136, catAcao);
        var v7  = saveVideo("Matrix Reloaded", "Neo enfrenta novos desafios e agentes", 138, catAcao);
        var v8  = saveVideo("Star Wars: Uma Nova Esperança", "A batalha da Rebelião contra o Império", 121, catAcao);
        var v9  = saveVideo("Star Wars: O Império Contra-Ataca", "A luta continua contra o Império", 124, catAcao);
        var v10 = saveVideo("Avatar", "Conflito em Pandora entre humanos e Na’vi", 162, catAcao);

        // Animação
        var v11 = saveVideo("Toy Story", "Brinquedos ganham vida quando os humanos não estão por perto", 81, catAnimacao);
        var v12 = saveVideo("Procurando Nemo", "Um peixe-palhaço cruza o oceano em busca do filho", 100, catAnimacao);
        var v13 = saveVideo("Os Incríveis", "Família de super-heróis enfrenta vilões e desafios", 115, catAnimacao);
        var v14 = saveVideo("Wall-E", "Um pequeno robô descobre o futuro da humanidade", 98, catAnimacao);
        var v15 = saveVideo("Divertida Mente", "As emoções de uma menina ganham vida própria", 95, catAnimacao);

        // Usuários
        var u1 = saveUsuario("Maria Fernanda",   "mariafernanda@gmail.com",   "138454");
        var u2 = saveUsuario("Lais Lessa",  "laislessa@gmail.com", "287468");
        var u3 = saveUsuario("Tiago Fermiano",  "tiagofermiano@gmail.com", "037518");
        var u4 = saveUsuario("Bruno Benedet",  "brunobenedet@gmail.com", "3418849");

        // Perfis
        var p1 = savePerfil("Maria", u1);
        var p2 = savePerfil("Lais", u2);
        var p3 = savePerfil("Tiago", u3);
        var p4 = savePerfil("Bruno", u4);

        // Visualizações (progresso em %)
        saveVis(p1, v1, 100); // Interestelar
        saveVis(p1, v2, 90);  // Gravidade
        saveVis(p1, v3, 80);  // Perdido em Marte
        saveVis(p1, v11, 100); // Toy Story
        saveVis(p1, v12, 95);  // Procurando Nemo

        saveVis(p2, v4, 100); // A Chegada
        saveVis(p2, v5, 70);  // 2001: Uma Odisseia
        saveVis(p2, v13, 100); // Os Incríveis
        saveVis(p2, v14, 85);  // Wall-E
        saveVis(p2, v15, 90);  // Divertida Mente

        saveVis(p3, v6, 100); // Matrix
        saveVis(p3, v7, 100); // Matrix Reloaded
        saveVis(p3, v8, 100); // Star Wars IV
        saveVis(p3, v9, 100); // Star Wars V
        saveVis(p3, v10, 95); // Avatar
        saveVis(p3, v6, 100); // reassistiu Matrix

        // Avaliações (1..5)
        saveAva(p1, v1, 5, "Excelente");
        saveAva(p1, v2, 4, "Muito bom");
        saveAva(p1, v3, 3, "Ok");
        saveAva(p1, v11, 5, "Clássico da infância");
        saveAva(p1, v12, 4, "Engraçado e emocionante");

        saveAva(p2, v4, 5, "Genial");
        saveAva(p2, v5, 5, "Obra-prima");
        saveAva(p2, v13, 4, "Família incrível!");
        saveAva(p2, v14, 5, "Poético e profundo");
        saveAva(p2, v15, 4, "Criativo e emocionante");

        saveAva(p3, v6, 5, "Revolucionário");
        saveAva(p3, v7, 4, "Cheio de ação");
        saveAva(p3, v8, 5, "Clássico absoluto");
        saveAva(p3, v9, 5, "Melhor da saga");
        saveAva(p3, v10, 4, "Visual impressionante");


        banner("RESULTADOS DAS CONSULTAS");

        // 1) Buscar vídeos pelo título com ordenação (ex: "Missão")
        section("#1 - Vídeos com 'Matrix' no título (ordem alfabética)");
        var videosMatrix = videoRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc("Matrix");
        videosMatrix.forEach(v -> log.info(" - {}", v.getTitulo()));

        // 2) Todos os vídeos de uma categoria ordenados pelo título (ex: Ação)
        section("#2 - Vídeos da categoria 'Ação' (ordem alfabética)");
        var videosAcao = videoRepository.findByCategoria_NomeIgnoreCaseOrderByTituloAsc("Ação");
        videosAcao.forEach(v -> log.info(" - {}", v.getTitulo()));

        // 3) Top 10 vídeos mais bem avaliados (média)
        var top10Avaliados = videoRepository.findTopVideosMaisBemAvaliados(PageRequest.of(0, 10));
        top10Avaliados.forEach(p ->
                log.info(" - {} | média: {}", p.getVideo().getTitulo(), String.format("%.2f", p.getMedia()))
        );

        // 4) Top 10 vídeos mais assistidos (contagem de visualizações)
        var top10Assistidos = visualizacaoRepository.findTopVideosMaisAssistidos(PageRequest.of(0, 10));
        top10Assistidos.forEach(vc ->
                log.info(" - {} | views: {}", vc.getVideo().getTitulo(), vc.getTotal())
        );

        // 5) Usuário que mais assistiu vídeos
        section("#5 - Usuário que mais assistiu (ranking 1)");
        var rankingUsuarios = visualizacaoRepository.findUsuariosQueMaisAssistiram(PageRequest.of(0, 1));
        if (!rankingUsuarios.isEmpty()) {
            var uTop = rankingUsuarios.getFirst();
            log.info(" - {} | views: {}", uTop.getUsuario().getNome(), uTop.getTotal());
        } else {
            log.info(" - (sem dados)");
        }

        banner("DATA LOADER FINALIZADO");
    }



    private Categoria saveCategoria(String nome) {
        var c = new Categoria();
        c.setNome(nome);
        return categoriaRepository.save(c);
    }

    private Video saveVideo(String titulo, String desc, int duracao, Categoria cat) {
        var v = new Video();
        v.setTitulo(titulo);
        v.setDescricao(desc);
        v.setDuracao(duracao);
        v.setCategoria(cat);
        return videoRepository.save(v);
    }

    private Usuario saveUsuario(String nome, String email, String senha) {
        var u = new Usuario();
        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(senha);
        u.setDataCadastro(LocalDateTime.now());
        return usuarioRepository.save(u);
    }

    private Perfil savePerfil(String nomePerfil, Usuario usuario) {
        var p = new Perfil();
        p.setNomePerfil(nomePerfil);
        p.setUsuario(usuario);
        return perfilRepository.save(p);
    }

    private void saveVis(Perfil perfil, Video video, int progresso) {
        var vis = new Visualizacao();
        vis.setPerfil(perfil);
        vis.setVideo(video);
        vis.setDataHora(LocalDateTime.now().minusDays((long) (Math.random() * 10)));
        vis.setProgresso(progresso);
        visualizacaoRepository.save(vis);
    }

    private void saveAva(Perfil perfil, Video video, int nota, String comentario) {
        var a = new Avaliacao();
        a.setPerfil(perfil);
        a.setVideo(video);
        a.setNota(nota);
        a.setComentario(comentario);
        avaliacaoRepository.save(a);
    }


    private void banner(String title) {
        log.info("");
        log.info("==================================================");
        log.info("= {} =", center(title, 44));
        log.info("==================================================");
        log.info("");
    }

    private void section(String title) {
        log.info("");
        log.info("---- {} ----", title);
    }

    private String center(String text, int width) {
        if (text.length() >= width) return text;
        int pad = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, pad)) + text + " ".repeat(Math.max(0, width - pad - text.length()));
    }
}
