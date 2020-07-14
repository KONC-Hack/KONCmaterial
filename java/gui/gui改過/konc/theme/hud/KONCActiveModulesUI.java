package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.KONCMod;
import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.rgui.component.AlignedComponent;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.module.Module;
import kotori.koncclient.konc.module.ModuleManager;
import kotori.koncclient.konc.module.modules.client.ActiveModules;
import kotori.koncclient.konc.util.ColourUtils;
import kotori.koncclient.konc.util.Wrapper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static kotori.koncclient.konc.util.ColourConverter.toF;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;

public class KONCActiveModulesUI extends AbstractComponentUI<kotori.koncclient.konc.gui.konc.component.ActiveModules> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    ActiveModules activeMods;
    @Override
    public void renderComponent(kotori.koncclient.konc.gui.konc.component.ActiveModules component, FontRenderer f) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        FontRenderer renderer = Wrapper.getFontRenderer();
        List<Module> mods = ModuleManager.getModules().stream()
                .filter(Module::isEnabled)
                .filter(Module::isOnArray)
                .sorted(Comparator.comparing(module -> Integer.valueOf(renderer.getStringWidth(module.getName() + ((module.getHudInfo() == null) ? "" : (module.getHudInfo() + " "))) * (component.sort_up ? -1 : 1))))
                .collect(Collectors.toList());

        final int[] y = {2};
        activeMods = (ActiveModules) ModuleManager.getModuleByName("ActiveModules");

        if (activeMods.potion.getValue() && component.getParent().getY() < 26 && Wrapper.getPlayer().getActivePotionEffects().size() > 0 && component.getParent().getOpacity() == 0)
            y[0] = Math.max(component.getParent().getY(), 26 - component.getParent().getY());

        final float[] hue = {(System.currentTimeMillis() % (360 * activeMods.getRainbowSpeed())) / (360f * activeMods.getRainbowSpeed())};

        Function<Integer, Integer> xFunc;
        switch (component.getAlignment()) {
            case RIGHT:
                xFunc = i -> component.getWidth() - i;
                break;
            case CENTER:
                xFunc = i -> component.getWidth() / 2 - i / 2;
                break;
            case LEFT:
            default:
                xFunc = i -> 0;
                break;
        }

        for (int i = 0 ; i < mods.size() ; i++) {
            Module module = mods.get(i);
            int rgb;

            switch (activeMods.mode.getValue()) {
                case RAINBOW:
                    rgb = Color.HSBtoRGB(hue[0], toF(activeMods.saturationR.getValue()), toF(activeMods.brightnessR.getValue()));
                    break;
                case CATEGORY:
                    rgb = activeMods.getCategoryColour(module);
                    break;
                case CUSTOM:
                    rgb = Color.HSBtoRGB(toF(activeMods.hueC.getValue()), toF(activeMods.saturationC.getValue()), toF(activeMods.brightnessC.getValue()));
                    break;
                case INFO_OVERLAY:
                    rgb = activeMods.getInfoColour(i);
                    break;
                default:
                    rgb = 0;
            }

            String hudInfo = module.getHudInfo();
            String text = activeMods.getAlignedText(module.getName(), (hudInfo == null ? "" : KONCMod.colour + "7" + hudInfo + KONCMod.colour + "r"), component.getAlignment().equals(AlignedComponent.Alignment.RIGHT));
            int textwidth = renderer.getStringWidth(text);
            int textheight = renderer.getFontHeight() + 1;
            int red = rgb >> 16 & 0xFF;
            int green = rgb >> 8 & 0xFF;
            int blue = rgb & 0xFF;

            int trgb = ColourUtils.toRGBA(red, green, blue, 255);
            if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
                cFontRenderer.drawStringWithShadow(text, xFunc.apply(Integer.valueOf(textwidth)).intValue(), y[0], trgb);
            } else {
                renderer.drawStringWithShadow(xFunc.apply(Integer.valueOf(textwidth)).intValue(), y[0], red, green, blue, text);
            }
            hue[0] = hue[0] + 0.02F;
            y[0] = y[0] + textheight;
        }

        component.setHeight(y[0]);

        GL11.glEnable(GL11.GL_CULL_FACE);
        glDisable(GL_BLEND);
    }

    @Override
    public void handleSizeComponent(kotori.koncclient.konc.gui.konc.component.ActiveModules component) {
        component.setWidth(100);
        component.setHeight(100);
    }
}
