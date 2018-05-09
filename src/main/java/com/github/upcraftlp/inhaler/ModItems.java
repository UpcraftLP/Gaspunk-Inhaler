package com.github.upcraftlp.inhaler;

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

    @Mod.EventBusSubscriber(modid = GaspunkInhaler.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemInhaler());
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public static void onRegisterRender(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(INHALER, 0, new ModelResourceLocation(INHALER.getRegistryName(), "inventory"));
        }
    }
}
