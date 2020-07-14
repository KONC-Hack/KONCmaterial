package kotori.koncclient.konc.gui.konc;

import kotori.koncclient.konc.KONCMod;
import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.component.container.use.Frame;
import kotori.koncclient.konc.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class DisplayGuiScreen
extends GuiScreen {
    KONCGUI gui;
    public final GuiScreen lastScreen;
    public static int mouseX;
    public static int mouseY;
    Framebuffer framebuffer;

    public DisplayGuiScreen(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        KONCGUI gui = KONCMod.getInstance().getGuiManager();
        for (Component c : gui.getChildren()) {
            Frame child;
            if (!(c instanceof Frame) || !(child = (Frame)c).isPinneable() || !child.isVisible()) continue;
            child.setOpacity(0.5f);
        }
        this.framebuffer = new Framebuffer(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight, false);
    }

    public void onGuiClosed() {
        if (Wrapper.getMinecraft().entityRenderer.getShaderGroup() != null) {
            Wrapper.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
        }
        KONCGUI gui = KONCMod.getInstance().getGuiManager();
        gui.getChildren().stream().filter(component -> component instanceof Frame && ((Frame)component).isPinneable() && component.isVisible()).forEach(component -> component.setOpacity(0.0f));
    }

    @Override
    public void initGui() {
        if (OpenGlHelper.shadersSupported && Wrapper.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
            if (Wrapper.getMinecraft().entityRenderer.getShaderGroup() != null) {
                Wrapper.getMinecraft().entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            Wrapper.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
        gui = KONCMod.getInstance().getGuiManager();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.calculateMouse();
        this.gui.drawGUI();
        GL11.glEnable(3553);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.gui.handleMouseDown(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.gui.handleMouseRelease(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        this.gui.handleMouseDrag(DisplayGuiScreen.mouseX, DisplayGuiScreen.mouseY);
    }

    public void updateScreen() {
        int a;
        if (Mouse.hasWheel() && (a = Mouse.getDWheel()) != 0) {
            this.gui.handleWheel(mouseX, mouseY, a);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.lastScreen);
        } else {
            this.gui.handleKeyDown(keyCode);
            this.gui.handleKeyUp(keyCode);
        }
    }

    public static int getScale() {
        int scaleFactor = 0;
        int scale = Wrapper.getMinecraft().gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        while (scaleFactor < scale && Wrapper.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Wrapper.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            scaleFactor++;
        }
        if (scaleFactor == 0) {
            scaleFactor = 1;
        }
        return scaleFactor;
    }

    private void calculateMouse() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int scaleFactor = getScale();
        mouseX = Mouse.getX() / scaleFactor;
        mouseY = minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1;
    }

}

