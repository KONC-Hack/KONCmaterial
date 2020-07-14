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
import kotori.koncclient.konc.gui.rgui.component.AlignedComponent;
import kotori.koncclient.konc.gui.rgui.component.use.Label;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

public class RootLabelUI<T extends Label>
extends AbstractComponentUI<Label> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);

    @Override
    public void renderComponent(Label component, FontRenderer a) {
        a = component.getFontRenderer();
        String[] lines = component.getLines();
        int y = 0;
        boolean shadow = component.isShadow();
        for (String s : lines) {
            int x = 0;
            if (component.getAlignment() == AlignedComponent.Alignment.CENTER) {
                x = component.getWidth() / 2 - a.getStringWidth(s) / 2;
            } else if (component.getAlignment() == AlignedComponent.Alignment.RIGHT) {
                x = component.getWidth() - a.getStringWidth(s);
            }
            if (shadow) {
                if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
                    GL11.glDisable((int)2884);
                    GL11.glEnable((int)3042);
                    GL11.glEnable((int)3553);
                    this.cFontRenderer.drawStringWithShadow(s, x, y, Color.white.getRGB());
                    GL11.glEnable((int)2884);
                    GL11.glDisable((int)3042);
                    GL11.glDisable((int)3553);
                } else {
                    a.drawStringWithShadow(x, y, 255, 255, 255, s);
                }
            } else if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
                GL11.glDisable((int)2884);
                GL11.glEnable((int)3042);
                GL11.glEnable((int)3553);
                this.cFontRenderer.drawString(s, x, y, Color.white.getRGB());
                GL11.glEnable((int)2884);
                GL11.glDisable((int)3042);
                GL11.glDisable((int)3553);
            } else {
                a.drawString(x, y, s);
            }
            y += a.getFontHeight() + 3;
        }
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleSizeComponent(Label component) {
        String[] lines = component.getLines();
        int y = 0;
        int w = 0;
        for (String s : lines) {
            w = Math.max(w, component.getFontRenderer().getStringWidth(s));
            y += component.getFontRenderer().getFontHeight() + 3;
        }
        component.setWidth(w);
        component.setHeight(y);
    }
}

