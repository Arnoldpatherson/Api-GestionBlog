package com.blog_api.dto;


import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO {
    private Long id;
    private String titre;
    private String contenu;
    private LocalDateTime datePublication;
    private List<CommentaireDTO> commentaires;

    public ArticleDTO(Long id, String titre, String contenu, LocalDateTime datePublication, List<CommentaireDTO> commentaires) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
        this.commentaires = commentaires;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public List<CommentaireDTO> getCommentaires() {
        return commentaires;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDatePublication(LocalDateTime datePublication) {
        this.datePublication = datePublication;
    }

    public void setCommentaires(List<CommentaireDTO> commentaires) {
        this.commentaires = commentaires;
    }
}

