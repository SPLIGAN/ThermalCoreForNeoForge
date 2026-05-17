package cofh.thermal.core.common.fluid;

import cofh.lib.common.fluid.FluidCoFH;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static cofh.lib.util.Utils.itemProperties;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.core.init.registries.ThermalCreativeTabs.toolsTab;
import static cofh.thermal.lib.util.ThermalIDs.ID_FLUID_HEAVY_OIL;

public class HeavyOilFluid extends FluidCoFH {

    private static HeavyOilFluid INSTANCE;

    public static HeavyOilFluid instance() {

        if (INSTANCE == null) {
            INSTANCE = new HeavyOilFluid();
        }
        return INSTANCE;
    }

    protected HeavyOilFluid() {

        super(FLUIDS, ID_FLUID_HEAVY_OIL);

        bucket = toolsTab(1000, ITEMS.register(bucket(ID_FLUID_HEAVY_OIL), () -> new BucketItem(stillFluid.get(), itemProperties().craftRemainder(Items.BUCKET).stacksTo(1))));
    }

    @Override
    protected BaseFlowingFluid.Properties fluidProperties() {

        return new BaseFlowingFluid.Properties(type(), stillFluid, flowingFluid).bucket(bucket);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final Supplier<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_HEAVY_OIL, () -> new FluidType(FluidType.Properties.create()
            .density(900)
            .viscosity(1600)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)) {

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {

            consumer.accept(new IClientFluidTypeExtensions() {

                private static final ResourceLocation
                        STILL = ResourceLocation.parse("thermal:block/fluids/heavy_oil_still"),
                        FLOW = ResourceLocation.parse("thermal:block/fluids/heavy_oil_flow");

                @Override
                public ResourceLocation getStillTexture() {

                    return STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {

                    return FLOW;
                }

            });
        }
    });

}
