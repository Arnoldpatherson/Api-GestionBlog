package com.blog_api.Repository;

import com.blog_api.Model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Méthodes de recherche par catégorie
    List<Article> findByCategoryIgnoreCase(String category);

    // Méthodes de recherche par auteur
    List<Article> findByAuteurIgnoreCase(String auteur);

    // Articles récents
    List<Article> findTop10ByOrderByDatePublicationDesc();

    // ✅ CORRECTION : Ajoutez l'annotation @Query pour la méthode searchArticles
    @Query("SELECT a FROM Article a WHERE " +
            "LOWER(a.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.contenu) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.auteur) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> searchArticles(@Param("keyword") String keyword);

    // Méthodes supplémentaires optionnelles
    List<Article> findByTitreContainingIgnoreCase(String titre);
    List<Article> findByContenuContainingIgnoreCase(String contenu);

    List<Article> findTop10ByOrderByNombreVuesDesc();
}