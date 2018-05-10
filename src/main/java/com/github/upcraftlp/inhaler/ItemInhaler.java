package com.github.upcraftlp.inhaler;

import com.github.upcraftlp.inhaler.api.IEntityGasEffect;
import com.github.upcraftlp.inhaler.capability.CapabilityGasEffect;
import ladysnake.gaspunk.GasPunk;
import ladysnake.gaspunk.api.IGas;
import ladysnake.gaspunk.item.ItemGasTube;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemInhaler extends ItemGasTube {

    public ItemInhaler() {
        this.setRegistryName("inhaler");
        this.setUnlocalizedName(GaspunkInhaler.MODID + ".inhaler");
        this.setCreativeTab(GasPunk.CREATIVE_TAB);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            IGas gas = getContainedGas(stack);
            IEntityGasEffect gasEffect = playerIn.getCapability(CapabilityGasEffect.GAS_EFFECT_CAPABILITY, null);
            gasEffect.addGasEffect(gas);
            if(!playerIn.isCreative()) stack = new ItemStack(ModItems.EMPTY_INHALER);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
