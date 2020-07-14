/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package kotori.koncclient.konc.gui.konc.theme.hud;

import java.awt.Color;
import java.awt.Font;
import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.container.use.Groupbox;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

public class RootGroupboxUI
extends AbstractComponentUI<Groupbox> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);

    @Override
    public void renderComponent(Groupbox component, FontRenderer fontRenderer) {
        GL11.glLineWidth((float)1.0f);
        if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
            this.cFontRenderer.drawString(component.getName(), 1.0f, 1.0f, Color.white.getRGB());
        } else {
            fontRenderer.drawString(1, 1, component.getName());
        }
        GL11.glColor3f((float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)component.getWidth(), (double)0.0);
        GL11.glVertex2d((double)component.getWidth(), (double)0.0);
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glVertex2d((double)0.0, (double)component.getHeight());
        GL11.glVertex2d((double)0.0, (double)component.getHeight());
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glEnd();
    }

    @Override
    public void handleMouseDown(Groupbox component, int x, int y, int button) {
    }

    @Override
    public void handleAddComponent(Groupbox component, Container container) {
        component.setWidth(100);
        component.setHeight(200);
        component.setOriginOffsetY(component.getTheme().getFontRenderer().getFontHeight() + 3);
    }
}

