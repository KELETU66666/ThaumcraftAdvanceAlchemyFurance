package com.keletu.advanced_alchemy_furnace;

import com.keletu.advanced_alchemy_furnace.block.BlockAlchemyFurnace;
import com.keletu.advanced_alchemy_furnace.client.render.TileAlchemyFurnaceAdvancedRenderer;
import com.keletu.advanced_alchemy_furnace.tile.TileAlchemyFurnaceAdvanced;
import com.keletu.advanced_alchemy_furnace.tile.TileAlchemyFurnaceAdvancedNozzle;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.Part;
import thaumcraft.common.lib.crafting.DustTriggerMultiblock;

@Mod(
        modid = AdvancedAlchemyFurnace.MOD_ID,
        name = AdvancedAlchemyFurnace.MOD_NAME,
        version = AdvancedAlchemyFurnace.VERSION,
        dependencies = AdvancedAlchemyFurnace.dependencies
)
public class AdvancedAlchemyFurnace {

    public static final String MOD_ID = "advanced_alchemy_furnace";
    public static final String MOD_NAME = "AdvancedAlchemyFurnace";
    public static final String VERSION = "0.2";
    public static final String dependencies = "required-after:thaumcraft@[6.1.BETA26,);required-after:thaumicaugmentation";

    public static AdvancedAlchemyFurnace instance;
    public static Block alchemyFurnace = new BlockAlchemyFurnace();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;

        ForgeRegistries.BLOCKS.register(alchemyFurnace);
        ForgeRegistries.ITEMS.register(new ItemBlock(alchemyFurnace).setRegistryName("advanced_alchemy_furnace"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileAlchemyFurnaceAdvanced.class, "tk_advanced_alchemy");
        GameRegistry.registerTileEntity(TileAlchemyFurnaceAdvancedNozzle.class, "tk_nozzle");

        Part A = new Part(BlocksTC.alembic, new ItemStack(alchemyFurnace, 1, 3));
        Part N = new Part(BlocksTC.metalAlchemical, new ItemStack(alchemyFurnace, 1, 4));
        Part G = new Part(Blocks.GLASS, "AIR");
        Part AD = new Part(BlocksTC.metalAlchemicalAdvanced, new ItemStack(alchemyFurnace, 1, 1), true);
        Part S = new Part(BlocksTC.smelterBasic, new ItemStack(alchemyFurnace, 1, 0), true);
        Part AU = new Part(BlocksTC.metalAlchemicalAdvanced, new ItemStack(alchemyFurnace, 1, 2), true);
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("ADVANCED_ALCHEMY_FURNACE",
                new Part[][][]{
                        {
                                {A, N, A},
                                {N, G, N},
                                {A, N, A}
                        },
                        {
                                {AU, AD, AU},
                                {AD, S, AD},
                                {AU, AD, AU}
                        }
                }));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation(AdvancedAlchemyFurnace.MOD_ID, "advanced_alchemy_furnace"), new ThaumcraftApi.BluePrint(
                "ADVANCED_ALCHEMY_FURNACE",
                new Part[][][]{
                        {
                                {A, N, A},
                                {N, G, N},
                                {A, N, A}
                        },
                        {
                            {AU, AD, AU},
                            {AD, S, AD},
                            {AU, AD, AU}
                        }
                },
                new ItemStack(BlocksTC.alembic, 4),
                new ItemStack(BlocksTC.metalAlchemical, 4),
                new ItemStack(Blocks.GLASS),
                new ItemStack(BlocksTC.metalAlchemicalAdvanced, 4),
                new ItemStack(BlocksTC.smelterBasic)
        ));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("advanced_alchemy_furnace", "research/research.json"));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(alchemyFurnace), 0, new ModelResourceLocation(AdvancedAlchemyFurnace.MOD_ID + ":" + alchemyFurnace.getTranslationKey().substring(5), "inventory"));
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {


        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {

        }

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {

        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyFurnaceAdvanced.class, new TileAlchemyFurnaceAdvancedRenderer());
        }
    }
}
