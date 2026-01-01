package yourscraft.jasdewstarfield.noenchantment.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    /**
     * 拦截战利品和生物生成的附魔逻辑。
     * 对应方法: enchantItemFromProvider
     * 作用: 只要游戏尝试从 Provider (战利品表配置) 给物品附魔，直接取消操作。
     */
    @Inject(method = "enchantItemFromProvider", at = @At("HEAD"), cancellable = true)
    private static void cancelLootEnchantment(
            ItemStack stack,
            RegistryAccess registries,
            ResourceKey<EnchantmentProvider> key,
            DifficultyInstance difficulty,
            RandomSource random,
            CallbackInfo ci
    ) {
        if (!NoEnchantmentConfig.INSTANCE.disableLootEnchantments.get()) {
            return;
        }
        // 直接取消，不执行任何附魔逻辑
        ci.cancel();
    }

    /**
     * 拦截随机附魔计算逻辑。
     * 对应方法: selectEnchantment
     * 作用: 无论是附魔台还是其他随机机制，只要请求“计算有哪些附魔”，永远返回空列表。
     */
    @Inject(method = "selectEnchantment", at = @At("HEAD"), cancellable = true)
    private static void cancelRandomEnchantmentCalculation(
            RandomSource random,
            ItemStack stack,
            int level,
            Stream<Holder<Enchantment>> possibleEnchantments,
            CallbackInfoReturnable<List<EnchantmentInstance>> cir
    ) {
        if (!NoEnchantmentConfig.INSTANCE.disableRandomEnchantments.get()) {
            return;
        }
        // 设置返回值为不可变的空列表，并取消原方法
        cir.setReturnValue(Collections.emptyList());
    }
}
