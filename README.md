# better-clear
**BetterClear** is a Minecraft plugin that enhances the vanilla `/clear` command,
allowing administrators to clear players' inventories with precision by category
(e.g., blocks, tools, armor) and specific areas (hotbar, inventory, armor slots).

## Features

- **Targeted Item clearing by category:**
    - Blocks
    - Items
    - Armor
    - Tools
    - Every item

- **Targeted clearing by inventory section:**
    - Hotbar
    - Main Inventory
    - Armor Slots
    - Offhand
    - Everything  
- **Testet versions**: 1.15 - 1-21

## Installation

1. Download the latest version of the plugin from [SpigotMC]() or from [GitHub](https://github.com/AwayAllay/better-clear/releases).
2. Move the `.jar` file into your server's `/plugins` folder.
3. Restart the server or load the plugin using `/reload`.

## Commands

- `/betterClear <name> <category> <section>`: Clears the specified player's inventory by category and section.  
    - Categories:
        - `blocks`: Clear only blocks.
        - `tools`: Clear only tools.
        - `armor`: Clear only armor.
        - `items`: Clear everything not specified above.
        - `*`: Clear everything.
    - Sections:
        - `hotbar`: Clear only the hotbar.
        - `armor`: Clear only the armor slots.
        - `inventory`: Clear the main inventory (excluding hotbar and armor slots).
        - `offhand`: Clear only the offhand.
        - `*`: Clear every slot.

## Permissions

- `betterclear.clear`: Allows clearing yourself.
- `betterclear.clear.hotbar`: Allows clearing other players' hotbars.
- `betterclear.clear.armor`: Allows clearing other players' armor slots.
- `betterclear.clear.inventory`: Allows clearing other players' main inventory space (no hotbar or armor slots).
- `betterclear.clear.offhand`: Allows clearing the other players' offhand.
- `betterclear.clear.all`: Allows clearing other players' inventory completely.

## My Version is Not Supported

If you can't find a version of this plugin that is working for your server:


- Create an issue in the [Issues tab](https://github.com/AwayAllay/better-clear/issues) and i will fix it as soon as possible.
- If you are a developer you can also create a [Pull request](https://github.com/AwayAllay/better-clear/pulls).
