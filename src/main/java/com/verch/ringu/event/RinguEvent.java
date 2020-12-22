package com.verch.ringu.event;

import com.verch.ringu.buff.Buff;
import com.verch.ringu.util.RinguNBTUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RinguEvent {

  private static final String isActive = "ringu:onering:IsActive";

  private static final int maxTick = 19; // Updates every 20 ticks, i.e. every second
  private static final int startTick = -1;

  static int tick = startTick;

  private static int newTick(int tick) {
    int newTick = tick + 1;
    if (newTick >= maxTick) {
      newTick = startTick;
    }

    return newTick;
  }

  private static boolean doUpdate(PlayerEntity player, int tick) {
    return !player.world.isRemote && tick == 0;
  }

  public static boolean isActive(ItemStack stack) {
    return RinguNBTUtil.getBoolean(stack, isActive, true);
  }

  private static boolean isActive(PlayerEntity player) {
    return RinguNBTUtil.getBoolean(player, isActive, false);
  }

  private static void toggleNBTEnabled(PlayerEntity player) {
    RinguNBTUtil.toggleBoolean(player, isActive, false);
  }

  private static void toggleNBTEnabled(ItemStack ring) {
    RinguNBTUtil.toggleBoolean(ring, isActive, true);
  }

  public static void onActivation(PlayerEntity player) {
    Buff.onActivation(player);
  }

  public static void onDeactivation(PlayerEntity player) {
    Buff.onDeactivation(player);
  }

  public static void toggleEnabled(PlayerEntity player, ItemStack stack) {
    boolean playerIsActive = isActive(player);
    boolean itemIsActive = isActive(stack);

    if (itemIsActive) {
      RinguEvent.onActivation(player);
    } else if (playerIsActive) {
      RinguEvent.onDeactivation(player);
    }

    // Only need to toggle if they have different activation states
    if (playerIsActive != itemIsActive) {
      toggleNBTEnabled(player);
      toggleNBTEnabled(stack);
    }
  }

  public static void disable(PlayerEntity player) {
    boolean playerIsActive = isActive(player);
    if (!playerIsActive) {
      return;
    }
    onDeactivation(player);
    toggleNBTEnabled(player);
  }

  @SubscribeEvent
  public static void onTickServerEvent(TickEvent.ServerTickEvent event) {
    tick = newTick(tick);
  }

  @SubscribeEvent
  public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event) {
    PlayerEntity player = event.player;

    if (!doUpdate(player, tick)) {
      return;
    }

    if (isActive(player)) {
      Buff.onTickPassive(player);
      Buff.onTickActive(player);
    }
  }
}
