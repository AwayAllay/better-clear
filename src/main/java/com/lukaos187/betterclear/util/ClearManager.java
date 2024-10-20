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
package com.lukaos187.betterclear.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

enum SLOTS{
    HOTBAR(Arrays.asList(0,1,2,3,4,5,6,7,8)),
    INVENTORY(Arrays.asList(9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35)),
    ARMOR(Arrays.asList(36,37,38,39)),
    OFFHAND(Arrays.asList(40)),
    ALL(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40));

    private final List<Integer> slots;

    SLOTS(List<Integer> slots) {
        this.slots = slots;
    }

    public List<Integer> getSlots() {
        return slots;
    }
}

public class ClearManager {

    private final Player sender;
    private final Player target;
    private final String typeToClear;
    private final String placeToClear;
    private List<Integer> slotsToClear;

    public ClearManager(String[] args, Player sender) {
        this.sender = sender;
        this.target = Bukkit.getPlayer(args[0]);
        this.typeToClear = args[1];
        this.placeToClear = args[2];
    }

    /**Clears the players inventory based on the command arguments*/
    public void clear(){

        slotsToClear = getSlots();

        if (typeToClear.equalsIgnoreCase("blocks")){
            clearBlocks();
        }
        else if (typeToClear.equalsIgnoreCase("armor")) {
            clearArmor();
        }
        else if (typeToClear.equalsIgnoreCase("tools")){
            clearTools();
        }
        else if (typeToClear.equalsIgnoreCase("items")){
            clearItems();
        }
        else {
            target.getInventory().clear();
            sender.sendMessage(ChatColor.GRAY + "The inventory of " + ChatColor.WHITE + target.getName() + ChatColor.GRAY + " has been cleared.");
        }

    }


    /**Clears all the items that are not armour, blocks or tools from the given slots.*/
    private void clearItems() {
        slotsToClear.forEach(slot -> {

            ItemStack item = target.getInventory().getItem(slot);
            if (item != null && !isTool(item.getType()) && !isArmor(item.getType()) && !item.getType().isBlock()){

                target.getInventory().clear(slot);

            }
        });

        sender.sendMessage(ChatColor.GRAY + "All the items(not armour, tools or blocks) have been removed from " + ChatColor.WHITE + target.getName()
                + ChatColor.GRAY + ".");
    }

    /**Clears all the tools from the given slots.*/
    private void clearTools() {
        slotsToClear.forEach(slot -> {

            ItemStack item = target.getInventory().getItem(slot);
            if (item != null && isTool(item.getType())){

                target.getInventory().clear(slot);

            }
        });

        sender.sendMessage(ChatColor.GRAY + "All the tools have been removed from " + ChatColor.WHITE + target.getName()
                + ChatColor.GRAY + ".");
    }

    /**Tests if the Material is a tool*/
    private boolean isTool(Material material) {
        return material.name().endsWith("_PICKAXE") ||
                material.name().endsWith("_AXE") ||
                material.name().endsWith("_SHOVEL") ||
                material.name().endsWith("_HOE") ||
                material.name().endsWith("_SWORD");
    }

    /**Clears all the armour from the given slots.*/
    private void clearArmor() {
        slotsToClear.forEach(slot -> {

            ItemStack item = target.getInventory().getItem(slot);
            if (item != null && isArmor(item.getType())){

                target.getInventory().clear(slot);

            }
        });

        sender.sendMessage(ChatColor.GRAY + "All the armour has been removed from " + ChatColor.WHITE + target.getName()
                + ChatColor.GRAY + ".");
    }

    /**Tests if the Material is an armour-piece*/
    private boolean isArmor(Material material) {
        return material.name().endsWith("_HELMET") ||
                material.name().endsWith("_CHESTPLATE") ||
                material.name().endsWith("_LEGGINGS") ||
                material.name().endsWith("_BOOTS");
    }

    /**Clears all the blocks from the given slots.*/
    private void clearBlocks() {
        slotsToClear.forEach(slot -> {

            ItemStack item = target.getInventory().getItem(slot);
            if (item != null && item.getType().isBlock()){

                target.getInventory().clear(slot);

            }
        });

        sender.sendMessage(ChatColor.GRAY + "All the blocks have been removed from " + ChatColor.WHITE + target.getName()
                + ChatColor.GRAY + ".");
    }

    /**Returns the slots which have to be cleared.*/
    private List<Integer> getSlots() {

        if (placeToClear.equalsIgnoreCase("hotbar")){
            return SLOTS.HOTBAR.getSlots();
        }
        else if (placeToClear.equalsIgnoreCase("inventory")) {
            return SLOTS.INVENTORY.getSlots();
        }
        else if (placeToClear.equalsIgnoreCase("armor")) {
            return SLOTS.ARMOR.getSlots();
        }
        else if (placeToClear.equalsIgnoreCase("offhand")) {
            return SLOTS.OFFHAND.getSlots();
        }
        else {
            return SLOTS.ALL.getSlots();
        }
    }
}
