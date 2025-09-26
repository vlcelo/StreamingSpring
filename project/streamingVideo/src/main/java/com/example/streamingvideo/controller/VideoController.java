package com.example.streamingvideo.controller;

import com.example.streamingvideo.model.Video;
import com.example.streamingvideo.repository.VideoRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoRepository videoRepository;

    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    // Listar todos os vídeos
    @GetMapping
    public List<Video> listarTodos() {
        return videoRepository.findAll();
    }

    // Buscar vídeos pelo título (ordem alfabética)
    @GetMapping("/buscar")
    public List<Video> buscarPorTitulo(@RequestParam String titulo) {
        return videoRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
    }

    // Listar vídeos por nome da categoria (ordem alfabética)
    @GetMapping("/categoria/{nome}")
    public List<Video> porCategoria(@PathVariable String nome) {
        return videoRepository.findByCategoria_NomeIgnoreCaseOrderByTituloAsc(nome);
    }
}
