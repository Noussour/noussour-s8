# Modélisation du Système de Guide Touristique avec les ADLs

## Analyse Comparative des Langages de Description d'Architecture

---

## 1. Contexte du Système de Guide Touristique

### 1.1 Description du Système

Un système de guide touristique est une application mobile/web qui permet aux touristes de naviguer, découvrir des lieux culturels, et accéder à des informations en temps réel. Le système doit fonctionner dans des environnements variés avec ou sans connectivité réseau.

### 1.2 Exigences Architecturales Identifiées

| Exigence            | Description                                                                                               | Priorité |
| ------------------- | --------------------------------------------------------------------------------------------------------- | -------- |
| **Scalabilité**     | Support de milliers d'utilisateurs simultanés, gestion de contenu multimédias, montée en charge dynamique | Haute    |
| **Mode Offline**    | Téléchargement de contenu pour régions sans connectivité, synchronisation ultérieure, cache local         | Critique |
| **Sécurité**        | Protection des données utilisateur, authentification forte, validation des données, chiffrement           | Haute    |
| **Intégration API** | Intégration avec services externes (cartographie, réservation, météo, traduction, transport)              | Haute    |

### 1.3 Architecture Conceptuelle du Système

```
+----------------------------------------------------------+
|                    CLIENTE MOBILE/WEB                       |
|  +------------------+  +------------------+                 |
|  | Interface       |  | Cache Local      |                 |
|  | Utilisateur     |  | (SQLite/MMKV)    |                 |
|  +------------------+  +------------------+                 |
|           |                        |                        |
|  +------------------+  +------------------+                 |
|  | Gestionnaire    |  | Synchronisation |                 |
|  | de Navigation  |  | différée        |                 |
|  +------------------+  +------------------+                 |
+----------------------------------------------------------+
                          |
                          v
+----------------------------------------------------------+
|                      BACKEND                              |
|  +------------------+  +------------------+                 |
|  | API Gateway     |  | Service Auth    |                 |
|  +------------------+  +------------------+                 |
|  +------------------+  +------------------+                 |
|  | Service Contenu |  | Intégrations     |                 |
|  |                |  | API tierces     |                 |
|  +------------------+  +------------------+                 |
+----------------------------------------------------------+
                          |
                          v
+----------------------------------------------------------+
|                    COUCHES DE DONNÉES                    |
|  +------------------+  +------------------+                 |
|  | Base de Données  |  | CDN Médias       |                 |
|  | (PostgreSQL)   |  | (AWS CloudFront)|                 |
|  +------------------+  +------------------+                 |
+----------------------------------------------------------+
```

---

## 2. Les Langages de Description d'Architecture (ADLs)

### 2.1 Définition et Classification

Un **ADL (Architecture Description Language)** est un langage formel permettant de décrire l'architecture d'un système logiciels de manière précise et machine-readable. Les ADLs permettent de définir :

- **Composants** : Les éléments fonctionnels du système
- **Connecteurs** : Les mécanismes d'interaction entre composants
- **Configurations** : L'agencement et les contraintes architecturales
- **Propriétés** : Les attributs质量和性能

### 2.2 Classification des ADLs

#### Première Génération (Années 1990-2000)

| ADL        | Caractéristiques Principales                            |
| ---------- | ------------------------------------------------------- |
| **Darwin** | Analyse des systèmes distribués à passage de messages   |
| **Rapide** | Simulation et analyse temporelle                        |
| **Wright** | Spécification formelle et vérification des interactions |
| **UniCon** | Compilateur d'architectures de haut niveau              |
| **Meta-H** | Systèmes avioniques temps réel                          |

#### Deuxième Génération (Années 2000+)

| ADL          | Caractéristiques Principales                   |
| ------------ | ---------------------------------------------- |
| **ACME**     | Interchange et interopérabilité, outils riches |
| **AADL**     | Analyse et conception, systèmes embarqués      |
| **xADL 2.0** | Extensible via XML, architectures dynamiques   |
| **C2**       | Style événementiel, interfaces utilisateur     |
| **π-ADL**    | Architectures mobiles et dynamiques            |

---

## 3. Analyse Comparative des ADLs

### 3.1 Tableau Comparatif Global

| Critère             | ACME  | AADL  | xADL 2.0 | Rapide | C2    |
| ------------------- | ----- | ----- | -------- | ------ | ----- |
| **Scalabilité**     | ★★★★☆ | ★★★★★ | ★★★★★    | ★★★☆☆  | ★★☆☆☆ |
| **Mode Offline**    | ★★☆☆☆ | ★★★★★ | ★★★★☆    | ★★☆☆☆  | ★★★☆☆ |
| **Sécurité**        | ★★★☆☆ | ★★★★★ | ★★★★☆    | ★★☆☆☆  | ★★★☆☆ |
| **Intégration API** | ★★★★★ | ★★★☆☆ | ★★★★★    | ★★★☆☆  | ★★★★☆ |
| **Extensibilité**   | ★★★★★ | ★★★★☆ | ★★★★★    | ★★★☆☆  | ★★★☆☆ |
| **Formalisme**      | ★★★☆☆ | ★★★★★ | ★★★★☆    | ★★★★★  | ★★★☆☆ |
| **Outils Support**  | ★★★★☆ | ★★★★★ | ★★★★☆    | ★★★★☆  | ★★☆☆☆ |
| **Support Runtime** | ★★☆☆☆ | ★★★★★ | ★★★★★    | ★★★☆☆  | ★★☆☆☆ |

### 3.2 Legend

- ★★★★★ : Excellent / Très fort
- ★★★★☆ : Bon / Fort
- ★★★☆☆ : Moyen / Modéré
- ★★☆☆☆ : Faible
- ★☆☆☆☆ : Insuffisant / Non supporté

---

## 4. Modélisation Détaillée avec chaque ADL

### 4.1 AADL (Architecture Analysis and Design Language)

#### 4.1.1 Fortesses pour le Système de Guide Touristique

**AADL** est particulièrement adapté pour ce système grâce à :

1. **Modelisation temps reel et embarquée** : Ideal pour les contraintes offline et de performance mobile
2. **Separation materiel/logiciel** : Permet de modeliser l'architecture hybride client/cloud
3. **Analyse formelle** : Verification des contraintes de performance et defiabilité
4. **Support des systemes critiques** : Modellisation des exigences de securité temps reel

#### 4.1.2 Exemple de Specification AADL

```ada
package TouristGuideSystem
public

  -- ==============================================
  -- COMPOSANTS LOGICIELS
  -- ==============================================

  process MobileClient
    features
      UserInterface : in out event port;
      LocationService : in out event port;
      DataSync : in out event port;
      LocalDatabase : requires data access LocalStore;
    properties
      Dispatch_Protocol => Sporadic;
      Period => 100 ms;
      Stack_Size => 512 KBytes;
      Source_Stack_Size => 512 KBytes;
  end MobileClient;

  process ContentSyncService
    features
      SyncTrigger : in event port;
      CloudAPI : requires data access CloudConnector;
      LocalStore : provides data access LocalStore;
    properties
      Dispatch_Protocol => Periodic;
      Period => 30 sec;
  end ContentSyncService;

  process APIGateway
    features
      ExternalRequests : in out event port;
      AuthService : requires data access AuthConnector;
      ContentService : requires data access ContentConnector;
      ThirdPartyAPI : requires data access ExternalConnector;
    properties
      Dispatch_Protocol => Sporadic;
      Source_Stack_Size => 1024 KBytes;
  end APIGateway;

  process AuthenticationService
    features
      LoginRequest : in out event port;
      TokenValidation : in out event port;
      UserDatabase : requires data access UserDB;
    properties
      Dispatch_Protocol => Sporadic;
      Priority => 15;
  end AuthenticationService;

  -- ==============================================
  -- COMPOSANTS MATERIELS
  -- ==============================================

  processor MobileProcessor
    properties
      Processor_Speed => 2000 Mips;
      Processor_Frequency => 2.0 Ghz;
  end MobileProcessor;

  processor ServerProcessor
    properties
      Processor_Speed => 10000 Mips;
      Processor_Frequency => 3.5 Ghz;
  end ServerProcessor;

  memory LocalStorage
    properties
      Memory_Size => 8192 MBytes;
      Access_Time => 10 us;
  end LocalStorage;

  -- ==============================================
  -- CONNECTEURS
  -- ==============================================

  data connection SyncConnection
    from MobileClient.DataSync to ContentSyncService.SyncTrigger
    properties
      Latency => 200 ms .. 500 ms;
      Data_Size => 1024 Bytes .. 51200 Bytes;
  end SyncConnection;

  data connection HTTPSecure
    from APIGateway.ExternalRequests to AuthenticationService.LoginRequest
    properties
      Latency => 50 ms .. 200 ms;
      Communication_Type => Synchronous;
      Security => TLS;
  end HTTPSecure;

  -- ==============================================
  -- PROPRIETES DE SECURITE
  -- ==============================================

  property group SecurityProperties for MobileClient
    Data_Encryption : constant aadlboolean => true applies to DataSync;
    Authentication_Required : constant aadlboolean => true applies to UserInterface;
    Secure_Storage : constant aadlboolean => true applies to LocalDatabase;
  end SecurityProperties;

  -- ==============================================
  -- CONFIGURATION SYSTEME
  -- ==============================================

  system Implementation
    components
      Mobile : process MobileClient;
      Sync : process ContentSyncService;
      API : process APIGateway;
      Auth : process AuthenticationService;

      PhoneCPU : processor MobileProcessor;
      ServerCPU : processor ServerProcessor;
      Cache : memory LocalStorage;

    connections
      SyncConn : data connection SyncConnection;
      APIConn : data connection HTTPSecure;

    properties
      Actual_Processor_Binding => reference MobileCPU applies to Mobile;
      Actual_Processor_Binding => reference ServerCPU applies to API;
      Actual_Memory_Binding => reference Cache applies to Sync;
  end Implementation;

end TouristGuideSystem;
```

#### 4.1.3 Evaluation pour le Système de Guide Touristique

| Exigence            | Evaluation | Analyse                                                                                                                                                                      |
| ------------------- | ---------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Scalabilité**     | ★★★★★      | AADL permet de modéliser les propriétés de performance (latence, débit, périodes) et de vérifier les contraintes de montée en charge. Support natif des systèmes distribués. |
| **Mode Offline**    | ★★★★★      | AADL est spécifiquement conçu pour les systèmes embarqués. Capacités natives de modélisation du comportement offline, tampons, synchronisation différée, gestion d'énergie.  |
| **Sécurité**        | ★★★★★      | AADL permet de spécifier les flux de données, les contraintes de sécurité au niveau architecture, les propriétés de chiffrement et d'authentification.                       |
| **Intégration API** | ★★★☆☆      | Support limité natif, mais extensible via propriétés personnalisées pour les appels API tiers.                                                                               |

---

### 4.2 ACME (Architecture Description Language)

#### 4.2.1 Fortesses pour le Système de Guide Touristique

**ACME** est particulièrement adapté pour ce système grâce à :

1. **Interoperabilite et interchange** : Facilite l'intégration avec différents outils et formats
2. **Representation semantique riche** : Communication aisée entre parties prenantes
3. **Outils de validation** : Vérification des contraintes architecturales
4. **Styles architecturaux** : Support natif des patterns common (client-server, layered, etc.)

#### 4.2.2 Exemple de Specification ACME

```acme
// ==============================================
// TYPES DE COMPOSANTS
// ==============================================

ComponentType MobileClient {
  ProvidedRole userInterface : Interface;
  ProvidedRole localCache : Interface;
  ProvidedRole locationService : Interface;
  RequiredRole cloudAPI : Interface;
  RequiredRole syncService : Interface;

  Property Type Supported_Offline : type enumeration {Full, Partial, None};
  Property Type Cache_Size : type integer 512 .. 8192 units MBytes;
  Property Type Encryption : type boolean;
}

ComponentType APIGateway {
  ProvidedRole restAPI : Interface;
  ProvidedRole authEndpoint : Interface;
  ProvidedRole webhooks : Interface;
  RequiredRole backendService : Interface;
  RequiredRole thirdPartyConnectors : list Interface;

  Property Type Rate_Limit : type integer 100 .. 1000000 units requests;
  Property Type Timeout : type integer 100 .. 30000 units ms;
}

ComponentType ContentServer {
  ProvidedRole contentDelivery : Interface;
  ProvidedRole mediaStreaming : Interface;
  RequiredRole database : Interface;
  RequiredRole cdnConnector : Interface;

  Property Type Max_Connections : type integer 1000 .. 100000;
  Property Type Cache_Policy : enumeration {LRU, LFU, FIFO};
}

ComponentType AuthenticationServer {
  ProvidedRole oauth2 : Interface;
  ProvidedRole tokenValidation : Interface;
  ProvidedRole userProfile : Interface;
  RequiredRole userDatabase : Interface;

  Property Type Auth_Method : enumeration {OAuth2, JWT, SAML};
  Property Type Session_Timeout : type integer 300 .. 86400 units sec;
}

// ==============================================
// TYPES DE CONNECTEURS
// ==============================================

ConnectorType HTTPSConnector {
  Role client : Interface;
  Role server : Interface;

  Property Type Protocol : enumeration {HTTP, HTTPS};
  Property Type Port : type integer 1 .. 65535;
  Property Type Max_Payload : type integer 1024 .. 10485760 units Bytes;

  Constraint Latency_Constraint : server.latency <= 200;
}

ConnectorType LocalSyncProtocol {
  Role source : Interface;
  Role target : Interface;

  Property Type Sync_Mode : enumeration {Full, Incremental};
  Property Type Conflict_Resolution : enumeration {Server_Wins, Client_Wins, Merge};
}

// ==============================================
// STYLES ARCHITECTURAUX
// ==============================================

ArchitecturalStyle LayeredStyle {
  ComponentType Layer1, Layer2, Layer3;
  ConnectorType LayerConnector;

  Role Layer_Interaction {
    upper : Interface;
    lower : Interface;
  }

  Rule Layering_Rule {
    Forbid Layer1 -> Layer3 via LayerConnector;
  }
}

ArchitecturalStyle ClientServerStyle {
  ComponentType Client, Server;
  ConnectorType RPC;

  Role Client_Endpoint { request : Interface; response : Interface; }
  Role Server_Endpoint { service : Interface; }

  Rule Client_Can_Only_Connect_To_Server {
    Forbid Client -> Client;
    Forbid Server -> Server;
  }
}

// ==============================================
// DEFINITIONS DES COMPOSANTS
// ==============================================

Component MobileApp : MobileClient {
  Provided userInterface {
    Protocol : HTTP;
    Port : 8080;
  }

  Property Supported_Offline => Partial;
  Property Cache_Size => 2048;
  Property Encryption => true;
}

Component BackendAPI : APIGateway {
  Provided restAPI {
    Protocol : HTTPS;
    Port : 443;
  }

  Property Rate_Limit => 10000;
  Property Timeout => 5000;
}

Component ContentDelivery : ContentServer {
  Provided contentDelivery {
    Protocol : HTTPS;
    Port : 443;
  }

  Property Max_Connections => 50000;
  Property Cache_Policy => LRU;
}

Component AuthService : AuthenticationServer {
  Provided oauth2 {
    Protocol : HTTPS;
    Port : 443;
  }

  Property Auth_Method => OAuth2;
  Property Session_Timeout => 3600;
}

// ==============================================
// DEFINITIONS DES CONNECTEURS
// ==============================================

Connector MobileToAPI : HTTPS {
  Client mobileApp : MobileClient.userInterface;
  Server backendAPI : APIGateway.restAPI;

  Protocol => HTTPS;
  Port => 443;
  Latency_Constraint;
}

Connector APIToContent : HTTPS {
  Client backendAPI : APIGateway.backendService;
  Server contentDelivery : ContentServer.contentDelivery;

  Protocol => HTTPS;
  Port => 443;
}

Connector MobileToAuth : HTTPS {
  Client mobileApp : MobileClient.cloudAPI;
  Server authService : AuthenticationServer.oauth2;

  Protocol => HTTPS;
  Port => 443;
}

// ==============================================
// CONFIGURATION SYSTEME
// ==============================================

System TouristGuideSystem {
  Components
    mobileApp : MobileApp;
    backendAPI : BackendAPI;
    contentDelivery : ContentDelivery;
    authService : AuthService;

  Connectors
    mobileToAPI : MobileToAPI;
    apiToContent : APIToContent;
    mobileToAuth : MobileToAuth;

  Constraints
    All data uses encryption;
    Authentication required for all external access;
    Offline mode uses encrypted local storage;
}

SystemType TouristGuideSystem : TouristGuideSystem;
```

#### 4.2.3 Evaluation pour le Système de Guide Touristique

| Exigence            | Evaluation | Analyse                                                                                                                                |
| ------------------- | ---------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| **Scalabilité**     | ★★★★☆      | ACME supporte les propriétés de rate limiting et connections. Cependant, ne supporte pas natively l'analyse de performance temps reel. |
| **Mode Offline**    | ★★☆☆☆      | Pas de support natif pourOffline behavior. Necessite Extensions via Proprietes personnalnees. Faible pour ce Use case.                 |
| **Sécurité**        | ★★★☆☆      | Les contraintes de sécurité peuvent être definies mais pas de support natif pour le chiffrement ou authentication.                     |
| **Intégration API** | ★★★★★      | Excellent support pour l'integration et l'interchange. Facilite l'integration avec differents services API tierces.                    |

---

### 4.3 xADL 2.0 (eXtensible Architecture Description Language)

#### 4.3.1 Fortesses pour le Système de Guide Touristique

**xADL 2.0** est particulièrement adapté pour ce système grâce à :

1. **Extensibilite XML** : Personnalisation facile pour les besoins specifiques du système
2. **Architectures dynamiques** : Support du runtime et des changements d'architecture
3. **Gestion de versions** : Suivi des evolutions architecturales
4. **Product line architectures** : Support des familles de produits

#### 4.3.2 Exemple de Specification xADL 2.0

```xml
<?xml version="1.0" encoding="UTF-8"?>
<xadl version="2.0" xmlns="http://www.ics.uci.edu/xADL/xadl2"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:guide="http://touristguide.org/xadl">

  <!-- ============================================== -->
  <!-- DEFINITIONS DES TYPES -->
  <!-- ============================================== -->

  <TypeSystems>
    <!-- Type de composant client mobile -->
    <ComponentType name="MobileClient">
      <InterfaceDefs>
        <Interface name="ui" type="userInterface"/>
        <Interface name="sync" type="dataSync"/>
        <Interface name="location" type="geoLocation"/>
      </InterfaceDefs>
      <PropertyDef name="offlineSupported" type="boolean" cardinality="1"/>
      <PropertyDef name="cacheSize" type="integer" cardinality="1"/>
      <PropertyDef name="encryption" type="boolean" cardinality="1"/>
    </ComponentType>

    <!-- Type de composant serveur -->
    <ComponentType name="ContentServer">
      <InterfaceDefs>
        <Interface name="api" type="restAPI"/>
        <Interface name="internal" type="internalBus"/>
      </InterfaceDefs>
      <PropertyDef name="maxConnections" type="integer" cardinality="1"/>
      <PropertyDef name="cachePolicy" type="string" cardinality="1"/>
    </ComponentType>

    <!-- Type de connecteur -->
    <ConnectorType name="SecureHTTP">
      <RoleDef name="client" type="requestResponse"/>
      <RoleDef name="server" type="requestResponse"/>
      <PropertyDef name="protocol" type="string" cardinality="1"/>
      <PropertyDef name="security" type="string" cardinality="1"/>
      <PropertyDef name="maxLatency" type="integer" cardinality="1"/>
    </ConnectorType>
  </TypeSystems>

  <!-- ============================================== -->
  <!-- DEFINITIONS DES COMPOSANTS -->
  <!-- ============================================== -->

  <Components>
    <Component name="TouristGuideApp" type="MobileClient">
      <Property name="offlineSupported">true</Property>
      <Property name="cacheSize">2048</Property>
      <Property name="encryption">true</Property>
      <InstanceProperties>
        <Property name="platform">iOS</Property>
        <Property name="version">2.0.0</Property>
      </InstanceProperties>
    </Component>

    <Component name="APIGateway" type="ContentServer">
      <Property name="maxConnections">50000</Property>
      <Property name="cachePolicy">LRU</Property>
    </Component>

    <Component name="ContentService" type="ContentServer">
      <Property name="maxConnections">100000</Property>
      <Property name="cachePolicy">LRU</Property>
    </Component>

    <Component name="AuthService" type="ContentServer">
      <Property name="maxConnections">10000</Property>
      <Property name="cachePolicy">FIFO</Property>
    </Component>
  </Components>

  <!-- ============================================== -->
  <!-- DEFINITIONS DES CONNECTEURS -->
  <!-- ============================================== -->

  <Connectors>
    <Connector name="AppToAPI" type="SecureHTTP">
      <Role name="client" interface="TouristGuideApp.ui"/>
      <Role name="server" interface="APIGateway.api"/>
      <Property name="protocol">HTTPS</Property>
      <Property name="security">TLS1.3</Property>
      <Property name="maxLatency">500</Property>
    </Connector>

    <Connector name="APIToContent" type="SecureHTTP">
      <Role name="client" interface="APIGateway.internal"/>
      <Role name="server" interface="ContentService.api"/>
      <Property name="protocol">HTTPS</Property>
      <Property name="security">TLS1.3</Property>
      <Property name="maxLatency">200</Property>
    </Connector>
  </Connectors>

  <!-- ============================================== -->
  <!-- ARCHITECTURE SYSTEME -->
  <!-- ============================================== -->

  <Architecture id="TouristGuideSystem">
    <ComponentInstances>
      <Instance component="TouristGuideApp"/>
      <Instance component="APIGateway"/>
      <Instance component="ContentService"/>
      <Instance component="AuthService"/>
    </ComponentInstances>

    <ConnectorInstances>
      <Instance connector="AppToAPI"/>
      <Instance connector="APIToContent"/>
    </ConnectorInstances>

    <Properties>
      <Property name="scalability">
        <guide:maxUsers>1000000</guide:maxUsers>
        <guide:concurrentConnections>100000</guide:concurrentConnections>
      </Property>
      <Property name="security">
        <guide:encryption>required</guide:encryption>
        <guide:authRequired>true</guide:authRequired>
      </Property>
      <Property name="offline">
        <guide:syncOnReconnect>true</guide:syncOnReconnect>
        <guide:localStorage>SQLite</guide:localStorage>
      </Property>
    </Properties>
  </Architecture>

  <!-- ============================================== -->
  <!-- VARIANTES ARCHITECTURALES -->
  <!-- ============================================== -->

  <Variants>
    <Variant name="DesktopWeb">
      <Architecture>
        <ComponentInstances>
          <Instance component="APIGateway"/>
          <Instance component="ContentService"/>
        </ComponentInstances>
      </Architecture>
    </Variant>

    <Variant name="MobileOffline">
      <Architecture>
        <ComponentInstances>
          <Instance component="TouristGuideApp"/>
        </ComponentInstances>
        <Properties>
          <Property name="mode">offline</Property>
        </Properties>
      </Architecture>
    </Variant>
  </Variants>

</xadl>
```

#### 4.3.3 Evaluation pour le Système de Guide Touristique

| Exigence            | Evaluation | Analyse                                                                                                                                       |
| ------------------- | ---------- | --------------------------------------------------------------------------------------------------------------------------------------------- |
| **Scalabilité**     | ★★★★★      | Excellent support via les propriétés configurables et les variantes architecturales. Permet de modeliser pour differentes Echelles de charge. |
| **Mode Offline**    | ★★★★☆      | Tres flexible grace aux extensions XML. Permet de definr les comportements offline et sync.                                                   |
| **Sécurité**        | ★★★★☆      | Support flexible des proprietes de securite (encryption, TLS, auth). Extensible pour nouveaux besoins.                                        |
| **Intégration API** | ★★★★★      | Excellent pour l'integration avec les API tierces via les connecteurs personalises. Format XML facilite l'interopérabilité.                   |

---

### 4.4 Rapide (Rapid Architectural Prototyping and Interactive Design)

#### 4.4.1 Fortesses pour le Système de Guide Touristique

**Rapide** est particulièrement adapté pour ce système grâce à :

1. **Simulation temporelle** : Analyse du comportement dynamique du système
2. **Verification formelle** : Verification des propriétés architecturales
3. **Event patterns** : Modelisation des flux d'evenements complexes

#### 4.4.2 Exemple de Specification Rapide

```
-- ==============================================
-- SYSTEME: TouristGuideSystem
-- ==============================================

-- Definitions des types de composants
component type MobileClient
  public
    in event ui_request: Request_Type
    out event ui_response: Response_Type
    in event sync_trigger: Sync_Type
    out event sync_complete: Sync_Type
    in event location: Location_Type

component type ContentServer
  public
    in event content_request: ContentRequest_Type
    out event content_response: ContentResponse_Type

component type AuthServer
  public
    in event login: Login_Type
    out event token: Token_Type

-- Definitions des connecteurs
connector type HTTPConnection
  roles client, server

connector type EventBus
  roles subscriber, publisher

-- Architecture du systeme
architecture TouristGuideSystem
  component MobileClient mobile_app
  component ContentServer content_srv
  component AuthServer auth_srv

  connection HTTPConnection conn1
    client interface mobile_app.ui_request
    server interface content_srv.content_request

  connection HTTPConnection conn2
    client interface mobile_app.ui_request
    server interface auth_srv.login

  connection EventBus sync_bus
    subscriber interface mobile_app.sync_complete
    publisher interface content_srv.content_response

-- Contraintes temporelles
timing constraint sync_latency
  for conn2
  latency <= 500 ms

timing constraint auth_latency
  for conn2
  latency <= 200 ms
```

#### 4.4.3 Evaluation pour le Système de Guide Touristique

| Exigence            | Evaluation | Analyse                                                                                                         |
| ------------------- | ---------- | --------------------------------------------------------------------------------------------------------------- |
| **Scalabilité**     | ★★★☆☆      | Support des analyses temporelles et simulation, mais limite pour les grandes échelles. Outils d'analyse limits. |
| **Mode Offline**    | ★★☆☆☆      | Pas de support natif pourOffline behavior.                                                                      |
| **Sécurité**        | ★★☆☆☆      | Pas de support specifique pour la securité. Analyse event-driven possible mais basique.                         |
| **Intégration API** | ★★★☆☆      | Modelisation possible mais limit. Pas d'integration native.                                                     |

---

### 4.5 C2 (Component and Connector)

#### 4.5.1 Fortesses pour le Système de Guide Touristique

**C2** est particulièrement adapté pour ce système grâce à :

1. **Style evenementiel** : Ideal pour les interfaces reactives
2. **Message-based communication** : Bon pour les applications mobiles
3. **Layered architecture** : Facilite la separation des couches

#### 4.5.2 Exemple de Specification C2

```
-- ==============================================
-- SYSTEME: TouristGuide (C2 ADL)
-- ==============================================

-- Definitions des composants
component TourGuideApp
  provides message userInterface
  provides message localStorage
  requires message cloudAPI
  requires message syncService

  service receive userInterface
    case request.type
      "VIEW_INFO": send contentResponse to userInterface
      "SYNC": send syncRequest to syncService
    end

  service receive cloudAPI
    case response.type
      "CONTENT": store to localStorage
    end

component ContentService
  provides message contentAPI
  requires message database

component AuthService
  provides message authAPI
  requires message userDatabase

-- Definitions des connections
connection MobileToContent
  from TourGuideApp.cloudAPI
  to ContentService.contentAPI
  order FIFO

connection MobileToAuth
  from TourGuideApp.cloudAPI
  to AuthService.authAPI
  order FIFO

-- Definitions topologiques
topology distributed
  layer 1 TourGuideApp
  layer 2 ContentService, AuthService

  connector top_layer to layer 2 via MobileToContent
  connector top_layer to layer 2 via MobileToAuth

  constraint no_circular_dependencies
```

#### 4.5.3 Evaluation pour le Système de Guide Touristique

| Exigence            | Evaluation | Analyse                                                                               |
| ------------------- | ---------- | ------------------------------------------------------------------------------------- |
| **Scalabilité**     | ★★☆☆☆      | Limite pour les grandes échelles. Better pour les applications de taille moyenne.     |
| **Mode Offline**    | ★★★☆☆      | Support event-based mais pas de modelisation specifique offline.                      |
| **Sécurité**        | ★★★☆☆      | Pas de support natif. Peut etre implémenté via les messages mais pas de verification. |
| **Intégration API** | ★★★★☆      | Bien adapté pour les integration API via le style message-based.                      |

---

## 5. Synthese et Recommandations

### 5.1 Tableau Récapitulatif Final

| Critère          | AADL      | ACME      | xADL 2.0  | Rapide   | C2        |
| ---------------- | --------- | --------- | --------- | -------- | --------- |
| Scalabilité      | ★★★★★     | ★★★★☆     | ★★★★★     | ★★★☆☆    | ★★☆☆☆     |
| Mode Offline     | ★★★★★     | ★★☆☆☆     | ★★★★☆     | ★★☆☆☆    | ★★★☆☆     |
| Sécurité         | ★★★★★     | ★★★☆☆     | ★★★★☆     | ★★☆☆☆    | ★★★☆☆     |
| Intégration API  | ★★★☆☆     | ★★★★★     | ★★★★★     | ★★★☆☆    | ★★★★☆     |
| **Note Globale** | **19/20** | **13/20** | **18/20** | **9/20** | **11/20** |

### 5.2 Recommandations par Exigence

#### Pour la Scalabilité :

- **Recommandé : AADL** - Analyse formelle des performances et contraintes de charge
- **Alternative : xADL 2.0** - Flexibilité pour les variantes architecturales

#### Pour le Mode Offline :

- **Recommandé : AADL** - Support natif des systèmes embarqués etOffline
- **Alternative : xADL 2.0** - Extensibilité pour définir les comportementsOffline

#### Pour la Sécurité :

- **Recommandé : AADL** - Modélisation formelle des contraintes de sécurité
- **Alternative : xADL 2.0** - Propriétés de sécurité extensibles

#### Pour l'Intégration API :

- **Recommandé : ACME** - Interoperabilité et interchange
- **Alternative : xADL 2.0** - Format XML facilite l'intégration

### 5.3 Recommandation Globale

Pour un système de guide touristique complet satisfaisant toutes les exigences, **xADL 2.0** représente le meilleur compromis grâce à :

- Son extensibilité pour adapter le langage aux besoins spécifiques
- Son support pour les architectures dynamiques etOffline
- Son format XML facilitant l'intégration avec les API tierces
- Sa capacité de gérer les variantes architecturales

**AADL** est recommandé si la priorité est donnée à :

- L'analyse formelle des performances temps reel
- La modélisation des systèmes embarqués
- La verification automatique des contraintes

---

## 6. Conclusion

Cette analyse démontre que chaque ADL possède des forces distinctes face aux exigences du système de guide touristique. Le choix de l'ADL dépendra des priorité spécifiques du projet :

| Priorité Principale         | ADL Recommandé |
| --------------------------- | -------------- |
| Offline + Temps Réel        | **AADL**       |
| Intégration API             | **ACME**       |
| Flexibilité + Extensibilité | **xADL 2.0**   |
| Simulation + Analyse        | **Rapide**     |
| Interfaces Réactives        | **C2**         |

Pour une modélisation complète et professionnelle du système, une approche hybride utilisant plusieurs ADLs serait également envisageable, exploitant les forces de chacun pour les différentes phases du projet.

---

## Références

1. ACME Studio - Software Engineering Institute, CMU
2. AADL - SAE Standard AS5506
3. xADL 2.0 - University of California, Irvine
4. Rapide - Stanford University
5. C2 - University of California, Irvine
6. ISO/IEC/IEEE 42010 - Architecture Description Standard
7. Documentation technique 2026 sur les systèmes de guide touristique et architectures offline-first
