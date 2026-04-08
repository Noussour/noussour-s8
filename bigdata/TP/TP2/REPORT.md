# Compte Rendu - TP N°02 : Requêtes d'agrégation

**Nom et Prénom :** HADJ ARAB Adel
**Matricule :** 222231482117
**Matière :** Big Data

---

## Introduction

Ce compte rendu présente les solutions aux exercices du TP N°02 sur les requêtes d'agrégation avec MongoDB, appliquées à une collection de commandes e-commerce (`orders`).

## 1. Préparation de la base de données

### 1.1 Importation de la collection
La première étape consiste à importer les données depuis le fichier `Orders.json` dans la collection `orders`.

### 1.2 Création des index stratégiques
Avant d'exécuter les requêtes, nous créons plusieurs index pour optimiser les performances :

```javascript
// Index simple sur le statut
db.orders.createIndex({ status: 1 });

// Index composé pour les recherches par ville + date
db.orders.createIndex({ "customer.city": 1, orderDate: -1 });

// Index composé pour optimiser les agrégations par catégorie et montant
db.orders.createIndex({ "products.category": 1, totalAmount: -1 });

// Index texte sur le nom du produit
db.orders.createIndex({ "products.name": "text" });

// Index sur la date pour les requêtes temporelles
db.orders.createIndex({ orderDate: 1 });
```

---

## 2. Requêtes d'Agrégation

Voici les solutions aux différentes questions posées dans le TP :

### Requête 1 : Compter le nombre total de commandes par statut
```javascript
db.Orders.aggregate([
  { 
    $group: { 
      _id: "$status", 
      total: { $sum: 1 }
    }
  }
]);
```

### Requête 2 : Afficher les commandes d'Alger
```javascript
db.Orders.find({
  "customer.city": "Alger"
});
```

### Requête 3 : Calculer le montant total et moyen de toutes les commandes
```javascript
db.Orders.aggregate([
  { 
    $group: { 
      _id: null, 
      total: { $sum: "$totalAmount" }, 
      moyen: { $avg: "$totalAmount" }
    }
  }
]);
```

### Requête 4 : Calculez le montant total, moyen, maximum et minimum de toutes les commandes de la collection
```javascript
db.Orders.aggregate([
  { 
    $group: {
      _id: null,
      total: { $sum: "$totalAmount" },
      moyen: { $avg: "$totalAmount" },
      max: { $max: "$totalAmount" },
      min: { $min: "$totalAmount" }
    }
  }
]);
```

### Requête 5 : Affichez toutes les commandes dont le montant dépasse 50 000 DA, triées du montant le plus élevé au plus bas
```javascript
db.Orders.find({ 
  totalAmount: { $gt: 50000 }
}).sort({ totalAmount: -1 });
```

### Requête 6 : Nombre de commandes et CA par méthode de paiement
```javascript
db.Orders.aggregate([
  { 
    $group: {
      _id: "$paymentMethod",
      nbCommandes: { $sum: 1 },
      chiffreAffaires: { $sum: "$totalAmount" }
    }
  }
]);
```

### Requête 7 : Statistiques par ville pour les commandes livrées
**Question :** Retourner pour chaque ville, le chiffre d'affaires total, le nombre de commandes et le panier moyen, pour les commandes dont le statut est "livré". Trier par CA décroissant.
```javascript
db.Orders.aggregate([
  { $match: { status: "livré" } },
  { 
    $group: {
      _id: "$customer.city",
      chiffreAffaires: { $sum: "$totalAmount" },
      nbCommandes: { $sum: 1 },
      panierMoyen: { $avg: "$totalAmount" }
    }
  },
  { $sort: { chiffreAffaires: -1 } }
]);
```

### Requête 8 : Top 5 des produits les plus rentables
**Question :** Décomposer le tableau de produits pour calculer pour chaque produit son revenu total généré, la quantité vendue et le nombre de commandes. Retourner le top 5 des plus rentables.
```javascript
db.Orders.aggregate([
  { $unwind: "$products" },
  { 
    $group: {
      _id: "$products.name",
      revenuTotal: { 
        $sum: { $multiply: ["$products.price", "$products.quantity"] }
      },
      quantiteVendue: { $sum: "$products.quantity" },
      nbCommandes: { $sum: 1 }
    }
  },
  { $sort: { revenuTotal: -1 } },
  { $limit: 5 }
]);
```

### Requête 9 : Analyse croisée (méthode de paiement et statut)
**Question :** Analyser la répartition des commandes en croisant la méthode de paiement et le statut. Afficher le nombre de commandes et le montant total, triés par méthode de paiement puis par montant décroissant.
```javascript
db.Orders.aggregate([
  { 
    $group: {
      _id: { paymentMethod: "$paymentMethod", status: "$status" },
      nbCommandes: { $sum: 1 },
      montantTotal: { $sum: "$totalAmount" }
    }
  },
  { 
    $sort: { "_id.paymentMethod": 1, montantTotal: -1 }
  }
]);
```

### Requête 10 : Clients VIP (dépenses supérieures à la moyenne)
**Question :** Identifier les clients ayant dépensé un montant total supérieur à la moyenne globale de toutes les commandes (calculée dynamiquement).
```javascript
db.Orders.aggregate([
  { 
    $facet: {
      moyenne: [
        { $group: { _id: null, avgGlobal: { $avg: "$totalAmount" } } }
      ],
      clients: [
        { $group: { _id: "$customer.name", totalDepense: { $sum: "$totalAmount" } } }
      ]
    }
  },
  { $unwind: "$moyenne" },
  { $unwind: "$clients" },
  { 
    $match: { 
      $expr: { 
        $gt: ["$clients.totalDepense", "$moyenne.avgGlobal"]
      }
    }
  },
  { 
    $project: {
      _id: 0,
      client: "$clients._id",
      totalDepense: "$clients.totalDepense",
      moyenneGlobale: "$moyenne.avgGlobal"
    }
  },
  { $sort: { totalDepense: -1 } }
]);
```

### Requête 11 : Statistiques par catégorie de produits
**Question :** Pour chaque catégorie, calculer le nombre de produits distincts commandés et le revenu total généré. Trier par revenu décroissant.
```javascript
db.Orders.aggregate([
  { $unwind: "$products" },
  { 
    $group: {
      _id: "$products.category",
      produitsDistincts: { $addToSet: "$products.name" },
      revenuTotal: { 
        $sum: { $multiply: ["$products.price", "$products.quantity"] }
      }
    }
  },
  { 
    $project: {
      _id: 1,
      nbProduitsDistincts: { $size: "$produitsDistincts" },
      revenuTotal: 1
    }
  },
  { $sort: { revenuTotal: -1 } }
]);
```
