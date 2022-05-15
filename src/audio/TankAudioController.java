package audio;

import GameObject.Tank;

import java.util.Objects;

public class TankAudioController extends Sound {
    private Tank tank;
    private String previousSound;

    public TankAudioController(Tank tank) {
        this.tank = tank;
    }

    public void initialSound() {
        setFile("idling");
        play();
        previousSound = "idling";
    }

    public void playTankMovementSound() {
        if (tank.isIdle() && !Objects.equals(previousSound, "idling")) {
            stop();
            setFile("idling");
            play();
            previousSound = "idling";
        }
        else if (!tank.isIdle() && !Objects.equals(previousSound, "moving")) {
            stop();
            setFile("moving");
            play();
            previousSound = "moving";
        }
    }
}
