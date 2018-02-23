package com.zzming.chess.message;

import java.io.Serializable;
import java.util.List;

import com.zzming.chess.entity.Player;

public class PlayersMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Player> players;

    public PlayersMessage(List<Player> players) {
        super();
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

}
