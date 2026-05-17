package cofh.thermal.lib.util.recipes.internal;

import cofh.lib.api.inventory.IItemStackHolder;
import cofh.thermal.lib.util.recipes.IMachineInventory;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class DisenchantMachineRecipe extends BaseMachineRecipe {

    public DisenchantMachineRecipe(int energy, float experience) {

        super(energy, experience);
    }

    public DisenchantMachineRecipe(int energy, float experience, @Nullable List<ItemStack> inputItems, @Nullable List<FluidStack> inputFluids, @Nullable List<ItemStack> outputItems, @Nullable List<Float> chance, @Nullable List<FluidStack> outputFluids) {

        super(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
    }

    private int getEnchantmentXp(ItemStack stack) {

        int encXP = 0;
        ItemEnchantments map = EnchantmentHelper.getEnchantmentsForCrafting(stack);
        for (var entry : map.entrySet()) {
            if (!entry.getKey().is(EnchantmentTags.CURSE)) {
                encXP += entry.getKey().value().getMinCost(entry.getIntValue());
            }
        }
        return encXP;
    }

    // region IMachineRecipe
    @Override
    public float getXp(IMachineInventory inventory) {

        int encXP = 0;
        for (IItemStackHolder holder : inventory.inputSlots()) {
            encXP += getEnchantmentXp(holder.getItemStack());
        }
        return encXP + experience * inventory.getMachineProperties().getXpMod();
    }
    // endregion
}
