package com.ramapo.opl.joegramuglia.canoga.model;

import com.ramapo.opl.joegramuglia.canoga.model.Board;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by JoeGramuglia on 11/9/2015.
 */
public abstract class Player implements Serializable {

    // Determines if the player can uncover squares
    private boolean allowUncover;

    // True if the player is using their board
    private boolean myBoardFlag;

    // The move the player wants to make
    private Stack<Integer> combination;

    /**
     * Creates a new default player
     */
    public Player() {
        allowUncover = false;
        myBoardFlag = false;
        combination = new Stack<Integer>();
    }

    /**
     * Gets if the player is allowed to uncover
     * @return - boolean, true if the player is allowed to uncover
     */
    public boolean getAllowUncover() { return allowUncover; }

    /**
     * Set if the player is allowed to uncover
     * @param allowUncover - boolean, true if the player is allowed to uncover
     */
    public void setAllowUncover(boolean allowUncover) { this.allowUncover = allowUncover; }

    /**
     * Gets if the player wants to use their board
     * @return - boolean, true if the player wants to use their board
     */
    public boolean getMyBoardFlag() { return myBoardFlag; }

    /**
     * Set if the player wants to use their board
     * @param myBoardFlag - boolean, True if the player wants to use their board
     */
    public void setMyBoardFlag(boolean myBoardFlag) { this.myBoardFlag = myBoardFlag; }

    /**
     * Gets the series of square the player wants to use
     * @return - a stack of integers, The squares the player wants to use
     */
    public Stack<Integer> getCombination() { return combination; }

    /**
     * Set the series of squares the player wants to use
     * @param combination - a stack of integers, The series of squares
     */
    public void setCombination(Stack<Integer> combination) { this.combination = combination; }

    /**
     * Determines the best move the player can make, Sets the myBoardFlag and the combination
     * @param myBoard - a board, The players board
     * @param theirBoard - a board, The opponents board
     * @param rollTotal - int, The number of pips the player rolled
     * @return - String, The logic behind why computer made this move
     */
    public String makeBestMove(Board myBoard, Board theirBoard, int rollTotal) {
        String logic = "";
        if (getSum(myBoard, true) >= getSum(theirBoard, false)) {
            myBoardFlag = true;
            int size = myBoard.getSize();
            combination = findBestMove(myBoard, false, rollTotal, new Stack<Integer>());
            myBoard.setSize(size);
            logic += "More points by covering.\n";
            logic += "Covering...\n";
            if (combination.empty()) {
                logic += "Can't cover.\n";
                myBoardFlag = false;
                size = theirBoard.getSize();
                combination = findBestMove(theirBoard, true, rollTotal, new Stack<Integer>());
                theirBoard.setSize(size);
                logic += "Uncovering...\n";
            }
        } else {
            logic += "More points by uncovering.\n";
            logic += "Uncovering...\n";
            myBoardFlag = false;
            int size = theirBoard.getSize();
            combination = findBestMove(theirBoard, true, rollTotal, new Stack<Integer>());
            theirBoard.setSize(size);
            if (combination.empty()) {
                logic += "Can't uncover.\n";
                myBoardFlag = true;
                size = myBoard.getSize();
                combination = findBestMove(myBoard, false, rollTotal, new Stack<Integer>());
                myBoard.setSize(size);
                logic += "Covering...\n";
            }
        }
        return logic;
    }

    /**
     * Finds the best move
     * @param board - a board, The board to find the best move on
     * @param cover - boolean, Determines if the move should cover or uncover
     * @param rollTotal - int, The number of pips rolled
     * @param combination - a stack of integers, The current combination of squares
     * @return - a stack of integers, The best combination of squares
     */
    private Stack<Integer> findBestMove(Board board, boolean cover, int rollTotal, Stack<Integer> combination) {
        if (getSum(combination) == rollTotal) {
            return combination;
        } else if (getSum(combination) > rollTotal) {
            int newSize = combination.pop();
            board.setSize(newSize);
        } else { // getSum(combination) < rollTotal
            if (board.getSize() == 0) {
                if (combination.size() == 0) {
                    return new Stack<Integer>();
                } else {
                    board.setSize(combination.lastElement());
                    return findBestMove(board, cover, rollTotal, new Stack<Integer>());
                }
            } else {
                if (board.isCovered(board.getSize() - 1) == cover) {
                    combination.push(board.getSize() - 1);
                }
                board.setSize(board.getSize() - 1);
            }
        }
        return findBestMove(board, cover, rollTotal, combination);
    }

    /**
     * Get the sum of certain squares on a board
     * @param board - a board, The board to sum up
     * @param cover - boolean, To sum the covered or uncovered squares
     * @return - int, The sum of the specified squares
     */
    protected int getSum(Board board, boolean cover) {
        int sum = 0;
        for (int i = 0; i < board.getSize(); i++) {
            if (board.isCovered(i) == cover) {
                sum += i + 1;
            }
        }
        return sum;
    }

    /**
     * Get the sum of a combination of squares
     * @param combination - a stack of integers, A series of squares
     * @return - int, The sum of the given squares
     */
    protected int getSum(Stack<Integer> combination) {
        int sum = 0;
        for (int i = 0; i < combination.size(); i++) {
            sum += combination.get(i) + 1;
        }
        return sum;
    }

    /**
     * Used to make the players move
     * @param myBoard - a board, The players board
     * @param theirBoard - a board, The opponents board
     * @param rollTotal - int, The number of pips rolled
     * @param combination - a stack of integers, A series of squares
     * @param myBoardFlag - boolean, The board to use
     * @return - boolean, If the move was successful
     */
    public abstract boolean makeMove(Board myBoard, Board theirBoard, int rollTotal, Stack<Integer> combination, boolean myBoardFlag);
}

