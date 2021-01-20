package com.verch.ringu.buff;

import com.verch.ringu.effect.BuffEffect;
import com.verch.ringu.setup.RinguConfig;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class Buff {

  private static final float defaultWalkSpeed = 0.1f;
  private static final float defaultFlySpeed = 0.05f;
  private static final int airFull = 300;

  private static final boolean enableFlight = RinguConfig.enableFlight.get();
  private static final float buffFlySpeed =
      RinguConfig.flySpeedMultiplier.get().floatValue() * defaultFlySpeed;

  private static final boolean enableWalkingBuff = RinguConfig.enableWalkingBuff.get();
  private static final float buffWalkSpeed =
      RinguConfig.walkSpeedMultiplier.get().floatValue() * defaultWalkSpeed;

  private static final boolean enableWaterBreathing = RinguConfig.enableWaterBreathing.get();

  private static final boolean enableFood = RinguConfig.enableFood.get();
  private static final int foodToAdd = RinguConfig.foodToAdd.get();
  private static final float foodSaturationToAdd =
      RinguConfig.foodSaturationToAdd.get().floatValue();

  private static final boolean enableEffect = RinguConfig.enableEffect.get();
  private static final BuffEffect[] buffEffectArray = RinguConfig.buffEffectArray;

  private static final boolean enableCure = RinguConfig.enableCure.get();
  private static final Effect[] cureEffectArray = RinguConfig.cureEffectArray;

  private static final boolean enableMagnet = RinguConfig.enableMagnet.get();
  private static final int magnetRange = RinguConfig.magnetRange.get();

  private static boolean isDelayed(Entity entity) {
    if (entity instanceof ItemEntity) {
      return ((ItemEntity) entity).cannotPickup();
    } else if (entity instanceof ExperienceOrbEntity)
      return ((ExperienceOrbEntity) entity).delayBeforeCanPickup > 0;
    else {
      return true;
    }
  }

  private static void moveEntityToPlayer(PlayerEntity player, Entity entity) {
    entity.setMotion(0, 0, 0);
    entity.setPosition(
        player.posX - 0.2 + (player.world.rand.nextDouble() * 0.4),
        player.posY - 0.6,
        player.posZ - 0.2 + (player.world.rand.nextDouble() * 0.4));
  }

  private static boolean canMoveEntity(PlayerEntity player, Entity entity) {
    if (entity.getPersistentData().getBoolean("PreventRemoteMovement") || !entity.isAlive()) {
      return false;
    }

    PlayerEntity closest = player.world.getClosestPlayer(entity, 4);
    if (closest != null && closest != player) {
      return false;
    }

    return !isDelayed(entity);
  }

  private static void updateMagnet(PlayerEntity player) {
    if (!enableMagnet || player.isSneaking()) {
      return;
    }

    World world = player.world;

    List<ItemEntity> items =
        world.getEntitiesWithinAABB(
            ItemEntity.class,
            new AxisAlignedBB(
                    player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ)
                .grow(magnetRange, magnetRange, magnetRange));

    for (Entity entity : items) {
      if (canMoveEntity(player, entity)) {
        moveEntityToPlayer(player, entity);
      }
    }

    List<ExperienceOrbEntity> xp =
        world.getEntitiesWithinAABB(
            ExperienceOrbEntity.class,
            new AxisAlignedBB(
                    player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ)
                .grow(magnetRange, magnetRange, magnetRange));

    for (ExperienceOrbEntity orb : xp) {
      if (canMoveEntity(player, orb)) {
        player.onItemPickup(orb, 1);
        player.giveExperiencePoints(orb.xpValue);
        orb.remove();
      }
    }
  }

  private static void passiveBuff(PlayerEntity player) {
    if (enableFood) {
      player.getFoodStats().addStats(foodToAdd, foodSaturationToAdd);
    }
    if (enableWaterBreathing) {
      player.setAir(airFull);
    }
    if (enableEffect) {
      for (BuffEffect buffEffect : buffEffectArray) {
        player.addPotionEffect(
            new EffectInstance(
                buffEffect.getEffect(),
                buffEffect.getDuration(),
                buffEffect.getLevel(),
                true,
                false));
      }
    }
    if (enableCure) {
      for (Effect cureEffect : cureEffectArray) {
        player.removePotionEffect(cureEffect);
      }
    }
  }

  private static void onTickActive(PlayerEntity player) {
    if (enableMagnet) {
      updateMagnet(player);
    }
  }

  private static void onTickPassive(PlayerEntity player) {
    passiveBuff(player);
  }

  private static boolean playerEffectHasChanged(
      PlayerEntity player, boolean allowFlying, float flySpeed, float walkSpeed) {
    return flightNeedsUpdate(player, allowFlying, flySpeed) || walkNeedsUpdate(player, walkSpeed);
  }

  private static boolean walkNeedsUpdate(PlayerEntity player, float walkSpeed) {
    return enableWalkingBuff && player.abilities.getWalkSpeed() != walkSpeed;
  }

  private static boolean flightNeedsUpdate(
      PlayerEntity player, boolean allowFlying, float flySpeed) {
    return enableFlight
        && (player.abilities.allowFlying != allowFlying
            || player.abilities.getFlySpeed() != flySpeed);
  }

  private static void setFlight(CompoundNBT abilitiesTag, boolean allowFlying, float flySpeed) {
    abilitiesTag.putBoolean("mayfly", allowFlying);
    abilitiesTag.putBoolean("flying", allowFlying);
    abilitiesTag.putFloat("flySpeed", flySpeed);
  }

  private static void setWalkBuff(CompoundNBT abilitiesTag, float walkSpeed) {
    abilitiesTag.putFloat("walkSpeed", walkSpeed);
  }

  private static CompoundNBT getPlayerCapabilities(PlayerEntity player) {
    CompoundNBT capabilitiesTag = new CompoundNBT();
    player.abilities.write(capabilitiesTag);
    return capabilitiesTag;
  }

  private static void setPlayerCapabilities(PlayerEntity player, CompoundNBT capabilitiesTag) {
    player.abilities.read(capabilitiesTag);
    player.sendPlayerAbilities();
  }

  private static void setPlayerAbilitiesTag(
      CompoundNBT abilitiesTag, boolean enableFlying, float flightSpeed, float walkSpeed) {
    setFlight(abilitiesTag, enableFlying, flightSpeed);
    setWalkBuff(abilitiesTag, walkSpeed);
  }

  private static void setPlayerEffect(
      PlayerEntity player, boolean enableFlying, float flightSpeed, float walkSpeed) {
    if (playerEffectHasChanged(player, enableFlying, flightSpeed, walkSpeed)) {
      CompoundNBT capabilitiesTag = getPlayerCapabilities(player);
      CompoundNBT abilitiesTag = capabilitiesTag.getCompound("abilities");
      setPlayerAbilitiesTag(abilitiesTag, enableFlying, flightSpeed, walkSpeed);
      setPlayerCapabilities(player, capabilitiesTag);
    }
  }

  private static void enablePlayerEffect(PlayerEntity player) {
    setPlayerEffect(player, true, buffFlySpeed, buffWalkSpeed);
  }

  private static void disablePlayerEffect(PlayerEntity player) {
    setPlayerEffect(player, player.isCreative(), defaultFlySpeed, defaultWalkSpeed);
  }

  /** Activates the One Ring Buffs. */
  public static void activateBuff(PlayerEntity player) {
    enablePlayerEffect(player);
    onTickPassive(player);
    onTickActive(player);
  }

  /** Deactivate the One Ring Buffs. */
  public static void deactivateBuff(PlayerEntity player) {
    disablePlayerEffect(player);
  }

  /** Toggle the One Ring Buffs. */
  public static void toggleBuff(PlayerEntity player, boolean enable) {
    if (enable) {
      activateBuff(player);
    } else {
      deactivateBuff(player);
    }
  }
}
