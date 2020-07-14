/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package kotori.koncclient.konc.gui.konc.theme.hud;

import java.awt.Font;
import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.component.UnboundSlider;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

public class KONCUnboundSliderUI
extends AbstractComponentUI<UnboundSlider> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);

    @Override
    public void renderComponent(UnboundSlider component, FontRenderer fontRenderer) {
        int c;
        String s = component.getText() + ": " + component.getValue();
        int n = c = component.isPressed() ? 11184810 : 14540253;
        if (component.isHovered()) {
            c = (c & 8355711) << 1;
        }
        if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
            GL11.glDisable((int)2884);
            GL11.glEnable((int)3042);
            GL11.glEnable((int)3553);
            this.cFontRenderer.drawString(s, component.getWidth() / 2 - fontRenderer.getStringWidth(s) / 2, component.getHeight() - fontRenderer.getFontHeight() / 2 - 4, c);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)3553);
        } else {
            fontRenderer.drawString(component.getWidth() / 2 - fontRenderer.getStringWidth(s) / 2, component.getHeight() - fontRenderer.getFontHeight() / 2 - 4, c, s);
        }
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleAddComponent(UnboundSlider component, Container container) {
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight());
        component.setWidth(component.getTheme().getFontRenderer().getStringWidth(component.getText()));
    }
}

