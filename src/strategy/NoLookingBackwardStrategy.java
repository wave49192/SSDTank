package strategy;

import GameObject.Tank;
import main.Board;

import java.util.List;

public class NoLookingBackwardStrategy implements Strategy {

    @Override
    public void execute(List<Tank> tanks, Board board) {
        for (Tank tank : tanks) {
                    while (!board.canMoveTank(tank)) {
                        tank.turnEast();
                    }
                    tank.move();
                }
        };
}
