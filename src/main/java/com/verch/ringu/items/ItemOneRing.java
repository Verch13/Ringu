package com.verch.ringu.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.verch.ringu.Ringu;
import com.verch.ringu.event.RinguEvent;
import com.verch.ringu.util.RinguNBTUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemOneRing extends Item implements IBauble {

    public ItemOneRing() {
        setRegistryName(new ResourceLocation(Ringu.MODID, "onering"));
        setUnlocalizedName(Ringu.MODID + ".onering");
        setCreativeTab(Ringu.creativeTab);
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public static boolean isEnabled(ItemStack stack) {
        return RinguNBTUtil.getBoolean(stack, "IsActive", false);
    }

    public static void toggleEnabled(ItemStack stack) {
        RinguNBTUtil.setBoolean(stack, "IsActive", !isEnabled(stack));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnabled(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            toggleEnabled(stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    public void onTickUpdate(EntityPlayer player, boolean isActive) {
        RinguEvent.onTick(player, isActive);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean hotbar) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        onTickUpdate((EntityPlayer) entity, isEnabled(stack));
    }

    @Optional.Method(modid = "baubles")
    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Optional.Method(modid = "baubles")
    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        onTickUpdate((EntityPlayer) player, isEnabled(itemstack));
    }

    @Optional.Method(modid = "baubles")
    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        RinguEvent.onEquip((EntityPlayer) player);
    }

    @Optional.Method(modid = "baubles")
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        RinguEvent.onUnequip((EntityPlayer) player);
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Optional.Method(modid = "baubles")
    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }

}
