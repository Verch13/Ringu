package com.verch.ringu.buff;

import com.verch.ringu.effect.BuffEffect;
import com.verch.ringu.setup.RinguConfig;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class Buff {

  private static final float defaultWalkSpeed = 0.1f;
  private static final float defaultFlySpeed = 0.05f;
  private static final int airFull = 300;

  private static final boolean enableFlight = RinguConfig.flight.enableFlight;
  private static final float buffFlySpeed = RinguConfig.flight.flySpeedMultiplier * defaultFlySpeed;

  private static final boolean enableWalkingBuff = RinguConfig.ground.enableWalkingBuff;
  private static final float buffWalkSpeed =
      RinguConfig.ground.walkSpeedMultiplier * defaultWalkSpeed;

  private static final boolean enableWaterBreathing = RinguConfig.water.enableWaterBreathing;

  private static final boolean enableFood = RinguConfig.food.enableFood;
  private static final int foodToAdd = RinguConfig.food.foodToAdd;
  private static final float foodSaturationToAdd = RinguConfig.food.foodSaturationToAdd;

  private static final boolean enableEffect = RinguConfig.effect.enableEffect;
  private static final BuffEffect[] buffEffectArray = RinguConfig.effect.buffEffectArray;

  private static final boolean enableCure = RinguConfig.cure.enableCure;
  private static final Potion[] cureEffectArray = RinguConfig.cure.cureEffectArray;

  private static final boolean enableMagnet = RinguConfig.magnet.enableMagnet;
  private static final int magnetRange = RinguConfig.magnet.magnetRange;

  private static boolean isDelayed(Entity entity) {
    if (entity instanceof EntityItem) {
      return ((EntityItem) entity).cannotPickup();
    } else if (entity instanceof EntityXPOrb)
      return ((EntityXPOrb) entity).delayBeforeCanPickup > 0;
    else {
      return true;
    }
  }

  private static void moveEntityToPlayer(EntityPlayer player, Entity entity) {
    entity.motionX = entity.motionY = entity.motionZ = 0;
    entity.setPosition(
        player.posX - 0.2 + (player.world.rand.nextDouble() * 0.4),
        player.posY - 0.6,
        player.posZ - 0.2 + (player.world.rand.nextDouble() * 0.4));
  }

  private static boolean canMoveEntity(EntityPlayer player, Entity entity) {
    if (entity.getEntityData().getBoolean("PreventRemoteMovement") || entity.isDead) {
      return false;
    }

    EntityPlayer closest = player.world.getClosestPlayerToEntity(entity, 4);
    if (closest != null && closest != player) {
      return false;
    }

    return !isDelayed(entity);
  }

  private static void updateMagnet(EntityPlayer player) {
    if (!enableMagnet || player.isSneaking()) {
      return;
    }

    World world = player.world;

    List<EntityItem> items =
        world.getEntitiesWithinAABB(
            EntityItem.class,
            new AxisAlignedBB(
                    player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ)
                .grow(magnetRange, magnetRange, magnetRange));

    for (Entity entity : items) {
      if (canMoveEntity(player, entity)) {
        moveEntityToPlayer(player, entity);
      }
    }

    List<EntityXPOrb> xp =
        world.getEntitiesWithinAABB(
            EntityXPOrb.class,
            new AxisAlignedBB(
                    player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ)
                .grow(magnetRange, magnetRange, magnetRange));

    for (EntityXPOrb orb : xp) {
      if (canMoveEntity(player, orb)) {
        player.onItemPickup(orb, 1);
        player.addExperience(orb.xpValue);
        orb.setDead();
      }
    }
  }

  private static void passiveBuff(EntityPlayer player) {
    if (enableFood) {
      player.getFoodStats().addStats(foodToAdd, foodSaturationToAdd);
    }
    if (enableWaterBreathing) {
      player.setAir(airFull);
    }
    if (enableEffect) {
      for (BuffEffect buffEffect : buffEffectArray) {
        player.addPotionEffect(
            new PotionEffect(
                buffEffect.getEffect(),
                buffEffect.getDuration(),
                buffEffect.getLevel(),
                true,
                false));
      }
    }
    if (enableCure) {
      for (Potion cureEffect : cureEffectArray) {
        player.removePotionEffect(cureEffect);
      }
    }
  }

  public static void onTickActive(EntityPlayer player) {
    if (enableMagnet) {
      updateMagnet(player);
    }
  }

  public static void onTickPassive(EntityPlayer player) {
    passiveBuff(player);
  }

  public static void onActivation(EntityPlayer player) {
    if (enableFlight) {
      player.capabilities.allowFlying = true;
      player.capabilities.setFlySpeed(buffFlySpeed);
    }
    if (enableWalkingBuff) {
      player.capabilities.setPlayerWalkSpeed(buffWalkSpeed);
    }

    player.sendPlayerAbilities();
  }

  public static void onDeactivation(EntityPlayer player) {
    if (enableFlight) {
      player.capabilities.setFlySpeed(defaultFlySpeed);
      if (!player.isCreative()) {
        player.capabilities.isFlying = false;
        player.capabilities.allowFlying = false;
      }
    }
    if (enableWalkingBuff) {
      player.capabilities.setPlayerWalkSpeed(defaultWalkSpeed);
    }

    player.sendPlayerAbilities();
  }
}
