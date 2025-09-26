package com.example.streamingvideo.repository;

import com.example.streamingvideo.model.Usuario;
import com.example.streamingvideo.model.Video;
import com.example.streamingvideo.model.Visualizacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisualizacaoRepository extends JpaRepository<Visualizacao, Integer> {


    interface VideoCount {
        Video getVideo();
        long getTotal();
    }
    interface UsuarioCount {
        Usuario getUsuario();
        long getTotal();
    }

    // 4) Top vídeos mais assistidos
    @Query("""
           select v.video as video, count(v) as total
           from Visualizacao v
           group by v.video
           order by total desc
           """)
    List<VideoCount> findTopVideosMaisAssistidos(Pageable pageable);

    // 5) Usuários que mais assistiram (soma das visualizações por usuário)
    @Query("""
           select u as usuario, count(vis) as total
           from Visualizacao vis
           join vis.perfil p
           join p.usuario u
           group by u
           order by total desc
           """)
    List<UsuarioCount> findUsuariosQueMaisAssistiram(Pageable pageable);
}
