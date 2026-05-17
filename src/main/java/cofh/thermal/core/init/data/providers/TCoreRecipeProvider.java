package cofh.thermal.core.init.data.providers;

import cofh.lib.common.conditions.FlagSetCondition;
import cofh.lib.init.data.RecipeProviderCoFH;
import cofh.lib.init.tags.ItemTagsCoFH;
import cofh.thermal.lib.util.references.ThermalTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.util.ThermalFlags.*;
import static cofh.thermal.lib.util.ThermalIDs.*;
import static net.minecraft.data.recipes.RecipeCategory.*;

public class TCoreRecipeProvider extends RecipeProviderCoFH {

    public TCoreRecipeProvider(PackOutput output) {

        super(output, ID_THERMAL);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;

        generateStorageRecipes(recipeOutput, reg.get(ID_CHARCOAL_BLOCK), Items.CHARCOAL);
        // generateStorageRecipes(recipeOutput, reg.get(ID_BAMBOO_BLOCK), Items.BAMBOO);
        generateStorageRecipes(recipeOutput, reg.get(ID_SUGAR_CANE_BLOCK), Items.SUGAR_CANE);
        generateStorageRecipes(recipeOutput, reg.get(ID_GUNPOWDER_BLOCK), Items.GUNPOWDER);

        generateStorageRecipes(recipeOutput, reg.get(ID_APPLE_BLOCK), Items.APPLE);
        generateStorageRecipes(recipeOutput, reg.get(ID_BEETROOT_BLOCK), Items.BEETROOT);
        generateStorageRecipes(recipeOutput, reg.get(ID_CARROT_BLOCK), Items.CARROT);
        generateStorageRecipes(recipeOutput, reg.get(ID_POTATO_BLOCK), Items.POTATO);

        generateStorageRecipes(recipeOutput, reg.get(ID_APATITE_BLOCK), reg.get("apatite"), commonTag("gems/apatite"));
        generateStorageRecipes(recipeOutput, reg.get(ID_CINNABAR_BLOCK), reg.get("cinnabar"), commonTag("gems/cinnabar"));
        generateStorageRecipes(recipeOutput, reg.get(ID_NITER_BLOCK), reg.get("niter"), commonTag("gems/niter"));
        generateStorageRecipes(recipeOutput, reg.get(ID_SULFUR_BLOCK), reg.get("sulfur"), commonTag("gems/sulfur"));

        generateGearRecipe(recipeOutput, reg.get("copper_gear"), Items.COPPER_INGOT, commonTag("ingots/copper"));
        generateGearRecipe(recipeOutput, reg.get("iron_gear"), Items.IRON_INGOT, commonTag("ingots/iron"));
        generateGearRecipe(recipeOutput, reg.get("gold_gear"), Items.GOLD_INGOT, commonTag("ingots/gold"));
        generateGearRecipe(recipeOutput, reg.get("netherite_gear"), Items.NETHERITE_INGOT, commonTag("ingots/netherite"));

        generateGearRecipe(recipeOutput, reg.get("diamond_gear"), Items.IRON_INGOT, commonTag("gems/diamond"));
        generateGearRecipe(recipeOutput, reg.get("emerald_gear"), Items.IRON_INGOT, commonTag("gems/emerald"));
        generateGearRecipe(recipeOutput, reg.get("quartz_gear"), Items.IRON_INGOT, commonTag("gems/quartz"));
        generateGearRecipe(recipeOutput, reg.get("lapis_gear"), Items.IRON_INGOT, commonTag("gems/lapis"));

        generateStorageRecipes(recipeOutput, reg.get(ID_SAWDUST_BLOCK), reg.get("sawdust"), ThermalTags.Items.SAWDUST);
        generateStorageRecipes(recipeOutput, reg.get(ID_COAL_COKE_BLOCK), reg.get("coal_coke"), ThermalTags.Items.COAL_COKE);
        generateStorageRecipes(recipeOutput, reg.get(ID_BITUMEN_BLOCK), reg.get("bitumen"), ThermalTags.Items.BITUMEN);
        generateStorageRecipes(recipeOutput, reg.get(ID_TAR_BLOCK), reg.get("tar"), ThermalTags.Items.TAR);
        generateStorageRecipes(recipeOutput, reg.get(ID_ROSIN_BLOCK), reg.get("rosin"), ThermalTags.Items.ROSIN);

        generateSmallStorageRecipes(recipeOutput, reg.get(ID_RUBBER_BLOCK), reg.get("rubber"));
        generateSmallStorageRecipes(recipeOutput, reg.get(ID_CURED_RUBBER_BLOCK), reg.get("cured_rubber"));
        generateSmallStorageRecipes(recipeOutput, reg.get(ID_SLAG_BLOCK), reg.get("slag"));
        generateSmallStorageRecipes(recipeOutput, reg.get(ID_RICH_SLAG_BLOCK), reg.get("rich_slag"));

        generateTypeRecipes(reg, recipeOutput, "signalum");
        generateTypeRecipes(reg, recipeOutput, "lumium");
        generateTypeRecipes(reg, recipeOutput, "enderium");

        generateSmeltingAndBlastingRecipes(reg, recipeOutput, "signalum", 0);
        generateSmeltingAndBlastingRecipes(reg, recipeOutput, "lumium", 0);
        generateSmeltingAndBlastingRecipes(reg, recipeOutput, "enderium", 0);

        generateSmeltingAndBlastingRecipes(reg, recipeOutput, reg.get("copper_dust"), Items.COPPER_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, recipeOutput, reg.get("iron_dust"), Items.IRON_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, recipeOutput, reg.get("gold_dust"), Items.GOLD_INGOT, 0.0F, "smelting", "_dust");
        generateSmeltingAndBlastingRecipes(reg, recipeOutput, reg.get("netherite_dust"), Items.NETHERITE_INGOT, 0.0F, "smelting", "_dust");

        generatePackingRecipe(recipeOutput, Items.COPPER_INGOT, reg.get("copper_nugget"), "_from_nuggets");
        generatePackingRecipe(recipeOutput, Items.NETHERITE_INGOT, reg.get("netherite_nugget"), "_from_nuggets");

        generateUnpackingRecipe(recipeOutput, Items.COPPER_INGOT, reg.get("copper_nugget"), "_from_ingot");
        generateUnpackingRecipe(recipeOutput, Items.NETHERITE_INGOT, reg.get("netherite_nugget"), "_from_ingot");

        generateSmeltingRecipe(reg, recipeOutput, Items.GRAVEL, reg.get("slag"), 0.1F, "smelting");
        generateSmeltingRecipe(reg, recipeOutput, reg.get("rubber"), reg.get("cured_rubber"), 0.2F, "smelting");

        ShapelessRecipeBuilder.shapeless(MISC, Items.GUNPOWDER, 4)
                .requires(Items.CHARCOAL)
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .requires(fromTags(ItemTagsCoFH.GEMS_SULFUR, ItemTagsCoFH.DUSTS_SULFUR))
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
                .save(recipeOutput, ID_THERMAL + ":gunpowder_4");

        generateAlloyRecipes(recipeOutput);
        generateArmorRecipes(recipeOutput);
        generateAugmentRecipes(recipeOutput);
        generateBasicRecipes(recipeOutput);
        generateChargeRecipes(recipeOutput);
        generateComponentRecipes(recipeOutput);
        generateExplosiveRecipes(recipeOutput);
        generateRockwoolRecipes(recipeOutput);
        generateSlagRecipes(recipeOutput);
        generateTileRecipes(recipeOutput);
    }

    // region HELPERS
    private void generateAlloyRecipes(RecipeOutput consumer) {

        var reg = ITEMS;

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("signalum_dust"), 4)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_COPPER)
                .requires(ItemTagsCoFH.DUSTS_SILVER)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer, ID_THERMAL + ":signalum_dust_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("lumium_dust"), 4)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .requires(ItemTagsCoFH.DUSTS_TIN)
                .requires(ItemTagsCoFH.DUSTS_SILVER)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_glowstone_dust", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(consumer, ID_THERMAL + ":lumium_dust_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("enderium_dust"), 2)
                .requires(ItemTagsCoFH.DUSTS_LEAD)
                .requires(ItemTagsCoFH.DUSTS_LEAD)
                .requires(ItemTagsCoFH.DUSTS_LEAD)
                .requires(ItemTagsCoFH.DUSTS_DIAMOND)
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(consumer, ID_THERMAL + ":enderium_dust_2");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("signalum_ingot"), 4)
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_COPPER, Tags.Items.INGOTS_COPPER))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer, ID_THERMAL + ":fire_charge/signalum_ingot_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("lumium_ingot"), 4)
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(fromTags(ItemTagsCoFH.DUSTS_TIN, ItemTagsCoFH.INGOTS_TIN))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SILVER, ItemTagsCoFH.INGOTS_SILVER))
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_glowstone_dust", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(consumer, ID_THERMAL + ":fire_charge/lumium_ingot_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("enderium_ingot"), 2)
                .requires(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .requires(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .requires(fromTags(ItemTagsCoFH.DUSTS_LEAD, ItemTagsCoFH.INGOTS_LEAD))
                .requires(ItemTagsCoFH.DUSTS_DIAMOND)
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .requires(fromTags(Tags.Items.ENDER_PEARLS, ItemTagsCoFH.DUSTS_ENDER_PEARL))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(consumer, ID_THERMAL + ":fire_charge/enderium_ingot_2");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, reg.get("obsidian_glass"), 2)
                .requires(Tags.Items.OBSIDIANS_NORMAL)
                .requires(fromTags(ItemTagsCoFH.DUSTS_QUARTZ, Tags.Items.GEMS_QUARTZ))
                .requires(Tags.Items.SANDS)
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIANS_NORMAL))
                .save(consumer, ID_THERMAL + ":fire_charge/obsidian_glass_2");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, reg.get("signalum_glass"), 2)
                .requires(reg.get("obsidian_glass"))
                .requires(reg.get("obsidian_glass"))
                .requires(fromTags(ItemTagsCoFH.DUSTS_SIGNALUM, ItemTagsCoFH.INGOTS_SIGNALUM))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_signalum_dust", has(ItemTagsCoFH.DUSTS_SIGNALUM))
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(consumer, ID_THERMAL + ":fire_charge/signalum_glass_2");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, reg.get("lumium_glass"), 2)
                .requires(reg.get("obsidian_glass"))
                .requires(reg.get("obsidian_glass"))
                .requires(fromTags(ItemTagsCoFH.DUSTS_LUMIUM, ItemTagsCoFH.INGOTS_LUMIUM))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_lumium_dust", has(ItemTagsCoFH.DUSTS_LUMIUM))
                .unlockedBy("has_lumium_ingot", has(ItemTagsCoFH.INGOTS_LUMIUM))
                .save(consumer, ID_THERMAL + ":fire_charge/lumium_glass_2");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, reg.get("enderium_glass"), 2)
                .requires(reg.get("obsidian_glass"))
                .requires(reg.get("obsidian_glass"))
                .requires(fromTags(ItemTagsCoFH.DUSTS_ENDERIUM, ItemTagsCoFH.INGOTS_ENDERIUM))
                .requires(Items.FIRE_CHARGE)
                .unlockedBy("has_enderium_dust", has(ItemTagsCoFH.DUSTS_ENDERIUM))
                .unlockedBy("has_enderium_ingot", has(ItemTagsCoFH.INGOTS_ENDERIUM))
                .save(consumer, ID_THERMAL + ":fire_charge/enderium_glass_2");
    }

    private void generateArmorRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;
        String folder = "armor";
        Item result;

        Item beekeeperFabric = reg.get("beekeeper_fabric");
        Item divingFabric = reg.get("diving_fabric");
        Item hazmatFabric = reg.get("hazmat_fabric");

        ShapedRecipeBuilder.shaped(MISC, beekeeperFabric)
                .define('S', Tags.Items.STRINGS)
                .define('H', Items.HONEYCOMB)
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BEEKEEPER_ARMOR)));

        result = reg.get(ID_BEEKEEPER_HELMET);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('X', beekeeperFabric)
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BEEKEEPER_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_CHESTPLATE);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('X', beekeeperFabric)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BEEKEEPER_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_LEGGINGS);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('X', beekeeperFabric)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BEEKEEPER_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_BEEKEEPER_BOOTS);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('L', Tags.Items.LEATHERS)
                .define('X', beekeeperFabric)
                .pattern("X X")
                .pattern("L L")
                .unlockedBy("has_fabric", has(beekeeperFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BEEKEEPER_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        ShapedRecipeBuilder.shaped(MISC, divingFabric)
                .define('S', Tags.Items.STRINGS)
                .define('H', Tags.Items.GEMS_PRISMARINE)
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .unlockedBy("has_prismarine", has(Tags.Items.GEMS_PRISMARINE))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DIVING_ARMOR)));

        result = reg.get(ID_DIVING_HELMET);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("XIX")
                .pattern("IGI")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DIVING_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_CHESTPLATE);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("X X")
                .pattern("IXI")
                .pattern("XXX")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DIVING_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_LEGGINGS);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("XXX")
                .pattern("I I")
                .pattern("X X")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DIVING_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DIVING_BOOTS);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('L', Tags.Items.LEATHERS)
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('X', divingFabric)
                .pattern("X X")
                .pattern("LIL")
                .unlockedBy("has_fabric", has(divingFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DIVING_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        ShapedRecipeBuilder.shaped(MISC, hazmatFabric)
                .define('S', Tags.Items.STRINGS)
                .define('H', reg.get("cured_rubber"))
                .pattern(" S ")
                .pattern("SHS")
                .pattern(" S ")
                .unlockedBy("has_cured_rubber", has(reg.get("cured_rubber")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_HAZMAT_ARMOR)));

        result = reg.get(ID_HAZMAT_HELMET);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('G', Tags.Items.GLASS_PANES)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('X', hazmatFabric)
                .pattern("XIX")
                .pattern("IGI")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_HAZMAT_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_CHESTPLATE);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('X', hazmatFabric)
                .pattern("X X")
                .pattern("IXI")
                .pattern("XXX")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_HAZMAT_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_LEGGINGS);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('X', hazmatFabric)
                .pattern("XXX")
                .pattern("I I")
                .pattern("X X")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_HAZMAT_ARMOR)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_HAZMAT_BOOTS);
        ShapedRecipeBuilder.shaped(COMBAT, result)
                .define('L', Tags.Items.LEATHERS)
                .define('R', reg.get("cured_rubber"))
                .define('X', hazmatFabric)
                .pattern("X X")
                .pattern("LRL")
                .unlockedBy("has_fabric", has(hazmatFabric))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_HAZMAT_ARMOR)), this.modid + ":" + folder + "/" + name(result));
    }

    private void generateAugmentRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;
        String folder = "augments";
        Item result;

        Item redstoneServo = reg.get("redstone_servo");
        Item rfCoil = reg.get("rf_coil");

        result = reg.get("area_radius_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', ItemTagsCoFH.GEARS_IRON)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('X', redstoneServo)
                .pattern(" G ")
                .pattern("IXI")
                .pattern(" G ")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_AREA_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_output_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', ItemTagsCoFH.GEARS_SILVER)
                .define('S', ItemTagsCoFH.PLATES_SIGNALUM)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("SXS")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DYNAMO_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_fuel_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', ItemTagsCoFH.GEARS_LEAD)
                .define('L', ItemTagsCoFH.PLATES_LUMIUM)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("LXL")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DYNAMO_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("dynamo_throttle_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('E', ItemTagsCoFH.INGOTS_ELECTRUM)
                .define('l', ItemTagsCoFH.NUGGETS_LEAD)
                .pattern(" l ")
                .pattern("lEl")
                .pattern(" l ")
                .unlockedBy("has_electrum_ingot", has(ItemTagsCoFH.INGOTS_ELECTRUM))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_DYNAMO_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        //        result = reg.get("dual_filter_augment");
        //        ShapedRecipeBuilder.shapedRecipe(result)
        //                .key('c', ItemTagsCoFH.NUGGETS_COPPER)
        //                .key('t', ItemTagsCoFH.NUGGETS_TIN)
        //                .key('S', ItemTagsCoFH.INGOTS_SIGNALUM)
        //                .patternLine(" c ")
        //                .patternLine("tSt")
        //                .patternLine(" c ")
        //                .addCriterion("has_signalum_ingot", hasItem(ItemTagsCoFH.INGOTS_SIGNALUM))
        //                .build(withConditions(recipeOutput).flag(FLAG_FILTER_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("fluid_filter_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('c', ItemTagsCoFH.NUGGETS_COPPER)
                .define('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .pattern(" c ")
                .pattern("cSc")
                .pattern(" c ")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_FILTER_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("item_filter_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('t', ItemTagsCoFH.NUGGETS_TIN)
                .define('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .pattern(" t ")
                .pattern("tSt")
                .pattern(" t ")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_FILTER_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_speed_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('E', ItemTagsCoFH.PLATES_ELECTRUM)
                .define('L', ItemTagsCoFH.GEARS_LEAD)
                .define('X', rfCoil)
                .pattern(" L ")
                .pattern("EXE")
                .pattern(" L ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_MACHINE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_efficiency_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('L', ItemTagsCoFH.PLATES_LUMIUM)
                .define('N', ItemTagsCoFH.GEARS_NICKEL)
                .define('X', rfCoil)
                .pattern(" N ")
                .pattern("LXL")
                .pattern(" N ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_MACHINE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_output_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('B', ItemTagsCoFH.GEARS_BRONZE)
                .define('I', ItemTagsCoFH.PLATES_INVAR)
                .define('X', redstoneServo)
                .pattern(" B ")
                .pattern("IXI")
                .pattern(" B ")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_MACHINE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_catalyst_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('C', ItemTagsCoFH.GEARS_CONSTANTAN)
                .define('L', ItemTagsCoFH.PLATES_LEAD)
                .define('X', redstoneServo)
                .pattern(" C ")
                .pattern("LXL")
                .pattern(" C ")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_MACHINE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_cycle_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('C', ItemTagsCoFH.PLATES_CONSTANTAN)
                .define('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('S', ItemTagsCoFH.PLATES_SILVER)
                .define('X', redstoneServo)
                .pattern("SGS")
                .pattern("CXC")
                .pattern("SGS")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_MACHINE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("machine_null_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('C', Items.CACTUS)
                .pattern(" i ")
                .pattern("iCi")
                .pattern(" i ")
                .unlockedBy("has_cactus", has(Items.CACTUS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_MACHINE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("potion_amplifier_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("IXI")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_POTION_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("potion_duration_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', ItemTagsCoFH.GEARS_LUMIUM)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern(" G ")
                .pattern("IXI")
                .pattern(" G ")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_POTION_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .define('X', rfCoil)
                .pattern(" G ")
                .pattern("SXS")
                .pattern(" G ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_STORAGE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_storage_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .define('X', rfCoil)
                .pattern(" S ")
                .pattern("GXG")
                .pattern(" G ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_STORAGE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rf_coil_xfer_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .define('X', rfCoil)
                .pattern(" S ")
                .pattern("SXS")
                .pattern(" G ")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_STORAGE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("fluid_tank_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', ITEMS.get("cured_rubber"))
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern("RIR")
                .pattern("IXI")
                .pattern("RIR")
                .unlockedBy("has_hardened_glass", has(ThermalTags.Items.HARDENED_GLASS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_STORAGE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_1");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_INVAR)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('X', ItemTagsCoFH.GEARS_GOLD)
                .pattern("IGI")
                .pattern("RXR")
                .pattern("IGI")
                .unlockedBy("has_invar_ingot", has(ItemTagsCoFH.INGOTS_INVAR))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_UPGRADE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_2");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', Tags.Items.GEMS_QUARTZ)
                .define('I', ItemTagsCoFH.INGOTS_ELECTRUM)
                .define('R', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('X', reg.get("upgrade_augment_1"))
                .pattern("IGI")
                .pattern("RXR")
                .pattern("IGI")
                .unlockedBy("has_electrum_ingot", has(ItemTagsCoFH.INGOTS_ELECTRUM))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_UPGRADE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("upgrade_augment_3");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('G', ThermalTags.Items.HARDENED_GLASS)
                .define('I', ItemTagsCoFH.INGOTS_ENDERIUM)
                .define('R', ItemTagsCoFH.GEARS_LUMIUM)
                .define('X', reg.get("upgrade_augment_2"))
                .pattern("IGI")
                .pattern("RXR")
                .pattern("IGI")
                .unlockedBy("has_enderium_ingot", has(ItemTagsCoFH.INGOTS_ENDERIUM))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_UPGRADE_AUGMENTS)), this.modid + ":" + folder + "/" + name(result));

        result = reg.get("rs_control_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .pattern(" i ")
                .pattern("iri")
                .pattern(" i ")
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get("side_config_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('i', ItemTagsCoFH.NUGGETS_TIN)
                .define('G', Tags.Items.INGOTS_GOLD)
                .pattern(" i ")
                .pattern("iGi")
                .pattern(" i ")
                .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get("xp_storage_augment");
        ShapedRecipeBuilder.shaped(MISC, result)
                .define('i', Tags.Items.NUGGETS_GOLD)
                .define('C', reg.get("xp_crystal"))
                .pattern(" i ")
                .pattern("iCi")
                .pattern(" i ")
                .unlockedBy("has_crystal", has(reg.get("xp_crystal")))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));
    }

    private void generateBasicRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;
        String folder = "tools";
        Item result;

        result = reg.get(ID_WRENCH);
        ShapedRecipeBuilder.shaped(TOOLS, result)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', ItemTagsCoFH.GEARS_IRON)
                .pattern("I I")
                .pattern(" G ")
                .pattern(" I ")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_REDPRINT);
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(Items.PAPER)
                .requires(Items.PAPER)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_LOCK);
        ShapedRecipeBuilder.shaped(TOOLS, result)
                .define('i', Tags.Items.NUGGETS_IRON)
                .define('S', ItemTagsCoFH.INGOTS_SIGNALUM)
                .pattern(" i ")
                .pattern("iSi")
                .pattern("iii")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_SATCHEL);
        ShapedRecipeBuilder.shaped(TOOLS, result)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('L', Tags.Items.LEATHERS)
                .define('W', ThermalTags.Items.ROCKWOOL)
                .pattern("LWL")
                .pattern("WIW")
                .pattern("LWL")
                .unlockedBy("has_leather", has(Tags.Items.LEATHERS))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_DETONATOR);
        ShapedRecipeBuilder.shaped(TOOLS, result)
                .define('G', ItemTagsCoFH.GEARS_SIGNALUM)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', ItemTagsCoFH.INGOTS_SILVER)
                .pattern(" S ")
                .pattern("IGI")
                .pattern("III")
                .unlockedBy("has_signalum_ingot", has(ItemTagsCoFH.INGOTS_SIGNALUM))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_RF_POTATO);
        ShapedRecipeBuilder.shaped(TOOLS, result)
                .define('D', Tags.Items.DUSTS_REDSTONE)
                .define('L', ItemTagsCoFH.NUGGETS_LEAD)
                .define('P', Tags.Items.CROPS_POTATO)
                .define('R', reg.get("cured_rubber"))
                .pattern("LDL")
                .pattern("RPR")
                .pattern("DLD")
                .unlockedBy("has_potato", has(Tags.Items.CROPS_POTATO))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        result = reg.get(ID_XP_CRYSTAL);
        ShapedRecipeBuilder.shaped(TOOLS, result)
                .define('B', Items.EXPERIENCE_BOTTLE)
                .define('E', Tags.Items.GEMS_EMERALD)
                .define('L', Tags.Items.GEMS_LAPIS)
                .pattern(" L ")
                .pattern("EBE")
                .pattern(" L ")
                .unlockedBy("has_experience_bottle", has(Items.EXPERIENCE_BOTTLE))
                .save(recipeOutput, this.modid + ":" + folder + "/" + name(result));

        ShapelessRecipeBuilder.shapeless(MISC, reg.get(ID_FLORB), 8)
                .requires(ThermalTags.Items.SAWDUST)
                .requires(ThermalTags.Items.SLAG)
                .requires(fromTags(ThermalTags.Items.ROSIN, Tags.Items.SLIMEBALLS))
                .unlockedBy("has_slag", has(ThermalTags.Items.SLAG))
                .save(recipeOutput, ID_THERMAL + ":florb_8");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("phytogro"), 8)
                .requires(Tags.Items.SANDS)
                .requires(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .requires(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(recipeOutput, ID_THERMAL + ":phytogro_8");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("phytogro"), 4)
                .requires(Tags.Items.SANDS)
                .requires(Items.BONE_MEAL)
                .requires(fromTags(ItemTagsCoFH.GEMS_APATITE, ItemTagsCoFH.DUSTS_APATITE))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(recipeOutput, ID_THERMAL + ":phytogro_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("phytogro"), 2)
                .requires(Tags.Items.SANDS)
                .requires(Items.BONE_MEAL)
                .requires(reg.get("rich_slag"))
                .requires(fromTags(ItemTagsCoFH.GEMS_NITER, ItemTagsCoFH.DUSTS_NITER))
                .unlockedBy("rich_slag", has(reg.get("rich_slag")))
                .save(recipeOutput, ID_THERMAL + ":phytogro_2");

        ShapedRecipeBuilder.shaped(MISC, reg.get("junk_net"), 1)
                .define('#', Tags.Items.STRINGS)
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('S', Items.STICK)
                .pattern("n#n")
                .pattern("#S#")
                .pattern("n#n")
                .unlockedBy("has_string", has(Tags.Items.STRINGS))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_FISHER)));

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("aquachow"), 4)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.SLIME_BALL)
                .unlockedBy("has_wheat", has(Tags.Items.CROPS_WHEAT))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_FISHER)), ID_THERMAL + ":aquachow_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("deep_aquachow"), 4)
                .requires(Items.WHEAT)
                .requires(Items.BEETROOT)
                .requires(Items.SLIME_BALL)
                .requires(ItemTagsCoFH.NUGGETS_LEAD)
                .unlockedBy("has_beetroot", has(Tags.Items.CROPS_BEETROOT))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_FISHER)), ID_THERMAL + ":deep_aquachow_4");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("basalz_powder"), 2)
                .requires(reg.get("basalz_rod"))
                .unlockedBy("has_basalz_rod", has(reg.get("basalz_rod")))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("blitz_powder"), 2)
                .requires(reg.get("blitz_rod"))
                .unlockedBy("has_blitz_rod", has(reg.get("blitz_rod")))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("blizz_powder"), 2)
                .requires(reg.get("blizz_rod"))
                .unlockedBy("has_blizz_rod", has(reg.get("blizz_rod")))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(MISC, Items.CYAN_DYE)
                .requires(ItemTagsCoFH.GEMS_APATITE)
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(recipeOutput, ID_THERMAL + ":cyan_dye_from_apatite");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("rubber"), 3)
                .requires(reg.get("latex_bucket"))
                .unlockedBy("latex_bucket", has(reg.get("latex_bucket")))
                .save(recipeOutput, ID_THERMAL + ":rubber_3");

        ShapedRecipeBuilder.shaped(MISC, reg.get("rubber"), 1)
                .define('B', Items.WATER_BUCKET)
                .define('#', Items.DANDELION)
                .pattern("###")
                .pattern("#B#")
                .pattern("###")
                .unlockedBy("has_dandelion", has(Items.DANDELION))
                .save(recipeOutput, ID_THERMAL + ":rubber_from_dandelion");

        ShapedRecipeBuilder.shaped(MISC, reg.get("rubber"), 1)
                .define('B', Items.WATER_BUCKET)
                .define('#', Items.VINE)
                .pattern("###")
                .pattern("#B#")
                .pattern("###")
                .unlockedBy("has_vine", has(Items.VINE))
                .save(recipeOutput, ID_THERMAL + ":rubber_from_vine");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, Blocks.DIRT)
                .requires(reg.get("compost"))
                .requires(Tags.Items.SANDS)
                .requires(ThermalTags.Items.SLAG)
                .unlockedBy("has_compost", has(reg.get("compost")))
                .save(recipeOutput, ID_THERMAL + ":dirt_crafting");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, Blocks.PODZOL)
                .requires(reg.get("compost"))
                .requires(ItemTags.LEAVES)
                .requires(Tags.Items.SANDS)
                .requires(ThermalTags.Items.SLAG)
                .unlockedBy("has_compost", has(reg.get("compost")))
                .save(recipeOutput, ID_THERMAL + ":podzol_crafting");

        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, Blocks.MYCELIUM)
                .requires(reg.get("compost"))
                .requires(Tags.Items.MUSHROOMS)
                .requires(Tags.Items.SANDS)
                .requires(ThermalTags.Items.SLAG)
                .unlockedBy("has_compost", has(reg.get("compost")))
                .save(recipeOutput, ID_THERMAL + ":mycelium_crafting");
    }

    private void generateChargeRecipes(RecipeOutput consumer) {

        var reg = ITEMS;

        Item earthCharge = reg.get("earth_charge");
        Item iceCharge = reg.get("ice_charge");
        Item lightningCharge = reg.get("lightning_charge");

        ShapelessRecipeBuilder.shapeless(TOOLS, earthCharge, 3)
                .requires(Tags.Items.GUNPOWDERS)
                .requires(reg.get("basalz_powder"))
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy("has_basalz_powder", has(reg.get("basalz_powder")))
                .save(consumer, ID_THERMAL + ":earth_charge_3");

        ShapelessRecipeBuilder.shapeless(TOOLS, iceCharge, 3)
                .requires(Tags.Items.GUNPOWDERS)
                .requires(reg.get("blizz_powder"))
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy("has_blizz_powder", has(reg.get("blizz_powder")))
                .save(consumer, ID_THERMAL + ":ice_charge_3");

        ShapelessRecipeBuilder.shapeless(TOOLS, lightningCharge, 3)
                .requires(Tags.Items.GUNPOWDERS)
                .requires(reg.get("blitz_powder"))
                .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                .unlockedBy("has_blitz_powder", has(reg.get("blitz_powder")))
                .save(consumer, ID_THERMAL + ":lightning_charge_3");

        // region EARTH CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(MISC, Items.PRISMARINE_SHARD, 4)
                .requires(Items.PRISMARINE)
                .requires(earthCharge)
                .unlockedBy("has_prismarine", has(Items.PRISMARINE))
                .save(consumer, ID_THERMAL + ":earth_charge/prismarine_shard_from_prismarine");

        ShapelessRecipeBuilder.shapeless(MISC, Items.PRISMARINE_SHARD, 9)
                .requires(Items.PRISMARINE_BRICKS)
                .requires(earthCharge)
                .unlockedBy("has_prismarine_bricks", has(Items.PRISMARINE_BRICKS))
                .save(consumer, ID_THERMAL + ":earth_charge/prismarine_shard_from_prismarine_bricks");

        ShapelessRecipeBuilder.shapeless(MISC, Items.QUARTZ, 4)
                .requires(Items.QUARTZ_BLOCK)
                .requires(earthCharge)
                .unlockedBy("has_quartz_block", has(Items.QUARTZ_BLOCK))
                .save(consumer, ID_THERMAL + ":earth_charge/quartz_from_quartz_block");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("diamond_dust"))
                .requires(Tags.Items.GEMS_DIAMOND)
                .requires(earthCharge)
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer, ID_THERMAL + ":earth_charge/diamond_dust_from_diamond");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("emerald_dust"))
                .requires(Tags.Items.GEMS_EMERALD)
                .requires(earthCharge)
                .unlockedBy("has_emerald", has(Tags.Items.GEMS_EMERALD))
                .save(consumer, ID_THERMAL + ":earth_charge/emerald_dust_from_emerald");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("ender_pearl_dust"))
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(earthCharge)
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(consumer, ID_THERMAL + ":earth_charge/ender_pearl_dust_from_ender_pearl");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("lapis_dust"))
                .requires(Tags.Items.GEMS_LAPIS)
                .requires(earthCharge)
                .unlockedBy("has_lapis", has(Tags.Items.GEMS_LAPIS))
                .save(consumer, ID_THERMAL + ":earth_charge/lapis_dust_from_lapis");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("quartz_dust"))
                .requires(Tags.Items.GEMS_QUARTZ)
                .requires(earthCharge)
                .unlockedBy("has_quartz", has(Tags.Items.GEMS_QUARTZ))
                .save(consumer, ID_THERMAL + ":earth_charge/quartz_dust_from_quartz");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("apatite_dust"))
                .requires(ItemTagsCoFH.GEMS_APATITE)
                .requires(earthCharge)
                .unlockedBy("has_apatite", has(ItemTagsCoFH.GEMS_APATITE))
                .save(consumer, ID_THERMAL + ":earth_charge/apatite_dust_from_apatite");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("cinnabar_dust"))
                .requires(ItemTagsCoFH.GEMS_CINNABAR)
                .requires(earthCharge)
                .unlockedBy("has_cinnabar", has(ItemTagsCoFH.GEMS_CINNABAR))
                .save(consumer, ID_THERMAL + ":earth_charge/cinnabar_dust_from_cinnabar");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("niter_dust"))
                .requires(ItemTagsCoFH.GEMS_NITER)
                .requires(earthCharge)
                .unlockedBy("has_niter", has(ItemTagsCoFH.GEMS_NITER))
                .save(consumer, ID_THERMAL + ":earth_charge/niter_dust_from_niter");

        ShapelessRecipeBuilder.shapeless(MISC, reg.get("sulfur_dust"))
                .requires(ItemTagsCoFH.GEMS_SULFUR)
                .requires(earthCharge)
                .unlockedBy("has_sulfur", has(ItemTagsCoFH.GEMS_SULFUR))
                .save(consumer, ID_THERMAL + ":earth_charge/sulfur_dust_from_sulfur");
        // endregion

        // region ICE CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(MISC, Items.OBSIDIAN)
                .requires(Items.LAVA_BUCKET)
                .requires(iceCharge)
                .unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
                .save(consumer, ID_THERMAL + ":ice_charge/obsidian_from_lava_bucket");

        ShapelessRecipeBuilder.shapeless(MISC, Items.ICE)
                .requires(Items.WATER_BUCKET)
                .requires(iceCharge)
                .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
                .save(consumer, ID_THERMAL + ":ice_charge/ice_from_water_bucket");
        // endregion

        // region LIGHTNING CHARGE CONVERSIONS
        ShapelessRecipeBuilder.shapeless(MISC, Items.WITCH_SPAWN_EGG)
                .requires(Items.VILLAGER_SPAWN_EGG)
                .requires(lightningCharge)
                .unlockedBy("has_villager_spawn_egg", has(Items.VILLAGER_SPAWN_EGG))
                .save(consumer, ID_THERMAL + ":lightning_charge/witch_from_villager");

        ShapelessRecipeBuilder.shapeless(MISC, Items.ZOMBIFIED_PIGLIN_SPAWN_EGG)
                .requires(Items.PIG_SPAWN_EGG)
                .requires(lightningCharge)
                .unlockedBy("has_pig_spawn_egg", has(Items.PIG_SPAWN_EGG))
                .save(consumer, ID_THERMAL + ":lightning_charge/zombified_piglin_from_pig");
        // endregion
    }

    private void generateComponentRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;

        ShapedRecipeBuilder.shaped(MISC, reg.get("redstone_servo"))
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern(" R ")
                .pattern(" I ")
                .pattern(" R ")
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, reg.get("rf_coil"))
                .define('I', Tags.Items.INGOTS_GOLD)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("  R")
                .pattern(" I ")
                .pattern("R  ")
                .unlockedBy("has_redstone_dust", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(MISC, reg.get("drill_head"))
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern(" I ")
                .pattern("ICI")
                .pattern("III")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_TOOL_COMPONENTS)));

        ShapedRecipeBuilder.shaped(MISC, reg.get("saw_blade"))
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("II ")
                .pattern("ICI")
                .pattern(" II")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_TOOL_COMPONENTS)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_MACHINE_FRAME))
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('T', ItemTagsCoFH.GEARS_TIN)
                .pattern("IGI")
                .pattern("GTG")
                .pattern("IGI")
                .unlockedBy("has_tin", has(ItemTagsCoFH.INGOTS_TIN))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_MACHINE_FRAME)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_ENERGY_CELL_FRAME))
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_LEAD)
                .define('E', ItemTagsCoFH.GEARS_ELECTRUM)
                .pattern("IGI")
                .pattern("GEG")
                .pattern("IGI")
                .unlockedBy("has_lead", has(ItemTagsCoFH.INGOTS_LEAD))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_ENERGY_CELL_FRAME)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_FLUID_CELL_FRAME))
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('E', ItemTagsCoFH.GEARS_BRONZE)
                .pattern("IGI")
                .pattern("GEG")
                .pattern("IGI")
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_FLUID_CELL_FRAME)));

        //        ShapedRecipeBuilder.shaped(reg.get(ID_ITEM_CELL_FRAME))
        //                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
        //                .define('I', ItemTagsCoFH.INGOTS_TIN)
        //                .define('E', ItemTagsCoFH.GEARS_IRON)
        //                .pattern("IGI")
        //                .pattern("GEG")
        //                .pattern("IGI")
        //                .unlockedBy("has_copper", has(ItemTagsCoFH.INGOTS_TIN))
        //                .save(recipeOutput.withConditions(new FlagSetCondition(ID_ITEM_CELL_FRAME));
    }

    private void generateExplosiveRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_EXPLOSIVE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.SANDS)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
                .save(recipeOutput, ID_THERMAL + ":explosive_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_SLIME_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.SLIMEBALLS)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_slimeball", has(Tags.Items.SLIMEBALLS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)), ID_THERMAL + ":slime_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_REDSTONE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.DUSTS_REDSTONE)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)), ID_THERMAL + ":redstone_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_GLOWSTONE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.DUSTS_GLOWSTONE)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)), ID_THERMAL + ":glowstone_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_ENDER_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.ENDER_PEARLS)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)), ID_THERMAL + ":ender_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_PHYTO_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("phytogro"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_phytogro", has(reg.get("phytogro")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_PHYTOGRO_EXPLOSIVES)), ID_THERMAL + ":phyto_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_EARTH_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("basalz_powder"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_basalz_powder", has(reg.get("basalz_powder")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)), ID_THERMAL + ":earth_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_FIRE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Items.BLAZE_POWDER)
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)), ID_THERMAL + ":fire_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_ICE_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("blizz_powder"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_blizz_powder", has(reg.get("blizz_powder")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)), ID_THERMAL + ":ice_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_LIGHTNING_GRENADE), 4)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', reg.get("blitz_powder"))
                .pattern("GPG")
                .pattern("PIP")
                .pattern("GPG")
                .unlockedBy("has_blitz_powder", has(reg.get("blitz_powder")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)), ID_THERMAL + ":lightning_grenade_4");

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_SLIME_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', Tags.Items.SLIMEBALLS)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_slimeball", has(Tags.Items.SLIMEBALLS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_REDSTONE_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', Tags.Items.DUSTS_REDSTONE)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_GLOWSTONE_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', Tags.Items.DUSTS_GLOWSTONE)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_ENDER_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', Tags.Items.ENDER_PEARLS)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_ender_pearl", has(Tags.Items.ENDER_PEARLS))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_BASIC_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_PHYTO_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', reg.get("phytogro"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_phytogro", has(reg.get("phytogro")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_PHYTOGRO_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_EARTH_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', reg.get("basalz_powder"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_basalz_powder", has(reg.get("basalz_powder")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_FIRE_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', Items.BLAZE_POWDER)
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_ICE_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', reg.get("blizz_powder"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_blizz_powder", has(reg.get("blizz_powder")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)));

        ShapedRecipeBuilder.shaped(TOOLS, reg.get(ID_LIGHTNING_TNT))
                .define('G', Tags.Items.GUNPOWDERS)
                .define('P', reg.get("blitz_powder"))
                .pattern("GPG")
                .pattern("PGP")
                .pattern("GPG")
                .unlockedBy("has_blitz_powder", has(reg.get("blitz_powder")))
                .save(recipeOutput.withConditions(new FlagSetCondition(FLAG_ELEMENTAL_EXPLOSIVES)));
    }

    private void generateRockwoolRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;

        Item rockwool = reg.get(ID_WHITE_ROCKWOOL);
        String folder = "rockwool";
        String recipeId;
        Item result;

        generateSmeltingAndBlastingRecipes(reg, recipeOutput, reg.get("slag"), rockwool, 0.1F, "rockwool");

        //        ShapelessRecipeBuilder.shapelessRecipe(reg.get(ID_WHITE_ROCKWOOL))
        //                .addIngredient(rockwool)
        //                .addIngredient(Tags.Items.DYES_WHITE)
        //                .addCriterion("has_" + path(rockwool), hasItem(rockwool))
        //                .build(recipeOutput);

        result = reg.get(ID_ORANGE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_ORANGE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_MAGENTA_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_MAGENTA)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_LIGHT_BLUE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_YELLOW_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_LIME_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_LIME)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_PINK_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_PINK)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_GRAY_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_GRAY)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_LIGHT_GRAY_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_LIGHT_GRAY)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_CYAN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_CYAN)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_PURPLE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_PURPLE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_BLUE_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_BROWN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_BROWN)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_GREEN_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_GREEN)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_RED_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);

        result = reg.get(ID_BLACK_ROCKWOOL);
        recipeId = this.modid + ":" + folder + "/" + name(result) + "_from_dye";
        ShapelessRecipeBuilder.shapeless(BUILDING_BLOCKS, result)
                .requires(rockwool)
                .requires(Tags.Items.DYES_BLACK)
                .unlockedBy("has_" + name(rockwool), has(rockwool))
                .save(recipeOutput, recipeId);
    }

    private void generateSlagRecipes(RecipeOutput consumer) {

        var reg = ITEMS;

        generateSmeltingRecipe(reg, consumer, reg.get(ID_SLAG_BLOCK), reg.get(ID_POLISHED_SLAG), 0.1F, "smelting");
        generateSmeltingRecipe(reg, consumer, reg.get(ID_RICH_SLAG_BLOCK), reg.get(ID_POLISHED_RICH_SLAG), 0.1F, "smelting");

        generateSmeltingRecipe(reg, consumer, reg.get(ID_SLAG_BRICKS), reg.get(ID_CRACKED_SLAG_BRICKS), 0.1F, "smelting");
        generateSmeltingRecipe(reg, consumer, reg.get(ID_RICH_SLAG_BRICKS), reg.get(ID_CRACKED_RICH_SLAG_BRICKS), 0.1F, "smelting");

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, reg.get(ID_SLAG_BRICKS), 4)
                .define('#', reg.get(ID_POLISHED_SLAG))
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_slag", has(reg.get("slag")))
                .save(consumer);

        ShapedRecipeBuilder.shaped(BUILDING_BLOCKS, reg.get(ID_RICH_SLAG_BRICKS), 4)
                .define('#', reg.get(ID_POLISHED_RICH_SLAG))
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_rich_slag", has(reg.get("rich_slag")))
                .save(consumer);

        generateStonecuttingRecipe(reg, consumer, reg.get(ID_POLISHED_SLAG), reg.get(ID_CHISELED_SLAG), "stonecutting");
        generateStonecuttingRecipe(reg, consumer, reg.get(ID_POLISHED_RICH_SLAG), reg.get(ID_CHISELED_RICH_SLAG), "stonecutting");
    }

    private void generateTileRecipes(RecipeOutput recipeOutput) {

        var reg = ITEMS;

        Item energyCellFrame = reg.get(ID_ENERGY_CELL_FRAME);
        Item fluidCellFrame = reg.get(ID_FLUID_CELL_FRAME);
        // Item itemCellFrame = reg.get(ID_ITEM_CELL_FRAME);
        Item redstoneServo = reg.get("redstone_servo");
        Item rfCoil = reg.get("rf_coil");

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_HIVE_EXTRACTOR))
                .define('C', Items.SHEARS)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_HIVE_EXTRACTOR)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_TREE_EXTRACTOR))
                .define('C', Items.BUCKET)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_TREE_EXTRACTOR)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_FISHER))
                .define('C', Items.FISHING_ROD)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_COPPER)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_FISHER)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_COMPOSTER))
                .define('C', Blocks.COMPOSTER)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTags.PLANKS)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_COMPOSTER)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_SOIL_INFUSER))
                .define('C', reg.get("phytogro"))
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTags.PLANKS)
                .define('P', rfCoil)
                .define('X', ItemTagsCoFH.GEARS_LUMIUM)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_SOIL_INFUSER)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_ROCK_GEN))
                .define('C', Items.PISTON)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_INVAR)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_CONSTANTAN)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_ROCK_GEN)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_WATER_GEN))
                .define('C', Items.BUCKET)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', Tags.Items.INGOTS_COPPER)
                .define('P', redstoneServo)
                .define('X', Tags.Items.INGOTS_IRON)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_WATER_GEN)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_COLLECTOR))
                .define('C', Items.HOPPER)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('P', redstoneServo)
                .define('X', Tags.Items.ENDER_PEARLS)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_COLLECTOR)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_XP_CONDENSER))
                .define('C', reg.get("xp_crystal"))
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_SILVER)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_LAPIS)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_XP_CONDENSER)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_NULLIFIER))
                .define('C', Items.LAVA_BUCKET)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_TIN)
                .define('P', redstoneServo)
                .define('X', Tags.Items.DUSTS_REDSTONE)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_NULLIFIER)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_DEVICE_POTION_DIFFUSER))
                .define('C', Items.GLASS_BOTTLE)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', ItemTagsCoFH.INGOTS_SILVER)
                .define('P', redstoneServo)
                .define('X', ItemTagsCoFH.GEARS_CONSTANTAN)
                .pattern("IXI")
                .pattern("GCG")
                .pattern("IPI")
                .unlockedBy("has_redstone_servo", has(redstoneServo))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_DEVICE_POTION_DIFFUSER)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_ENERGY_CELL))
                .define('C', energyCellFrame)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', rfCoil)
                .define('R', reg.get("cured_rubber"))
                .define('X', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .pattern("RXR")
                .pattern("ICI")
                .pattern("RPR")
                .unlockedBy("has_energy_cell_frame", has(energyCellFrame))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_ENERGY_CELL)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_FLUID_CELL))
                .define('C', fluidCellFrame)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', redstoneServo)
                .define('R', reg.get("cured_rubber"))
                .define('X', ThermalTags.Items.HARDENED_GLASS)
                .pattern("RXR")
                .pattern("ICI")
                .pattern("RPR")
                .unlockedBy("has_fluid_cell_frame", has(fluidCellFrame))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_FLUID_CELL)));

        //        ShapedRecipeBuilder.shaped(reg.get(ID_ITEM_CELL))
        //                .define('C', itemCellFrame)
        //                .define('I', Tags.Items.INGOTS_IRON)
        //                .define('P', redstoneServo)
        //                .define('R', reg.get("cured_rubber"))
        //                .define('X', Items.CHEST)
        //                .pattern("RXR")
        //                .pattern("ICI")
        //                .pattern("RPR")
        //                .unlockedBy("has_item_cell_frame", has(itemCellFrame))
        //                .save(recipeOutput.withConditions(new FlagSetCondition(ID_ITEM_CELL)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_TINKER_BENCH))
                .define('C', Blocks.CRAFTING_TABLE)
                .define('G', Tags.Items.GLASS_BLOCKS_COLORLESS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', rfCoil)
                .define('X', ItemTags.PLANKS)
                .pattern("III")
                .pattern("GCG")
                .pattern("XPX")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_TINKER_BENCH)));

        ShapedRecipeBuilder.shaped(MISC, reg.get(ID_CHARGE_BENCH))
                .define('C', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('I', ItemTagsCoFH.INGOTS_ELECTRUM)
                .define('P', rfCoil)
                .define('X', ItemTagsCoFH.INGOTS_LEAD)
                .pattern("III")
                .pattern("PCP")
                .pattern("XPX")
                .unlockedBy("has_rf_coil", has(rfCoil))
                .save(recipeOutput.withConditions(new FlagSetCondition(ID_CHARGE_BENCH)));
    }
    // endregion
}
