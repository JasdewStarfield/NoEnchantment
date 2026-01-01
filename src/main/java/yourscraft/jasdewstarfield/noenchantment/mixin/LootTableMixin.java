package yourscraft.jasdewstarfield.noenchantment.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

import java.util.function.Consumer;

import static yourscraft.jasdewstarfield.noenchantment.common.CommonEvents.processItem;

@Mixin(LootTable.class)
public class LootTableMixin {
    /**
     * 拦截 getRandomItems 方法的 consumer 参数。
     * 这个 consumer 是战利品生成后接收物品的回调。
     * 我们将其替换为自己的代理，先清洗物品，再传给原 consumer。
     */
    @ModifyVariable(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private Consumer<ItemStack> interceptLootOutput(Consumer<ItemStack> originalConsumer) {
        // 1. 检查配置，如果未开启“禁用战利品附魔”，则直接放行
        if (!NoEnchantmentConfig.INSTANCE.disableLootEnchantments.get()) {
            return originalConsumer;
        }

        // 2. 返回一个新的 Consumer，作为中间层;将清洗后的物品传给原始接收者 (放入箱子或给玩家)
        return (stack) -> {
            if (!stack.isEmpty()) {
                ItemStack finalStack = processItem(stack);
                originalConsumer.accept(finalStack);
            }else {
                originalConsumer.accept(stack);
            }
        };
    }
}
