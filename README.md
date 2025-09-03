Prérequis :

IDE recommandé : IntelliJ IDEA
Outils de test : Swagger UI ou Postman
Environnement : JDK 21 et Maven
Étapes pour lancer et tester l'API :
 

Importation du projet :
Ouvrir le dossier du projet dans IntelliJ IDEA
Laisser l'IDE configurer les dépendances automatiquement


création de la base de donnée :
Créer une base PostgreSQL nommée blogDB via pgAdmin, ou vous pouvez aussi aussi créer une base de donnée avec un autre nom il vous suffira juste de modifier le nom de la base de données dans le fichier de configuration de la base de données "application.properties" ou "application.yml" pour les adapter aux votres.


Compilation et lancement :
Compiler le projet
Lancer l'application via la classe principale (annotée @SpringBootApplication) à travers le bouton "run" de IntelliJ


Accès à l'interface Swagger :
Ouvrir un navigateur web
Accéder à : http://localhost:8080/swagger-ui/index.html
Vous devriez voir la documentation interactive de l'API


Tester un endpoint (exemple POST) dans Swagger UI :

Trouver la section correspondant à votre contrôleur
Cliquer sur "POST" pour développer la section
Cliquer sur "Try it out"
Dans le corps de la requête :
Remplacer le contenu exemple par vos données

{
  "titre": "nouveau titre",
  "contenu": "nouveau contenu"
}

Supprimer l'id (généré automatiquement)
Renseigner les champs obligatoires (titre, contenu)
Cliquer sur "Execute"
Vérifier la réponse code 200 response body avec les details de l'article nouvellement créé, puis un autre code 200 OK.



Tester un endpoint (exemple PUT) : a. Dans Swagger UI :

Trouver la section correspondant à votre contrôleur
Cliquer sur "PUT" pour développer la section
Cliquer sur "Try it out"
Dans le champ ID, mettez l'ID de l'article que vous  voulez modifier Ex: 6
Dans le corps de la requête :
Remplacer le contenu exemple par vos données

{
  "titre": "nouveau",
  "contenu": "nouveau"
}

Cliquer sur "Execute"
Vérifier la réponse code 200 response body avec les details de l'article mis à jour, puis un autre code 200 OK.



Tester un Endpoint d'ajout d'un commentaire (exemple POST) dans Swagger UI :

Trouver la section correspondant à votre contrôleur
Cliquer sur "POST" pour développer la section
Cliquer sur "Try it out"
Dans le corps de la requête :
Remplacer le contenu exemple par vos données

{
  "auteur": "Boris",
  "contenu": "Ceci est un commentaire"
}

Supprimer l'id (généré automatiquement)
Renseigner les champs obligatoires (auteur, contenu)
Cliquer sur "Execute"
Vérifier la réponse code 200 response body avec les details de l'article nouvellement créé, puis un autre code 200 OK.


Tester un Endpoint d'affichage d'un article par ID (exemple GET) dans Swagger UI :

Trouver la section correspondant à votre contrôleur
Cliquer sur "GET" pour développer la section
Cliquer sur "Try it out"
Dans le champ reservé à l'ID, insérez l'ID de l'article correspondant puis clique sur "Execute" en dessous.

Vérifier la réponse code 200 response body avec les details de l'article affiché avec tous ses commentaires, puis un autre code 200 OK.



