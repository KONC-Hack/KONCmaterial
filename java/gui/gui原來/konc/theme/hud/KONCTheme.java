package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.konc.KONCGUI;
import kotori.koncclient.konc.gui.konc.theme.staticui.RadarUI;
import kotori.koncclient.konc.gui.konc.theme.staticui.TabGuiUI;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.gui.rgui.render.theme.AbstractTheme;

public class KONCTheme extends AbstractTheme {

    FontRenderer fontRenderer;

    public KONCTheme() {
        installUI(new RootButtonUI<>());
        installUI(new GUIUI());
        installUI(new RootGroupboxUI());
        installUI(new KONCFrameUI<>());
        installUI(new RootScrollpaneUI());
        installUI(new RootInputFieldUI());
        installUI(new RootLabelUI());
        installUI(new RootChatUI());
        installUI(new RootCheckButtonUI());
        installUI(new KONCActiveModulesUI());
        installUI(new KONCSettingsPanelUI());
        installUI(new RootSliderUI());
        installUI(new KONCEnumButtonUI());
        installUI(new RootColorizedCheckButtonUI());
        installUI(new KONCUnboundSliderUI());

        installUI(new RadarUI());
        installUI(new TabGuiUI());

        fontRenderer=KONCGUI.fontRenderer;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public class GUIUI extends AbstractComponentUI<KONCGUI> {
    }
}
