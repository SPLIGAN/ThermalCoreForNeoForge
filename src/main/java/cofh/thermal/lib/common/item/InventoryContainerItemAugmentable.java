package cofh.thermal.lib.common.item;

import cofh.core.common.item.IAugmentableItem;
import cofh.core.common.item.InventoryContainerItem;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.util.CoFHItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntSupplier;

import static cofh.core.util.helpers.AugmentableHelper.getPropertyWithDefault;
import static cofh.core.util.helpers.AugmentableHelper.setAttributeFromAugmentMax;
import static cofh.lib.util.constants.NBTTags.*;

public class InventoryContainerItemAugmentable extends InventoryContainerItem implements IAugmentableItem {

    protected IntSupplier numSlots = () -> 0;
    protected BiPredicate<ItemStack, List<ItemStack>> augValidator = (e, f) -> true;

    public InventoryContainerItemAugmentable(Properties builder, int slots) {

        super(builder, slots);
    }

    public InventoryContainerItemAugmentable setNumSlots(IntSupplier numSlots) {

        this.numSlots = numSlots;
        return this;
    }

    public InventoryContainerItemAugmentable setAugValidator(BiPredicate<ItemStack, List<ItemStack>> augValidator) {

        this.augValidator = augValidator;
        return this;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {

        return Math.round(super.getEnchantmentValue(stack) * getBaseMod(stack));
    }

    protected float getBaseMod(ItemStack stack) {

        return getPropertyWithDefault(stack, TAG_AUGMENT_BASE_MOD, 1.0F);
    }

    protected void setAttributesFromAugment(ItemStack container, CompoundTag augmentData) {

        CompoundTag root = CoFHItemData.getTag(container);
        if (!root.contains(TAG_PROPERTIES, net.minecraft.nbt.Tag.TAG_COMPOUND)) {
            return;
        }
        CompoundTag subTag = root.getCompound(TAG_PROPERTIES);
        setAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_BASE_MOD);
        setAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_ITEM_STORAGE);
        setAttributeFromAugmentMax(subTag, augmentData, TAG_AUGMENT_ITEM_CREATIVE);
    }

    // region IInventoryContainerItem
    @Override
    public int getContainerSlots(ItemStack container) {

        float base = getPropertyWithDefault(container, TAG_AUGMENT_BASE_MOD, 1.0F);
        float mod = getPropertyWithDefault(container, TAG_AUGMENT_ITEM_STORAGE, 1.0F);
        return Math.round(slots * mod * base);
    }
    // endregion

    // region IAugmentableItem
    @Override
    public int getAugmentSlots(ItemStack augmentable) {

        return numSlots.getAsInt();
    }

    @Override
    public boolean validAugment(ItemStack augmentable, ItemStack augment, List<ItemStack> augments) {

        return augValidator.test(augment, augments);
    }

    @Override
    public void updateAugmentState(ItemStack container, List<ItemStack> augments) {

        CoFHItemData.updateTag(container, tag -> tag.put(TAG_PROPERTIES, new CompoundTag()));
        for (ItemStack augment : augments) {
            CompoundTag augmentData = AugmentDataHelper.getAugmentData(augment);
            if (augmentData == null) {
                continue;
            }
            setAttributesFromAugment(container, augmentData);
        }
    }
    // endregion
}
