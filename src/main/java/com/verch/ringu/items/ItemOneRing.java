package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import com.verch.ringu.event.RinguEvent;
import com.verch.ringu.util.RinguNBTUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;

public class ItemOneRing extends Item {

    private static final String isActive = "ringu:onering:IsActive";

    public ItemOneRing() {
        setRegistryName(new ResourceLocation(Ringu.MODID, "onering"));
        setUnlocalizedName(Ringu.MODID + ".onering");
        setCreativeTab(Ringu.creativeTab);
    }

    private static boolean oneRingItemIsActive(ItemStack stack){return RinguNBTUtil.getBoolean(stack, isActive, true);}

    public static boolean oneRingPlayerIsActive(EntityPlayer player){return RinguNBTUtil.getBoolean(player, isActive, false);}

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack) {
        return oneRingItemIsActive(stack);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public static void toggleEnabled(EntityPlayer player, ItemStack stack) {
        if (oneRingItemIsActive(stack) == oneRingPlayerIsActive(player)){
            return;
        }
        else if(oneRingPlayerIsActive(player)) {
            RinguEvent.onDeactivation(player);
        }
        else{
            RinguEvent.onActivation(player);
        }
         RinguNBTUtil.toggleBoolean(player, isActive, false);
         RinguNBTUtil.toggleBoolean(stack, isActive, true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (player.isSneaking()) {
            toggleEnabled(player, stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        if(oneRingItemIsActive(stack)){
            tooltip.add(TextFormatting.BLUE + I18n.format("item.ringu.charged"));
            tooltip.add(TextFormatting.BLUE + I18n.format("item.ringu.activate"));
        }
        else{
            tooltip.add(TextFormatting.BLUE + I18n.format("item.ringu.depleted"));
            tooltip.add(TextFormatting.BLUE + I18n.format("item.ringu.deactivate"));

        }
    }
}
