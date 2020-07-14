/*
 * Decompiled with CFR 0.145.
 */
package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.konc.KONCGUI;
import kotori.koncclient.konc.gui.konc.theme.staticui.RadarUI;
import kotori.koncclient.konc.gui.konc.theme.staticui.TabGuiUI;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.gui.rgui.render.theme.AbstractTheme;

public class KONCTheme
extends AbstractTheme {
    FontRenderer fontRenderer;

    public KONCTheme() {
        this.installUI(new RootButtonUI());
        this.installUI(new GUIUI());
        this.installUI(new RootGroupboxUI());
        this.installUI(new KONCFrameUI());
        this.installUI(new RootScrollpaneUI());
        this.installUI(new RootInputFieldUI());
        this.installUI(new RootLabelUI());
        this.installUI(new RootChatUI());
        this.installUI(new RootCheckButtonUI());
        this.installUI(new KONCActiveModulesUI());
        this.installUI(new KONCSettingsPanelUI());
        this.installUI(new RootSliderUI());
        this.installUI(new KONCEnumbuttonUI());
        this.installUI(new RootColorizedCheckButtonUI());
        this.installUI(new KONCUnboundSliderUI());
        this.installUI(new RadarUI());
        this.installUI(new TabGuiUI());
        this.fontRenderer = KONCGUI.fontRenderer;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public class GUIUI
    extends AbstractComponentUI<KONCGUI> {
    }

}

