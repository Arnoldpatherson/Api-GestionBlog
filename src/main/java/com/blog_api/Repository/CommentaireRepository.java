package com.blog_api.Repository;

import com.blog_api.Model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    // Trouver tous les commentaires d'un article
    List<Commentaire> findByArticleId(Long articleId);

    // Compter les commentaires d'un article
    Long countByArticleId(Long articleId);

    // Trouver les commentaires par auteur pour un article spécifique
    @Query("SELECT c FROM Commentaire c WHERE c.article.id = :articleId AND LOWER(c.auteur) LIKE LOWER(CONCAT('%', :auteur, '%'))")
    List<Commentaire> findByArticleIdAndAuteurContainingIgnoreCase(@Param("articleId") Long articleId,
                                                                   @Param("auteur") String auteur);

    // Rechercher des commentaires par contenu pour un article spécifique
    @Query("SELECT c FROM Commentaire c WHERE c.article.id = :articleId AND LOWER(c.contenu) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Commentaire> findByArticleIdAndContenuContainingIgnoreCase(@Param("articleId") Long articleId,
                                                                    @Param("keyword") String keyword);

    // Trouver les commentaires les plus récents d'un article (limité à 5)
    List<Commentaire> findTop5ByArticleIdOrderByDateCreationDesc(Long articleId);

    // Méthode pour vérifier l'existence d'un commentaire dans un article
    boolean existsByIdAndArticleId(Long commentId, Long articleId);
}