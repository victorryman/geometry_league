package gamemechanics;

import java.util.Timer;
import java.util.TimerTask;
/**
 * A power up that makes multiple bullets
 * This is done by in the player setting multishot == true
 */
public class PowerUpMultiShot extends PowerUp {
	private Timer timer;
	
	public PowerUpMultiShot(PlayingField pf) {
		super(pf);
		timer = new Timer();
	}
	
//	------------------------------------------- GETTERS & SETTERS

//	------------------------------------------- LOGIC
	
	public void action() {
		timer.schedule(enableMultiShot, 0001);
		timer.schedule(disableMultiShot, 6000);
	}
	
	TimerTask enableMultiShot = new TimerTask()
	{
	        public void run()
	        {
	        	getPlayingField().getPlayer().setMultiShot(true);
	        }
	};
	
	TimerTask disableMultiShot = new TimerTask()
	{
	        public void run()
	        {
	        	getPlayingField().getPlayer().setMultiShot(false);
	        }
	};
	
//	------------------------------------------- GRAPHICS


	

}
