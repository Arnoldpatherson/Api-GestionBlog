package com.blog_api.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentaires")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;
    private String auteur;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    // Constructeurs
    public Commentaire() {
        this.dateCreation = LocalDateTime.now();
    }

    public Commentaire(String contenu, String auteur) {
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
        // Mettre à jour automatiquement la date de modification quand le contenu change
        this.setDateModification();
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
        // Mettre à jour automatiquement la date de modification quand l'auteur change
        this.setDateModification();
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    // Méthode setDateModification - Celle que vous demandez
    public void setDateModification() {
        this.dateModification = LocalDateTime.now();
    }

    // Surcharge pour permettre une date spécifique (optionnel)
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    // Méthode toString pour le débogage
    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateModification=" + dateModification +
                '}';
    }
}