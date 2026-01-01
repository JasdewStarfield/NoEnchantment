package yourscraft.jasdewstarfield.noenchantment;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NoEnchantmentConfig {
    public static final ModConfigSpec SPEC;
    public static final Config INSTANCE;

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);
        SPEC = pair.getRight();
        INSTANCE = pair.getLeft();
    }

    public static class Config {
        public final ModConfigSpec.BooleanValue disableLootEnchantments;
        public final ModConfigSpec.BooleanValue disableRandomEnchantments;
        public final ModConfigSpec.BooleanValue freeAnvilRepair;
        public final ModConfigSpec.BooleanValue stripItemsOnEvent;
        public final ModConfigSpec.BooleanValue disableEnchantingTable;
        public final ModConfigSpec.BooleanValue hideAnvilRecipesJei;
        public final ModConfigSpec.BooleanValue hideGrindstoneRecipesJei;
        public final ModConfigSpec.BooleanValue hideEnchantedBookIngredientsJei;

        Config(ModConfigSpec.Builder builder) {

            builder.comment("Mixins that inject into Minecraft enchantment system. Might cause incompatibilities.").push("mixin");

            disableLootEnchantments = builder
                    .comment("Whether to prevent enchantments from generating in loot tables and mob equipment.")
                    .comment("是否禁止战利品和生物生成时的附魔逻辑")
                    .define("disableLootEnchantments", true);

            disableRandomEnchantments = builder
                    .comment("Whether to prevent random enchantment calculation (e.g. fishing, enchanting table logic).")
                    .comment("是否禁止随机附魔计算，如钓鱼、附魔台运算")
                    .define("disableRandomEnchantments", true);

            freeAnvilRepair = builder
                    .comment("Whether to make anvil operations cost no experience and remove the 'Too Expensive' limit.")
                    .comment("是否让铁砧操作不消耗经验并移除“过于昂贵”的限制")
                    .define("freeAnvilRepair", true);

            builder.pop();

            builder.comment("Item postprocessing").push("item");

            stripItemsOnEvent = builder
                    .comment("Whether to actively strip enchantments from items when they spawn, drop, or are picked up.")
                    .comment("是否在物品掉落、拾取或生物生成时主动移除物品上的附魔")
                    .define("stripItemsOnEvent", true);

            builder.pop();

            builder.comment("Interactions with blocks").push("interaction");

            disableEnchantingTable = builder
                    .comment("Whether to block players from opening the Enchanting Table interface.")
                    .comment("是否禁止玩家打开附魔台界面")
                    .define("disableEnchantingTable", true);

            builder.pop();

            builder.comment("JEI Plugin").push("jei");

            hideAnvilRecipesJei = builder
                    .comment("Whether to hide anvil enchanting recipes in JEI.")
                    .comment("是否在 JEI 中隐藏铁砧附魔配方")
                    .define("hideAnvilRecipesJei", true);

            hideGrindstoneRecipesJei = builder
                    .comment("Whether to hide grindstone recipes in JEI.")
                    .comment("是否在 JEI 中隐藏石磨配方")
                    .define("hideGrindstoneRecipesJei", true);

            hideEnchantedBookIngredientsJei = builder
                    .comment("Whether to hide Enchanted Books in JEI.")
                    .comment("是否在 JEI 中隐藏附魔书")
                    .define("hideEnchantedBookIngredientsJei", true);

            builder.pop();
        }
    }
}
