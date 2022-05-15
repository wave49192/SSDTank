package command;

import GameObject.Tank;

public class CommandStop extends Command{
    public CommandStop(Tank tank){super(tank);}
    @Override
    public void execute(){
        getTank().stop();
    }
}
