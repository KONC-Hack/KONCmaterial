package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.KONCGUI;
import kotori.koncclient.konc.gui.konc.RenderHelper;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.use.CheckButton;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.ModuleManager;
import kotori.koncclient.konc.setting.Setting;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RootCheckButtonUI<T extends CheckButton> extends AbstractComponentUI<CheckButton> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    protected Color backgroundColour = new Color(46, 88, 200);
    protected Color backgroundColourHover = new Color(81, 184, 255);
    protected Color idleColourNormal = new Color(200, 200, 200);
    protected Color downColourNormal = new Color(190, 190, 190);
    protected Color idleColourToggle = new Color(126, 206, 250);
    protected Color downColourToggle = idleColourToggle.brighter();
    public float redForBG;
    public float greenForBG;
    public float blueForBG;
    public float redForBorder;
    public float greenForBorder;
    public float blueForBorder;
    public float alphaForBG;

    private void checkSettingGuiColour(Setting setting) {
        String var2 = setting.getName();
        byte var3 = -1;
        switch(var2.hashCode()) {
            case -780023768:
                if (var2.equals("Red Main")) {
                    var3 = 0;
                }
                break;
            case -400719425:
                if (var2.equals("Blue Main")) {
                    var3 = 2;
                }
                break;
            case 110097449:
                if (var2.equals("Green Border")) {
                    var3 = 5;
                }
                break;
            case 721272699:
                if (var2.equals("Alpha Main")) {
                    var3 = 3;
                }
                break;
            case 1153959602:
                if (var2.equals("Blue Border")) {
                    var3 = 6;
                }
                break;
            case 1595957494:
                if (var2.equals("Green Main")) {
                    var3 = 1;
                }
                break;
            case 1714706139:
                if (var2.equals("Red Border")) {
                    var3 = 4;
                }
        }

        switch(var3) {
            case 0:
                redForBG = (Float)setting.getValue();
                break;
            case 1:
                greenForBG = (Float)setting.getValue();
                break;
            case 2:
                blueForBG = (Float)setting.getValue();
                break;
            case 3:
                alphaForBG = (Float)setting.getValue();
                break;
            case 4:
                redForBorder = (Float)setting.getValue();
                break;
            case 5:
                greenForBorder = (Float)setting.getValue();
                break;
            case 6:
                blueForBorder = (Float)setting.getValue();
        }
    }

    @Override
    public void renderComponent(CheckButton component, FontRenderer ff) {
        ModuleManager.getModuleByName("GuiColour").settingList.forEach(setting -> checkSettingGuiColour(setting));
        if (component.isToggled()) {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            //RGB
            if (ModuleManager.getModuleByName("Rainbow").isEnabled()) {
                float[] tick_color1 = {(System.currentTimeMillis()  % 11520L) / 11520.0f *  2};
                int color_rgb1  = Color.HSBtoRGB(tick_color1[0], 1, 1);
                float r = (color_rgb1 >> 16 & 0xFF) / 255.0F;
                float g = (color_rgb1 >> 8 & 0xFF)  / 255.0F;
                float b = (color_rgb1  & 0xFF)      / 255.0F;
                GL11.glColor3f(r, g, b);
            } else{
                GL11.glColor3f(redForBorder, greenForBorder, blueForBorder);
            }
            RenderHelper.drawFilledRectangle(0.0F, (KONCGUI.fontRenderer.getFontHeight() / 2 - 5), component.getWidth(), KONCGUI.fontRenderer.getFontHeight() * 1.6F);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }
        String text = component.getName();
        int c = 16777215;
        if (component.isHovered()) {
            c = (c & 0x7F7F7F) << 1;
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (ModuleManager.getModuleByName("SmoothFont").isDisabled()) {
            KONCGUI.fontRenderer.drawString(1, KONCGUI.fontRenderer.getFontHeight() / 2 - 2, c, text);
        } else {
            GL11.glEnable(3553);
            GL11.glEnable(3042);
            GL11.glDisable(2884);
            cFontRenderer.drawString(text, 1.0F, (KONCGUI.fontRenderer.getFontHeight() / 2 - 2), c);
            GL11.glDisable(3553);
            GL11.glDisable(3042);
            GL11.glEnable(2884);
        }
    }

    @Override
    public void handleAddComponent(CheckButton component, Container container) {
        component.setWidth(KONCGUI.fontRenderer.getStringWidth("Dispenser32kne") + 2);
        component.setHeight(KONCGUI.fontRenderer.getFontHeight() + 2);
    }
}
