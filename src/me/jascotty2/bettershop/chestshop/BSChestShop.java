/**
 * Copyright (C) 2011 Jacob Scott <jascottytechie@gmail.com>
 * Description: for adding a chest interface to bettershop
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.jascotty2.bettershop.chestshop;

import me.jascotty2.bettershop.BetterShop;
import me.jascotty2.bettershop.BSEcon;
import me.jascotty2.bettershop.BSutils;
import me.jascotty2.bettershop.enums.BetterShopPermission;
import me.jascotty2.bettershop.shop.BSPriceList;
import me.jascotty2.bettershop.shop.Shop;
import me.jascotty2.bettershop.BSPermissions;
import me.jascotty2.bettershop.utils.BetterShopLogger;

import me.jascotty2.lib.bukkit.inventory.ChestManip;
import me.jascotty2.lib.bukkit.item.JItem;
import me.jascotty2.lib.bukkit.item.JItemDB;
import me.jascotty2.lib.bukkit.shop.UserTransaction;

import net.minecraft.server.v1_7_R1.EntityPlayer;
import net.minecraft.server.v1_7_R1.IInventory;
import net.minecraft.server.v1_7_R1.InventoryLargeChest;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import me.jascotty2.lib.bukkit.inventory.ItemStackManip;
import me.jascotty2.lib.util.ArrayManip;
import me.jascotty2.lib.util.Str;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * @author jacob
 */
public class BSChestShop implements Listener {

	final static int chestStrLen = 16; // 1.8 won't allow many chars :(
	final static long chestProtectDelay = 5000;
	protected final BetterShop plugin;
	final ChestDB chestsDB;
	final HashMap<Player, ItemStack[]> openPlayers = new HashMap<Player, ItemStack[]>();
	final HashMap<Player, IInventory> openChests = new HashMap<Player, IInventory>();
	final HashMap<Player, Location> openChestLocations = new HashMap<Player, Location>();
	final ArrayList<Player> editActive = new ArrayList<Player>();
	public final ChestListener chestChecker;
	ChestShopInventoryListenerSpout invListen = null;

	public BSChestShop(BetterShop shop) {
		plugin = shop;
		chestsDB = new ChestDB(plugin.getServer());
		chestChecker = new ChestListener(plugin, chestsDB);

	} // end default constructor

	public void registerEvents() {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(this, plugin);
		
		pm.registerEvents(chestChecker, plugin);
		chestChecker.startProtect();
	}

	public void registerSpout(boolean enable) {
		if (enable) {
			if (invListen == null) {
				invListen = new ChestShopInventoryListenerSpout(this);

				plugin.getServer().getPluginManager().registerEvents(invListen, plugin);
			}
		} else if (invListen != null) {
			invListen = null;
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled() || !BetterShop.getSettings().chestShopEnabled) {
			return;
		}
		Player p = event.getPlayer();
		chestClose(p);
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (chestsDB.has(b.getLocation())) {
				// sanity check
				if (!(b.getState() instanceof Chest)) {
					chestsDB.remove(b.getLocation());
					return;
				}
				event.setCancelled(true);
				// now check if this user has permission to use chest shops
				if (!BSPermissions.hasPermission(p, BetterShopPermission.USER_CHEST, true)) {
					return;
				}
				openChest(p, (Chest) b.getState(), false);
			}
		}
	}

	public void openChest(Player p, Chest open, boolean isEditing) {
		if (p == null || open == null) {
			return;
		}
		chestClose(p);
		Location loc = open.getBlock().getLocation();
		//System.out.println("opening chest at " + loc.getBlockX() + ", " + loc.getBlockZ());
		Chest other = ChestManip.otherChest(open.getBlock());
		ItemStack[] chestShopItems = open.getInventory().getContents();
		IInventory chestShop;
		String chestTxt = Str.strTrim(BetterShop.getSettings().chestShopText.
				replace("<e>", isEditing ? BetterShop.getSettings().chestEditText : ""), chestStrLen);
		if (other != null) {
			ItemStack[] chestShopItems2 = other.getInventory().getContents();
			if (isEditing) {
				if (open == ChestManip.topChest(open)) {
					chestShop = new InventoryLargeChest(chestTxt,
							new InventorySmallChest("", chestShopItems),
							new InventorySmallChest("", chestShopItems2));
				} else {
					chestShop = new InventoryLargeChest(chestTxt,
							new InventorySmallChest("", chestShopItems2),
							new InventorySmallChest("", chestShopItems));
				}
			} else {
				ItemStack[] comb = open == ChestManip.topChest(open)
						? ArrayManip.arrayConcat(chestShopItems, chestShopItems2)
						: ArrayManip.arrayConcat(chestShopItems2, chestShopItems);
				ItemStack[] afford = canAfford(p, loc, comb);
				
				if(BetterShop.getSettings().chestSellBar){
					afford = ArrayManip.arrayConcat(afford, new ItemStack[9]);
				}
				
				chestShop = new InventoryLargeChest(chestTxt,
						new InventorySmallChest("", ArrayManip.arraySub(afford, 0, chestShopItems.length)),
						new InventorySmallChest("", ArrayManip.arraySub(afford, chestShopItems.length, afford.length)));
			
			}
		} else {
			chestShop = new InventorySmallChest(
					chestTxt, isEditing ? chestShopItems : canAfford(p, loc, chestShopItems));
		}
		// Get the EntityPlayer handle from the sender
		EntityPlayer entityplayer = ((CraftPlayer) p).getHandle();
		// open the "chest"
		entityplayer.openContainer(chestShop);

		// save a copy of the chest's current inventory
		openPlayers.put(p, ItemStackManip.copy(chestShop.getContents()));
		openChests.put(p, chestShop);
		openChestLocations.put(p, loc);
		if (isEditing) {
			editActive.add(p);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		chestClose(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerKick(PlayerKickEvent event) {
		chestClose(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		chestClose(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (openPlayers.containsKey(event.getPlayer())) {
			event.setCancelled(true);
			//event.getPlayer().updateInventory();
		}
	}

	public synchronized void closeAllChests() {
		for (Player p : openPlayers.keySet().toArray(new Player[0])) {
			chestClose(p);
		}
	}

	void chestClose(Player player) {
		if (!openPlayers.containsKey(player)) {
			return;
		}
		ItemStack[] preItems = openPlayers.get(player),
				newItems = ItemStackManip.copy(openChests.get(player).getContents());//player.getInventory().getContents();
		Location shopLocation = openChestLocations.get(player);
		openPlayers.remove(player);
		openChests.remove(player);
		openChestLocations.remove(player);
		if (editActive.contains(player)) {
			editActive.remove(player);
			chestEdit(shopLocation, newItems);
			return;
		}
		try {
			boolean diff = false;
			for (int i = 0; i < preItems.length; ++i) {
				if ((preItems[i] != null
						&& (!preItems[i].equals(newItems[i])
						|| (newItems[i] != null && preItems[i].getAmount() != newItems[i].getAmount())))
						|| (preItems[i] == null && newItems[i] != null)) {
					diff = true;
					break;
				}
			}
			if (!diff) {
				return;
			}
			// init shop
			Shop shop = BetterShop.getShop(shopLocation);
			// find the differences
			List<ItemStack> changedItems = ItemStackManip.itemStackDifferences(preItems, newItems);
			// records
			List<UserTransaction> transactions = new LinkedList<UserTransaction>();
			// name of item(s) sold
			String itemN = "";
			int numItems = 0;
			double total = 0;
			// for transactions to cancel
			List<UserTransaction> cancel = new LinkedList<UserTransaction>();
			// first sell
			for (ItemStack i : changedItems) {
				if (i.getAmount() > 0) {
					//System.out.println("sold " + i.getAmount() + " " + i.getType().name());
					JItem toSell = JItemDB.GetItem(i);
					double credit = shop.pricelist.itemSellPrice(player, toSell, i.getAmount());

					if (credit < 0 || (toSell.IsTool() && !BetterShop.getSettings().buybacktools)) {
						UserTransaction n = new UserTransaction(toSell, true, i.getAmount());
						if (toSell.IsTool()) {
							n.damage = i.getDurability();
						}
						cancel.add(n);
						BSutils.sendMessage(player, BetterShop.getSettings().getString("donotwant").
								replace("<item>", toSell.coloredName()));
						continue;
					} else if (toSell.IsTool()) {
						credit *= (1 - ((double) i.getDurability() / toSell.MaxDamage()));
					}
					// now check the remaining stock can sell back
					if (shop.config.useStock()) {
						long avail = shop.stock.freeStockRemaining(toSell);
						if (avail == 0 && shop.config.noOverStock) {
							BSutils.sendMessage(player, BetterShop.getSettings().getString("maxstock").
									replace("<item>", toSell.coloredName()));
							UserTransaction n = new UserTransaction(toSell, true, i.getAmount());
							if (toSell.IsTool()) {
								n.damage = i.getDurability();
							}
							cancel.add(n);
							continue;
						} else if (avail > 0 && i.getAmount() > avail && shop.config.noOverStock) {
							BSutils.sendMessage(player, BetterShop.getSettings().getString("highstock").
									replace("<item>", toSell.coloredName()).
									replace("<amt>", String.valueOf(avail)));
							UserTransaction n = new UserTransaction(toSell, true, i.getAmount() - (int) avail);
							if (toSell.IsTool()) {
								n.damage = i.getDurability();
							}
							cancel.add(n);
							i.setAmount((int) avail);
						}
					}

					total += credit;
					numItems += i.getAmount();
					itemN += toSell.coloredName() + ", ";

					transactions.add(new UserTransaction(toSell, true,
							i.getAmount(), credit, player.getDisplayName()));

					if (shop.config.useStock()) {
						shop.stock.changeItemAmount(toSell, i.getAmount());
					}
				}
			}
			if (numItems > 0) {
				BSEcon.addMoney(player, total);
				BSutils.sendFormttedMessage(player, "sellmsg",
						itemN.substring(0, itemN.length() - 2), numItems, total);
			}
			if (cancel.size() > 0) {
				List<ItemStack> reAdd = new ArrayList<ItemStack>();
				for(UserTransaction u : cancel) {
					reAdd.add(new ItemStack(u.itemNum, u.amount, u.damage));
				}
				player.getInventory().setContents(
						ItemStackManip.add(player.getInventory().getContents(),
						reAdd, !BetterShop.getSettings().usemaxstack));
				delayUpdate(player);
			}

			itemN = "";
			numItems = 0;
			total = 0;
			cancel.clear();
			double balance = BSEcon.getBalance(player);
			// then buy
			for (ItemStack i : changedItems) {
				if (i.getAmount() < 0) {
					//System.out.println("bought " + (-i.getAmount()) + " " + i.getType().name());
					JItem toBuy = JItemDB.GetItem(i);
					double cost = shop.pricelist.itemBuyPrice(player, toBuy, -i.getAmount());
					if (balance - cost < 0) {
						BSutils.sendMessage(player,
								BetterShop.getSettings().getString("insuffunds").
								replace("<item>", toBuy.coloredName()).
								replace("<amt>", String.valueOf(-i.getAmount())).
								replace("<total>", String.valueOf(cost)).
								replace("<curr>", BetterShop.getSettings().currency()).
								replace("<priceper>", String.valueOf(cost / (-i.getAmount()))).
								replace("<totcur>", BSEcon.format(cost)));
						// change amount so can buy some
						int afford = shop.pricelist.getAmountCanBuy(player, balance, toBuy);
						if (afford > 0) {
							cost = shop.pricelist.itemBuyPrice(player, toBuy, afford);
							cancel.add(new UserTransaction(toBuy, false, -i.getAmount() - afford));
							i.setAmount(-(-i.getAmount() - afford));
						} else {
							cancel.add(new UserTransaction(toBuy, false, -i.getAmount()));
							continue;
						}
					}

					balance -= cost;
					total += cost;
					numItems += -i.getAmount();
					itemN += toBuy.coloredName() + ", ";

					transactions.add(new UserTransaction(toBuy, false,
							-i.getAmount(), cost, player.getDisplayName()));

					if (shop.config.useStock()) {
						shop.stock.changeItemAmount(toBuy, i.getAmount());
					}
				}
			}
			// now check for & remove transactions that the user can't afford
			if (cancel.size() > 0) {
				List<ItemStack> reAdd = new ArrayList<ItemStack>();
				for(UserTransaction u : cancel) {
					reAdd.add(new ItemStack(u.itemNum, u.amount, u.damage));
				}
				player.getInventory().setContents(
						ItemStackManip.add(player.getInventory().getContents(),
						reAdd, !BetterShop.getSettings().usemaxstack));
				delayUpdate(player);
			}

			if (numItems > 0) {
				BSEcon.subtractMoney(player, total);
				BSutils.sendFormttedMessage(player, "buymsg",
						itemN.substring(0, itemN.length() - 2), numItems, total);
			}
			// apppend records
			try {
				for (UserTransaction t : transactions) {
					shop.transactions.addRecord(t);
				}
			} catch (Exception ex) {
				BetterShopLogger.Severe(ex);
			}
		} catch (Exception e) {
			BetterShopLogger.Severe("Error in ChestShop", e);
		}
	}

	void chestEdit(Location l, ItemStack[] edited) {
		if (l == null || edited == null) {
			return;
		}
		Block b = l.getBlock();
		if (b.getState() instanceof Chest) {
			//System.out.println("saving chest at " + l.getBlockX() + ", " + l.getBlockZ());
			//Chest open = (Chest) b.getState();
			ChestManip.setContents((Chest) b.getState(), edited);
		}
	}

	ItemStack[] canAfford(Player p, Location shopLoc, ItemStack[] source) {
		ItemStack[] copy = new ItemStack[source.length];

		// now scan through chest & increase item amounts to max player can afford
		HashMap<String, Integer> used = new HashMap<String, Integer>();
		BSPriceList pricelist = BetterShop.getPricelist(shopLoc);
		for (int i = 0; i < source.length; ++i) {
			if (source[i] == null) {
				copy[i] = null;
			} else {
				JItem it = JItemDB.GetItem(source[i]);
				if (it != null) {
					int max = pricelist.getAmountCanBuy(p, it);
					int maxSize = BetterShop.getSettings().usemaxstack ? it.getMaxStackSize() : 64;
					if (max < 0) {
						max = maxSize; // Integer.MAX_VALUE;
					} else if (max > 0) {
						if (used.containsKey(it.IdDatStr())) {
							int u = used.get(it.IdDatStr());
							max -= u;
							used.put(it.IdDatStr(), u + maxSize);
						} else {
							used.put(it.IdDatStr(), maxSize);
						}
					}
					if (max > maxSize) {
						max = maxSize;
					}
					if (max <= 0) {
						copy[i] = null;
					} else {
						copy[i] = source[i].clone();
						copy[i].setAmount(max);
					}
				}
			}
		}
		return copy;
	}

	public boolean defineChestShop(Block b) {
		if (b == null || chestsDB.has(b.getLocation())) {
			return false;
		} else {
			chestsDB.setChest(b.getLocation());
			return true;
		}
	}

	public boolean removeChestShop(Block b) {
		if (b == null || !chestsDB.has(b.getLocation())) {
			return false;
		} else {
			chestsDB.remove(b.getLocation(), true);
			return true;
		}
	}

	public boolean hasChestShop(Block b) {
		return b != null && chestsDB.has(b.getLocation());
	}

	public boolean load() {
		return chestsDB.load();
	}

	public boolean save() {
		return !chestsDB.isChanged() || chestsDB.save();
	}

	public boolean saveDelayActive() {
		return chestsDB.saveDelayActive();
	}

	public int numChests() {
		return chestsDB.getSavedChests().size();
	}
	HashMap<Player, Integer> playerUpdate = new HashMap<Player, Integer>();

	private void delayUpdate(Player p) {
		BukkitScheduler sc = plugin.getServer().getScheduler();
		if (playerUpdate.containsKey(p)) {
			sc.cancelTask(playerUpdate.get(p));
			playerUpdate.remove(p);
		}
		int t = sc.scheduleSyncDelayedTask(plugin, new UpdateTask(p), 1);
		if (t != -1) {
			playerUpdate.put(p, t);
		}
	}

	private class UpdateTask implements Runnable {

		Player player;

		UpdateTask(Player p) {
			player = p;
		}

		public void run() {
			playerUpdate.remove(player);
			player.updateInventory();
		}
	}
} // end class BSSignShop

