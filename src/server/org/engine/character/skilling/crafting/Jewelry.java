package server.org.engine.character.skilling.crafting;

import server.org.core.event.CycleEvent;
import server.org.core.event.CycleEventContainer;
import server.org.core.event.CycleEventHandler;
import server.org.engine.character.Client;
import server.org.Config;
import server.org.Server;

/**
 * 
 * @author South-Park
 *
 */

public class Jewelry {
		Client c;
		public Jewelry(Client c) {
			this.c = c;
		}
		
		public static enum jewelryData {
			
			RINGS(new int[][] {{2357, 1635, 5, 15}, {1607, 1637, 20, 40}, {1605, 1639, 27, 55}, {1603, 1641, 34, 70}, {1601, 1643, 43, 85}, {1615, 1645, 55, 100}, {6573, 6575, 67, 115}}),
			NECKLACE(new int[][] {{2357, 1654, 6, 20}, {1607, 1656, 22, 55}, {1605, 1658, 29, 60}, {1603, 1660, 40, 75}, {1601, 1662, 56, 90}, {1615, 1664, 72, 105}, {6573, 6577, 82, 120}}),
			AMULETS(new int[][] {{2357, 1673, 8, 30}, {1607, 1675, 24, 65}, {1605, 1677, 31, 70}, {1603, 1679, 50, 85}, {1601, 1681, 70, 100}, {1615, 1683, 80, 150}, {6573, 6579, 90, 165}});
			
			public int[][] item;
			
			private jewelryData(final int[][] item) {
				this.item = item;
			}	
		}
		
		public static enum amuletData {
			GOLD(1673, 1692),
			SAPPHIRE(1675, 1694),
			EMERALD(1677, 1696),
			RUBY(1679, 1698),
			DIAMOND(1681, 1700),
			DRAGONSTONE(1683, 1702),
			ONYX(6579, 6581);
			
			private int amuletId, product;
			
			private amuletData(final int amuletId, final int product) {
				this.amuletId = amuletId;
				this.product = product;
			}
			
			public int getAmuletId() {
				return amuletId;
			}
			
			public int getProduct() {
				return product;
			}
		}
		
		public static void jewelryMaking(final Client c, final String type, final int itemId, final int amount) {
			switch (type) {
			case "RING":
				for (int i = 0; i < jewelryData.RINGS.item.length; i++) {
					if (itemId == jewelryData.RINGS.item[i][1]) {
						mouldJewelry(c, jewelryData.RINGS.item[i][0], itemId, amount, jewelryData.RINGS.item[i][2], jewelryData.RINGS.item[i][3]);
					}
				}
				break;
			case "NECKLACE":
				for (int i = 0; i < jewelryData.NECKLACE.item.length; i++) {
					if (itemId == jewelryData.NECKLACE.item[i][1]) {
						mouldJewelry(c, jewelryData.NECKLACE.item[i][0], itemId, amount, jewelryData.NECKLACE.item[i][2], jewelryData.NECKLACE.item[i][3]);
					}
				}
				break;
			case "AMULET":
				for (int i = 0; i < jewelryData.AMULETS.item.length; i++) {
					if (itemId == jewelryData.AMULETS.item[i][1]) {
						mouldJewelry(c, jewelryData.AMULETS.item[i][0], itemId, amount, jewelryData.AMULETS.item[i][2], jewelryData.AMULETS.item[i][3]);
					}
				}
				break;
			}
		}
		
		private static int time;
		
		private static void mouldJewelry(final Client c, final int required, final int itemId, final int amount, final int level, final int xp) {
			if (c.isCrafting == true) {
				return;
			}
			if (c.playerLevel[12] < level) {
				c.sendMessage("You need a crafting level of "+ level +" to mould this item.");
				return;
			}
			if (!c.getItems().playerHasItem(2357)) {
				c.sendMessage("You need a gold bar to mould this item.");
				return;
			}
			final String itemRequired = c.getItems().getItemName(required);		
			if (!c.getItems().playerHasItem(required)) {
				c.sendMessage("You need "+ ((itemRequired.startsWith("A") || itemRequired.startsWith("E") || itemRequired.startsWith("O")) ? "an" : "a") +" "+ itemRequired.toLowerCase() +" to mould this item.");
				return;
			}
			time = amount;
			c.getPA().removeAllWindows();
			final String itemName = c.getItems().getItemName(itemId);
			c.startAnimation(899);
			c.isCrafting = true;
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					if (c.isCrafting == true) {
						if (time == 0) {
							container.stop();
						}
						if (c.getItems().playerHasItem(2357) && c.getItems().playerHasItem(required)) {
							c.getItems().deleteItem(2357, 1);
							c.getItems().deleteItem(required, 1);
							c.getItems().addItem(itemId, 1);
							c.startAnimation(899);
							c.getPA().addSkillXP(xp, 12);
							time--;
							c.sendMessage("You make "+ ((itemName.startsWith("A") || itemName.startsWith("E") || itemName.startsWith("O")) ? "an" : "a") +" "+ itemName.toLowerCase());
						} else {
							container.stop();
						}
					} else {
						container.stop();
					}
				}
				@Override
				public void stop() {
					
				}
			}, 4);
		}
		
		public static void stringAmulet(final Client c, final int itemUsed, final int usedWith) {
			final int amuletId = (itemUsed == 1759 ? usedWith : itemUsed);
			for (final amuletData a : amuletData.values()) {
				if (amuletId == a.getAmuletId()) {
					c.getItems().deleteItem(1759, 1);
					c.getItems().deleteItem(amuletId, 1);
					c.getItems().addItem(a.getProduct(), 1);
					c.getPA().addSkillXP(4, 12);
				}
			}
		}
		
		public static void jewelryInterface(final Client c) {
			c.getPA().showInterface(4161);
			for (final jewelryData i : jewelryData.values()) {
				if (c.getItems().playerHasItem(1592)) {
					for (int j = 0; j < i.item.length; j++) {
						if (c.getItems().playerHasItem(jewelryData.RINGS.item[j][0])) {
							c.getPA().sendFrame34(jewelryData.RINGS.item[j][1], j, 4233, 1);
						} else {
							c.getPA().sendFrame34(-1, j, 4233, 1);
						}
						c.getPA().sendFrame126("", 4230);
						c.getPA().sendFrame246(4229, 0, -1);
					}
				} else {
					c.getPA().sendFrame246(4229, 120, 1592);
					for (int j = 0; j < i.item.length; j++) {
						c.getPA().sendFrame34(-1, j, 4233, 1);
					}
					c.getPA().sendFrame126("You need a ring mould to craft rings.", 4230);
				}
				if (c.getItems().playerHasItem(1597)) {
					for (int j = 0; j < i.item.length; j++) {
						if (c.getItems().playerHasItem(jewelryData.NECKLACE.item[j][0])) {
							c.getPA().sendFrame34(jewelryData.NECKLACE.item[j][1], j, 4239, 1);
						} else {
							c.getPA().sendFrame34(-1, j, 4239, 1);
						}
						c.getPA().sendFrame126("", 4236);
						c.getPA().sendFrame246(4235, 0, -1);
					}
				} else {
					c.getPA().sendFrame246(4235, 120, 1597);
					for (int j = 0; j < i.item.length; j++) {
						c.getPA().sendFrame34(-1, j, 4239, 1);
					}
					c.getPA().sendFrame126("You need a necklace mould to craft necklaces.", 4236);
				}
				if (c.getItems().playerHasItem(1595)) {
					for (int j = 0; j < i.item.length; j++) {
						if (c.getItems().playerHasItem(jewelryData.AMULETS.item[j][0])) {
							c.getPA().sendFrame34(jewelryData.AMULETS.item[j][1], j, 4245, 1);
						} else {
							c.getPA().sendFrame34(-1, j, 4245, 1);
						}
						c.getPA().sendFrame126("", 4242);
						c.getPA().sendFrame246(4241, 0, -1);
					}
				} else {
					c.getPA().sendFrame246(4235, 120, 1597);
					for (int j = 0; j < i.item.length; j++) {
						c.getPA().sendFrame34(-1, j, 4245, 1);
					}
					c.getPA().sendFrame126("You need an amulet mould to craft amulets.", 4242);
				}
			}
		}
	
}
