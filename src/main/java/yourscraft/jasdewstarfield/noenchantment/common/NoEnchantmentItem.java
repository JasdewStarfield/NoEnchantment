package yourscraft.jasdewstarfield.noenchantment.common;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantment;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

import static yourscraft.jasdewstarfield.noenchantment.common.CommonEvents.processItem;

@EventBusSubscriber(modid = NoEnchantment.MODID)
public class NoEnchantmentItem {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (!NoEnchantmentConfig.INSTANCE.stripItemsOnEvent.get()) {
            return;
        }
        for (ItemEntity itemEntity : event.getDrops()) {
            ItemStack original = itemEntity.getItem();
            ItemStack processed = processItem(original);

            // 如果物品发生了变化（例如变成了普通书），更新掉落物实体
            if (original != processed) {
                itemEntity.setItem(processed);
            }
        }
    }

    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        if (!NoEnchantmentConfig.INSTANCE.stripItemsOnEvent.get()) {
            return;
        }
        ItemEntity itemEntity = event.getItemEntity();
        ItemStack original = itemEntity.getItem();
        ItemStack processed = processItem(original);
        if (original != processed) {
            itemEntity.setItem(processed);
        }
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        if (!NoEnchantmentConfig.INSTANCE.stripItemsOnEvent.get()) {
            return;
        }
        ItemEntity itemEntity = event.getEntity();
        ItemStack original = itemEntity.getItem();
        ItemStack processed = processItem(original);
        if (original != processed) {
            itemEntity.setItem(processed);
        }
    }
}
