# 📌 JavaFX Booking System  

## 📖 Description  
Ce projet est une application de réservation de chambres d'hôtel développée en **JavaFX** et utilisant **Scene Builder** pour l'interface utilisateur.  

L'application permet aux **clients** de réserver des chambres en fonction de la disponibilité et de la validité de leur carte bancaire. Les **administrateurs** peuvent gérer les chambres, modifier les prix et ajouter des images.  

## ⚙️ Fonctionnalités  
### 🔹 Clients  
- S'inscrire et se connecter  
- Consulter les chambres disponibles  
- Réserver une chambre (si leur carte est valide)  
- Modifier ou annuler une réservation (si plus de 4 jours avant la date prévue)  

### 🔹 Administrateurs  
- Ajouter, supprimer et modifier des chambres  
- Modifier les prix et images des chambres  
- Gérer les réservations des clients  

## 🛠️ Technologies utilisées  
- **Java 17**  
- **JavaFX 17**  
- **Scene Builder**  
- **MySQL** (ou autre SGBD pour la gestion des utilisateurs et des réservations)  

## 📦 Installation et Exécution  
1. **Cloner le dépôt**  
   ```bash
   git clone https://github.com/ton-repo/JavaFX-Booking-System.git
   cd JavaFX-Booking-System
   ```  
2. **Configurer la base de données**  
   - Importer le fichier SQL dans MySQL  
   - Modifier les paramètres de connexion dans `DatabaseConfig.java`  

3. **Lancer l'application**  
   - Importer le projet dans **IntelliJ IDEA** ou **Eclipse**  
   - Ajouter JavaFX au `module-info.java`  
   - Exécuter `Main.java`  

## 🚀 Améliorations futures  
- Paiement en ligne via une API  
- Ajout d'un tableau de bord pour les statistiques des réservations  
- Notifications par e-mail  

## 📩 Contact    
Si vous avez des questions, n'hésitez pas à nous contacter à **[imaneaitboukdir25@gmail.com](mailto:imaneaitboukdir25@gmail.com)**.  
