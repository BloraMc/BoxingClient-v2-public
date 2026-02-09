package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.ColorSetting;
import bloramcboxingclient.lystech.org.settings.ModeSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public class ESP extends Module {

    private ModeSetting mode = new ModeSetting("Mode", "Box", "Box", "2D");
    private ColorSetting color = new ColorSetting("Color", Color.RED);
    private BooleanSetting thruBlocks = new BooleanSetting("ThruBlocks", true);

    public ESP() {
        super("ESP", "Draws a visual indicator around entities.", Category.RENDER);
        addSetting(mode);
        addSetting(color);
        addSetting(thruBlocks);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (mc.theWorld == null) {
            return;
        }

        for (EntityLivingBase entity : mc.theWorld.getEntities(EntityLivingBase.class, e -> e instanceof EntityPlayer && e != mc.thePlayer)) {
            if (shouldRender(entity)) {
                render(entity, event.partialTicks);
            }
        }
    }

    private boolean shouldRender(EntityLivingBase entity) {
        if (entity.isDead || entity == mc.thePlayer) {
            return false;
        }
        return true;
    }

    private void render(EntityLivingBase entity, float partialTicks) {
        Color entityColor = color.getColor();

        if (thruBlocks.isEnabled()) {
            GlStateManager.disableDepth();
        }

        switch (mode.getMode()) {
            case "Box":
                drawEntityBox(entity, entityColor, partialTicks);
                break;
            case "2D":
                draw2DBox(entity, entityColor, partialTicks);
                break;
        }

        if (thruBlocks.isEnabled()) {
            GlStateManager.enableDepth();
        }
    }

    private void drawEntityBox(EntityLivingBase entity, Color color, float partialTicks) {
        RenderManager rm = mc.getRenderManager();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - rm.viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - rm.viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - rm.viewerPosZ;

        AxisAlignedBB bb = entity.getEntityBoundingBox();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                bb.minX - entity.posX + x,
                bb.minY - entity.posY + y,
                bb.minZ - entity.posZ + z,
                bb.maxX - entity.posX + x,
                bb.maxY - entity.posY + y,
                bb.maxZ - entity.posZ + z
        );

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glLineWidth(2.0F);
        setColor(color);
        drawOutlinedBoundingBox(axisAlignedBB);
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void draw2DBox(EntityLivingBase entity, Color color, float partialTicks) {
        // Simplified 2D box - for demonstration
        RenderManager rm = mc.getRenderManager();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - rm.viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - rm.viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - rm.viewerPosZ;
        
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(-rm.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(rm.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.02666667, -0.02666667, 0.02666667);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glLineWidth(2.0F);
        setColor(color);

        float halfWidth = entity.width / 2.0f * 30;
        float height = entity.height * 35;

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2f(-halfWidth, 0);
        GL11.glVertex2f(-halfWidth, height);
        GL11.glVertex2f(halfWidth, height);
        GL11.glVertex2f(halfWidth, 0);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex3d(aa.minX, aa.minY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.minY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        GL11.glVertex3d(aa.minX, aa.minY, aa.maxZ);
        GL11.glVertex3d(aa.minX, aa.minY, aa.minZ);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex3d(aa.minX, aa.maxY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        GL11.glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        GL11.glVertex3d(aa.minX, aa.maxY, aa.minZ);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(aa.minX, aa.minY, aa.minZ);
        GL11.glVertex3d(aa.minX, aa.maxY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.minY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.maxY, aa.minZ);
        GL11.glVertex3d(aa.maxX, aa.minY, aa.maxZ);
        GL11.glVertex3d(aa.maxX, aa.maxY, aa.maxZ);
        GL11.glVertex3d(aa.minX, aa.minY, aa.maxZ);
        GL11.glVertex3d(aa.minX, aa.maxY, aa.maxZ);
        GL11.glEnd();
    }

    private void setColor(Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
}