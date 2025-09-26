package com.example.streamingvideo.config;

import com.example.streamingvideo.repository.VideoRepository;
import com.example.streamingvideo.repository.VisualizacaoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Scanner;

@Component
public class ConsoleMenuRunner {

    private final VideoRepository videoRepository;
    private final VisualizacaoRepository visualizacaoRepository;

    public ConsoleMenuRunner(VideoRepository videoRepository,
                             VisualizacaoRepository visualizacaoRepository) {
        this.videoRepository = videoRepository;
        this.visualizacaoRepository = visualizacaoRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        Scanner in = new Scanner(System.in);

        printlnBanner("CONSULTAS INTERATIVAS");
        boolean keep = true;
        while (keep) {
            printMenu();
            String op = in.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Digite parte do título (ex: Matrix): ");
                    String termo = in.nextLine().trim();
                    var lista = videoRepository.findByTituloContainingIgnoreCaseOrderByTituloAsc(termo);
                    printlnSection("#1 - Vídeos por título (ordem alfabética)");
                    if (lista.isEmpty()) System.out.println("Nenhum vídeo encontrado para: " + termo);
                    else lista.forEach(v -> System.out.println(" - " + v.getTitulo() + "  [" + v.getCategoria().getNome() + "]"));
                    printlnLine();
                }
                case "2" -> {
                    System.out.print("Digite o nome da categoria (ex: Ação, Drama, Animação): ");
                    String cat = in.nextLine().trim();
                    var lista = videoRepository.findByCategoria_NomeIgnoreCaseOrderByTituloAsc(cat);
                    printlnSection("#2 - Vídeos da categoria '" + cat + "' (ordem alfabética)");
                    if (lista.isEmpty()) System.out.println("Nenhum vídeo encontrado na categoria: " + cat);
                    else lista.forEach(v -> System.out.println(" - " + v.getTitulo()));
                    printlnLine();
                }
                case "3" -> {
                    var top = videoRepository.findTopVideosMaisBemAvaliados(PageRequest.of(0, 10));
                    printlnSection("#3 - Top 10 vídeos mais bem avaliados (média)");
                    if (top.isEmpty()) System.out.println("Sem avaliações suficientes.");
                    else {
                        int rank = 1;
                        for (var p : top) {
                            System.out.printf(" %2d) %-40s | média: %.2f%n", rank++, p.getVideo().getTitulo(), p.getMedia());
                        }
                    }
                    printlnLine();
                }
                case "4" -> {
                    var top = visualizacaoRepository.findTopVideosMaisAssistidos(PageRequest.of(0, 10));
                    printlnSection("#4 - Top 10 vídeos mais assistidos (views)");
                    if (top.isEmpty()) System.out.println("Sem visualizações suficientes.");
                    else {
                        int rank = 1;
                        for (var vc : top) {
                            System.out.printf(" %2d) %-40s | views: %d%n", rank++, vc.getVideo().getTitulo(), vc.getTotal());
                        }
                    }
                    printlnLine();
                }
                case "5" -> {
                    var ranking = visualizacaoRepository.findUsuariosQueMaisAssistiram(PageRequest.of(0, 1));
                    printlnSection("#5 - Usuário que mais assistiu");
                    if (ranking.isEmpty()) System.out.println("Sem dados de visualização.");
                    else {
                        var top = ranking.getFirst();
                        System.out.printf(" - %s | views: %d%n", top.getUsuario().getNome(), top.getTotal());
                    }
                    printlnLine();
                }
                case "0", "q", "Q", "s", "S" -> {
                    keep = false;
                    printlnBanner("SAINDO DO MENU");
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        printlnLine();
        System.out.println(" Escolha uma opção:");
        System.out.println("  1) Buscar vídeos pelo título (ordenado)");
        System.out.println("  2) Vídeos por categoria (ordenado)");
        System.out.println("  3) Top 10 mais bem avaliados");
        System.out.println("  4) Top 10 mais assistidos");
        System.out.println("  5) Usuário que mais assistiu");
        System.out.println("  0) Sair");
        printlnLine();
        System.out.print(" > ");
    }

    private void printlnBanner(String title) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println(center(" " + title + " ", 50, '='));
        System.out.println("==================================================");
        System.out.println();
    }

    private void printlnSection(String title) {
        System.out.println();
        System.out.println("---- " + title + " ----");
    }

    private void printlnLine() {
        System.out.println("--------------------------------------------------");
    }

    private String center(String text, int width, char pad) {
        if (text.length() >= width) return text;
        int padLeft = (width - text.length()) / 2;
        int padRight = width - text.length() - padLeft;
        return String.valueOf(pad).repeat(Math.max(0, padLeft)) + text + String.valueOf(pad).repeat(Math.max(0, padRight));
    }
}
