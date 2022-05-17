package strategy;

import GameObject.Tank;
import main.Board;

import java.util.List;

public interface Strategy {
    public void execute(List<Tank> tanks, Board board);
}
