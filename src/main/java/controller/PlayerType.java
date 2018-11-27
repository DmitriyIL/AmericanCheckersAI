package controller;

import ai.ComputerPlayer;
import model.HumanPlayer;
import model.Player;

import java.util.Arrays;

public enum PlayerType {
    HUMAN("Human") {
        @Override
        Player getPlayer() {
            return new HumanPlayer();
        }
    },

    COMPUTER("Computer") {
        @Override
        Player getPlayer() {
            return new ComputerPlayer();
        }
    };

    String typeName;

    PlayerType(String type) {
        typeName = type;
    }

    abstract Player getPlayer();

    public static String[] getStrValues() {
        Object[] typeNames = Arrays.stream(values()).map(e -> e.typeName).toArray(String[]::new);
        return (String[]) typeNames;
    }

    public static Player getPlayerFromTypeName(String typeName) {
        for (PlayerType playerType : values()) {
            if (typeName.equals(playerType.typeName)) {
                return playerType.getPlayer();
            }
        }
        throw new IllegalArgumentException("there is no type with such name");
    }
}
