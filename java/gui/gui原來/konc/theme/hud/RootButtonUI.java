package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.konc.KONCGUI;
import kotori.koncclient.konc.gui.konc.RenderHelper;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.use.Button;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RootButtonUI<T extends Button> extends AbstractComponentUI<Button> {

    protected Color idleColour = new Color(163, 163, 163);

    protected Color downColour = new Color(255, 255, 255);

    @Override
    public void renderComponent(Button component, FontRenderer ff) {
        GL11.glColor3f(0.22F, 0.22F, 0.22F);
        if (component.isHovered() || component.isPressed())
            GL11.glColor3f(0.26F, 0.26F, 0.26F);
        RenderHelper.drawRoundedRectangle(0.0F, 0.0F, component.getWidth(), component.getHeight(), 3.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        KONCGUI.fontRenderer.drawString(component.getWidth() / 2 - KONCGUI.fontRenderer.getStringWidth(component.getName()) / 2, 0, component.isPressed() ? downColour : idleColour, component.getName());
        GL11.glDisable(3553);
        GL11.glDisable(3042);
    }

    @Override
    public void handleAddComponent(Button component, Container container) {
        component.setWidth(KONCGUI.fontRenderer.getStringWidth(component.getName()) + 28);
        component.setHeight(KONCGUI.fontRenderer.getFontHeight() + 2);
    }
}
