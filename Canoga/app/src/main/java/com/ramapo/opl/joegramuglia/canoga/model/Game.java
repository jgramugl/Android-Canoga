package com.ramapo.opl.joegramuglia.canoga.model;

import java.io.Serializable;
import java.util.Stack;

public class Game implements Serializable {

    private Human human;
    private Computer computer;
    private Board humanBoard;
    private Board computerBoard;
    private boolean firstTurnHuman;
    private boolean nextTurnHuman;
    private int humanScore;
    private int computerScore;

    /**
     * Creates a new game
     * @param size - int, The size of the boards
     * @param nextTurnHuman - boolean, Determines if the human goes next
     */
    public Game(int size, boolean nextTurnHuman) {
        human = new Human();
        computer = new Computer();
        humanBoard = new Board(size);
        computerBoard = new Board(size);
        this.firstTurnHuman = nextTurnHuman;
        this.nextTurnHuman = nextTurnHuman;
        humanScore = 0;
        computerScore = 0;
    }

    /**
     * Creates a handicapped game
     * @param humanBoard - a board, The human's board
     * @param computerBoard - a board, The computer's board
     * @param firstTurnHuman - boolean, Determines if the human goes first
     */
    public Game(Board humanBoard, Board computerBoard, boolean firstTurnHuman) {
        human = new Human();
        human.setAllowUncover(false);
        computer = new Computer();
        computer.setAllowUncover(false);
        this.humanBoard = humanBoard;
        this.computerBoard = computerBoard;
        this.firstTurnHuman = firstTurnHuman;
        this.nextTurnHuman = firstTurnHuman;
        humanScore = 0;
        computerScore = 0;
    }

    /**
     * Recreates a game that has already begun
     * @param humanBoard - a board, The human's board
     * @param computerBoard - a board, The computer's board
     * @param firstTurnHuman - boolean, Determines if the human went first
     * @param nextTurnHuman - boolean, Determines if the human goes next
     */
    public Game(Board humanBoard, Board computerBoard, boolean firstTurnHuman, boolean nextTurnHuman) {
        human = new Human();
        human.setAllowUncover(true);
        computer = new Computer();
        computer.setAllowUncover(true);
        this.humanBoard = humanBoard;
        this.computerBoard = computerBoard;
        this.firstTurnHuman = firstTurnHuman;
        this.nextTurnHuman = nextTurnHuman;
        humanScore = 0;
        computerScore = 0;
    }

    /**
     * Takes the human's turn
     * @param rollTotal - boolean, The number of pips rolled
     * @param combination - a stack of integers, A series of squares
     * @param myBoardFlag - boolean, Determines if the human is using their board or the opponents
     * @return - boolean, Determines if the move was successful
     */
    public boolean takeHumanTurn(int rollTotal, Stack<Integer> combination, boolean myBoardFlag) {
        nextTurnHuman = false;
        return human.makeMove(humanBoard, computerBoard, rollTotal, combination, myBoardFlag);
    }

    /**
     * Gets the series of squares the human wants to use
     * @return - The series of squares
     */
    public Stack<Integer> getHumanCombination() {
        return human.getCombination();
    }

    /**
     * Gets if the human wants to use their board
     * @return - boolean, Determines if the human's move is on their board
     */
    public boolean getHumanBoardFlag() {
        return human.getMyBoardFlag();
    }

    /**
     * Cover a series of square on the human's board
     * @param combination - a stack of integers, The series of squares
     */
    public void coverHumanBoard(Stack<Integer> combination) {
        for (int i = 0; i < combination.size(); i++) {
            humanBoard.cover(combination.get(i));
        }
    }

    /**
     * Uncover a series of squares on the human's board
     * @param combination - a stack of integers, The series of squares
     */
    public void uncoverHumanBoard(Stack<Integer> combination) {
        for (int i = 0; i < combination.size(); i++) {
            humanBoard.uncover(combination.get(i));
        }
    }

    /**
     * Take the computer's turn
     * @param rollTotal - int, The number of pips the computer rolled
     * @return - boolean, Determines if the move was successful
     */
    public boolean takeComputerTurn(int rollTotal) {
        nextTurnHuman = true;
        return computer.makeMove(computerBoard, humanBoard, rollTotal, new Stack<Integer>(), false);
    }

    /**
     * Gets the series of square the computer wants to use
     * @return - The series of squares the computer wants to use
     */
    public Stack<Integer> getComputerCombination() {
        return computer.getCombination();
    }

    /**
     * Determines if the computer wants to use their board
     * @return - Determines if the computer wants to use their board
     */
    public boolean getComputerBoardFlag() {
        return computer.getMyBoardFlag();
    }

    /**
     * Cover a series of squares on the computer's board
     * @param combination - The series of squares to cover
     */
    public void coverComputerBoard(Stack<Integer> combination) {
        for (int i = 0; i < combination.size(); i++) {
            computerBoard.cover(combination.get(i));
        }
    }

    /**
     * Uncover a series of squares on the computer's board
     * @param combination - The series of square to uncover
     */
    public void uncoverComputerBoard(Stack<Integer> combination) {
        for (int i = 0; i < combination.size(); i++) {
            computerBoard.uncover(combination.get(i));
        }
    }

    /**
     * Get the human's board
     * @return - a board, The human's board
     */
    public Board getHumanBoard() { return humanBoard; }

    /**
     * Get the computer's board
     * @return - a board, The computer's board
     */
    public Board getComputerBoard() { return computerBoard; }

    /**
     * Determines if the human went first
     * @return - boolean, True if the human went first
     */
    public boolean isFirstTurnHuman() {
        return firstTurnHuman;
    }

    /**
     * Determines if the human goes next
     * @return - boolean, True if the human goes next
     */
    public boolean isNextTurnHuman() {
        return nextTurnHuman;
    }

    /**
     * Gets the human playing the game
     * @return - a human, The human playing the game
     */
    public Human getHuman() {return human; }

    /**
     * Gets the computer playing the game
     * @return a human, The computer playing the game
     */
    public Computer getComputer() { return computer; }

    /**
     * Allows the players to uncover
     */
    public void allowUncover() {
        human.setAllowUncover(true);
        computer.setAllowUncover(true);
    }

    /**
     * Calculates the scores of each player
     */
    public void calcScores() {
        if (humanBoard.isAllCovered()) {
            humanScore = human.getSum(computerBoard, false);
            computerScore = 0;
        } else if (computerBoard.isAllUncovered()) {
            humanScore = human.getSum(humanBoard, true);
            computerScore = 0;
        } else if (humanBoard.isAllUncovered()) {
            humanScore = 0;
            computerScore = computer.getSum(computerBoard, true);
        } else {
            humanScore = 0;
            computerScore = computer.getSum(humanBoard, false);
        }
    }

    /**
     * Get the human's score
     * @return - int, The human's score
     */
    public int getHumanScore() {
        return humanScore;
    }

    /**
     * Get the computer's score
     * @return - int, The computer's score
     */
    public int getComputerScore() {
        return computerScore;
    }
}
