package yourscraft.jasdewstarfield.noenchantment.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    // 映射 AnvilMenu 中的 cost 字段 (用于存储当前的经验消耗)
    @Shadow
    @Final
    private DataSlot cost;

    /**
     * 拦截 mayPickup 方法。原版要求 cost > 0 才能取出物品。
     * 我们修改为：只要配置开启，且结果槽里有东西 (hasStack)，就允许取出，无视 cost。
     */
    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void allowFreePickup(Player player, boolean hasStack, CallbackInfoReturnable<Boolean> cir) {
        if (NoEnchantmentConfig.INSTANCE.freeAnvilRepair.get()) {
            // 直接返回 hasStack (如果有物品就能拿，不需要经验，不需要 cost > 0)
            cir.setReturnValue(hasStack);
        }
    }

    /**
     * 拦截 createResult 中的常量 40。
     * 原版逻辑：if (cost >= 40) item = empty;
     * 我们把 40 修改为 Integer.MAX_VALUE，这样永远不会触发“过于昂贵”的清空逻辑。
     */
    @ModifyConstant(method = "createResult", constant = @Constant(intValue = 40))
    private int removeRepairLimit(int originalLimit) {
        if (NoEnchantmentConfig.INSTANCE.freeAnvilRepair.get()) {
            return NoEnchantmentConfig.INSTANCE.freeAnvilRepair.get() ? Integer.MAX_VALUE : originalLimit;
        }
        return originalLimit;
    }

    /**
     * 拦截 createResult 方法。
     * 这是铁砧计算输出物品和消耗的核心方法。
     * 我们在它执行完所有计算后(RETURN)，强行把 cost 设为 0。
     */
    @Inject(method = "createResult", at = @At("RETURN"))
    private void onCreateResult(CallbackInfo ci) {
        if (NoEnchantmentConfig.INSTANCE.freeAnvilRepair.get()) {
            // 将消耗设为 0
            this.cost.set(0);
        }
    }
}
