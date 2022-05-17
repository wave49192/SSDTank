package command;

import GameObject.Tank;

public class CommandTurnEast extends Command{
	public CommandTurnEast(Tank tank) {super(tank);}
	@Override
	public void execute(){
		getTank().turnEast();
		getTank().setMoving(true);
	}
}
