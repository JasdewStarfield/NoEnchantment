package yourscraft.jasdewstarfield.noenchantment.common;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CommonEvents {

    /**
     * 核心处理方法：移除附魔，并在需要时将附魔书转换为普通书。
     * @return 处理后的 ItemStack (可能是原来的对象，也可能是新创建的普通书)
     */
    public static ItemStack processItem(ItemStack stack) {
        // 1. 移除常规附魔
        if (stack.has(DataComponents.ENCHANTMENTS)) {
            stack.remove(DataComponents.ENCHANTMENTS);
        }

        // 2. 移除附魔书存储的附魔
        if (stack.is(Items.ENCHANTED_BOOK) && stack.has(DataComponents.STORED_ENCHANTMENTS)) {
            stack.remove(DataComponents.STORED_ENCHANTMENTS);
        }

        // 3. 移除修补惩罚
        if (stack.has(DataComponents.REPAIR_COST)) {
            stack.remove(DataComponents.REPAIR_COST);
        }

        // 4. 检测并转换：如果是附魔书，变成普通书
        if (stack.is(Items.ENCHANTED_BOOK) && !stack.has(DataComponents.STORED_ENCHANTMENTS)) {
            // 创建一个新的普通书 ItemStack，保持原来的数量
            return new ItemStack(Items.BOOK, stack.getCount());
        }

        return stack;
    }
}
