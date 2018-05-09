package com.github.upcraftlp.inhaler.api;

import ladysnake.gaspunk.api.IBreathingHandler;
import ladysnake.gaspunk.api.IGas;
import net.minecraft.entity.EntityLivingBase;

import java.util.Map;

public interface IEntityGasEffect {

    Map<IGas, Float> getGases();

    float getMinConcentration();

    void applyEffects(EntityLivingBase entity, IBreathingHandler handler);

    void addGasEffect(IGas gas);

    void addGasEffect(IGas gas, float concentration);

}
