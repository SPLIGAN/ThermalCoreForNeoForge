package cofh.thermal.core.client.event;

import cofh.core.client.model.SimpleModel;
import cofh.thermal.core.client.renderer.entity.layers.FestiveLayer;
import cofh.thermal.core.client.renderer.model.*;
import cofh.thermal.core.common.config.ThermalClientConfig;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

public class TCoreClientSetupEvents {

    private TCoreClientSetupEvents() {

    }

    @SuppressWarnings ({"rawtypes", "unchecked"})
    @SubscribeEvent
    public static void addRenderLayers(final EntityRenderersEvent.AddLayers event) {

        if (!ThermalClientConfig.festiveVanillaMobs.get()) {
            return;
        }
        var blaze = event.getRenderer(EntityType.BLAZE);
        if (blaze instanceof LivingEntityRenderer livingEntityRenderer) {
            livingEntityRenderer.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) blaze, -0.15F, 0.9F));
        }
        //        var creeper = event.getRenderer(EntityType.CREEPER);
        //        if (creeper instanceof LivingEntityRenderer<Creeper, ? extends EntityModel<Creeper>>) {
        //            creeper.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) creeper, 0.0F, 0.9F));
        //        }
        var enderman = event.getRenderer(EntityType.ENDERMAN);
        if (enderman instanceof LivingEntityRenderer livingEntityRenderer) {
            livingEntityRenderer.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) enderman, -1.15F, 0.9F));
        }
        //        var ghast = event.getRenderer(EntityType.GHAST);
        //        if (ghast instanceof LivingEntityRenderer<Ghast, ? extends EntityModel<Ghast>>) {
        //            ghast.addLayer(new FestiveLayer<>(event.getContext(), (RenderLayerParent) ghast, 0.75F, 1.5F));
        //        }
    }

    @SubscribeEvent
    public static void colorSetupBlock(final RegisterColorHandlersEvent.Block event) {

        // BlockColors colors = event.getBlockColors();

        // colors.register((state, reader, pos, tintIndex) -> (reader == null || pos == null) ? FoliageColors.getDefault() : BiomeColors.getFoliageColor(reader, pos), BLOCKS.get(ID_RUBBER_LEAVES));
    }

    @SubscribeEvent
    public static void registerModels(final RegisterGeometryLoaders event) {

        event.register(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "underlay"), new SimpleModel.Loader(UnderlayBakedModel::new));
        event.register(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "dynamo"), new SimpleModel.Loader(DynamoBakedModel::new));
        event.register(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "reconfigurable"), new SimpleModel.Loader(ReconfigurableBakedModel::new));
        event.register(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "energy_cell"), new SimpleModel.Loader(EnergyCellBakedModel::new));
        event.register(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "fluid_cell"), new SimpleModel.Loader(FluidCellBakedModel::new));
        event.register(ResourceLocation.fromNamespaceAndPath(ID_THERMAL, "item_cell"), new SimpleModel.Loader(ItemCellBakedModel::new));
    }

}
