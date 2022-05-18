package strategy;

import GameObject.Tank;
import main.Board;

import java.util.List;

public class RandomStrategy implements Strategy {

    @Override
    public void execute(List<Tank> tanks, Board board) {

        for (Tank tank : tanks) {
            int random_int = (int)Math.floor(Math.random()*(4));
            if(random_int == 0) {
                tank.turnNorth();
            }
            if (random_int == 1){
                tank.turnSouth();
            }
            if(random_int == 2){
                tank.turnWest();
            }
            if (random_int == 3){
                tank.turnEast();
            }
            }
        }
    };

