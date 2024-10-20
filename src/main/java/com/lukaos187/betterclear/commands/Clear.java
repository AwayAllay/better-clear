/*
 * WarpsAndHomes - Minecraft plugin
 * Copyright (C) 2024 AwayAllay
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package com.lukaos187.betterclear.commands;

import com.lukaos187.betterclear.util.ClearManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clear implements TabExecutor {
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

        List<String> args1 = getArgs1();
        List<String> args2 = getArgs2();

        if (args.length == 0 || args[0] == null || Bukkit.getPlayer(args[0]) == null){
            player.sendMessage(ChatColor.GRAY + "Please provide a valid player to clear.");
            return false;
        }
        if (args[1] == null || !args1.contains(args[1])){
            player.sendMessage(ChatColor.GRAY + "Please provide a valid itemType to clear.");
            return false;
        }
        if (args[2] == null || !args2.contains(args[2])){
            player.sendMessage(ChatColor.GRAY + "Please provide a valid place to clear.");
            return false;
        }

        return true;
    }

    /**Returns a list of the args[2] possibilities.*/
    private List<String> getArgs2() {
        List<String> toReturn = new ArrayList<>();
        toReturn.add("hotbar");
        toReturn.add("inventory");
        toReturn.add("armor");
        toReturn.add("offhand");
        toReturn.add("*");
        return toReturn;
    }

    /**Returns a list of the args[1] possibilities.*/
    private List<String> getArgs1() {
        List<String> toReturn = new ArrayList<>();
        toReturn.add("blocks");
        toReturn.add("armor");
        toReturn.add("tools");
        toReturn.add("items");
        toReturn.add("*");
        return toReturn;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player))
            return null;

        if (args.length == 1){

            List<String> tabComplete = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> tabComplete.add(player.getName()));

            return tabComplete;
        }
        else if(args.length == 2){

            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("blocks");
            tabComplete.add("armor");
            tabComplete.add("tools");
            tabComplete.add("items");
            tabComplete.add("*");

            return tabComplete;
        }
        else if (args.length == 3) {

            List<String> tabComplete = new ArrayList<>();
            tabComplete.add("hotbar");
            tabComplete.add("inventory");
            tabComplete.add("armor");
            tabComplete.add("offhand");
            tabComplete.add("*");

            return tabComplete;
        }

        return null;
    }
}
