package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.konc.RenderHelper;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.use.InputField;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

public class RootInputFieldUI<T extends InputField> extends AbstractComponentUI<InputField> {
    @Override
    public void renderComponent(InputField component, FontRenderer fontRenderer) {
        GL11.glColor3f(0.33F, 0.22F, 0.22F);
        RenderHelper.drawFilledRectangle(0.0F, 0.0F, component.getWidth(), component.getHeight());
        GL11.glLineWidth(1.5F);
        GL11.glColor4f(0.33F, 0.33F, 1.0F, 0.6F);
        RenderHelper.drawRectangle(0.0F, 0.0F, component.getWidth(), component.getHeight());
    }

    @Override
    public void handleAddComponent(InputField component, Container container) {
        component.setWidth(200);
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight());
    }
}
