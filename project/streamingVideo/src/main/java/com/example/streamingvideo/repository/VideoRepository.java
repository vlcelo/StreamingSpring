package com.example.streamingvideo.repository;

import com.example.streamingvideo.model.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    // Buscar vídeos pelo título (ordenado)
    List<Video> findByTituloContainingIgnoreCaseOrderByTituloAsc(String titulo);

    // Vídeos de uma categoria ordenados por título
    List<Video> findByCategoria_NomeIgnoreCaseOrderByTituloAsc(String nomeCategoria);

    // Top mais bem avaliados (média)
    interface VideoMediaProjection {
        Video getVideo();
        Double getMedia();
    }

    @Query("""
           select a.video as video, avg(a.nota) as media
           from Avaliacao a
           group by a.video
           order by media desc
           """)
    List<VideoMediaProjection> findTopVideosMaisBemAvaliados(Pageable pageable);
}
