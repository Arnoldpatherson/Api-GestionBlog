package com.blog_api.dto;


public class CommentaireDTO {
    private Long id;
    private String auteur;
    private String contenu;

    public CommentaireDTO(Long id, String auteur, String contenu) {
        this.id = id;
        this.auteur = auteur;
        this.contenu = contenu;
    }

    public Long getId() {
        return id;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}

