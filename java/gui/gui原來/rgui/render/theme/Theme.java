package kotori.koncclient.konc.gui.rgui.render.theme;

import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.render.ComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;

/**
 * Created by 086 on 25/06/2017.
 */
public interface Theme {
    ComponentUI getUIForComponent(Component component);

    FontRenderer getFontRenderer();
}
