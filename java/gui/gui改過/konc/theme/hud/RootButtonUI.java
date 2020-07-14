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
import kotori.koncclient.konc.gui.konc.KONCGUI;
import kotori.koncclient.konc.gui.konc.RenderHelper;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.use.Button;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

public class RootButtonUI<T extends Button>
extends AbstractComponentUI<Button> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);
    protected Color idleColour = new Color(163, 163, 163);
    protected Color downColour = new Color(255, 255, 255);

    @Override
    public void renderComponent(Button component, FontRenderer ff) {
        GL11.glColor3f((float)0.22f, (float)0.22f, (float)0.22f);
        if (component.isHovered() || component.isPressed()) {
            GL11.glColor3f((float)0.26f, (float)0.26f, (float)0.26f);
        }
        RenderHelper.drawRoundedRectangle(0.0f, 0.0f, component.getWidth(), component.getHeight(), 3.0f);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        KONCGUI.fontRenderer.drawString(component.getWidth() / 2 - KONCGUI.fontRenderer.getStringWidth(component.getName()) / 2, 0, component.isPressed() ? this.downColour : this.idleColour, component.getName());
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleAddComponent(Button component, Container container) {
        component.setWidth(KONCGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KONCGUI.fontRenderer.getFontHeight() + 2);
    }
}

