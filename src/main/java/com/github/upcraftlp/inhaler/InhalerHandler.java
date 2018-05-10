package com.github.upcraftlp.inhaler;

import com.github.upcraftlp.inhaler.api.IEntityGasEffect;
import com.github.upcraftlp.inhaler.capability.CapabilityGasEffect;
import com.google.common.collect.Lists;
import ladysnake.gaspunk.api.IBreathingHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.RecipeBookServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.oredict.OreDictionary;

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

    private static IRecipe EMPTY_INHALER_RECIPE, INHALER_RECIPE;
    private static int OREDICT_ID_IRON_INGOT = -1;

    @SubscribeEvent
    public static void onCraftingIron(PlayerEvent.ItemSmeltedEvent event) {
        if(event.player instanceof EntityPlayerMP) {
            RecipeBookServer recipeBook = ((EntityPlayerMP) event.player).getRecipeBook();
            if((recipeBook.isUnlocked(EMPTY_INHALER_RECIPE) && recipeBook.isUnlocked(INHALER_RECIPE))) return;
            for(int id : OreDictionary.getOreIDs(event.smelting)) {
                if(id == OREDICT_ID_IRON_INGOT) {
                    event.player.unlockRecipes(Lists.newArrayList(EMPTY_INHALER_RECIPE, INHALER_RECIPE));
                    if(GaspunkInhaler.isDebugMode()) GaspunkInhaler.getLogger().info("Player {} has unlocked the inhaler recipes!", event.player.getDisplayNameString());
                }
            }
        }
    }

    static void init() {
        OREDICT_ID_IRON_INGOT = OreDictionary.getOreID("ingotIron");
        EMPTY_INHALER_RECIPE = CraftingManager.getRecipe(new ResourceLocation(GaspunkInhaler.MODID, "empty_inhaler"));
        INHALER_RECIPE = CraftingManager.getRecipe(new ResourceLocation(GaspunkInhaler.MODID, "inhaler"));
        if(GaspunkInhaler.isDebugMode()) GaspunkInhaler.getLogger().info("Ore Dictionary ID of \"ingotIron\": {}", OREDICT_ID_IRON_INGOT);
    }

}
