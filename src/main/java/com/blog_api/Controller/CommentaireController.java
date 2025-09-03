package com.blog_api.Controller;

import com.blog_api.Model.Commentaire;
import com.blog_api.Service.CommentaireService;
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
@RequestMapping("/api/articles/{articleId}/commentaires")
@Tag(name = "Commentaires", description = "API endpoints pour la gestion des commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    public CommentaireController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    // ========== ENDPOINT: Ajouter un commentaire ==========
    @PostMapping
    @Operation(
            summary = "Ajouter un commentaire à un article",
            description = "Endpoint pour ajouter un nouveau commentaire à un article spécifique"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Commentaire ajouté avec succès",
                    content = @Content(schema = @Schema(implementation = Commentaire.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    public ResponseEntity<Commentaire> ajouterCommentaire(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "Objet Commentaire à ajouter", required = true)
            @RequestBody Commentaire commentaire) {
        Commentaire nouveauCommentaire = commentaireService.ajouterCommentaire(articleId, commentaire);
        return new ResponseEntity<>(nouveauCommentaire, HttpStatus.CREATED);
    }

    // ========== ENDPOINT: Lister tous les commentaires d'un article ==========
    @GetMapping
    @Operation(
            summary = "Lister tous les commentaires d'un article",
            description = "Endpoint pour récupérer tous les commentaires d'un article spécifique"
    )
    @ApiResponse(responseCode = "200", description = "Liste des commentaires récupérée",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Commentaire.class))))
    public ResponseEntity<List<Commentaire>> getCommentairesByArticle(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId) {
        List<Commentaire> commentaires = commentaireService.getCommentairesByArticle(articleId);
        return ResponseEntity.ok(commentaires);
    }

    // ========== ENDPOINT: Récupérer un commentaire par ID ==========
    @GetMapping("/{commentId}")
    @Operation(
            summary = "Récupérer un commentaire par ID",
            description = "Endpoint pour récupérer un commentaire spécifique par son identifiant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Commentaire trouvé",
                    content = @Content(schema = @Schema(implementation = Commentaire.class))),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
    })
    public ResponseEntity<Commentaire> getCommentaireById(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "ID du commentaire", required = true, example = "1")
            @PathVariable Long commentId) {
        Commentaire commentaire = commentaireService.getCommentaireById(commentId);
        return ResponseEntity.ok(commentaire);
    }

    // ========== ENDPOINT: Mettre à jour un commentaire ==========
    @PutMapping("/{commentId}")
    @Operation(
            summary = "Mettre à jour un commentaire",
            description = "Endpoint pour mettre à jour un commentaire existant"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Commentaire mis à jour",
                    content = @Content(schema = @Schema(implementation = Commentaire.class))),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
    })
    public ResponseEntity<Commentaire> updateCommentaire(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "ID du commentaire", required = true, example = "1")
            @PathVariable Long commentId,
            @Parameter(description = "Nouvelles données du commentaire", required = true)
            @RequestBody Commentaire commentaire) {
        Commentaire updatedCommentaire = commentaireService.update(commentId, commentaire);
        return ResponseEntity.ok(updatedCommentaire);
    }

    // ========== ENDPOINT: Supprimer un commentaire ==========
    @DeleteMapping("/{commentId}")
    @Operation(
            summary = "Supprimer un commentaire",
            description = "Endpoint pour supprimer définitivement un commentaire"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Commentaire supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
    })
    public ResponseEntity<Void> deleteCommentaire(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "ID du commentaire à supprimer", example = "1")
            @PathVariable Long commentId) {
        commentaireService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINT: Commentaires par auteur ==========
    @GetMapping("/auteur/{auteur}")
    @Operation(
            summary = "Commentaires par auteur",
            description = "Endpoint pour filtrer les commentaires par auteur dans un article spécifique"
    )
    @ApiResponse(responseCode = "200", description = "Commentaires de l'auteur trouvés",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Commentaire.class))))
    public ResponseEntity<List<Commentaire>> getCommentairesByAuteur(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "Nom de l'auteur", required = true, example = "John Doe")
            @PathVariable String auteur) {
        List<Commentaire> commentaires = commentaireService.getCommentairesByAuteur(articleId, auteur);
        return ResponseEntity.ok(commentaires);
    }

    // ========== ENDPOINT: Recherche de commentaires ==========
    @GetMapping("/recherche")
    @Operation(
            summary = "Rechercher des commentaires",
            description = "Endpoint pour rechercher des commentaires par mot-clé dans un article"
    )
    @ApiResponse(responseCode = "200", description = "Résultats de la recherche",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Commentaire.class))))
    public ResponseEntity<List<Commentaire>> searchCommentaires(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "Mot-clé de recherche", required = true, example = "excellent")
            @RequestParam String keyword) {
        List<Commentaire> commentaires = commentaireService.searchCommentaires(articleId, keyword);
        return ResponseEntity.ok(commentaires);
    }

    // ========== ENDPOINT: Commentaires récents ==========
    @GetMapping("/recents")
    @Operation(
            summary = "Commentaires récents",
            description = "Endpoint pour récupérer les commentaires les plus récents d'un article"
    )
    @ApiResponse(responseCode = "200", description = "Commentaires récents récupérés",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Commentaire.class))))
    public ResponseEntity<List<Commentaire>> getRecentCommentaires(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId) {
        List<Commentaire> commentaires = commentaireService.getRecentCommentaires(articleId);
        return ResponseEntity.ok(commentaires);
    }

    // ========== ENDPOINT: Nombre de commentaires par article ==========
    @GetMapping("/count")
    @Operation(
            summary = "Compter les commentaires",
            description = "Endpoint pour obtenir le nombre total de commentaires d'un article"
    )
    @ApiResponse(responseCode = "200", description = "Nombre de commentaires récupéré",
            content = @Content(schema = @Schema(implementation = Long.class)))
    public ResponseEntity<Long> countCommentairesByArticle(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId) {
        Long count = commentaireService.countCommentairesByArticle(articleId);
        return ResponseEntity.ok(count);
    }

    // ========== ENDPOINT: Vérifier si un commentaire existe dans un article ==========
    @GetMapping("/{commentId}/exists")
    @Operation(
            summary = "Vérifier l'existence d'un commentaire dans un article",
            description = "Endpoint pour vérifier si un commentaire spécifique appartient à un article"
    )
    @ApiResponse(responseCode = "200", description = "Vérification effectuée",
            content = @Content(schema = @Schema(implementation = Boolean.class)))
    public ResponseEntity<Boolean> commentaireExistsInArticle(
            @Parameter(description = "ID de l'article", required = true, example = "1")
            @PathVariable Long articleId,
            @Parameter(description = "ID du commentaire", required = true, example = "1")
            @PathVariable Long commentId) {
        boolean exists = commentaireService.commentaireBelongsToArticle(commentId, articleId);
        return ResponseEntity.ok(exists);
    }
}