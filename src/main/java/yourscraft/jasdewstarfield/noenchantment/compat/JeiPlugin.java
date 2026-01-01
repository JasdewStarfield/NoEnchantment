package yourscraft.jasdewstarfield.noenchantment.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.vanilla.IJeiAnvilRecipe;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantment;
import yourscraft.jasdewstarfield.noenchantment.NoEnchantmentConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(NoEnchantment.MODID, "jei_plugin");
    }

    @Override
    public void onRuntimeAvailable(@NotNull IJeiRuntime jeiRuntime) {
        // 移除附魔书相关铁砧配方
        if (NoEnchantmentConfig.INSTANCE.hideAnvilRecipesJei.get()) {
            hideAnvilRecipes(jeiRuntime);
        }
        // 移除所有砂轮配方显示
        if (NoEnchantmentConfig.INSTANCE.hideGrindstoneRecipesJei.get()) {
            jeiRuntime.getRecipeManager().hideRecipeCategory(RecipeTypes.GRINDSTONE);
        }
        // 从右侧物品列表中隐藏所有附魔书
        if (NoEnchantmentConfig.INSTANCE.hideEnchantedBookIngredientsJei.get()) {
            hideEnchantedBookIngredients(jeiRuntime);
        }
    }

    private void hideAnvilRecipes(IJeiRuntime jeiRuntime) {
        // 获取所有已注册的铁砧配方
        List<IJeiAnvilRecipe> allAnvilRecipes = jeiRuntime.getRecipeManager()
                .createRecipeLookup(RecipeTypes.ANVIL)
                .get()
                .toList();

        List<IJeiAnvilRecipe> recipesToHide = new ArrayList<>();

        for (IJeiAnvilRecipe recipe : allAnvilRecipes) {
            // 检查右侧输入列表，看是否包含“附魔书”
            List<ItemStack> rightInputs = recipe.getRightInputs();

            boolean isEnchantmentRecipe = rightInputs.stream()
                    .anyMatch(stack -> stack.is(Items.ENCHANTED_BOOK));

            if (isEnchantmentRecipe) {
                recipesToHide.add(recipe);
            }
        }

        // 告诉 JEI 隐藏这些配方
        if (!recipesToHide.isEmpty()) {
            jeiRuntime.getRecipeManager().hideRecipes(RecipeTypes.ANVIL, recipesToHide);
        }
    }

    private void hideEnchantedBookIngredients(IJeiRuntime jeiRuntime) {
        IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();

        // 获取 JEI 中当前显示的所有 ItemStack 类型的物品
        Collection<ItemStack> allItems = ingredientManager.getAllIngredients(VanillaTypes.ITEM_STACK);

        List<ItemStack> booksToHide = new ArrayList<>();

        // 遍历筛选
        for (ItemStack stack : allItems) {
            if (stack.is(Items.ENCHANTED_BOOK)) {
                booksToHide.add(stack);
            }
        }

        // 执行移除操作
        if (!booksToHide.isEmpty()) {
            ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, booksToHide);
        }
    }
}
