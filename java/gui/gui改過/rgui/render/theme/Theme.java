package kotori.koncclient.konc.gui.rgui.render.theme;

import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.render.ComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;

public interface Theme {
    public ComponentUI getUIForComponent(Component var1);

    public FontRenderer getFontRenderer();
}

