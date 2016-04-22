package com.ramapo.opl.joegramuglia.canoga.model;

import java.io.Serializable;
import java.util.Random;
import java.util.Stack;

/**
 * Created by JoeGramuglia on 11/9/2015.
 */
public class Tournament implements Serializable {

    private int humanScore;
    private int computerScore;
    private Game game;
    private Stack<Integer> rolls;

    /**
     * Creates a new tournament
     */
    public Tournament() {
        humanScore = 0;
        computerScore = 0;
        rolls = new Stack<Integer>();
    }

    /**
     * Recreates a tournament
     * @param humanBoard - a board, The human's board
     * @param humanScore - int, The human's score
     * @param computerBoard - a board, The computer's board
     * @param computerScore - int, The computer's score
     * @param firstTurnHuman - boolean, Determines if the human went first
     * @param nextTurnHuman - boolean, Determines if the human goes next
     */
    public Tournament(Board humanBoard, int humanScore, Board computerBoard, int computerScore, boolean firstTurnHuman, boolean nextTurnHuman) {
        this.humanScore = humanScore;
        this.computerScore = computerScore;
        game = new Game(humanBoard, computerBoard, firstTurnHuman, nextTurnHuman);
        rolls = new Stack<Integer>();
    }

    /**
     * Get the human's score
     * @return - int, The human's score
     */
    public int getHumanScore() { return humanScore; }

    /**
     * Set the human's score
     * @param humanScore - int, The human's score
     */
    public void setHumanScore(int humanScore) { this.humanScore = humanScore; }

    /**
     * Get the computer's score
     * @return - int, The computer's score
     */
    public int getComputerScore() {
        return computerScore;
    }

    /**
     * Set the computer's score
     * @param computerScore - int, The computer's score
     */
    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }

    /**
     * Set the dice rolls
     * @param rolls - a stack of integers, The dice rolls
     */
    public void setRolls(Stack<Integer> rolls) {
        this.rolls = rolls;
    }

    /**
     * Roll a dice
     * @return - int, The number of pips rolled
     */
    public int rollDie() {
        int roll;
        if (!rolls.isEmpty()) {
            roll = rolls.pop();
        }
        else {
            Random random = new Random();
            roll = random.nextInt(6)+1;
        }
        return roll;
    }

    /**
     * Create a new game
     * @param size - int, The size of the board
     * @param humanTurn - boolean, Determines if the human goes first
     */
    public void newGame(int size, boolean humanTurn) {
        game = new Game(size, humanTurn);
    }

    /**
     * Create a game with a handicap
     * @param humanBoard - a board, The human's board
     * @param computerBoard - a board, The computer's board
     * @param firstTurnHuman - boolean, Determins if the human goes first
     */
    public void handicapGame(Board humanBoard, Board computerBoard, boolean firstTurnHuman) {
        game = new Game(humanBoard, computerBoard, firstTurnHuman);
    }

    /**
     * Get the game
     * @return - a game, The current game
     */
    public Game getGame() { return game; }
}
