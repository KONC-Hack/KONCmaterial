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
import kotori.koncclient.konc.gui.rgui.component.use.CheckButton;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

public class RootCheckButtonUI<T extends CheckButton>
extends AbstractComponentUI<CheckButton> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);
    protected Color backgroundColour = new Color(46, 88, 200);
    protected Color backgroundColourHover = new Color(81, 184, 255);
    protected Color idleColourNormal = new Color(200, 200, 200);
    protected Color downColourNormal = new Color(190, 190, 190);
    protected Color idleColourToggle = new Color(126, 206, 250);
    protected Color downColourToggle = this.idleColourToggle.brighter();

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {
        if (component.isToggled()) {
            GL11.glColor4f((float)0.25f, (float)0.5f, (float)0.75f, (float)1.0f);
            RenderHelper.drawFilledRectangle(0.0f, KONCGUI.fontRenderer.getFontHeight() / 2 - 5, component.getWidth(), (float)KONCGUI.fontRenderer.getFontHeight() * 1.6f);
        }
        String text = component.getName();
        int c = 16777215;
        if (component.isHovered()) {
            c = (c & 8355711) << 1;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (ModuleManager.getModuleByName("SmoothFont").isDisabled()) {
            KONCGUI.fontRenderer.drawString(1, KONCGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);
        } else {
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2884);
            this.cFontRenderer.drawString(text, 1.0f, KONCGUI.fontRenderer.getFontHeight() / 2 - 2, c);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2884);
        }
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KONCGUI.fontRenderer.getStringWidth("Dispenser32kne") + 2);
        component.setHeight(KONCGUI.fontRenderer.getFontHeight() + 2);
    }
}

