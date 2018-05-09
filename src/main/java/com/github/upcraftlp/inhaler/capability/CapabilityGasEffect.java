package com.github.upcraftlp.inhaler.capability;

import com.github.upcraftlp.inhaler.GaspunkInhaler;
import com.github.upcraftlp.inhaler.InhalerConfig;
import com.github.upcraftlp.inhaler.api.IEntityGasEffect;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import ladysnake.gaspunk.api.IBreathingHandler;
import ladysnake.gaspunk.api.IGas;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class CapabilityGasEffect implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IEntityGasEffect.class)
    public static final Capability<IEntityGasEffect> GAS_EFFECT_CAPABILITY = null;

    private final IEntityGasEffect instance = GAS_EFFECT_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == GAS_EFFECT_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == GAS_EFFECT_CAPABILITY ? GAS_EFFECT_CAPABILITY.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return GAS_EFFECT_CAPABILITY.getStorage().writeNBT(GAS_EFFECT_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        GAS_EFFECT_CAPABILITY.getStorage().readNBT(GAS_EFFECT_CAPABILITY, this.instance, null, nbt);
    }

    public static class Storage implements Capability.IStorage<IEntityGasEffect> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IEntityGasEffect> capability, IEntityGasEffect instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            for(Map.Entry<IGas, Float> entry : instance.getGases().entrySet()) {
                nbt.setFloat(entry.getKey().getRegistryName().toString(), entry.getValue());
            }
            return nbt;
        }

        @Override
        public void readNBT(Capability<IEntityGasEffect> capability, IEntityGasEffect instance, EnumFacing side, NBTBase nbt) {
            if(nbt instanceof NBTTagCompound) {
                IForgeRegistry<IGas> gasRegistry = GameRegistry.findRegistry(IGas.class);
                NBTTagCompound compound = (NBTTagCompound) nbt;
                for(String name : compound.getKeySet()) {
                    if(!compound.hasKey(name, Constants.NBT.TAG_FLOAT)) continue;
                    IGas gas = gasRegistry.getValue(new ResourceLocation(name));
                    instance.addGasEffect(gas, compound.getFloat(name));
                }
            }
        }
    }

    public static class Provider implements IEntityGasEffect {

        private final Map<IGas, Float> gases = Maps.newHashMap();

        @Override
        public Map<IGas, Float> getGases() {
            return ImmutableMap.copyOf(gases);
        }

        @Override
        public float getMinConcentration() {
            return 0.02F;
        }

        @Override
        public void applyEffects(EntityLivingBase entity, IBreathingHandler handler) {
            synchronized(this.gases) {
                Iterator<IGas> iterator = gases.keySet().iterator();
                while(iterator.hasNext()) {
                    IGas gas = iterator.next();
                    float concentration = gases.get(gas);
                    gas.applyEffect(entity, handler, concentration, concentration == 1.0F, true);
                    concentration *= (float) Math.pow(2.0D, 1.0D / InhalerConfig.halfLife);
                    this.gases.put(gas, concentration);
                    if(GaspunkInhaler.isDebugMode()) GaspunkInhaler.getLogger().info("gas effect: {} (remaining concentration: {})", gas.getRegistryName(), concentration);
                    if(concentration < this.getMinConcentration()) {
                        gas.onExitCloud(entity, handler);
                        iterator.remove();
                    }
                }
            }
        }

        @Override
        public void addGasEffect(IGas gas) {
            synchronized(this.gases) {
                this.gases.put(gas, 1.0F);
            }
        }

        @Override
        public void addGasEffect(IGas gas, float concentration) {
            gases.put(gas, concentration);
        }

    }
}
