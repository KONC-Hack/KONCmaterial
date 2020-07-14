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
import kotori.koncclient.konc.gui.konc.RootSmallFontRenderer;
import kotori.koncclient.konc.gui.konc.component.ColorizedCheckButton;
import kotori.koncclient.konc.gui.rgui.component.use.CheckButton;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

public class RootColorizedCheckButtonUI
extends RootCheckButtonUI<ColorizedCheckButton> {
    RootSmallFontRenderer ff = new RootSmallFontRenderer();
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);

    public RootColorizedCheckButtonUI() {
        this.backgroundColour = new Color(this.backgroundColour.getRed(), this.backgroundColour.getGreen(), this.backgroundColour.getBlue());
        this.backgroundColourHover = new Color(this.backgroundColourHover.getRed(), this.backgroundColourHover.getGreen(), this.backgroundColourHover.getBlue());
        this.downColourNormal = new Color(190, 190, 190);
    }

    @Override
    public void renderComponent(CheckButton component, FontRenderer aa) {
        GL11.glColor4f((float)((float)this.backgroundColour.getRed() / 255.0f), (float)((float)this.backgroundColour.getGreen() / 255.0f), (float)((float)this.backgroundColour.getBlue() / 255.0f), (float)component.getOpacity());
        if (component.isHovered() || component.isPressed()) {
            GL11.glColor4f((float)((float)this.backgroundColourHover.getRed() / 255.0f), (float)((float)this.backgroundColourHover.getGreen() / 255.0f), (float)((float)this.backgroundColourHover.getBlue() / 255.0f), (float)component.getOpacity());
        }
        if (component.isToggled()) {
            GL11.glColor3f((float)((float)this.backgroundColour.getRed() / 255.0f), (float)((float)this.backgroundColour.getGreen() / 255.0f), (float)((float)this.backgroundColour.getBlue() / 255.0f));
        }
        GL11.glLineWidth((float)2.5f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)0.0, (double)component.getHeight());
        GL11.glVertex2d((double)component.getWidth(), (double)component.getHeight());
        GL11.glEnd();
        Color idleColour = component.isToggled() ? this.idleColourToggle : this.idleColourNormal;
        Color downColour = component.isToggled() ? this.downColourToggle : this.downColourNormal;
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        this.ff.drawString(component.getWidth() / 2 - KONCGUI.fontRenderer.getStringWidth(component.getName()) / 2, 0, component.isPressed() ? downColour : idleColour, component.getName());
        GL11.glDisable((int)3553);
    }
}

