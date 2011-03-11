package com.nhksos.jjfs85.BetterShop;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.nijiko.coelho.iConomy.system.*;

public class BSutils {

    static boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }

    static boolean credit(CommandSender player, double amount) {
        Account account = BetterShop.iConomy.getBank().getAccount(((Player) player).getName());
        account.add(amount);
        account.save();
        return true;
    }

    static boolean debit(CommandSender player, double amount) {
        Account account = BetterShop.iConomy.getBank().getAccount(((Player) player).getName());
        // don't allow account to go negative
        if (account.getBalance() < amount) {
            return false;
        }
        account.subtract(amount);
        account.save();
        return true;
    }

    static boolean hasPermission(CommandSender player, String node, boolean notify) {
        if(BetterShop.Permissions==null){
            // only ops have access to .admin
            if((node==null || node.length()<16) ||
                    (!node.substring(0,16).equalsIgnoreCase("BetterShop.admin") || player.isOp())){
                return true;
            }
        }else if(player instanceof Player){
            if (BetterShop.Permissions.Security.has((Player) player, node)) {
                return true;
            }
        }else{ // is ConsoleSender
            return true;
        }
        if (notify == true) {
            PermDeny(player, node);
        }
        return false;
    }

    static void PermDeny(CommandSender player, String node) {
        BSutils.sendMessage(player, String.format(BetterShop.config.getString("permdeny").replace("<perm>", "%1$s"), node));
    }

    static void sendMessage(CommandSender player, String s) {
        player.sendMessage(BetterShop.config.getString("prefix") + s);
    }
}
