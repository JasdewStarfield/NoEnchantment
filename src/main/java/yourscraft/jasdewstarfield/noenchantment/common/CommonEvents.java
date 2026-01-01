package yourscraft.jasdewstarfield.noenchantment.common;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class CommonEvents {

    /**
     * 核心处理方法：移除附魔，并在需要时将附魔书转换为普通书。
     * @return 处理后的 ItemStack (可能是原来的对象，也可能是新创建的普通书)
     */
    public static ItemStack processItem(ItemStack stack) {
        if (stack.isEmpty()) return stack;

        // 1. 优先处理附魔书转换 (防止产生组件残留)
        if (stack.is(Items.ENCHANTED_BOOK)) {
            // 直接创建一个全新的普通书
            ItemStack Book = new ItemStack(Items.BOOK, stack.getCount());
            // 如果原书有自定义名字，把它复制过去
            if (stack.has(DataComponents.CUSTOM_NAME)) {
                Book.set(DataComponents.CUSTOM_NAME, stack.get(DataComponents.CUSTOM_NAME));
            }
            return Book;
        }

        // 2. 移除常规附魔
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments != null && !enchantments.isEmpty()) {
            stack.remove(DataComponents.ENCHANTMENTS);
        }

        return stack;
    }
}
