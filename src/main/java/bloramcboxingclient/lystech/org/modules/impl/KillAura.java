package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import bloramcboxingclient.lystech.org.utils.MSTimer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {

    private NumberSetting range = new NumberSetting("Range", 3.7, 1, 8, 0.1);
    private NumberSetting cps = new NumberSetting("CPS", 8, 1, 20, 1);
    private BooleanSetting swing = new BooleanSetting("Swing", true);
    private BooleanSetting keepSprint = new BooleanSetting("KeepSprint", true);

    private EntityLivingBase target;
    private MSTimer attackTimer = new MSTimer();

    private BooleanSetting playersOnly = new BooleanSetting("Players Only", true);

    public KillAura() {
        super("KillAura", "Automatically attacks entities in range.", Keyboard.KEY_R, Category.COMBAT);
        addSettings(range, cps, swing, keepSprint, playersOnly);
    }

    @Override
    public void onEnable() {
        target = null;
        attackTimer.reset();
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null) return;

        List<Entity> targets = mc.theWorld.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityLivingBase && entity != mc.thePlayer && mc.thePlayer.getDistanceToEntity(entity) <= range.getValue() && entity.isEntityAlive())
                .filter(entity -> !playersOnly.isEnabled() || entity instanceof EntityPlayer)
                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceSqToEntity(entity)))
                .collect(Collectors.toList());

        if (!targets.isEmpty()) {
            target = (EntityLivingBase) targets.get(0);
        } else {
            target = null;
            return;
        }

        if (attackTimer.hasTimePassed((long) (1000 / cps.getValue()))) {
            if (swing.isEnabled()) {
                mc.thePlayer.swingItem();
            }

            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

            if (!keepSprint.isEnabled() && mc.thePlayer.isSprinting()) {
                mc.thePlayer.setSprinting(false);
            }
            attackTimer.reset();
        }
    }
}