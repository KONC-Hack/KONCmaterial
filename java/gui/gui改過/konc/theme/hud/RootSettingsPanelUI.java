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

public class RootSettingsPanelUI
extends AbstractComponentUI<SettingsPanel> {
    @Override
    public void renderComponent(SettingsPanel component, FontRenderer fontRenderer) {
        GL11.glColor4f((float)0.2f, (float)0.2f, (float)0.2f, (float)0.75f);
        RenderHelper.drawOutlinedRoundedRectangle(0, 0, component.getWidth(), component.getHeight(), 0.2f, 0.2f, 0.2f, 0.8f, component.getOpacity(), 1.0f);
    }
}

