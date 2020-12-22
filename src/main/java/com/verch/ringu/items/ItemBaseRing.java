package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import com.verch.ringu.event.RinguEvent;
import java.util.List;
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

public class ItemBaseRing extends Item {

  public ItemBaseRing() {
    setRegistryName(new ResourceLocation(Ringu.MODID, "basering"));
    setUnlocalizedName(Ringu.MODID + ".basering");
    setCreativeTab(Ringu.creativeTab);
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(
        this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    if (!world.isRemote && player.isSneaking()) {
      RinguEvent.disable(player);
    }
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public void addInformation(
      ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add(TextFormatting.BLUE + I18n.format("item.ringu.deactivate"));
  }
}
