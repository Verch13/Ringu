package com.verch.ringu.event;

import com.verch.ringu.RinguItems;
import com.verch.ringu.buff.Buff;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RinguEvent {

    private static final int maxTick = 19; //Updates every 20 ticks, i.e. every second
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

    private static void onEquip(PlayerEntity player) {
        Buff.equipBuff(player);
    }

    private static void onUnequip(PlayerEntity player) {
        Buff.unequipBuff(player);
    }

    private static boolean hasOneRing(PlayerEntity player) {
        return player.inventory.hasItemStack(new ItemStack(RinguItems.itemOneRing));
    }

    public static void onInventoryTick(PlayerEntity player, boolean isActive) {
        if (!doUpdate(player, tick)) {
            return;
        }
        Buff.onTickBuff(player, isActive);
    }

    @SubscribeEvent
    public static void onTickServerEvent(TickEvent.ServerTickEvent event) {
        tick = newTick(tick);
    }

    @SubscribeEvent
    public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        //No need to update if the player has the item as a bauble
        if (!doUpdate(player, tick)) {
            return;
        }

        if (hasOneRing(player)) {
            onEquip(player);
        } else {
            onUnequip(player);
        }
    }
}
