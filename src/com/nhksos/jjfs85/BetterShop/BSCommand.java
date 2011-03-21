package com.nhksos.jjfs85.BetterShop;

import java.sql.SQLException;

import com.jascotty2.CheckInput;
import com.jascotty2.Item.Item;
import com.jascotty2.Item.ItemDB;
import com.jascotty2.Item.Kit;
import com.jascotty2.Item.Kit.KitItem;
import com.jascotty2.Item.PriceListItem;
import com.jascotty2.Shop.UserTransaction;
import com.jascotty2.Shop.PriceList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class BSCommand {

    HashMap<String, String> userbuyHistory = new HashMap<String, String>();
    HashMap<String, String> usersellHistory = new HashMap<String, String>();

    public BSCommand() {
    }

    public boolean help(CommandSender player) {
        if (!BSutils.hasPermission(player, "BetterShop.user.help", true)) {
            return true;
        }
        BSutils.sendMessage(player, "--------- Better Shop Usage --------");
        BSutils.sendMessage(player, "/shoplist <page> - List shop prices");
        BSutils.sendMessage(player, "/shopbuy [item] <amount> - Buy items");
        BSutils.sendMessage(player, "/shopsell [item] <amount> - Sell items (\"all\" can sell all of your items)");
        BSutils.sendMessage(player, "/shopcheck [item] - Check prices of item");
        BSutils.sendMessage(player, "/shopitems  - show listing of items in shop, without prices");

        if (BSutils.hasPermission(player, "BetterShop.admin", false)) {
            BSutils.sendMessage(player, "**-------- Admin commands --------**");
            BSutils.sendMessage(player, "/shopadd [item] [$buy] [$sell] - Add/Update an item in the shop");
            BSutils.sendMessage(player, "/shopremove [item] - Remove an item from the shop");
            BSutils.sendMessage(player, "/shopload - Reload the PriceList.yml file");
        }
        BSutils.sendMessage(player, "----------------------------------");
        return true;
    }

    public boolean check(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.check", true)) {
            return true;
        }
        if (s == null || s.length != 1) {
            return false;
        }
        Item lookup[] = Item.findItems(s[0]);
        if (lookup == null || lookup.length == 0 || lookup[0] == null) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").
                    replace("<item>", "%s"), s[0]));
            return true;
        }
        boolean canBuyIllegal = BetterShop.config.allowbuyillegal || BSutils.hasPermission(player, "BetterShop.admin.illegal", false);
        int inStore = 0;
        try {
            for (Item i : lookup) {
                PriceListItem price = BetterShop.pricelist.getItemPrice(i);
                if (price != null) {
                    ++inStore;
                    BSutils.sendMessage(player, String.format(
                            BetterShop.config.getString("pricecheck").
                            replace("<buyprice>", "%1$s").
                            replace("<sellprice>", "%2$s").
                            replace("<item>", "%3$s").
                            replace("<curr>", "%4$s").
                            replace("<buycur>", "%5$s").
                            replace("<sellcur>", "%6$s"),
                            (price.IsLegal() || canBuyIllegal) && price.buy >= 0 ? price.buy : "No",
                            price.sell >= 0 ? price.sell : "No",
                            i.coloredName(), BetterShop.config.currency,
                            (price.IsLegal() || canBuyIllegal) && price.buy >= 0
                            ? BetterShop.iConomy.getBank().format(price.buy) : "No",
                            price.sell >= 0 ? BetterShop.iConomy.getBank().format(price.sell) : "No"));
                } else if (lookup.length <= 5) { // only show nolisting if result page is 5 or less lines
                    BSutils.sendMessage(player,
                            String.format(BetterShop.config.getString("nolisting").
                            replace("<item>", "%s"), i.coloredName()));
                }
            }
            if (lookup.length > 5 && inStore == 0) {
                BSutils.sendMessage(player, String.format("No Sellable items found under \"%s\"", s[0]));
            }
            return true;
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
        }

        BSutils.sendMessage(player, "\u00A74An Error Occurred while looking up an item.. attemping to reload..");
        if (load(null)) {
            // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
            BSutils.sendMessage(player, "Success! Please try again.. ");
        } else {
            BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
        }
        return true;
    }

    public boolean list(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.list", true)) {
            return true;
        }
        int pagenum = 1;
        if ((s.length > 1)) {
            return false;
        } else if (s.length == 1) {
            if (s[0].equalsIgnoreCase("full") || s[0].equalsIgnoreCase("all")) {
                pagenum = -1;
            } else if (s[0].equalsIgnoreCase("item") || s[0].equalsIgnoreCase("items")) {
                return listitems(player, null);
            } else if (s[0].equalsIgnoreCase("kits")) {
                return listkits(player, null);
            } else if (!CheckInput.IsInt(s[0])) {
                BSutils.sendMessage(player, "That's not a page number.");
                return false;
            } else {
                pagenum = CheckInput.GetInt(s[0], 1);
            }
        }

        for (String line : BetterShop.pricelist.GetShopListPage(pagenum, player instanceof Player,
                BetterShop.config.allowbuyillegal || BSutils.hasPermission(player, "BetterShop.admin.illegal", false))) {
            BSutils.sendMessage(player, line);
        }

        return true;
    }

    public boolean listitems(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.list", true)) {
            return true;
        } else if (s != null || s.length > 0) {
            //return false;
        }
        try {
            LinkedList<String> items = BetterShop.pricelist.GetItemList(
                    BetterShop.config.allowbuyillegal || BSutils.hasPermission(player, "BetterShop.admin.illegal", false));
            String output = "\u00A72";
            for (int i = 0; i < items.size(); ++i) {
                output += items.get(i);
                if (i + 1 < items.size()) {
                    output += "\u00A72, ";
                }
            }
            BSutils.sendMessage(player, output);
            return true;
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
        }

        BSutils.sendMessage(player, "\u00A74An Error Occurred while looking up an item.. attemping to reload..");
        if (load(null)) {
            // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
            BSutils.sendMessage(player, "Success! Please try again.. ");
        } else {
            BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
        }
        return true;
    }

    public boolean load(CommandSender player) {
        if (player != null && !BSutils.hasPermission(player, "BetterShop.admin.load", true)) {
            return true;
        }
        if (ItemDB.load(BSConfig.pluginFolder)) {
            BSutils.sendMessage(player, ItemDB.size() + " items loaded.");
        } else {
            BetterShop.Log(Level.SEVERE, "Cannot Load Items db!");
            if (player == null) {
                return false;
            } else {
                BSutils.sendMessage(player, "\u00A74Item Database Load Error.");
            }
        }
        if (player == null && !BetterShop.config.load()) {
            return false;
        } else {
            BetterShop.config.load();
        }
        BSutils.sendMessage(player, "Config.yml loaded.");
        if (BetterShop.pricelist.load()) {
            BetterShop.transactions.load();
            BSutils.sendMessage(player, "Price Database loaded.");
        } else {
            if (player == null) {
                return false;
            } else {
                BSutils.sendMessage(player, "\u00A74Price Database Load Error.");
            }
        }
        return true;
    }

    public boolean add(CommandSender player, String[] s) {
        if (s.length == 2) {
            // append -1 as sell price
            String ns[] = new String[3];
            for (int i = 0; i < 2; ++i) {
                ns[i] = s[i];
            }
            ns[2] = "-1";
            s = ns;
        }
        if (s.length != 3) {
            return false;
        }
        if (!BSutils.hasPermission(player, "BetterShop.admin.add", true)) {
            return true;
        }

        Item toAdd = Item.findItem(s[0]);
        if (toAdd == null) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").
                    replace("<item>", "%s"), s[0]));
            return false;
        }

        if (CheckInput.IsDouble(s[1]) && CheckInput.IsDouble(s[2])) {
            if (CheckInput.GetDouble(s[1], -1) > PriceList.MAX_PRICE
                    || CheckInput.GetDouble(s[2], -1) > PriceList.MAX_PRICE) {
                BSutils.sendMessage(player, "Price set too high. Max = " + BetterShop.iConomy.getBank().format(PriceList.MAX_PRICE));
                return true;
            } else if (toAdd.isKit() && CheckInput.GetDouble(s[2], -1) >= 0) {
                BSutils.sendMessage(player, "Note: Kits cannot be sold");
                s[2] = "-1";
            }
            try {
                boolean isChanged = BetterShop.pricelist.isForSale(toAdd);
                if (BetterShop.pricelist.setPrice(s[0], s[1], s[2])) {
                    if (isChanged) {
                        BSutils.sendMessage(player,
                                String.format(BetterShop.config.getString("chgmsg").
                                replace("<item>", "%1$s").
                                replace("<buyprice>", "%2$01.2f").
                                replace("<sellprice>", "%3$01.2f").
                                replace("<curr>", "%4$s").
                                replace("<buycur>", "%5$s").
                                replace("<sellcur>", "%6$s"),
                                toAdd.name,
                                BetterShop.pricelist.getBuyPrice(toAdd),
                                BetterShop.pricelist.getSellPrice(toAdd),
                                BetterShop.config.currency,
                                BetterShop.iConomy.getBank().format(BetterShop.pricelist.getBuyPrice(toAdd)),
                                BetterShop.iConomy.getBank().format(BetterShop.pricelist.getSellPrice(toAdd))),
                                BetterShop.config.publicmarket);
                    } else {
                        BSutils.sendMessage(player,
                                String.format(BetterShop.config.getString("addmsg").
                                replace("<item>", "%1$s").
                                replace("<buyprice>", "%2$01.2f").
                                replace("<sellprice>", "%3$01.2f").
                                replace("<curr>", "%4$s").
                                replace("<buycur>", "%5$s").
                                replace("<sellcur>", "%6$s"),
                                toAdd.name,
                                BetterShop.pricelist.getBuyPrice(toAdd),
                                BetterShop.pricelist.getSellPrice(toAdd),
                                BetterShop.config.currency,
                                BetterShop.iConomy.getBank().format(BetterShop.pricelist.getBuyPrice(toAdd)),
                                BetterShop.iConomy.getBank().format(BetterShop.pricelist.getSellPrice(toAdd))),
                                BetterShop.config.publicmarket);
                    }
                    return true;
                }
            } catch (SQLException ex) {
                BetterShop.Log(Level.SEVERE, ex);
            } catch (Exception ex) {
                BetterShop.Log(Level.SEVERE, ex);
            }
            BSutils.sendMessage(player, "\u00A74An Error Occurred While Adding.");
        } else {
            BSutils.sendMessage(player, BetterShop.config.getString("paramerror"));
            return false;
        }

        return true;
    }

    public boolean remove(CommandSender player, String[] s) {
        if ((!BSutils.hasPermission(player, "BetterShop.admin.remove", true))) {
            return true;
        } else if (s.length != 1) {
            return false;
        }
        Item toRem = Item.findItem(s[0]);
        if (toRem != null) {
            try {
                BetterShop.pricelist.remove(toRem);
                BSutils.sendMessage(player, String.format(BetterShop.config.getString("removemsg").
                        replace("<item>", "%1$s"), toRem.name), BetterShop.config.publicmarket);

                return true;
            } catch (SQLException ex) {
                BetterShop.Log(Level.SEVERE, ex);
            } catch (Exception ex) {
                BetterShop.Log(Level.SEVERE, ex);
            }
            BSutils.sendMessage(player, "\u00A74Error removing item");
        } else {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").
                    replace("<item>", "%s"), toRem.name));
        }
        return true;

    }

    public boolean buy(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.buy", true)) {
            return true;
        } else if ((s.length > 2) || (s.length == 0)) {
            BSutils.sendMessage(player, "What?");
            return false;
        } else if (BSutils.anonymousCheck(player)) {
            return true;
        }
        if (s.length == 2 && s[0].equalsIgnoreCase("all")) {
            // swap two indicies
            String t = s[0];
            s[0] = s[1];
            s[1] = t;
        }
        Item toBuy = Item.findItem(s[0]);
        if (toBuy == null) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").replace("<item>", "%1$s"), s[0]));
            return true;
        } else if (!BetterShop.config.allowbuyillegal && !toBuy.IsLegal() && !BSutils.hasPermission(player, "BetterShop.admin.illegal", false)) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("illegalbuy").
                    replace("<item>", "%1$s"), toBuy.coloredName()));
            return true;
        } else if (toBuy.isKit()) {
            return buyKit(player, s);
        }


        // initial check complete: set as last action
        usersellHistory.put(((Player) player).getDisplayName(), "shopbuy " + argStr(s));


        double price = Double.NEGATIVE_INFINITY;

        try {
            price = BetterShop.pricelist.getBuyPrice(toBuy);
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
        }
        if (price < 0) {
            if (price == Double.NEGATIVE_INFINITY) {
                BSutils.sendMessage(player, "Error looking up price.. Attempting DB reload.. ");
                if (load(null)) {
                    // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
                    BSutils.sendMessage(player, "Success! Please try again.. ");
                } else {
                    BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
                }
            } else {
                BSutils.sendMessage(player, String.format(
                        BetterShop.config.getString("notforsale").
                        replace("<item>", "%1$s"), toBuy.coloredName()));
            }
            return true;
        }

        int amtbought = 1;
        int canHold = 0, maxStack = BetterShop.config.usemaxstack ? toBuy.getMaxStackSize() : 64;

        PlayerInventory inv = ((Player) player).getInventory();

        // don't search armor slots
        for (int i = 0; i <= 35; ++i) {
            ItemStack it = inv.getItem(i);
            if ((toBuy.equals(it) && it.getAmount() < maxStack) || it.getAmount() == 0) {
                canHold += maxStack - it.getAmount();
            }
        }
        /*
        for (int i = 0; i <= 35; ++i) {
        ItemStack it = inv.getItem(i);
        if (toBuy.equals(it) || it.getAmount() == 0) {
        canHold += maxStack - it.getAmount();
        }
        }//*/
        if (s.length == 2) {
            if (s[1].equalsIgnoreCase("all")) {
                amtbought = canHold;
            } else if (!CheckInput.IsInt(s[1])) {
                BSutils.sendMessage(player, s[1] + " is definitely not a number.");
                return true;
            } else {
                amtbought = CheckInput.GetInt(s[1], -1);
                if (amtbought > canHold) {
                    BSutils.sendMessage(player, String.format(BetterShop.config.getString("outofroom").
                            replace("<item>", "%1$s").replace("<amt>", "%2$d").
                            replace("<priceper>", "%3$01.2f").replace("<leftover>", "%4$d").
                            replace("<curr>", "%5$s").replace("<free>", "%6$d"), toBuy.coloredName(),
                            amtbought, price, amtbought - canHold, BetterShop.config.currency, canHold));
                    if (canHold == 0) {
                        return true;
                    }
                    amtbought = canHold;
                } else if (amtbought <= 0) {
                    BSutils.sendMessage(player, BetterShop.config.getString("nicetry"));
                    return true;
                }
            }
        }

        double cost = amtbought * price;
        try {
            if (BSutils.debit(player, cost)) {
                if (maxStack == 64) { //((Player) player).getInventory().addItem(toBuy.toItemStack(amtbought));
                    inv.addItem(toBuy.toItemStack(amtbought));
                } else {
                    int amtLeft = amtbought;
                    for (int i = 0; i <= 35; ++i) {
                        ItemStack it = inv.getItem(i);
                        if (it.getAmount() == 0 || (toBuy.equals(it) && it.getAmount() < maxStack)) {
                            inv.setItem(i, toBuy.toItemStack((maxStack < amtLeft ? maxStack : amtLeft) + it.getAmount()));
                            amtLeft -= maxStack;
                        }
                        if (amtLeft <= 0) {
                            break;
                        }
                    }
                }

                // drop in front of player?
                //World w = player.getServer().getWorld(""); w.dropItem(player.getServer().getPlayer("").getLocation(), leftover.values());//.dropItem(

                BSutils.sendMessage(player, String.format(BetterShop.config.getString("buymsg").
                        replace("<item>", "%1$s").
                        replace("<amt>", "%2$d").
                        replace("<priceper>", "%3$01.2f").
                        replace("<total>", "%4$01.2f").
                        replace("<curr>", "%5$s").
                        replace("<totcur>", "%6$s"),
                        toBuy.coloredName(), amtbought, price, cost,
                        BetterShop.config.currency, BetterShop.iConomy.getBank().format(cost)));

                if (BetterShop.config.publicmarket && BetterShop.config.hasString("publicbuymsg")) {
                    BSutils.broadcastMessage(player, String.format(BetterShop.config.getString("publicbuymsg").
                            replace("<item>", "%1$s").
                            replace("<amt>", "%2$d").
                            replace("<priceper>", "%3$01.2f").
                            replace("<total>", "%4$01.2f").
                            replace("<curr>", "%5$s").
                            replace("<totcur>", "%6$s").
                            replace("<player>", "%7$s"),
                            toBuy.coloredName(), amtbought, price, cost,
                            BetterShop.config.currency, BetterShop.iConomy.getBank().format(cost), ((Player) player).getDisplayName()), false);

                }

                BetterShop.transactions.addRecord(new UserTransaction(
                        toBuy, false, amtbought, price, ((Player) player).getDisplayName()));

                return true;
            } else {
                BSutils.sendMessage(player, String.format(BetterShop.config.getString("insuffunds").
                        replace("<item>", "%1$s").
                        replace("<amt>", "%2$d").
                        replace("<total>", "%3$01.2f").
                        replace("<curr>", "%5$s").
                        replace("<priceper>", "%4$01.2f").
                        replace("<totcur>", "%6$s"), toBuy.coloredName(),
                        amtbought, cost, price, BetterShop.config.currency,
                        BetterShop.iConomy.getBank().format(price)));
                return true;
            }
        } catch (Exception e) {
            BetterShop.Log("Error while debiting player.. possible iConomy crash? (attempting to reload iConomy)");
            BetterShop.Log(Level.SEVERE, e);
            BSutils.sendMessage(player, "Error while debiting player.. possible iConomy crash? ");
            BSutils.sendMessage(player, "Attempting reload.. ");
            try {
                PluginManager m = player.getServer().getPluginManager();
                Plugin icon = m.getPlugin("iConomy");

                m.disablePlugin(icon);
                m.enablePlugin(icon);

                BSutils.sendMessage(player, "Success! Please try again.. ");
                return true;
            } catch (Exception ex) {
                BetterShop.Log(Level.SEVERE, "Error");
                BetterShop.Log(Level.SEVERE, ex);
            }
            BSutils.sendMessage(player, "Failed.");
            return true;
        }
    }

    public boolean buystack(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.buy", true)) {
            return true;
        } else if (s.length == 0) {
            BSutils.sendMessage(player, "What?");
            return false;
        } else if (BSutils.anonymousCheck(player)) {
            return true;
        }
        if (s.length == 2 && CheckInput.IsInt(s[1])) {

            Item toBuy = Item.findItem(s[0]);
            if (toBuy == null) {
                BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").
                        replace("<item>", "%1$s"), s[0]));
                return true;
            } else if (!BetterShop.config.allowbuyillegal && !toBuy.IsLegal() && !BSutils.hasPermission(player, "BetterShop.admin.illegal", false)) {
                BSutils.sendMessage(player, String.format(BetterShop.config.getString("illegalbuy").
                        replace("<item>", "%1$s"), toBuy.coloredName()));
                return true;
            }
            // buy max. stackable
            buy(player, new String[]{toBuy.IdDatStr(), String.valueOf((BetterShop.config.usemaxstack ? toBuy.getMaxStackSize() : 64) * CheckInput.GetInt(s[1], 1))});
        } else {
            for (String is : s) {
                Item toBuy = Item.findItem(is);
                if (toBuy == null) {
                    BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").
                            replace("<item>", "%1$s"), is));
                    return true;
                } else if (!BetterShop.config.allowbuyillegal && !toBuy.IsLegal() && !BSutils.hasPermission(player, "BetterShop.admin.illegal", false)) {
                    BSutils.sendMessage(player, String.format(BetterShop.config.getString("illegalbuy").
                            replace("<item>", "%1$s"), toBuy.coloredName()));
                    return true;
                }
                // buy max. stackable
                buy(player, new String[]{toBuy.IdDatStr(), String.valueOf(BetterShop.config.usemaxstack ? toBuy.getMaxStackSize() : 64)});
            }
        }// overwrite history that buy wrote
        usersellHistory.put(((Player) player).getDisplayName(), "shopbuystack " + argStr(s));
        return true;
    }

    public boolean buyKit(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.buy", true)) {
            return true;
        } else if (s.length == 0 || s.length > 2) {
            BSutils.sendMessage(player, "What?");
            return false;
        } else if (s.length == 2 && !(s[1].equalsIgnoreCase("all") || CheckInput.IsInt(s[1]))) {
            BSutils.sendMessage(player, s[1] + " is not a number..");
            return true;
        } else if (BSutils.anonymousCheck(player)) {
            return true;
        }

        Item toBuy = Item.findItem(s[0]);
        if (toBuy == null) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("unkitem").
                    replace("<item>", "%1$s"), s[0]));
            return true;
        } else if (!BetterShop.config.allowbuyillegal && !toBuy.IsLegal() && !BSutils.hasPermission(player, "BetterShop.admin.illegal", false)) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("illegalbuy").
                    replace("<item>", "%1$s"), toBuy.coloredName()));
            return true;
        } else if (!toBuy.isKit()) {
            BSutils.sendMessage(player, toBuy.coloredName() + " is not a kit");
            return true;
        }// initial check complete: set as last action
        usersellHistory.put(((Player) player).getDisplayName(), "shopbuy " + argStr(s));

        double price = Double.NEGATIVE_INFINITY;

        try {
            price = BetterShop.pricelist.getBuyPrice(toBuy);
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
        }
        if (price < 0) {
            if (price == Double.NEGATIVE_INFINITY) {
                BSutils.sendMessage(player, "Error looking up price.. Attempting DB reload.. ");
                if (load(null)) {
                    // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
                    BSutils.sendMessage(player, "Success! Please try again.. ");
                } else {
                    BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
                }
            } else {
                BSutils.sendMessage(player, String.format(
                        BetterShop.config.getString("notforsale").
                        replace("<item>", "%1$s"), toBuy.coloredName()));
            }
            return true;
        }

        Kit kitToBuy = ItemDB.getKit(toBuy);

        PlayerInventory inv = ((Player) player).getInventory();
        KitItem items[] = kitToBuy.getKitItems();

        int maxBuy = 0;
        //*
        ItemStack invCopy[] = new ItemStack[36];
        for (int i = 0; i <= 35; ++i) {
            invCopy[i] = new ItemStack(inv.getItem(i).getType(), inv.getItem(i).getAmount(), inv.getItem(i).getDurability());
        }

        while (true) {
            int numtoadd = 0;
            for (int itn = 0; itn < kitToBuy.numItems(); ++itn) {
                numtoadd = items[itn].itemAmount;
                int maxStack = BetterShop.config.usemaxstack ? items[itn].getMaxStackSize() : 64;
                // don't search armor slots
                for (int i = 0; i <= 35; ++i) {
                    //if (items[itn].equals(new Item(invCopy[i])) ||  (invCopy[i].getAmount() == 0 && invCopy[i].getAmount()<maxStack)) {
                    if ((items[itn].equals(new Item(invCopy[i])) && invCopy[i].getAmount() < maxStack)
                            || invCopy[i].getAmount() == 0) {
                        //System.out.println("can place " + items[itn] + " at " + i + " : " + invCopy[i]);
                        invCopy[i].setTypeId(items[itn].ID());
                        invCopy[i].setDurability(items[itn].Data());
                        invCopy[i].setAmount(invCopy[i].getAmount() + (maxStack < numtoadd ? maxStack : numtoadd));
                        numtoadd -= maxStack < numtoadd ? maxStack : numtoadd;
                        //System.out.println(invCopy[i]);
                        if (numtoadd <= 0) {
                            break;
                        }
                    }
                }
                if (numtoadd > 0) {
                    break;
                }
            }
            if (numtoadd <= 0) {
                //System.out.println("1 added: " + maxBuy);
                ++maxBuy;
            } else {
                break;
            }
        }//*/
        int numToBuy = 1;
        if (s.length == 2) {
            if (s[1].equalsIgnoreCase("all")) {
                numToBuy = maxBuy;
            } else {
                numToBuy = CheckInput.GetInt(s[1], 1);
                if (numToBuy <= 0) {
                    BSutils.sendMessage(player, BetterShop.config.getString("nicetry"));
                    return true;
                }
            }
        }
        if (numToBuy > maxBuy) {
            BSutils.sendMessage(player, String.format(BetterShop.config.getString("outofroom").
                    replace("<item>", "%1$s").
                    replace("<amt>", "%2$d").
                    replace("<priceper>", "%3$01.2f").
                    replace("<leftover>", "%4$d").
                    replace("<curr>", "%5$s").
                    replace("<free>", "%6$d"), toBuy.coloredName(),
                    numToBuy, price, numToBuy - maxBuy, BetterShop.config.currency, maxBuy));
            if (maxBuy == 0) {
                return true;
            }
            numToBuy = maxBuy;
        }

        double cost = numToBuy * price;
        try {
            if (BSutils.debit(player, cost)) {
                for (int num = 0; num < numToBuy; ++num) {
                    int numtoadd = 0;
                    for (int itn = 0; itn < kitToBuy.numItems(); ++itn) {
                        numtoadd = items[itn].itemAmount;
                        int maxStack = BetterShop.config.usemaxstack ? items[itn].getMaxStackSize() : 64;
                        // don't search armor slots
                        for (int i = 0; i <= 35; ++i) {
                            if ((items[itn].equals(new Item(inv.getItem(i))) && inv.getItem(i).getAmount() < maxStack)
                                    || inv.getItem(i).getAmount() == 0) {
                                //System.out.println("placing " + items[itn] + " at " + i + " (" + inv.getItem(i) + ")");
                                inv.setItem(i, items[itn].toItemStack(inv.getItem(i).getAmount() + (maxStack < numtoadd ? maxStack : numtoadd)));
                                numtoadd -= maxStack < numtoadd ? maxStack : numtoadd;
                                //System.out.println(inv.getItem(i));
                                if (numtoadd <= 0) {
                                    break;
                                }
                            }
                        }
                        if (numtoadd > 0) {
                            System.out.println("failed to add " + items[itn] + "!");
                            break;
                        }
                    }
                    if (numtoadd > 0) {
                        System.out.println("early exit while adding!");
                        BSutils.broadcastMessage(player, "An Error occurred.. contact an admin to resolve this issue");
                        break;
                    }
                }

                BSutils.sendMessage(player, String.format(
                        BetterShop.config.getString("buymsg").
                        replace("<item>", "%1$s").
                        replace("<amt>", "%2$d").
                        replace("<priceper>", "%3$01.2f").
                        replace("<total>", "%4$01.2f").
                        replace("<curr>", "%5$s").
                        replace("<totcur>", "%6$s"),
                        toBuy.coloredName(), numToBuy, price, cost,
                        BetterShop.config.currency, BetterShop.iConomy.getBank().format(cost)));

                if (BetterShop.config.publicmarket && BetterShop.config.hasString("publicbuymsg")) {
                    BSutils.broadcastMessage(player, String.format(BetterShop.config.getString("publicbuymsg").
                            replace("<item>", "%1$s").
                            replace("<amt>", "%2$d").
                            replace("<priceper>", "%3$01.2f").
                            replace("<total>", "%4$01.2f").
                            replace("<curr>", "%5$s").
                            replace("<totcur>", "%6$s").
                            replace("<player>", "%7$s"),
                            toBuy.coloredName(), numToBuy, price, cost,
                            BetterShop.config.currency, BetterShop.iConomy.getBank().format(cost), ((Player) player).getDisplayName()), false);

                }

                BetterShop.transactions.addRecord(new UserTransaction(
                        toBuy, false, numToBuy, price, ((Player) player).getDisplayName()));

                return true;
            } else {
                BSutils.sendMessage(player, String.format(
                        BetterShop.config.getString("insuffunds").
                        replace("<item>", "%1$s").
                        replace("<amt>", "%2$d").
                        replace("<total>", "%3$01.2f").
                        replace("<curr>", "%5$s").
                        replace("<priceper>", "%4$01.2f").
                        replace("<totcur>", "%6$s"), toBuy.coloredName(),
                        numToBuy, cost, price, BetterShop.config.currency,
                        BetterShop.iConomy.getBank().format(price)));
                return true;
            }
        } catch (Exception e) {
            BetterShop.Log("Error while debiting player.. possible iConomy crash? (attempting to reload iConomy)");
            BetterShop.Log(Level.SEVERE, e);
            BSutils.sendMessage(player, "Error while debiting player.. possible iConomy crash? ");
            BSutils.sendMessage(player, "Attempting reload.. ");
            try {
                PluginManager m = player.getServer().getPluginManager();
                Plugin icon = m.getPlugin("iConomy");

                m.disablePlugin(icon);
                m.enablePlugin(icon);

                BSutils.sendMessage(player, "Success! Please try again.. ");
                return true;
            } catch (Exception ex) {
                BetterShop.Log(Level.SEVERE, "Error");
                BetterShop.Log(Level.SEVERE, ex);
            }
            BSutils.sendMessage(player, "Failed.");
            return true;
        }

    }

    public boolean sell(CommandSender player, String[] s) {
        if (!BSutils.hasPermission(player, "BetterShop.user.sell", true) || BSutils.anonymousCheck(player)) {
            return true;
        } // "sell all", "sell all [item]" moved to own method ("sell [item] all" kept here)
        else if (s.length == 1 && s[0].equalsIgnoreCase("all")) {
            return sellall(player, null);
        } else if (s.length == 2) {
            if (s[0].equalsIgnoreCase("all")) {
                return sellall(player, new String[]{s[1]});
            } else if (s[1].equalsIgnoreCase("all")) {
                return sellall(player, new String[]{s[0]});
            }
        } else if (s.length == 0 || s.length > 2) {
            return false;
        }// initial check complete: set as last action
        usersellHistory.put(((Player) player).getDisplayName(), "shopsell " + argStr(s));
        // expected syntax: item [amount]

        Item toSell = Item.findItem(s[0]);
        if (toSell == null) {
            BSutils.sendMessage(player, String.format(
                    BetterShop.config.getString("unkitem").
                    replace("<item>", "%1$s"), s[0]));
            return false;
        } else if (toSell.isKit()) {
            BSutils.sendMessage(player, "Kits cannot be sold");
            return true;
        }
        double price = Double.NEGATIVE_INFINITY;
        try {
            price = BetterShop.pricelist.getSellPrice(toSell);
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
        }
        if (price < 0) {
            if (price == Double.NEGATIVE_INFINITY) {
                BSutils.sendMessage(player, "Error looking up price.. Attempting DB reload..");
                if (load(null)) {
                    // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
                    BSutils.sendMessage(player, "Success! Please try again.. ");
                } else {
                    BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
                }
            } else {
                BSutils.sendMessage(player, String.format(
                        BetterShop.config.getString("donotwant").
                        replace("<item>", "%1$s"), toSell.coloredName()));
            }
            return true;
        }

        // go through inventory & find how much user has

        PlayerInventory inv = ((Player) player).getInventory();
        int amtSold = 1, amtHas = 0;

        for (ItemStack i : inv.getContents()) {
            if (toSell.equals(i)) {
                //System.out.println("found: " + i);
                if (!toSell.IsTool() || (toSell.IsTool()
                        && (i.getDurability() == 0 || (i.getDurability() > 0 && BetterShop.config.buybacktools)))) {
                    amtHas += i.getAmount();
                }
            }
        }

        if (amtHas <= 0) {
            BSutils.sendMessage(player, "You Don't have any " + (toSell == null ? "Sellable Items" : toSell.coloredName()));
            return true;
        }
        if (s.length == 2) {
            if (s[1].equalsIgnoreCase("all")) {
                amtSold = amtHas;
            } else if (CheckInput.IsInt(s[1])) {
                amtSold = CheckInput.GetInt(s[1], 1);
                if (amtSold > amtHas) {
                    BSutils.sendMessage(player, String.format(
                            BetterShop.config.getString("donthave").
                            replace("<hasamt>", "%1$d").
                            replace("<amt>", "%2$d").
                            replace("<item>", "%3$s"), amtHas, amtSold, toSell.coloredName()));
                    amtSold = amtHas;
                } else if (amtSold <= 0) {
                    BSutils.sendMessage(player, BetterShop.config.getString("nicetry"));
                    return true;
                }
            } else {
                BSutils.sendMessage(player, s[1] + " is definitely not a number.");
            }
        } // else  amtSold = 1

        double total = 0;//amtSold * price;

        int itemsLeft = amtSold;

        for (int i = 0; i <= 35; ++i) {
            ItemStack thisSlot = inv.getItem(i);
            //if (toSell.equals(thisSlot)) {
            if (toSell.equals(thisSlot) && (!toSell.IsTool() || (toSell.IsTool()
                    && (thisSlot.getDurability() == 0
                    || (thisSlot.getDurability() > 0 && BetterShop.config.buybacktools))))) {
                int amt = thisSlot.getAmount(), tamt = amt;

                if (itemsLeft >= amt) {
                    inv.setItem(i, null);
                } else {
                    // remove only whats left to remove
                    inv.setItem(i, toSell.toItemStack(amt - itemsLeft));
                    amt = itemsLeft;
                }
                if (toSell.IsTool()) {
                    //System.out.println("tool with " + thisSlot.getDurability() +"/"+ toSell.MaxDamage());
                    total += (price * (1 - ((double) thisSlot.getDurability() / toSell.MaxDamage()))) * amt;
                } else {
                    total += price * amt;
                }
                itemsLeft -= tamt;

                if (itemsLeft <= 0) {
                    break;
                }
            }
        }

        try {
            // items are removed: pay player
            BSutils.credit(player, total);
        } catch (Exception e) {
            BetterShop.Log("Error while crediting player.. possible iConomy crash? (attempting to reload iConomy)");
            BetterShop.Log(Level.SEVERE, e);
            BSutils.sendMessage(player, "Error while crediting player.. possible iConomy crash? ");
            BSutils.sendMessage(player, "Attempting reload.. ");
            try {
                PluginManager m = player.getServer().getPluginManager();
                Plugin icon = m.getPlugin("iConomy");

                m.disablePlugin(icon);
                m.enablePlugin(icon);

                BSutils.sendMessage(player, "Success! Please try again.. ");
                return true;
            } catch (Exception ex) {
                BetterShop.Log(Level.SEVERE, "Error");
                BetterShop.Log(Level.SEVERE, ex);
            }
            BSutils.sendMessage(player, "Failed.");
            return true;
        }
        BSutils.sendMessage(player, String.format(BetterShop.config.getString("sellmsg").
                replace("<item>", "%1$s").
                replace("<amt>", "%2$d").
                replace("<priceper>", "%3$01.2f").
                replace("<total>", "%4$01.2f").
                replace("<curr>", "%5$s").
                replace("<totcur>", "%6$s"),
                toSell.coloredName(), amtSold, total / amtSold, total, BetterShop.config.currency, BetterShop.iConomy.getBank().format(total)));
        //price
        if (BetterShop.config.publicmarket && BetterShop.config.hasString("publicsellmsg")) {
            BSutils.broadcastMessage(player, String.format(BetterShop.config.getString("publicsellmsg").
                    replace("<item>", "%1$s").
                    replace("<amt>", "%2$d").
                    replace("<priceper>", "%3$01.2f").
                    replace("<total>", "%4$01.2f").
                    replace("<curr>", "%5$s").
                    replace("<totcur>", "%6$s").
                    replace("<player>", "%7$s"),
                    toSell.coloredName(), amtSold, total / amtSold, total,
                    BetterShop.config.currency, BetterShop.iConomy.getBank().format(total), ((Player) player).getDisplayName()), false);

        }

        BetterShop.transactions.addRecord(new UserTransaction(
                toSell, true, amtSold, total / amtSold, ((Player) player).getDisplayName()));

        return true;
    }

    public boolean sellall(CommandSender player, String[] s) {
        Item toSell[] = null;
        boolean onlyInv = false;
        if (s != null) {
            if (!BSutils.hasPermission(player, "BetterShop.user.sell", true) || BSutils.anonymousCheck(player)) {
                return true;
            } /*else if (s.length < 1) {
            return false;
            }*/
            if (s.length > 0) {
                // expected syntax: [inv] [item [item [item [...]]]]
                int st = 0;
                if (s[0].equalsIgnoreCase("inv")) {
                    onlyInv = true;
                    st = 1;
                }
                toSell = new Item[s.length - st];
                for (int i = st; i < s.length; ++i) {
                    toSell[i - st] = Item.findItem(s[i]);
                    if (toSell[i - st] == null) {
                        BSutils.sendMessage(player, String.format(
                                BetterShop.config.getString("unkitem").
                                replace("<item>", "%1$s"), s[0]));
                        return false;
                    }
                }
            } // "[All Sellable]"
        }// initial check complete: set as last action
        usersellHistory.put(((Player) player).getDisplayName(), "shopsellall " + argStr(s));

        boolean err = false;
        PlayerInventory inv = ((Player) player).getInventory();
        ItemStack[] its = inv.getContents();
        int amtHas = 0;
        double total = 0;

        try {
            if (toSell != null && toSell.length > 0) {
                // check items not for sale
                ArrayList<String> notwant = new ArrayList<String>();
                boolean sendkitmsg = false; // if has already sent kit notification
                for (Item it : toSell) {
                    if (!BetterShop.pricelist.isForSale(it)) {
                        notwant.add(it.coloredName());
                        it = null;
                    } else if (it.isKit()) {
                        notwant.add(it.coloredName());
                        if (!sendkitmsg) {
                            BSutils.sendMessage(player, "Kits cannot be sold");
                            sendkitmsg = true;
                        }
                        return true;
                    }
                }
                if (notwant.size() > 0) {
                    BSutils.sendMessage(player, String.format(
                            BetterShop.config.getString("donotwant").
                            replace("<item>", "%1$s"), "(" + argStr(notwant.toArray(new String[0]), ", ") + ")"));
                    if (notwant.size() == toSell.length) {
                        return true;
                    }
                }
            }
            // go through inventory & find how much user has
            if (toSell == null || toSell.length == 0) {
                for (int i = (onlyInv ? 9 : 0); i <= 35; ++i) {
                    if (BetterShop.pricelist.isForSale(its[i])) {
                        amtHas += its[i].getAmount();
                    }
                }
            } else {
                for (int i = (onlyInv ? 9 : 0); i <= 35; ++i) {
                    for (Item it : toSell) {
                        if (it != null && it.equals(its[i])) {//  && BetterShop.pricelist.isForSale(it)) {
                            amtHas += its[i].getAmount();
                            break; // stop checking items toSell & continue to next inventory slot
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
            err = true;
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
            err = true;
        }
        if (err) {
            BSutils.sendMessage(player, "Error looking up an item.. Attempting DB reload..");
            if (load(null)) {
                // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
                BSutils.sendMessage(player, "Success! Please try again.. ");
            } else {
                BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
            }
            return true;
        }

        if (amtHas <= 0) {
            BSutils.sendMessage(player, "You Don't have any " + (toSell == null || toSell.length == 0 ? "Sellable Items"
                    : (toSell.length == 1 ? toSell[0].coloredName() : "of those items")));
            return true;
        }

        int amtSold = 0;
        LinkedList<UserTransaction> transactions = new LinkedList<UserTransaction>();
        // now scan through & remove the items
        /*
        if (toSell != null && toSell.length == 1) {
        transactions.add(new UserTransaction(toSell[0], true, amtHas, ((Player) player).getDisplayName()));
        //amtSold = amtHas;
        }*/
        try {
            if (toSell == null || toSell.length == 0) {
                for (int i = (onlyInv ? 9 : 0); i <= 35; ++i) {
                    Item it = Item.findItem(its[i]);
                    if (it != null) {
                        if (BetterShop.pricelist.isForSale(it) && (!it.IsTool()
                                || (its[i].getDurability() == 0 || BetterShop.config.buybacktools))) {
                            int amt = its[i].getAmount();

                            if (it.IsTool()) {
                                total += (BetterShop.pricelist.getSellPrice(it) * (1 - ((double) its[i].getDurability() / it.MaxDamage()))) * amt;
                            } else {
                                total += BetterShop.pricelist.getSellPrice(it) * amt;
                            }
                            amtSold += amt;
                            boolean in = false;
                            for (UserTransaction t : transactions) {
                                if (t.equals(its[i])) {
                                    in = true;
                                    t.amount += its[i].getAmount();
                                    break;
                                }
                            }
                            if (!in) {
                                transactions.add(new UserTransaction(it, true, its[i].getAmount(),
                                        BetterShop.pricelist.getSellPrice(it),
                                        ((Player) player).getDisplayName()));
                            }
                            inv.setItem(i, null);
                        }
                    }//else //null usually == AIR x -1
                    //    BetterShop.Log("Error: searching for an item that should exist, but isn't in db\n" + its[i]);
                }
            } else {
                for (int i = (onlyInv ? 9 : 0); i <= 35; ++i) {
                    for (Item it : toSell) {
                        if (it != null && it.equals(its[i])) {
                            if (!it.IsTool() || (its[i].getDurability() == 0 || BetterShop.config.buybacktools)) {
                                int amt = its[i].getAmount();

                                if (it.IsTool()) {
                                    total += (BetterShop.pricelist.getSellPrice(it) * (1 - ((double) its[i].getDurability() / it.MaxDamage()))) * amt;
                                } else {
                                    total += BetterShop.pricelist.getSellPrice(it) * amt;
                                }
                                amtSold += amt;
                                boolean in = false;
                                for (UserTransaction t : transactions) {
                                    if (t.equals(it)) {
                                        in = true;
                                        t.amount += its[i].getAmount();
                                        break;
                                    }
                                }
                                if (!in) {
                                    transactions.add(new UserTransaction(it, true, its[i].getAmount(),
                                            BetterShop.pricelist.getSellPrice(it),
                                            ((Player) player).getDisplayName()));
                                }
                                inv.setItem(i, null);

                            }
                            break;// stop checking against item list & continue to next inventory slot
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
            BSutils.sendMessage(player, "Error looking up an item");
            BSutils.credit(player, total); // credit anything that could be sold
            return true;
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
            BSutils.sendMessage(player, "Error looking up an item");
            BSutils.credit(player, total); // credit anything that could be sold
            return true;
        }

        BSutils.credit(player, total);
        String itemN = ""; // "(All Sellable)"
        if (toSell != null && toSell.length == 1) {
            itemN = toSell[0].coloredName();
        } else {
            itemN = "(";
            for (UserTransaction it : transactions) {
                itemN += it.GetItem().coloredName() + " ";
            }
            itemN = itemN.trim().replace(" ", ", ") + ")";
        }

        BSutils.sendMessage(player, String.format(BetterShop.config.getString("sellmsg").
                replace("<item>", "%1$s").
                replace("<amt>", "%2$d").
                replace("<priceper>", "%3$01.2f").
                replace("<total>", "%4$01.2f").
                replace("<curr>", "%5$s").
                replace("<totcur>", "%6$s"),
                itemN, amtSold, total / amtSold, total, BetterShop.config.currency, BetterShop.iConomy.getBank().format(total)));

        if (BetterShop.config.publicmarket && BetterShop.config.hasString("publicsellmsg")) {
            BSutils.broadcastMessage(player, String.format(BetterShop.config.getString("publicsellmsg").
                    replace("<item>", "%1$s").
                    replace("<amt>", "%2$d").
                    replace("<priceper>", "%3$01.2f").
                    replace("<total>", "%4$01.2f").
                    replace("<curr>", "%5$s").
                    replace("<totcur>", "%6$s").
                    replace("<player>", "%7$s"),
                    itemN, amtSold, total / amtSold, total,
                    BetterShop.config.currency, BetterShop.iConomy.getBank().format(total), ((Player) player).getDisplayName()), false);

        }

        for (UserTransaction t : transactions) {
            BetterShop.transactions.addRecord(t);
        }
        return true;
    }

    public boolean listkits(CommandSender player, String[] s) {
        try {
            BSutils.sendMessage(player, "Kit listing:");
            String kitNames = "";
            for (Item i : BetterShop.pricelist.getItems(BSutils.hasPermission(player, "BetterShop.admin.illegal"))) {
                if (i.isKit()) {
                    if (kitNames.length() > 0) {
                        kitNames += ", ";
                    }
                    kitNames += i.coloredName();
                }
            }
            BSutils.sendMessage(player, kitNames);
            return true;
        } catch (SQLException ex) {
            BetterShop.Log(Level.SEVERE, ex);
        } catch (Exception ex) {
            BetterShop.Log(Level.SEVERE, ex);
        }
        BSutils.sendMessage(player, "Error looking up an item.. Attempting DB reload..");
        if (load(null)) {
            // ask to try command again.. don't want accidental infinite recursion & don't want to plan for recursion right now
            BSutils.sendMessage(player, "Success! Please try again.. ");
        } else {
            BSutils.sendMessage(player, "\u00A74Failed! Please let an OP know of this error");
        }
        return true;
    }

    public static String argStr(String[] s) {
        return argStr(s, " ");
    }

    public static String argStr(String[] s, String sep) {
        String ret = "";
        if (s != null) {
            for (int i = 0; i < s.length; ++i) {
                ret += s[i];
                if (i + 1 < s.length) {
                    ret += sep;
                }
            }
        }
        return ret;
    }
}
