# 1. Enregistrer les décisions d'architecture

- Date : 08-10-2026
- Statut : Accepté

## Contexte
Nous avons besoin de garder une trace des décisions structurantes du projet
pour comprendre plus tard pourquoi elles ont été prises.

## Décision
Nous utilisons des Architecture Decision Records (ADR) légers, stockés dans
`docs/adr/`, numérotés séquentiellement (`NNNN-titre.md`).
Format : Contexte, Décision, Conséquences. Un ADR n'est jamais supprimé :
il est marqué « Remplacé par ADR-XXXX ».

## Conséquences
Chaque décision structurante demande 10 à 20 lignes de rédaction, en échange
d'un historique consultable dans le dépôt.