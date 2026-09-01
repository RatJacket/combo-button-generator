package com.generation;

public enum Button {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R"),
    LIGHT("1"),
    MEDIUM("2"),
    HEAVY("3"),
    ASSEMBLE("4");

    private final String input;

    Button(final String input) {

        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
