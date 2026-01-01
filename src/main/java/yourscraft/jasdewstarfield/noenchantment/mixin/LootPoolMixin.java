package yourscraft.jasdewstarfield.noenchantment.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

import java.util.function.Consumer;

import static yourscraft.jasdewstarfield.noenchantment.common.CommonEvents.processItem;

@Mixin(LootPool.class)
public class LootPoolMixin {

    /**
     * 拦截 LootPool.addRandomItems 方法的 Consumer 参数。
     * 这是所有战利品生成（箱子、抽奖、奖励）的必经之路。
     * * 方法签名: void addRandomItems(Consumer<ItemStack> stackConsumer, LootContext context)
     * 我们拦截第 1 个参数 (索引 0 的参数是 Consumer，索引 1 是 Context)。
     */
    @ModifyVariable(
            method = "addRandomItems",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private Consumer<ItemStack> interceptLootOutput(Consumer<ItemStack> originalConsumer) {
        // 1. 检查配置，如果未开启“禁用战利品附魔”，则直接放行
        if (!NoEnchantmentConfig.INSTANCE.disableLootEnchantments.get()) {
            return originalConsumer;
        }

        // 2. 返回一个新的 Consumer，作为中间层;将清洗后的物品传给原始接收者 (放入箱子或给玩家)
        return (stack) -> {
            if (stack != null && !stack.isEmpty()) {
                ItemStack finalStack = processItem(stack);
                originalConsumer.accept(finalStack);
            }
        };
    }
}
