package com.blog_api.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Column(length = 100)
    private String auteur;

    @Column(length = 100)
    private String category;

    @Column(name = "date_publication", nullable = false)
    private LocalDateTime datePublication;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "nombre_vues")
    private Integer nombreVues = 0;

    @Column(name = "nombre_likes")
    private Integer nombreLikes = 0;

    @Column(name = "est_publie")
    private Boolean estPublie = true;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "resume", length = 500)
    private String resume;

    @Column(unique = true)
    private String slug;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;

    @PrePersist
    protected void onCreate() {
        datePublication = LocalDateTime.now();
        // Générer un slug automatiquement si non fourni
        if (slug == null && titre != null) {
            slug = generateSlug(titre);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
        // Mettre à jour le slug si le titre change
        if (slug == null && titre != null) {
            slug = generateSlug(titre);
        }
    }

    // Méthode pour générer un slug à partir du titre
    private String generateSlug(String titre) {
        return titre.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Supprimer les caractères spéciaux
                .replaceAll("\\s+", "-") // Remplacer les espaces par des tirets
                .replaceAll("-+", "-") // Supprimer les tirets doubles
                .trim();
    }

    // Méthode pour incrémenter le nombre de vues
    public void incrementerVues() {
        if (nombreVues == null) {
            nombreVues = 0;
        }
        nombreVues++;
    }

    // Méthode pour incrémenter le nombre de likes
    public void incrementerLikes() {
        if (nombreLikes == null) {
            nombreLikes = 0;
        }
        nombreLikes++;
    }

    // Méthode pour décrémenter le nombre de likes
    public void decrementerLikes() {
        if (nombreLikes == null || nombreLikes == 0) {
            nombreLikes = 0;
        } else {
            nombreLikes--;
        }
    }

    // Méthode pour vérifier si l'article est populaire
    public boolean estPopulaire() {
        return nombreVues != null && nombreVues >= 100;
    }

    // Getters et Setters (générés par Lombok @Data)
}