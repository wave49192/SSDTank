package command;

import GameObject.Tank;

public class CommandTurnSouth extends Command{
	public CommandTurnSouth(Tank tank){super(tank);}
	@Override
	public void execute(){
		getTank().turnSouth();
		getTank().setMoving(true);
	}
}
