package kotori.koncclient.konc.gui.rgui.render;

import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;

/**
 * Created by 086 on 25/06/2017.
 */
public interface ComponentUI<T extends Component> {

    void renderComponent(T component, FontRenderer fontRenderer);

    void handleMouseDown(T component, int x, int y, int button);

    void handleMouseRelease(T component, int x, int y, int button);

    void handleMouseDrag(T component, int x, int y, int button);

    void handleScroll(T component, int x, int y, int amount, boolean up);

    void handleKeyDown(T component, int key);

    void handleKeyUp(T component, int key);

    void handleAddComponent(T component, Container container);

    void handleSizeComponent(T component);

    Class<? extends Component> getHandledClass();

}
