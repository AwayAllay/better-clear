
package com.lukaos187.betterclear.commands;

import com.lukaos187.betterclear.util.ClearManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**The command for clearing the players´ inventory in a better way. Can be called with /betterclear.
 *  The correct order to call this command is: /betterclear name category section.*/
public class Clear implements TabExecutor {

    private final List<String> category = Arrays.asList("blocks","armor","tools","items","*");
    private final List<String> section = Arrays.asList("hotbar","inventory","armor","offhand","*");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            return true;
        }
        if (!checkForNull(args, (Player) sender)){
            return true;
        }

        Player player = (Player) sender;

        if (hasPermissions(player, args)){
            new ClearManager(args, player).clear(); //Clears everything hoe it should be cleared :D
        }


        return true;
    }

    /**Checks if the player has all the necessary permissions.*/
    private boolean hasPermissions(Player player, String[] args) {

        if (player.hasPermission("betterclear.clear")) {

            if (Objects.equals(Bukkit.getPlayer(args[0]), player)){
                return true;
            }
            else if (args[0].equalsIgnoreCase("*") &&
                    player.hasPermission("betterclear.clear.everyone")) {
                return true;
            }
            else if (player.hasPermission("betterclear.clear.hotbar") &&
                    args[2].equalsIgnoreCase("hotbar")) {
                return true;
            }
            else if (player.hasPermission("betterclear.clear.armor") &&
                    args[2].equalsIgnoreCase("armor")) {
                return true;
            }
            else if (player.hasPermission("betterclear.clear.inventory") &&
                    args[2].equalsIgnoreCase("inventory")) {
                return true;
            }
            else if (player.hasPermission("betterclear.clear.all") &&
                    args[2].equalsIgnoreCase("*")) {
                return true;
            }
            else if (player.hasPermission("betterclear.clear.offhand") &&
                    args[2].equalsIgnoreCase("offhand")) {
                return true;
            }
            else {
                player.sendMessage(ChatColor.GRAY + "You do not have the permission to clear this place.");
                return false;
            }

        }
        else{
            player.sendMessage(ChatColor.GRAY + "Sorry, you do not have permission to use this command.");
        }

        return false;
    }

    /**Checks if the args needed are given or not. Returns true if everything is fine.*/
    private boolean checkForNull(String[] args, Player player) {

        if (args.length == 0 || args[0] == null || Bukkit.getPlayer(args[0]) == null){
            if (args.length == 0 || (args[0] != null && !args[0].equalsIgnoreCase("*"))){

                player.sendMessage(ChatColor.GRAY + "Please provide a valid" + ChatColor.WHITE + " player" + ChatColor.GRAY + " to clear.");
                player.sendMessage(ChatColor.WHITE + "---------------------------------");
                player.sendMessage(ChatColor.GRAY + "A precise description on how to use this plugin can be found here: "
                        + ChatColor.WHITE + "https://github.com/AwayAllay/better-clear#better-clear");
                player.sendMessage(ChatColor.WHITE + "---------------------------------");
                return false;
            }
        }
        if (args.length == 1 || (args[1] == null || !category.contains(args[1]))){
            player.sendMessage(ChatColor.GRAY + "Please provide a valid" + ChatColor.WHITE + " category" + ChatColor.GRAY + " to clear.");
            player.sendMessage(ChatColor.GRAY + "Use: " + ChatColor.WHITE + "/betterclear " + ChatColor.GRAY + "to get " + ChatColor.WHITE + "help" + ChatColor.GRAY + ".");
            return false;
        }
        if (args.length < 3 || (args[2] == null || !section.contains(args[2]))){
            player.sendMessage(ChatColor.GRAY + "Please provide a valid" + ChatColor.WHITE + " section" + ChatColor.GRAY + " to clear.");
            player.sendMessage(ChatColor.GRAY + "Use: " + ChatColor.WHITE + "/betterclear " + ChatColor.GRAY + "to get " + ChatColor.WHITE + "help" + ChatColor.GRAY + ".");
            return false;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player))
            return null;

        if (args.length == 1){

            List<String> tabComplete = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> tabComplete.add(player.getName()));
            tabComplete.add("*");

            return tabComplete;
        }
        else if(args.length == 2){
            return category;
        }
        else if (args.length == 3) {
            return section;
        }

        return null;
    }
}
