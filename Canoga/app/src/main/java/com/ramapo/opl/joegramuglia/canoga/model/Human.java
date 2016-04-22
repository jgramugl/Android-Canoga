package com.ramapo.opl.joegramuglia.canoga.model;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by JoeGramuglia on 11/9/2015.
 */
public class Human extends Player implements Serializable {

    /**
     * Makes the human's move
     * @param myBoard - a board, The players board
     * @param theirBoard - a board, The opponents board
     * @param rollTotal - int, The number of pips rolled
     * @param combination - a stack of integers, A series of squares
     * @param myBoardFlag - boolean, The board to use
     * @return - boolean, If the move was successful
     */
    @Override
    public boolean makeMove(Board myBoard, Board theirBoard, int rollTotal, Stack<Integer> combination, boolean myBoardFlag) {
        if (!getAllowUncover() && !myBoardFlag) {
            // TRYING TO UNCOVER BUT NOT ALLOWED
            return false;
        }
        if (getSum(combination) == rollTotal) {
            // CHECK IF THE COMBINATION CAN GO ON THE BOARD
            if (myBoardFlag) {
                // CHECK IF ALL THE SQUARES CAN BE COVERED
                for (int i = 0; i < combination.size(); i++) {
                    if (myBoard.isCovered(combination.get(i))) {
                        return false;
                    }
                }
                setCombination(combination);
                setMyBoardFlag(myBoardFlag);
                return true;
            }
            // CHECK IF ALL THE SQUARES CAN BE UNCOVERED
            for (int i = 0; i < combination.size(); i++) {
                if (!theirBoard.isCovered(combination.get(i))) {
                    return false;
                }
            }
            setCombination(combination);
            setMyBoardFlag(myBoardFlag);
            return true;
        }
        return false;
    }
}
