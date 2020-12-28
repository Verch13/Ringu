package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import com.verch.ringu.event.RinguEvent;
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

  public ItemOneRing() {
    super(new Item.Properties().maxStackSize(1).group(Ringu.ringuGroup));
    setRegistryName(new ResourceLocation(Ringu.MODID, "onering"));
  }

  @Override
  public boolean hasEffect(ItemStack stack) {
    return RinguEvent.isActive(stack);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
    if (!world.isRemote && player.isSneaking()) {
      RinguEvent.toggleEnabled(player, player.getHeldItem(hand));
    }
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public void addInformation(
      ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

    super.addInformation(stack, worldIn, tooltip, flagIn);
    if (RinguEvent.isActive(stack)) {
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
