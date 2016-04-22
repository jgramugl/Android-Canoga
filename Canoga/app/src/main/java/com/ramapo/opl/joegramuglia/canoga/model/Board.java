package com.ramapo.opl.joegramuglia.canoga.model;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by JoeGramuglia on 11/9/2015.
 */
public class Board implements Serializable {

    private Vector<Boolean> board;
    private int size;

    /**
     * Creates an empty board of a given size
     * @param size - int, the size of the board
     */
    public Board(int size) {
        board = new Vector<Boolean>(size);
        for (int i = 0; i < size; i++) {
            board.add(false);
        }
        this.size = size;
    }

    /**
     * Recreates a board
     * @param board - int, a board
     */
    public Board(Vector<Boolean> board) {
        this.board = board;
        this.size = board.size();
    }

    /**
     * Set the size of the board
     * @param size - int, the size of the board
     */
    public void setSize(int size) { this.size = size; }

    /**
     * Get the size of the board
     * @return - int, the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Cover a square
     * @param square - int, the square to cover
     */
    public void cover(int square) { board.setElementAt(true, square); }

    /**
     * Uncover a square
     * @param square - int, the square to uncover
     */
    public void uncover(int square) {
        board.setElementAt(false, square);
    }

    /**
     * Check if a square is covered
     * @param square - int, the square to check
     * @return - boolean, true if covered
     */
    public boolean isCovered(int square) {
        return board.get(square);
    }

    /**
     * Check if all the squares are covered
     * @return - boolean, true if all the squares are covered
     */
    public boolean AllCovered() {
        for(int i = 0; i < board.size(); i++) {
            if (board.get(i) == false) { return false; }
        }
        return true;
    }

    /**
     * Check if all the squares are uncovered
     * @return - boolean, true if all the squares are uncovered
     */
    public boolean AllUncovered() {
        for(int i = 0; i < board.size(); i++) {
            if (board.get(i) == true) { return false; }
        }
        return true;
    }

    /**
     * Check if squares 7-n are covered
     * @return - boolean, true if 7-n are covered
     */
    public boolean is7UpCovered() {
        for (int i = 6; i < board.size(); i++) {
            if (board.get(i) == false) { return false; }
        }
        return true;
    }

    /**
     * Check if squares 7-n are uncovered
     * @return - boolean, true if 7-n are uncovered
     */
    public boolean is7UpUncovered() {
        for (int i = 6; i < board.size(); i++) {
            if (board.get(i) == true) { return false; }
        }
        return true;
    }

    /**
     * Check if every square is covered
     * @return - boolean, true if all squares are covered
     */
    public boolean isAllCovered() {
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) == false) { return false; }
        }
        return true;
    }

    /**
     * Check if every square is uncovered
     * @return - boolean, true if all squares are uncovered
     */
    public boolean isAllUncovered() {
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) == true) { return false; }
        }
        return true;
    }
}