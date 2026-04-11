# Rapport - TP N°02

- **Nom et Prénom :** HADJ ARAB Adel
- **Matricule :** 222231482117

---

## Introduction

Ce rapport présente les solutions aux questions du TP N°02 sur les requêtes d'agrégation avec MongoDB, appliquées à une collection de commandes e-commerce (`Orders`). La base de données contient 50 commandes.

---

## 1. Préparation de la base de données

### 1.1 Importation de la collection

La première étape consiste à importer les données depuis le fichier `Orders.json` dans la collection `Orders`.

### 1.2 Création des index stratégiques

Avant d'exécuter les requêtes, nous créons plusieurs index pour optimiser les performances :

```javascript
// Index simple sur le statut
db.Orders.createIndex({ status: 1 });

// Index composé pour les recherches par ville + date
db.Orders.createIndex({ "customer.city": 1, orderDate: -1 });

// Index composé pour optimiser les agrégations par catégorie et montant
db.Orders.createIndex({ "products.category": 1, totalAmount: -1 });

// Index texte sur le nom du produit
db.Orders.createIndex({ "products.name": "text" });

// Index sur la date pour les requêtes temporelles
db.Orders.createIndex({ orderDate: 1 });
```

---

## 2. Requêtes d'Agrégation

Voici les solutions aux différentes questions posées dans le TP avec un aperçu de leurs résultats d'exécution :

### Requête 1 : Compter le nombre total de commandes par statut

```javascript
db.Orders.aggregate([
  {
    $group: {
      _id: "$status",
      total: { $sum: 1 },
    },
  },
]);
```

**Résultat d'exécution :**

```json
[
  { "_id": "en attente", "total": 10 },
  { "_id": "en cours", "total": 3 },
  { "_id": "annulé", "total": 10 },
  { "_id": "livré", "total": 27 }
]
```

### Requête 2 : Afficher les commandes d'Alger

```javascript
db.Orders.find({
  "customer.city": "Alger",
});
```

**Résultat d'exécution (Extrait) :**

```json
[
  {
    "orderId": "ORD-001",
    "customer": {
      "name": "Youcef Benaissa",
      "city": "Alger",
      "age": 23
    },
    "status": "livré",
    "totalAmount": 429000,
    "paymentMethod": "carte"
  }
]
```

### Requête 3 : Calculer le montant total et moyen de toutes les commandes

```javascript
db.Orders.aggregate([
  {
    $group: {
      _id: null,
      total: { $sum: "$totalAmount" },
      moyen: { $avg: "$totalAmount" },
    },
  },
]);
```

**Résultat d'exécution :**

```json
[{ "_id": null, "total": 8125500, "moyen": 162510 }]
```

### Requête 4 : Calculez le montant total, moyen, maximum et minimum de toutes les commandes

```javascript
db.Orders.aggregate([
  {
    $group: {
      _id: null,
      total: { $sum: "$totalAmount" },
      moyen: { $avg: "$totalAmount" },
      max: { $max: "$totalAmount" },
      min: { $min: "$totalAmount" },
    },
  },
]);
```

**Résultat d'exécution :**

```json
[{ "_id": null, "total": 8125500, "moyen": 162510, "max": 533000, "min": 1500 }]
```

### Requête 5 : Affichez toutes les commandes dont le montant dépasse 50 000 DA, triées du montant le plus élevé au plus bas

```javascript
db.Orders.find({
  totalAmount: { $gt: 50000 },
}).sort({ totalAmount: -1 });
```

**Résultat d'exécution (Extrait) :**

```json
[
  {
    "orderId": "ORD-028",
    "customer": { "name": "Naima Belhocine", "city": "Blida" },
    "totalAmount": 533000
  },
  {
    "orderId": "ORD-004",
    "customer": { "name": "Fatima Boudiaf", "city": "Sétif" },
    "totalAmount": 489000
  },
  {
    "orderId": "ORD-039",
    "customer": { "name": "Mehdi Boucherit", "city": "Constantine" },
    "totalAmount": 467000
  }
]
```

### Requête 6 : Nombre de commandes et CA par méthode de paiement

```javascript
db.Orders.aggregate([
  {
    $group: {
      _id: "$paymentMethod",
      nbCommandes: { $sum: 1 },
      chiffreAffaires: { $sum: "$totalAmount" },
    },
  },
]);
```

**Résultat d'exécution :**

```json
[
  { "_id": "virement", "nbCommandes": 13, "chiffreAffaires": 1696500 },
  { "_id": "chèque", "nbCommandes": 12, "chiffreAffaires": 1587000 },
  { "_id": "cash", "nbCommandes": 10, "chiffreAffaires": 1850500 },
  { "_id": "paypal", "nbCommandes": 6, "chiffreAffaires": 1032500 },
  { "_id": "carte", "nbCommandes": 9, "chiffreAffaires": 1959000 }
]
```

### Requête 7 : Statistiques par ville pour les commandes livrées

```javascript
db.Orders.aggregate([
  { $match: { status: "livré" } },
  {
    $group: {
      _id: "$customer.city",
      chiffreAffaires: { $sum: "$totalAmount" },
      nbCommandes: { $sum: 1 },
      panierMoyen: { $avg: "$totalAmount" },
    },
  },
  { $sort: { chiffreAffaires: -1 } },
]);
```

**Résultat d'exécution (Extrait du top 3) :**

```json
[
  {
    "_id": "Blida",
    "chiffreAffaires": 999500,
    "nbCommandes": 3,
    "panierMoyen": 333166.67
  },
  {
    "_id": "Oran",
    "chiffreAffaires": 749500,
    "nbCommandes": 5,
    "panierMoyen": 149900
  },
  {
    "_id": "Tizi Ouzou",
    "chiffreAffaires": 695500,
    "nbCommandes": 4,
    "panierMoyen": 173875
  }
]
```

### Requête 8 : Top 5 des produits les plus rentables

```javascript
db.Orders.aggregate([
  { $unwind: "$products" },
  {
    $group: {
      _id: "$products.name",
      revenuTotal: {
        $sum: { $multiply: ["$products.price", "$products.qty"] },
      },
      quantiteVendue: { $sum: "$products.qty" },
      nbCommandes: { $sum: 1 },
    },
  },
  { $sort: { revenuTotal: -1 } },
  { $limit: 5 },
]);
```

**Résultat d'exécution :**

```json
[
  {
    "_id": "Laptop",
    "revenuTotal": 2040000,
    "quantiteVendue": 17,
    "nbCommandes": 8
  },
  {
    "_id": "Tablette",
    "revenuTotal": 1320000,
    "quantiteVendue": 24,
    "nbCommandes": 12
  },
  {
    "_id": "Écran Monitor",
    "revenuTotal": 1170000,
    "quantiteVendue": 26,
    "nbCommandes": 11
  },
  {
    "_id": "Téléphone",
    "revenuTotal": 960000,
    "quantiteVendue": 12,
    "nbCommandes": 5
  },
  {
    "_id": "Chaise Bureau",
    "revenuTotal": 700000,
    "quantiteVendue": 20,
    "nbCommandes": 9
  }
]
```

### Requête 9 : Analyse croisée (méthode de paiement et statut)

```javascript
db.Orders.aggregate([
  {
    $group: {
      _id: { paymentMethod: "$paymentMethod", status: "$status" },
      nbCommandes: { $sum: 1 },
      montantTotal: { $sum: "$totalAmount" },
    },
  },
  {
    $sort: { "_id.paymentMethod": 1, montantTotal: -1 },
  },
]);
```

**Résultat d'exécution (Extrait) :**

```json
[
  {
    "_id": { "paymentMethod": "carte", "status": "livré" },
    "nbCommandes": 6,
    "montantTotal": 1183500
  },
  {
    "_id": { "paymentMethod": "carte", "status": "annulé" },
    "nbCommandes": 1,
    "montantTotal": 489000
  },
  {
    "_id": { "paymentMethod": "cash", "status": "livré" },
    "nbCommandes": 7,
    "montantTotal": 1321500
  }
]
```

### Requête 10 : Clients VIP (dépenses supérieures à la moyenne)

```javascript
db.Orders.aggregate([
  {
    $facet: {
      moyenne: [{ $group: { _id: null, avgGlobal: { $avg: "$totalAmount" } } }],
      clients: [
        {
          $group: {
            _id: "$customer.name",
            totalDepense: { $sum: "$totalAmount" },
          },
        },
      ],
    },
  },
  { $unwind: "$moyenne" },
  { $unwind: "$clients" },
  {
    $match: {
      $expr: {
        $gt: ["$clients.totalDepense", "$moyenne.avgGlobal"],
      },
    },
  },
  {
    $project: {
      _id: 0,
      client: "$clients._id",
      totalDepense: "$clients.totalDepense",
      moyenneGlobale: "$moyenne.avgGlobal",
    },
  },
  { $sort: { totalDepense: -1 } },
]);
```

**Résultat d'exécution (Extrait du top 3) :**

```json
[
  {
    "client": "Naima Belhocine",
    "totalDepense": 533000,
    "moyenneGlobale": 162510
  },
  {
    "client": "Fatima Boudiaf",
    "totalDepense": 489000,
    "moyenneGlobale": 162510
  },
  {
    "client": "Mehdi Boucherit",
    "totalDepense": 467000,
    "moyenneGlobale": 162510
  }
]
```

### Requête 11 : Statistiques par catégorie de produits

```javascript
db.Orders.aggregate([
  { $unwind: "$products" },
  {
    $group: {
      _id: "$products.category",
      produitsDistincts: { $addToSet: "$products.name" },
      revenuTotal: {
        $sum: { $multiply: ["$products.price", "$products.qty"] },
      },
    },
  },
  {
    $project: {
      _id: 1,
      nbProduitsDistincts: { $size: "$produitsDistincts" },
      revenuTotal: 1,
    },
  },
  { $sort: { revenuTotal: -1 } },
]);
```

**Résultat d'exécution :**

```json
[
  { "_id": "Electronique", "revenuTotal": 6342000, "nbProduitsDistincts": 9 },
  { "_id": "Mobilier", "revenuTotal": 1689000, "nbProduitsDistincts": 4 },
  { "_id": "Accessoires", "revenuTotal": 94500, "nbProduitsDistincts": 2 }
]
```
