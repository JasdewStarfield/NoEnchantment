# No Enchantment

**No Enchantment** is a Minecraft NeoForge mod designed to completely remove enchantment mechanics from the game.

For modpack authors looking to increase difficulty by disabling enchantments, or creating "low-magic/no-magic" modpacks, this is an ideal solution. The mod not only prohibits the generation of enchantments but also (optionally) actively converts existing enchanted items into normal items.

The author used Gemini to help with coding while building this mod. The core mechanics are written manually.

## 📦 Mod Info

- **Supported Version:** Minecraft 1.21.1 (NeoForge)
- **Author:** Jasdew Starfield
- **License:** MIT License

## ✨ Features

This mod employs multiple methods to ensure enchantments do not appear in the game. All features can be toggled freely:

1.  **Disable Loot Enchantments**
    * Items generated from chests, structure loot, or mob equipment will no longer carry enchantments.
    * Enchanted Books will be converted into normal Books.
    * Intercepts `LootPool` and `EnchantmentHelper` logic via Mixin.

2.  **Disable Random Enchantment Calculation**
    * Disables enchantments generated via fishing, villager trading, or other algorithms relying on random enchantment logic.
    * Intercepts `EnchantmentHelper` logic via Mixin.

3.  **Active Item Stripping (Fallback Option, Default: OFF)**
    * **Enchanted Book Conversion**: All "Enchanted Books" will automatically convert to normal "Books" (preserving custom names) when picked up, dropped, or spawned.
    * **Remove Enchantments**: Any enchanted items (swords, armor, etc.) will have their enchantment attributes forcibly removed when picked up, dropped, or spawned.

4.  **Disable Enchanting Table**
    * Players cannot open the Enchanting Table interface. Right-clicking the table will trigger no response.

5.  **Remove Anvil Experience Cost**
    * As compensation for the lack of Mending, using the Anvil requires no experience levels. This allows players to repair items freely.
    * Modifies `AnvilMenu` logic via Mixin.

6.  **JEI Support**
    * Hides Enchanted Books in the JEI panel.
    * Hides anvil enchanting recipes.
    * Hides the Grindstone recipe category.

**Please Note:** This mod only guarantees enchantment removal for the aspects mentioned above, which covers the vast majority of vanilla situations. Content added by other mods (e.g., directly crafting or obtaining Enchanted Books via specific methods) may still result in acquiring enchantments. Modpack authors may need to handle these edge cases manually.

## ⚙️ Configuration

The mod provides extensive configuration options, which can be adjusted in `config/noenchantment-common.toml`. By default, all features (except for the Item Stripping section) are enabled.

If you encounter any incompatibilities, please try disabling the Mixins first and report the Issue to us!

## 📄 License

This project is open-source under the MIT License.