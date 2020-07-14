package kotori.koncclient.konc.gui.konc.theme.hud;

import kotori.koncclient.konc.KONCMod;
import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.*;
import kotori.koncclient.konc.gui.rgui.GUI;
import kotori.koncclient.konc.gui.rgui.component.AlignedComponent;
import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.component.container.Container;
import kotori.koncclient.konc.gui.rgui.component.container.use.Frame;
import kotori.koncclient.konc.gui.rgui.component.listen.MouseListener;
import kotori.koncclient.konc.gui.rgui.component.listen.UpdateListener;
import kotori.koncclient.konc.gui.rgui.poof.use.FramePoof;
import kotori.koncclient.konc.gui.rgui.render.AbstractComponentUI;
import kotori.koncclient.konc.gui.rgui.render.font.FontRenderer;
import kotori.koncclient.konc.gui.rgui.util.ContainerHelper;
import kotori.koncclient.konc.gui.rgui.util.Docking;
import kotori.koncclient.konc.module.ModuleManager;
import kotori.koncclient.konc.setting.Setting;
import kotori.koncclient.konc.util.Bind;
import kotori.koncclient.konc.util.Wrapper;
import org.lwjgl.opengl.GL11;

import java.awt.*;


public class KONCFrameUI<T extends Frame> extends AbstractComponentUI<Frame>
{
    Component yLineComponent = null;
    Component xLineComponent = null;
    Component centerXComponent = null;
    Component centerYComponent = null;
    boolean centerX = false;
    boolean centerY = false;
    int xLineOffset = 0;
    private static RootFontRenderer ff;
    CFontRenderer cFontRendererLarge = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    public float redForBG;
    public float greenForBG;
    public float blueForBG;
    public float redForBorder;
    public float greenForBorder;
    public float blueForBorder;
    public float alphaForBG;
    public boolean guirgb = true;

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
    public void renderComponent(Frame component, FontRenderer fontRenderer) {
        if (component.getOpacity() == 0.0f) {
            return;
        }
        GL11.glDisable(3553);
        ModuleManager.getModuleByName("GuiColour").settingList.forEach(setting -> checkSettingGuiColour(setting));
        GL11.glColor4f(redForBG, greenForBG, blueForBG, alphaForBG);
        RenderHelper.drawFilledRectangle(0.0F, 0.0F, component.getWidth(), component.getHeight());

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
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
        RenderHelper.drawRectangle(0.0F, 0.0F, component.getWidth(), component.getHeight());
        RenderHelper.drawFilledRectangle(0.0f, 0.0f, (float)component.getWidth(), (float)(KONCFrameUI.ff.getFontHeight() + 1));
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
        GL11.glDisable(2884);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        cFontRendererLarge.drawString(component.getTitle(), (float)(component.getWidth() / 8 - cFontRendererLarge.getStringWidth(component.getTitle()) / 10), 1.0f, Color.white.getRGB());
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glDisable(3553);
        } else {
            ff.drawString(component.getWidth() / 8 - ff.getStringWidth(component.getTitle()) / 10, 1, component.getTitle());
        }
        int top_y = 5;
        int bottom_y = component.getTheme().getFontRenderer().getFontHeight() - 9;
        if (component.isCloseable() && component.isMinimizeable()) {
            top_y -= 4;
            bottom_y -= 4;
        }
        if (component.isCloseable()) {
            GL11.glLineWidth(2.0f);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            GL11.glBegin(1);
            GL11.glVertex2d((component.getWidth() - 20), top_y);
            GL11.glVertex2d((component.getWidth() - 10), bottom_y);
            GL11.glVertex2d((component.getWidth() - 10), top_y);
            GL11.glVertex2d((component.getWidth() - 20), bottom_y);
            GL11.glEnd();
        }
        if (component.isCloseable() && component.isMinimizeable()) {
            top_y += 12;
            bottom_y += 12;
        }
        if (component.isMinimizeable()) {
            GL11.glLineWidth(1.5f);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            if (component.isMinimized()) {
                GL11.glBegin(2);
                GL11.glVertex2d((component.getWidth() - 15), (top_y + 2));
                GL11.glVertex2d((component.getWidth() - 15), (bottom_y + 3));
                GL11.glVertex2d((component.getWidth() - 10), (bottom_y + 3));
                GL11.glVertex2d((component.getWidth() - 10), (top_y + 2));
                GL11.glEnd();
            }
            else {
                GL11.glBegin(1);
                GL11.glVertex2d((component.getWidth() - 15), (bottom_y + 4));
                GL11.glVertex2d((component.getWidth() - 10), (bottom_y + 4));
                GL11.glEnd();
            }
        }
        if (component.isPinneable()) {
            if (component.isPinned()) {
                GL11.glColor3f(1.0f, 0.33f, 0.33f);
            }
            else {
                GL11.glColor3f(0.66f, 0.66f, 0.66f);
            }
            RenderHelper.drawCircle(7.0f, 4.0f, 2.0f);
            GL11.glLineWidth(3.0f);
            GL11.glBegin(1);
            GL11.glVertex2d(7.0, 4.0);
            GL11.glVertex2d(4.0, 8.0);
            GL11.glEnd();
        }
        if (component.equals(xLineComponent)) {
            GL11.glColor3f(0.44f, 0.44f, 0.44f);
            GL11.glLineWidth(1.0f);
            GL11.glBegin(1);
            GL11.glVertex2d(xLineOffset, (-GUI.calculateRealPosition(component)[1]));
            GL11.glVertex2d(xLineOffset, Wrapper.getMinecraft().displayHeight);
            GL11.glEnd();
        }
        if (component == centerXComponent && centerX) {
            GL11.glColor3f(0.86f, 0.03f, 1.0f);
            GL11.glLineWidth(1.0f);
            GL11.glBegin(1);
            double x = component.getWidth() / 2;
            GL11.glVertex2d(x, (-GUI.calculateRealPosition(component)[1]));
            GL11.glVertex2d(x, Wrapper.getMinecraft().displayHeight);
            GL11.glEnd();
        }
        if (component.equals(yLineComponent)) {
            GL11.glColor3f(0.44f, 0.44f, 0.44f);
            GL11.glLineWidth(1.0f);
            GL11.glBegin(1);
            GL11.glVertex2d((-GUI.calculateRealPosition(component)[0]), 0.0);
            GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, 0.0);
            GL11.glEnd();
        }
        if (component == centerYComponent && centerY) {
            GL11.glColor3f(0.86f, 0.03f, 1.0f);
            GL11.glLineWidth(1.0f);
            GL11.glBegin(1);
            double y = component.getHeight() / 2;
            GL11.glVertex2d((-GUI.calculateRealPosition(component)[0]), y);
            GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, y);
            GL11.glEnd();
        }
        GL11.glDisable(3042);
    }

    @Override
    public void handleMouseRelease(Frame component, int x, int y, int button) {
        yLineComponent = null;
        xLineComponent = null;
        centerXComponent = null;
        centerYComponent = null;
    }

    @Override
    public void handleMouseDrag(Frame component, int x, int y, int button) {
        super.handleMouseDrag(component, x, y, button);
    }

    @Override
    public void handleAddComponent(Frame component, Container container) {
        super.handleAddComponent(component, container);
        component.setOriginOffsetY(component.getTheme().getFontRenderer().getFontHeight() + 3);
        component.setOriginOffsetX(3);
        component.addMouseListener(new MouseListener() {
            @Override
            public void onMouseDown(MouseButtonEvent event) {
                int y = event.getY();
                int x = event.getX();
                if (y < 0) {
                    if (x > component.getWidth() - 22) {
                        if (component.isMinimizeable() && component.isCloseable()) {
                            if (y > -component.getOriginOffsetY() / 2) {
                                if (component.isMinimized()) {
                                    component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MAXIMIZE));
                                }
                                else {
                                    component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MINIMIZE));
                                }
                            }
                            else {
                                component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.CLOSE));
                            }
                        }
                        else if (component.isMinimized() && component.isMinimizeable()) {
                            component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MAXIMIZE));
                        }
                        else if (component.isMinimizeable()) {
                            component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MINIMIZE));
                        }
                        else if (component.isCloseable()) {
                            component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.CLOSE));
                        }
                    }
                    if (x < 10 && x > 0 && component.isPinneable()) {
                        component.setPinned(!component.isPinned());
                    }
                }
            }

            @Override
            public void onMouseRelease(MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseButtonEvent event) {
            }

            @Override
            public void onMouseMove(MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseScrollEvent event) {
            }
        });
        component.addUpdateListener(new UpdateListener() {
            @Override
            public void updateSize(Component component, int oldWidth, int oldHeight) {
                if (component instanceof Frame) {
                    KONCGUI.dock((Frame)component);
                }
            }

            @Override
            public void updateLocation(Component component, int oldX, int oldY) {
            }
        });
        component.addPoof(new Frame.FrameDragPoof<Frame, Frame.FrameDragPoof.DragInfo>() {
            @Override
            public void execute(Frame component, DragInfo info) {
                if (Bind.isShiftDown() || Bind.isAltDown() || Bind.isCtrlDown()) {
                    return;
                }
                int x = info.getX();
                int y = info.getY();
                yLineComponent = null;
                xLineComponent = null;
                component.setDocking(Docking.NONE);
                KONCGUI rootGUI = KONCMod.getInstance().getGuiManager();
                for (Component c : rootGUI.getChildren()) {
                    if (c.equals(component)) {
                        continue;
                    }
                    int yDiff = Math.abs(y - c.getY());
                    if (yDiff < 4) {
                        y = c.getY();
                        yLineComponent = component;
                    }
                    yDiff = Math.abs(y - (c.getY() + c.getHeight() + 3));
                    if (yDiff < 4) {
                        y = c.getY() + c.getHeight();
                        y += 3;
                        yLineComponent = component;
                    }
                    int xDiff = Math.abs(x + component.getWidth() - (c.getX() + c.getWidth()));
                    if (xDiff < 4) {
                        x = c.getX() + c.getWidth();
                        x -= component.getWidth();
                        xLineComponent = component;
                        xLineOffset = component.getWidth();
                    }
                    xDiff = Math.abs(x - c.getX());
                    if (xDiff < 4) {
                        x = c.getX();
                        xLineComponent = component;
                        xLineOffset = 0;
                    }
                    xDiff = Math.abs(x - (c.getX() + c.getWidth() + 3));
                    if (xDiff >= 4) {
                        continue;
                    }
                    x = c.getX() + c.getWidth() + 3;
                    xLineComponent = component;
                    xLineOffset = 0;
                }
                if (x < 5) {
                    x = 0;
                    ContainerHelper.setAlignment(component, AlignedComponent.Alignment.LEFT);
                    component.setDocking(Docking.LEFT);
                }
                int diff = (x + component.getWidth()) * DisplayGuiScreen.getScale() - Wrapper.getMinecraft().displayWidth;
                if (-diff < 5) {
                    x = Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale() - component.getWidth();
                    ContainerHelper.setAlignment(component, AlignedComponent.Alignment.RIGHT);
                    component.setDocking(Docking.RIGHT);
                }
                if (y < 5) {
                    y = 0;
                    if (component.getDocking().equals(Docking.RIGHT)) {
                        component.setDocking(Docking.TOPRIGHT);
                    }
                    else if (component.getDocking().equals(Docking.LEFT)) {
                        component.setDocking(Docking.TOPLEFT);
                    }
                    else {
                        component.setDocking(Docking.TOP);
                    }
                }
                diff = (y + component.getHeight()) * DisplayGuiScreen.getScale() - Wrapper.getMinecraft().displayHeight;
                if (-diff < 5) {
                    y = Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale() - component.getHeight();
                    if (component.getDocking().equals(Docking.RIGHT)) {
                        component.setDocking(Docking.BOTTOMRIGHT);
                    }
                    else if (component.getDocking().equals(Docking.LEFT)) {
                        component.setDocking(Docking.BOTTOMLEFT);
                    }
                    else {
                        component.setDocking(Docking.BOTTOM);
                    }
                }
                if (Math.abs((x + component.getWidth() / 2) * DisplayGuiScreen.getScale() * 2 - Wrapper.getMinecraft().displayWidth) < 5) {
                    xLineComponent = null;
                    centerXComponent = component;
                    centerX = true;
                    x = Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2;
                    if (component.getDocking().isTop()) {
                        component.setDocking(Docking.CENTERTOP);
                    }
                    else if (component.getDocking().isBottom()) {
                        component.setDocking(Docking.CENTERBOTTOM);
                    }
                    else {
                        component.setDocking(Docking.CENTERVERTICAL);
                    }
                    ContainerHelper.setAlignment(component, AlignedComponent.Alignment.CENTER);
                }
                else {
                    centerX = false;
                }
                if (Math.abs((y + component.getHeight() / 2) * DisplayGuiScreen.getScale() * 2 - Wrapper.getMinecraft().displayHeight) < 5) {
                    yLineComponent = null;
                    centerYComponent = component;
                    centerY = true;
                    y = Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2;
                    if (component.getDocking().isLeft()) {
                        component.setDocking(Docking.CENTERLEFT);
                    }
                    else if (component.getDocking().isRight()) {
                        component.setDocking(Docking.CENTERRIGHT);
                    }
                    else if (component.getDocking().isCenterHorizontal()) {
                        component.setDocking(Docking.CENTER);
                    }
                    else {
                        component.setDocking(Docking.CENTERHOIZONTAL);
                    }
                }
                else {
                    centerY = false;
                }
                info.setX(x);
                info.setY(y);
            }
        });
    }

    static {
        ff = new RootLargeFontRenderer();
    }
}
