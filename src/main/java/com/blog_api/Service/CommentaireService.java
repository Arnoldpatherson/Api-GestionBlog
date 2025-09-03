package com.blog_api.Service;

import com.blog_api.Model.Article;
import com.blog_api.Model.Commentaire;
import com.blog_api.Repository.ArticleRepository;
import com.blog_api.Repository.CommentaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final ArticleRepository articleRepository;

    public Commentaire ajouterCommentaire(Long articleId, Commentaire commentaire) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé avec l'ID: " + articleId));
        commentaire.setArticle(article);
        commentaire.setDateCreation(LocalDateTime.now());
        return commentaireRepository.save(commentaire);
    }

    public List<Commentaire> getCommentairesByArticle(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new RuntimeException("Article non trouvé avec l'ID: " + articleId);
        }
        return commentaireRepository.findByArticleId(articleId);
    }

    public Commentaire getCommentaireById(Long commentId) {
        return commentaireRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé avec l'ID: " + commentId));
    }

    public Commentaire update(Long commentId, Commentaire commentaireDetails) {
        Commentaire commentaire = commentaireRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé avec l'ID: " + commentId));

        commentaire.setContenu(commentaireDetails.getContenu());
        commentaire.setAuteur(commentaireDetails.getAuteur());
        commentaire.setDateModification(LocalDateTime.now());

        return commentaireRepository.save(commentaire);
    }

    public void delete(Long commentId) {
        if (!commentaireRepository.existsById(commentId)) {
            throw new RuntimeException("Commentaire non trouvé avec l'ID: " + commentId);
        }
        commentaireRepository.deleteById(commentId);
    }

    public List<Commentaire> getCommentairesByAuteur(Long articleId, String auteur) {
        if (!articleRepository.existsById(articleId)) {
            throw new RuntimeException("Article non trouvé avec l'ID: " + articleId);
        }
        return commentaireRepository.findByArticleIdAndAuteurContainingIgnoreCase(articleId, auteur);
    }

    public List<Commentaire> searchCommentaires(Long articleId, String keyword) {
        if (!articleRepository.existsById(articleId)) {
            throw new RuntimeException("Article non trouvé avec l'ID: " + articleId);
        }
        return commentaireRepository.findByArticleIdAndContenuContainingIgnoreCase(articleId, keyword);
    }

    public List<Commentaire> getRecentCommentaires(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new RuntimeException("Article non trouvé avec l'ID: " + articleId);
        }
        return commentaireRepository.findTop5ByArticleIdOrderByDateCreationDesc(articleId);
    }

    public Long countCommentairesByArticle(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new RuntimeException("Article non trouvé avec l'ID: " + articleId);
        }
        return commentaireRepository.countByArticleId(articleId);
    }

    // Méthode supplémentaire utile : Vérifier si un commentaire appartient à un article
    public boolean commentaireBelongsToArticle(Long commentId, Long articleId) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(commentId);
        return commentaire.isPresent() &&
                commentaire.get().getArticle().getId().equals(articleId);
    }
}