
package com.lukaos187.betterclear.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**Represents a specific section of the inventory e.g. the hotbar.*/
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

    /**Returns a List<Integer> slots of the corresponding inventory slots of this section.*/
    public List<Integer> getSlots() {
        return slots;
    }
}
/**A class to manage the clearing conditions of the inventory. Initialized with the player as the sender of the command
 * and the String[] args as the command arguments. The arguments have to be correct otherwise this fails to do his job.
 * In this plugin the conditions for the arguments have been tested in the Clear class before initializing the
 * ClearManager.*/
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
            clearInventory(item -> item.getType().isBlock());
        }
        else if (typeToClear.equalsIgnoreCase("armor")) {
            clearInventory(item -> isArmor(item.getType()));
        }
        else if (typeToClear.equalsIgnoreCase("tools")){
            clearInventory(item -> isTool(item.getType()));
        }
        else if (typeToClear.equalsIgnoreCase("items")){
            clearInventory(item -> (!isTool(item.getType()) && !isArmor(item.getType()) && !item.getType().isBlock()));
        }
        else {
            targets.forEach(target -> target.getInventory().clear());
        }

        if (targets.size() > 1){
            sender.sendMessage(ChatColor.WHITE + capitalizeFirstLetter(placeToClear.toUpperCase()) + ChatColor.GRAY + " of " + ChatColor.WHITE + "everyone " + ChatColor.GRAY + "has been cleared of " + ChatColor.WHITE + typeToClear + ChatColor.GRAY + ".");
        }else {
            sender.sendMessage(ChatColor.WHITE + capitalizeFirstLetter(placeToClear.toUpperCase()) + ChatColor.GRAY + " of " + ChatColor.WHITE + targets.get(0).getName() + ChatColor.GRAY + " has been cleared of " + ChatColor.WHITE + typeToClear + ChatColor.GRAY + ".");
        }
    }

    /**Capitalizes the first letter of the given String and returns the formatted String.*/
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**Clears the inventory of the player. The given Predicate acts as the condition under which the items get cleared.
     *  e.g. item -> isArmor(item) //all the items that are armor get cleared.*/
    private void clearInventory(Predicate<ItemStack> test){

        targets.forEach(target -> slotsToClear.forEach(slot -> {

            ItemStack item = target.getInventory().getItem(slot);
            if (item != null && test.test(item)){
                target.getInventory().clear(slot);
            }
        }));
    }

    /**Tests if the Material is a tool*/
    private boolean isTool(Material material) {
        return material.name().endsWith("_PICKAXE") ||
                material.name().endsWith("_AXE") ||
                material.name().endsWith("_SHOVEL") ||
                material.name().endsWith("_HOE") ||
                material.name().endsWith("_SWORD");
    }

    /**Tests if the Material is an armour-piece*/
    private boolean isArmor(Material material) {
        return material.name().endsWith("_HELMET") ||
                material.name().endsWith("_CHESTPLATE") ||
                material.name().endsWith("_LEGGINGS") ||
                material.name().endsWith("_BOOTS");
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
