package server.org.engine.character.skilling.crafting;

import java.util.ArrayList;
import server.org.core.event.CycleEvent;
import server.org.core.event.CycleEventContainer;
import server.org.core.event.CycleEventHandler;
import server.org.engine.character.Client;
import server.org.core.util.Misc;
import server.org.Config;
import server.org.Server;

 /**
  * Flaxpicking.
  * @author EoG
  *
  */
public class Flax {
	
	private static final int FLAX = 1779, SPIN_ANIM = 894, PICK_ANIM = 827, BOW_STRING = 1777;
	
	public static ArrayList <int[]> flaxRemoved = new ArrayList<int[]>();
	
	public static void pickFlax(final Client c, final int x, final int y) {
		if (c.getItems().freeSlots() != 0) {
            c.getItems().addItem(FLAX, 1);
            c.startAnimation(PICK_ANIM);
			c.sendMessage("You pick some flax.");
			if (Misc.random(3) == 1) {
				flaxRemoved.add(new int[] { x, y });
				Server.objectManager.removeObject(x, y);
			}
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					replaceFlax(c, x, y);
				}
				@Override
				public void stop()
				{
					
				}
			}, 17);
					
		} else {
			c.sendMessage("Not enough space in your inventory.");
			return;
		}
	
	}
	
	public static void spinFlax(final Client c, final int x, final int y) {
		if (!c.isSpinning) {
		c.isSpinning = true;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.getItems().playerHasItem(FLAX) && c.isSpinning) {
						c.startAnimation(SPIN_ANIM);
						c.getItems().deleteItem(FLAX, 1);
						c.getItems().addItem(BOW_STRING, 1);
					} else {
						this.stop();
					}
				}
				@Override
				public void stop()
				{
					c.isSpinning = false;
				}
			}, 5);
		}
	}
	
	public static void replaceFlax(final Client c, final int x, final int y)
	{
		flaxRemoved.remove(new int[] { x, y });
		Server.objectHandler.createAnObject(c, 2646, x, y);
	}
}
