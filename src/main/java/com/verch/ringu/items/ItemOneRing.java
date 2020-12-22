package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import com.verch.ringu.event.RinguEvent;
import com.verch.ringu.util.RinguNBTUtil;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemOneRing extends Item {

  private static final String isActive = "ringu:onering:IsActive";

  public ItemOneRing() {
    super(new Item.Properties().maxStackSize(1).group(Ringu.ringuGroup));
    setRegistryName(new ResourceLocation(Ringu.MODID, "onering"));
  }

  private static boolean oneRingItemIsActive(ItemStack stack) {
    return RinguNBTUtil.getBoolean(stack, isActive, true);
  }

  public static boolean oneRingPlayerIsActive(PlayerEntity player) {
    return RinguNBTUtil.getBoolean(player, isActive, false);
  }

  @Override
  public boolean hasEffect(ItemStack stack) {
    return oneRingItemIsActive(stack);
  }

  public static void toggleEnabled(PlayerEntity player, ItemStack stack) {
    if (oneRingItemIsActive(stack) == oneRingPlayerIsActive(player)) {
      return;
    } else if (oneRingPlayerIsActive(player)) {
      RinguEvent.onDeactivation(player);
    } else {
      RinguEvent.onActivation(player);
    }
    RinguNBTUtil.toggleBoolean(player, isActive, false);
    RinguNBTUtil.toggleBoolean(stack, isActive, true);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getHeldItem(hand);

    if (player.isSneaking()) {
      toggleEnabled(player, stack);
    }
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public void addInformation(
      ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

    super.addInformation(stack, worldIn, tooltip, flagIn);
    if (oneRingItemIsActive(stack)) {
      tooltip.add(new StringTextComponent(TextFormatting.BLUE + I18n.format("item.ringu.charged")));
      tooltip.add(
          new StringTextComponent(TextFormatting.BLUE + I18n.format("item.ringu.activate")));
    } else {
      tooltip.add(
          new StringTextComponent(TextFormatting.BLUE + I18n.format("item.ringu.depleted")));
      tooltip.add(
          new StringTextComponent(TextFormatting.BLUE + I18n.format("item.ringu.deactivate")));
    }
  }
}
