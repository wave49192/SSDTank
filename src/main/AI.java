package main;

import GameObject.Tank;
import main.Board;
import strategy.RandomStrategy;
import strategy.Strategy;

import java.util.List;

public class AI {
    Strategy strategy;
    List<Tank> tanks;
    Board board;

    public AI(List<Tank> tank, Board board, String strategyName) {
        this.tanks = tank;
        this.board = board;

        if (strategyName.equals("RandomStrategy")) {
            this.strategy = new RandomStrategy();
        }
    }

    public void executeStrategy() {
        strategy.execute(tanks, board);
    }
}
