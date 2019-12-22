package com.verch.ringu.items;

import com.verch.ringu.Ringu;
import com.verch.ringu.event.RinguEvent;
import com.verch.ringu.util.RinguNBTUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOneRing extends Item {

    public ItemOneRing() {
        super(new Item.Properties().maxStackSize(1).group(Ringu.ringuGroup));
        setRegistryName(new ResourceLocation(Ringu.MODID, "onering"));
    }


    public static boolean isEnabled(ItemStack stack) {
        return RinguNBTUtil.getBoolean(stack, "IsActive", false);
    }

    public static void toggleEnabled(ItemStack stack) {
        RinguNBTUtil.setBoolean(stack, "IsActive", !isEnabled(stack));
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return isEnabled(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            toggleEnabled(stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    public void onInventoryTick(PlayerEntity player, boolean isActive) {
        RinguEvent.onInventoryTick(player, isActive);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean hotbar) {
        if (!(entity instanceof PlayerEntity)) {
            return;
        }
        onInventoryTick((PlayerEntity) entity, isEnabled(stack));
    }
}
