package com.michaelyvars.guacamole.data;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@Getter
public enum GameState {
    WAITING(Component.text("En attente des joueurs", NamedTextColor.GREEN), new Component[]{
            Component.empty(),
            Component.text("<gray>---<red> Partie <gray>---"),
            Component.text("<gray>• <green>Lancement: <white><game_time:starting>s"),
            Component.empty(),
            Component.text("<gray>• <green>Joueurs: <white><game_count:players>"),
            Component.text("<gray>• <green>Équipes: <white><game_count:teams>"),
            Component.empty(),
            Component.text("<gray>---<red> <player_name> <gray>---"),
            Component.text("<gray>• <green>Équipe: <white><player_team>"),
            Component.empty(),
            Component.text("<yellow>www.michaelyvars.com")
    }),
    STARTING(Component.text("Lancement de la partie", NamedTextColor.YELLOW), new Component[]{
            Component.empty(),
            Component.text("<gray>---<red> Partie <gray>---"),
            Component.text("<gray>• <green>Lancement: <white><game_time:starting>s"),
            Component.empty(),
            Component.text("<gray>• <green>Joueurs: <white><game_count:players>"),
            Component.text("<gray>• <green>Équipes: <white><game_count:teams>"),
            Component.empty(),
            Component.text("<gray>---<red> <player_name> <gray>---"),
            Component.text("<gray>• <green>Équipe: <white><player_team>"),
            Component.empty(),
            Component.text("<yellow>www.michaelyvars.com")
    }),
    IN_GAME(Component.text("En cours de jeu", NamedTextColor.GOLD), new Component[]{
            Component.empty(),
            Component.text("<gray>---<red> Partie <gray>---"),
            Component.text("<gray>• <green>Temps: <white><game_time>"),
            Component.text("<gray>• <green>Épisode: <white><game_time:episode>"),
            Component.empty(),
            Component.text("<gray>• <green>Joueurs: <white><game_count:players>"),
            Component.text("<gray>• <green>Équipes: <white><game_count:teams>"),
            Component.empty(),
            Component.text("<gray>---<red> <player_name> <gray>---"),
            Component.text("<gray>• <green>Kills: <red><player_statistic:PLAYERS_KILLED>"),
            Component.text("<gray>• <green>Équipe: <white><player_team>"),
            Component.empty(),
            Component.text("<yellow>www.michaelyvars.com")
    }),
    ENDING(Component.text("Fin de la partie", NamedTextColor.RED), new Component[]{
            Component.empty(),
            Component.text("<gray>---<red> Partie <gray>---"),
            Component.text("<gray>• <green>Temps: <white><game_time>"),
            Component.text("<gray>• <green>Épisode: <white><game_time:episode>"),
            Component.empty(),
            Component.text("<gray>• <green>Joueurs: <white><game_count:players>"),
            Component.text("<gray>• <green>Équipes: <white><game_count:teams>"),
            Component.empty(),
            Component.text("<gray>---<red> <player_name> <gray>---"),
            Component.text("<gray>• <green>Kills: <red><player_statistic:PLAYERS_KILLED>"),
            Component.text("<gray>• <green>Équipe: <white><player_team>"),
            Component.empty(),
            Component.text("<yellow>www.michaelyvars.com")
    });

    private final Component name;
    private final Component[] lines;

    GameState(Component name, Component[] lines) {
        this.name = name;
        this.lines = lines;
    }
}