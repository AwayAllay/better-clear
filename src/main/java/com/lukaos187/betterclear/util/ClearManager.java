
package com.lukaos187.betterclear.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
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
    private final List<Player> targets;
    private final String typeToClear;
    private final String placeToClear;
    private List<Integer> slotsToClear;

    public ClearManager(String[] args, Player sender) {
        this.sender = sender;
        targets = addTargets(args);
        this.typeToClear = args[1];
        this.placeToClear = args[2];
    }

    /**Adds all the targets to the targets list.*/
    private List<Player> addTargets(String[] args) {
        if (args[0].equalsIgnoreCase("*")){
           return new ArrayList<>(Bukkit.getOnlinePlayers());
        }
        else {
           return Arrays.asList(Bukkit.getPlayer(args[0]));
        }
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
            targets.forEach(target -> target.getInventory().clear());
        }

        if (targets.size() > 1){
            sender.sendMessage(ChatColor.GRAY + placeToClear.toUpperCase() + " of " + ChatColor.WHITE + "everyone " + ChatColor.GRAY + "has been cleared of " + ChatColor.WHITE + typeToClear + ChatColor.GRAY + ".");
        }else {
            sender.sendMessage(ChatColor.GRAY + placeToClear.toUpperCase() + " of " + ChatColor.WHITE + targets.get(0).getName() + ChatColor.GRAY + " has been cleared of " + ChatColor.WHITE + typeToClear + ChatColor.GRAY + ".");
        }
    }


    /**Clears all the items that are not armour, blocks or tools from the given slots.*/
    private void clearItems() {

        targets.forEach(target -> {

            slotsToClear.forEach(slot -> {

                ItemStack item = target.getInventory().getItem(slot);
                if (item != null && !isTool(item.getType()) && !isArmor(item.getType()) && !item.getType().isBlock()){

                    target.getInventory().clear(slot);

                }
            });

        });
    }

    /**Clears all the tools from the given slots.*/
    private void clearTools() {

        targets.forEach(target -> {

            slotsToClear.forEach(slot -> {

                ItemStack item = target.getInventory().getItem(slot);
                if (item != null && isTool(item.getType())) {

                    target.getInventory().clear(slot);

                }
            });
        });
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

        targets.forEach(target -> {

            slotsToClear.forEach(slot -> {

                ItemStack item = target.getInventory().getItem(slot);
                if (item != null && isArmor(item.getType())){

                    target.getInventory().clear(slot);

                }
            });

        });
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

        targets.forEach(target -> {

            slotsToClear.forEach(slot -> {

                ItemStack item = target.getInventory().getItem(slot);
                if (item != null && item.getType().isBlock()){

                    target.getInventory().clear(slot);

                }
            });

        });

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
