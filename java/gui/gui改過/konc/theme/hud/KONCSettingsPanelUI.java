/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.konc.RenderHelper;
import kotori.koncclient.konc.gui.konc.component.SettingsPanel;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

public class KONCSettingsPanelUI
extends AbstractComponentUI<SettingsPanel> {
    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        super.renderComponent(component, fontRenderer);
        GL11.glLineWidth((float)2.0f);
        GL11.glColor4f((float)0.2f, (float)0.2f, (float)0.2f, (float)0.8f);
        RenderHelper.drawFilledRectangle(0.0f, 0.0f, component.getWidth(), component.getHeight() + 3);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glLineWidth((float)1.0f);
        RenderHelper.drawRectangle(0.0f, 0.0f, component.getWidth(), component.getHeight() + 3);
    }
}

