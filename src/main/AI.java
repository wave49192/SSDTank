package main;

import GameObject.Tank;
import main.Board;
import strategy.NoLookingBackwardStrategy;
import strategy.Strategy;

import java.util.List;

public class AI {
    Strategy strategy;
    List<Tank> tanks;
    Board board;

    public AI(List<Tank> tank, Board board, String strategyName) {
        this.tanks = tank;
        this.board = board;

        if (strategyName.equals("NoLookingBackwardStrategy")) {
            this.strategy = new NoLookingBackwardStrategy();
        }
    }

    public void executeStrategy() {
        strategy.execute(tanks, board);
    }
}
