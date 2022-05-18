package audio;

import GameObject.Tank;

import java.util.List;
import java.util.Objects;

public class TankAudioController extends Sound {
    private List<Tank> tanks;
    private String previousSound;

    public TankAudioController(List<Tank> tank) {
        this.tanks = tank;
    }

    public void initialSound() {
        setFile("idling");
        play();
        previousSound = "idling";
    }

    public void playTankMovementSound() {
        for (Tank tank: tanks) {
            if (tank.getPlayerNumber() > 0 && tank.isIdle() && !Objects.equals(previousSound, "idling")) {
                stop();
                setFile("idling");
                play();
                previousSound = "idling";
            } else if (tank.getPlayerNumber() > 0 && !tank.isIdle() && !Objects.equals(previousSound, "moving")) {
                stop();
                setFile("moving");
                play();
                previousSound = "moving";
            }
        }
    }
}
