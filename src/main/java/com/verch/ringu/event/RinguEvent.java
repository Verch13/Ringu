package com.verch.ringu.event;

import com.verch.ringu.buff.Buff;
import com.verch.ringu.items.ItemOneRing;
import net.minecraft.entity.player.PlayerEntity;
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

    public static void onActivation(PlayerEntity player){
        Buff.onActivation(player);
    }

    public static void onDeactivation(PlayerEntity player){
        Buff.onDeactivation(player);
    }

    @SubscribeEvent
    public static void onTickServerEvent(TickEvent.ServerTickEvent event) {
        tick = newTick(tick);
    }

    @SubscribeEvent
    public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (!doUpdate(player, tick)){return;}

        if(ItemOneRing.oneRingPlayerIsActive(player)){
            Buff.onTickPassive(player);
            Buff.onTickActive(player);
        }
    }
}

