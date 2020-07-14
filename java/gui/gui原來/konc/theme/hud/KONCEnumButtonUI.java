package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.RootSmallFontRenderer;
import kotori.koncclient.konc.gui.konc.component.EnumButton;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class KONCEnumButtonUI extends AbstractComponentUI<EnumButton> {
    RootSmallFontRenderer smallFontRenderer = new RootSmallFontRenderer();

    CFontRenderer cfontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    EnumButton modeComponent;

    long lastMS = System.currentTimeMillis();

    public void renderComponent(EnumButton component, FontRenderer aa) {
        if (System.currentTimeMillis() - lastMS > 3000L && modeComponent != null)
            modeComponent = null;
        int c = component.isPressed() ? 11184810 : 14540253;
        if (component.isHovered())
            c = (c & 0x7F7F7F) << 1;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        int parts = (component.getModes()).length;
        double step = component.getWidth() / parts;
        double startX = step * component.getIndex();
        double endX = step * (component.getIndex() + 1);
        int height = component.getHeight();
        float downscale = 1.1F;
        GL11.glDisable(3553);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glBegin(1);
        GL11.glVertex2d(startX, (height / downscale));
        GL11.glVertex2d(endX, (height / downscale));
        GL11.glEnd();
        if (modeComponent == null || !modeComponent.equals(component)) {
            if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
                GL11.glDisable(2884);
                GL11.glEnable(3042);
                GL11.glEnable(3553);
                cfontRenderer.drawStringWithShadow(component.getName(), 0.0D, 0.0D, c);
                cfontRenderer.drawStringWithShadow(component.getIndexMode(), (component.getWidth() - smallFontRenderer.getStringWidth(component.getIndexMode())), 0.0D, c);
                GL11.glEnable(2884);
                GL11.glDisable(3042);
                GL11.glDisable(3553);
            } else {
                smallFontRenderer.drawString(0, 0, c, component.getName());
                smallFontRenderer.drawString(component.getWidth() - smallFontRenderer.getStringWidth(component.getIndexMode()), 0, c, component.getIndexMode());
            }
        } else if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
            GL11.glDisable(2884);
            GL11.glEnable(3042);
            GL11.glEnable(3553);
            cfontRenderer.drawStringWithShadow(component.getIndexMode(), (component.getWidth() - smallFontRenderer.getStringWidth(component.getIndexMode())), 0.0D, c);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDisable(3553);
        } else {
            smallFontRenderer.drawString(component.getWidth() / 2 - smallFontRenderer.getStringWidth(component.getIndexMode()) / 2, 0, c, component.getIndexMode());
        }
        GL11.glDisable(3042);
    }

    public void handleSizeComponent(EnumButton component) {
        int width = 0;
        for (String s : component.getModes())
            width = Math.max(width, smallFontRenderer.getStringWidth(s));
        component.setWidth(smallFontRenderer.getStringWidth(component.getName()) + width + 1);
        component.setHeight(smallFontRenderer.getFontHeight() + 2);
    }

    public void handleAddComponent(EnumButton component, Container container) {
        component.addPoof(new EnumButton.EnumbuttonIndexPoof<EnumButton, EnumButton.EnumbuttonIndexPoof.EnumbuttonInfo>() {
            public void execute(EnumButton component, EnumbuttonInfo info) {
                modeComponent = component;
                lastMS = System.currentTimeMillis();
            }
        });
    }
}
