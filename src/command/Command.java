package command;

import GameObject.Tank;

public abstract class Command {
	private Tank tank;
	public Command(Tank tank){
		this.tank = tank;

	}
	public Tank getTank(){
		return tank;
	}
	public abstract void execute();
}
