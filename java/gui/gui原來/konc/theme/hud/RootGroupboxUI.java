package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.container.use.Groupbox;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RootGroupboxUI extends AbstractComponentUI<Groupbox> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    @Override
    public void renderComponent(Groupbox component, FontRenderer fontRenderer) {
        GL11.glLineWidth(1.0F);
        if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
            cFontRenderer.drawString(component.getName(), 1.0F, 1.0F, Color.white.getRGB());
        } else {
            fontRenderer.drawString(1, 1, component.getName());
        }
        GL11.glColor3f(0.0F, 0.0F, 1.0F);
        GL11.glDisable(3553);
        GL11.glBegin(1);
        GL11.glVertex2d(0.0D, 0.0D);
        GL11.glVertex2d(component.getWidth(), 0.0D);
        GL11.glVertex2d(component.getWidth(), 0.0D);
        GL11.glVertex2d(component.getWidth(), component.getHeight());
        GL11.glVertex2d(component.getWidth(), component.getHeight());
        GL11.glVertex2d(0.0D, component.getHeight());
        GL11.glVertex2d(0.0D, component.getHeight());
        GL11.glVertex2d(0.0D, 0.0D);
        GL11.glEnd();
    }

    @Override
    public void handleMouseDown(Groupbox component, int x, int y, int button) {}

    @Override
    public void handleAddComponent(Groupbox component, Container container) {
        component.setWidth(100);
        component.setHeight(200);
        component.setOriginOffsetY(component.getTheme().getFontRenderer().getFontHeight() + 3);
    }
}
