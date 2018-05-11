package com.github.upcraftlp.inhaler;

import ladysnake.gaspunk.GasPunk;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@GameRegistry.ObjectHolder(GaspunkInhaler.MODID)
public class ModItems {

    public static final Item INHALER = null;
    public static final Item EMPTY_INHALER = null;

    @Mod.EventBusSubscriber(modid = GaspunkInhaler.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(new ItemInhaler(), new Item().setRegistryName("empty_inhaler").setUnlocalizedName("gp_inhaler.empty_inhaler").setCreativeTab(GasPunk.CREATIVE_TAB));
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public static void onRegisterRender(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(INHALER, 0, new ModelResourceLocation(INHALER.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(EMPTY_INHALER, 0, new ModelResourceLocation(EMPTY_INHALER.getRegistryName(), "inventory"));
        }
    }
}
