package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class AimAssist extends Module {
    private NumberSetting speed = new NumberSetting("Speed", 25, 1, 100, 1);
    private NumberSetting range = new NumberSetting("Range", 4.5, 1, 10, 0.1);
    private NumberSetting fov = new NumberSetting("FOV", 90, 1, 180, 1);
    private NumberSetting maxAngleChange = new NumberSetting("MaxAngleChange", 10, 1, 45, 1);
    private NumberSetting aimStopThreshold = new NumberSetting("AimStopThreshold", 0.1, 0, 5, 0.1);
    private BooleanSetting aimOnHold = new BooleanSetting("Aim On Hold", true);
    private Random random = new Random();

    public AimAssist() {
        super("AimAssist", "Automatically aims at players.", Category.COMBAT);
        addSetting(speed);
        addSetting(range);
        addSetting(fov);
        addSetting(maxAngleChange);
        addSetting(aimStopThreshold);
        addSetting(aimOnHold);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null) {
            return;
        }

        if (aimOnHold.isEnabled() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
            return;
        }

        EntityPlayer target = getClosestPlayer();
        if (target != null) {
            faceEntity(target);
        }
    }

    private EntityPlayer getClosestPlayer() {
        EntityPlayer closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        for (EntityPlayer player : mc.theWorld.playerEntities) {
            if (player != mc.thePlayer && mc.thePlayer.canEntityBeSeen(player)) {
                double distance = mc.thePlayer.getDistanceToEntity(player);
                if (distance < closestDistance && distance <= range.getValue() && getRotationDifference(player) <= fov.getValue()) {
                    closestDistance = distance;
                    closestPlayer = player;
                }
            }
        }

        return closestPlayer;
    }

    private void faceEntity(Entity entity) {
        // Dynamic vertical aim point
        double y = entity.posY + (random.nextDouble() * entity.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double x = entity.posX - mc.thePlayer.posX;
        double z = entity.posZ - mc.thePlayer.posZ;

        double dist = Math.sqrt(x * x + z * z);
        float targetYaw = (float) (Math.atan2(z, x) * 180 / Math.PI) - 90;
        float targetPitch = (float) -(Math.atan2(y, dist) * 180 / Math.PI);

        float yawChange = updateRotation(mc.thePlayer.rotationYaw, targetYaw, (float) speed.getValue());
        float pitchChange = updateRotation(mc.thePlayer.rotationPitch, targetPitch, (float) speed.getValue());

        mc.thePlayer.rotationYaw += yawChange;
        mc.thePlayer.rotationPitch += pitchChange;
    }

    private float updateRotation(float current, float intended, float speed) {
        float rotationDifference = net.minecraft.util.MathHelper.wrapAngleTo180_float(intended - current);

        // Stop aiming if already close enough
        if (Math.abs(rotationDifference) < aimStopThreshold.getValue()) {
            return 0.0f;
        }

        float smoothing = speed / 100.0f;
        float change = rotationDifference * smoothing;

        // Limit turn rate by MaxAngleChange
        float maxChange = (float) maxAngleChange.getValue();
        if (change > maxChange) {
            change = maxChange;
        } else if (change < -maxChange) {
            change = -maxChange;
        }

        return change;
    }

    private double getRotationDifference(Entity entity) {
        double x = entity.posX - mc.thePlayer.posX;
        double z = entity.posZ - mc.thePlayer.posZ;
        float yaw = (float) (Math.atan2(z, x) * 180 / Math.PI) - 90;
        return Math.abs(net.minecraft.util.MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) - yaw);
    }
}