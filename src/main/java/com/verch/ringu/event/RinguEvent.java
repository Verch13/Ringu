package com.verch.ringu.event;

import com.verch.ringu.RinguItems;
import com.verch.ringu.buff.Buff;
import com.verch.ringu.compat.BaubleTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class RinguEvent {

    private static final int maxTick = 19; //Updates every 20 ticks, i.e. every second
    private static final int startTick = -1;

    static int tick = startTick;
    static int tickBauble = startTick;

    private static final ItemStack oneRingStack = new ItemStack(RinguItems.itemOneRing);

    private static int newTick(int tick) {
        int newTick = tick + 1;
        if (newTick >= maxTick) {
            newTick = startTick;
        }

        return newTick;
    }

    private static boolean doUpdate(EntityPlayer player, int tick) {
        return !player.world.isRemote && tick == 0;
    }

    public static void onEquip(EntityPlayer player) {
        Buff.equipBuff(player);
    }

    public static void onUnequip(EntityPlayer player) {
        Buff.unequipBuff(player);
    }

    private static boolean hasOneRing(EntityPlayer player) {
        return player.inventory.hasItemStack(oneRingStack);
    }

    public static void onInventoryTick(EntityPlayer player, boolean isActive) {
        if (!doUpdate(player, tickBauble)) {
            return;
        }
        Buff.onTickBuff(player, isActive);
    }

    @SubscribeEvent
    public static void onTickServerEvent(TickEvent.ServerTickEvent event) {
        tick = newTick(tick);
        tickBauble = newTick(tickBauble);
    }

    @SubscribeEvent
    public static void onTickPlayerEvent(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;

        //No need to update if the player has the item as a bauble
        if (!doUpdate(player, tick) || BaubleTools.hasOneRingBauble(player)) {
            return;
        }

        if (hasOneRing(player)) {
            onEquip(player);
        } else {
            onUnequip(player);
        }
    }
}
