package cofh.thermal.core.common.event;

import cofh.core.compat.curios.CuriosProxy;
import cofh.core.util.filter.IFilterOptions;
import cofh.thermal.core.common.inventory.storage.SatchelMenu;
import cofh.thermal.core.common.item.DivingArmorItem;
import cofh.thermal.core.common.item.SatchelItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import static cofh.core.util.references.EnsorcIDs.ID_AIR_AFFINITY;
import static cofh.lib.util.Utils.getEnchantment;
import static cofh.lib.util.Utils.getMaxEquippedEnchantmentLevel;
import static cofh.lib.util.constants.ModIds.ID_ENSORCELLATION;
import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class TCoreCommonEvents {

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public static void handleBreakSpeedEvent(PlayerEvent.BreakSpeed event) {

        if (event.isCanceled()) {
            return;
        }
        Player player = event.getEntity();
        if (player.isEyeInFluid(FluidTags.WATER)) {
            boolean diveChest = player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivingArmorItem;
            if (getMaxEquippedEnchantmentLevel(player, Enchantments.AQUA_AFFINITY) <= 0 && diveChest) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
            boolean diveLegs = player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.onGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, getEnchantment(ID_ENSORCELLATION, ID_AIR_AFFINITY)) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        } else if (player.isInWater()) {
            boolean diveLegs = player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DivingArmorItem;
            if (!player.onGround() && diveLegs && (getMaxEquippedEnchantmentLevel(player, getEnchantment(ID_ENSORCELLATION, ID_AIR_AFFINITY)) <= 0)) {
                event.setNewSpeed(Math.max(event.getNewSpeed(), event.getOriginalSpeed() * 5.0F));
            }
        }
    }

    @SubscribeEvent
    public static void handleEntityItemPickup(final ItemEntityPickupEvent.Pre event) {

        Player player = event.getPlayer();
        if (player.containerMenu instanceof SatchelMenu || player.containerMenu instanceof IFilterOptions) {
            return;
        }
        Inventory inventory = player.getInventory();
        final boolean[] cancel = {false};
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (stack.getItem() instanceof SatchelItem) {
                cancel[0] |= SatchelItem.onItemPickup(event, stack);
            }
        }
        CuriosProxy.getAllWorn(player).ifPresent(c -> {
            for (int i = 0; i < c.getSlots(); ++i) {
                ItemStack stack = c.getStackInSlot(i);
                if (stack.getItem() instanceof SatchelItem) {
                    ItemStack satchelCopy = stack.copy();
                    cancel[0] |= SatchelItem.onItemPickup(event, satchelCopy);
                    c.setStackInSlot(i, satchelCopy);
                }
            }
        });
        if (cancel[0]) {
            event.setCanPickup(TriState.FALSE);
        }
    }

}
