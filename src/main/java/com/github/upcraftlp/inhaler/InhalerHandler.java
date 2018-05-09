package com.github.upcraftlp.inhaler;

import com.github.upcraftlp.inhaler.api.IEntityGasEffect;
import com.github.upcraftlp.inhaler.capability.CapabilityGasEffect;
import ladysnake.gaspunk.api.IBreathingHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = GaspunkInhaler.MODID)
public class InhalerHandler {

    @CapabilityInject(IBreathingHandler.class)
    public static final Capability<IBreathingHandler> CAPABILITY_BREATHING = null;

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if(!player.isServerWorld()) return;
        IEntityGasEffect effect = player.getCapability(CapabilityGasEffect.GAS_EFFECT_CAPABILITY, null);
        effect.applyEffects(player, player.getCapability(CAPABILITY_BREATHING, null));
    }

    @SubscribeEvent
    public static void initCaps(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) event.addCapability(new ResourceLocation(GaspunkInhaler.MODID, "gas_effect"), new CapabilityGasEffect());
    }
}
