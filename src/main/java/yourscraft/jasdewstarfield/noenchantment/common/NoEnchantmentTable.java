package yourscraft.jasdewstarfield.noenchantment.common;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantment;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

@EventBusSubscriber(modid = NoEnchantment.MODID)
public class NoEnchantmentTable {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!NoEnchantmentConfig.INSTANCE.disableEnchantingTable.get()) {
            return;
        }
        if (event.getLevel().getBlockState(event.getPos()).is(Blocks.ENCHANTING_TABLE)) {
            // 取消事件，阻止打开界面
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.FAIL);
        }
    }
}
