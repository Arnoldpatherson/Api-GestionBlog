package com.blog_api.Controller;

import com.blog_api.Model.Article;
import com.blog_api.Service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@Tag(name = "Articles", description = "API endpoints pour la gestion des articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // ========== ENDPOINT: Créer un article ==========
    @PostMapping
    @Operation(
            summary = "Créer un nouvel article",
            description = "Endpoint pour créer un nouvel article dans le système"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Article créé avec succès",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Article> createArticle(
            @Parameter(description = "Objet Article à créer", required = true)
            @RequestBody Article article) {
        Article createdArticle = articleService.create(article);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    // ========== ENDPOINT: Lister tous les articles ==========
    @GetMapping
    @Operation(
            summary = "Lister tous les articles",
            description = "Endpoint pour récupérer la liste de tous les articles"
    )
    @ApiResponse(responseCode = "200", description = "Liste des articles récupérée",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Article.class))))
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAll();
        return ResponseEntity.ok(articles);
    }

    // ========== ENDPOINT: Récupérer un article par ID ==========
    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer un article par ID",
            description = "Endpoint pour récupérer un article spécifique par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article trouvé",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    public ResponseEntity<Article> getArticleById(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    // ========== ENDPOINT: Mettre à jour un article ==========
    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre à jour un article",
            description = "Endpoint pour mettre à jour un article existant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article mis à jour",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    public ResponseEntity<Article> updateArticle(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données de l'article", required = true)
            @RequestBody Article article) {
        Article updatedArticle = articleService.update(id, article);
        return ResponseEntity.ok(updatedArticle);
    }

    // ========== ENDPOINT: Supprimer un article ==========
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un article",
            description = "Endpoint pour supprimer définitivement un article"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Article supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    public ResponseEntity<Void> deleteArticle(
            @Parameter(description = "ID de l'article à supprimer", example = "1")
            @PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINT: Articles par catégorie ==========
    @GetMapping("/categorie/{category}")
    @Operation(
            summary = "Articles par catégorie",
            description = "Endpoint pour filtrer les articles par catégorie"
    )
    @ApiResponse(responseCode = "200", description = "Articles de la catégorie trouvés",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Article.class))))
    public ResponseEntity<List<Article>> getArticlesByCategory(
            @Parameter(description = "Catégorie des articles", required = true, example = "technology")
            @PathVariable String category) {
        List<Article> articles = articleService.getArticlesByCategory(category);
        return ResponseEntity.ok(articles);
    }

    // ========== ENDPOINT: Recherche d'articles ==========
    @GetMapping("/recherche")
    @Operation(
            summary = "Rechercher des articles",
            description = "Endpoint pour rechercher des articles par mot-clé"
    )
    @ApiResponse(responseCode = "200", description = "Résultats de la recherche",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Article.class))))
    public ResponseEntity<List<Article>> searchArticles(
            @Parameter(description = "Mot-clé de recherche", required = true, example = "spring boot")
            @RequestParam String keyword) {
        List<Article> articles = articleService.searchArticles(keyword);
        return ResponseEntity.ok(articles);
    }

    // ========== ENDPOINT: Articles récents ==========
    @GetMapping("/recents")
    @Operation(
            summary = "Articles récents",
            description = "Endpoint pour récupérer les articles les plus récents"
    )
    @ApiResponse(responseCode = "200", description = "Articles récents récupérés",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Article.class))))
    public ResponseEntity<List<Article>> getRecentArticles() {
        List<Article> articles = articleService.getRecentArticles();
        return ResponseEntity.ok(articles);
    }

    // ========== ENDPOINT: Articles par auteur ==========
    @GetMapping("/auteur/{authorName}")
    @Operation(
            summary = "Articles par auteur",
            description = "Endpoint pour filtrer les articles par auteur"
    )
    @ApiResponse(responseCode = "200", description = "Articles de l'auteur trouvés",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Article.class))))
    public ResponseEntity<List<Article>> getArticlesByAuthor(
            @Parameter(description = "Nom de l'auteur", required = true, example = "John Doe")
            @PathVariable String authorName) {
        List<Article> articles = articleService.getArticlesByAuthor(authorName);
        return ResponseEntity.ok(articles);
    }
}