package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.component.UnboundSlider;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class KONCUnboundSliderUI extends AbstractComponentUI<UnboundSlider> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    @Override
    public void renderComponent(UnboundSlider component, FontRenderer fontRenderer) {
        String s = component.getText() + ": " + component.getValue();
        int c = component.isPressed() ? 11184810 : 14540253;
        if (component.isHovered())
            c = (c & 0x7F7F7F) << 1;
        if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL11.glEnable(3553);
            cFontRenderer.drawString(s, (component.getWidth() / 2 - fontRenderer.getStringWidth(s) / 2), (component.getHeight() - fontRenderer.getFontHeight() / 2 - 4), c);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDisable(3553);
        } else {
            fontRenderer.drawString(component.getWidth() / 2 - fontRenderer.getStringWidth(s) / 2, component.getHeight() - fontRenderer.getFontHeight() / 2 - 4, c, s);
        }
        GL11.glDisable(3042);
    }

    @Override
    public void handleAddComponent(UnboundSlider component, Container container) {
        component.setHeight(component.getTheme().getFontRenderer().getFontHeight());
        component.setWidth(component.getTheme().getFontRenderer().getStringWidth(component.getText()));
    }
}
