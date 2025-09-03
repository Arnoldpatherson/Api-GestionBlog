package com.blog_api.Service;

import com.blog_api.Model.Article;
import com.blog_api.Repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article create(Article article) {
        article.setDatePublication(LocalDateTime.now());
        return articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    public Article update(Long id, Article updatedArticle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitre(updatedArticle.getTitre());
                    article.setContenu(updatedArticle.getContenu());
                    article.setCategory(updatedArticle.getCategory());
                    article.setAuteur(updatedArticle.getAuteur());
                    article.setDateModification(LocalDateTime.now());
                    return articleRepository.save(article);
                })
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article non trouvé");
        }
        articleRepository.deleteById(id);
    }

    public List<Article> getArticlesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("La catégorie ne peut pas être vide");
        }

        String normalizedCategory = category.trim().toLowerCase();
        return articleRepository.findByCategoryIgnoreCase(normalizedCategory);
    }

    public List<Article> searchArticles(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot-clé de recherche ne peut pas être vide");
        }

        String normalizedKeyword = keyword.trim().toLowerCase();
        return articleRepository.searchArticles(normalizedKeyword);
    }

    public List<Article> getPopularArticles() {
        // Retourne les 10 articles les plus populaires (par nombre de vues ou likes)
        // Si vous n'avez pas de champ de popularité, retournez les plus récents
        return articleRepository.findTop10ByOrderByNombreVuesDesc();

        // Alternative si pas de champ de popularité :
        // return articleRepository.findTop10ByOrderByDatePublicationDesc();
    }

    public List<Article> getRecentArticles() {
        // Retourne les 10 articles les plus récents
        return articleRepository.findTop10ByOrderByDatePublicationDesc();
    }

    public List<Article> getArticlesByAuthor(String authorName) {
        if (authorName == null || authorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'auteur ne peut pas être vide");
        }

        String normalizedAuthor = authorName.trim();
        return articleRepository.findByAuteurIgnoreCase(normalizedAuthor);
    }

    // Méthode supplémentaire utile
    public List<Article> getLatestArticles(int limit) {
        return articleRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "datePublication"))
        ).getContent();
    }
}