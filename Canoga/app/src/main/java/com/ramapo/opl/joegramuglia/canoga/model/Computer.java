package com.ramapo.opl.joegramuglia.canoga.model;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by JoeGramuglia on 11/9/2015.
 */
public class Computer extends Player implements Serializable {

    /**
     * Makes the computer's move
     * @param myBoard - a board, The players board
     * @param theirBoard - a board, The opponents board
     * @param rollTotal - int, The number of pips rolled
     * @param combination - a stack of integers, A series of squares
     * @param myBoardFlag - boolean, The board to use
     * @return - boolean, If the move was successful
     */
    @Override
    public boolean makeMove(Board myBoard, Board theirBoard, int rollTotal, Stack<Integer> combination, boolean myBoardFlag) {
        if (!getAllowUncover()) {
            // Only cover
            makeBestMove(myBoard, new Board(0), rollTotal);
        } else {
            // Both cover and uncover
            makeBestMove(myBoard, theirBoard, rollTotal);
        }

        if (getCombination().empty()) {
            return false;
        }

        return true;
    }

    /**
     * Determines if the computer should roll one dice or two
     * @param myBoard - a board, The computer's board
     * @param theirBoard - a board, The human's board
     * @return - boolean, True for one dice, False for two
     */
    public boolean rollDie(Board myBoard, Board theirBoard) {
        if (myBoard.is7UpCovered()) {
            int coverScore = getSum(myBoard, true);
            int uncoverScore = getSum(theirBoard, false);
            if (coverScore >= uncoverScore) {
                return true;
            } else {
                if (theirBoard.is7UpUncovered()) {
                    return true;
                }
            }
        }
        return false;
    }
}
