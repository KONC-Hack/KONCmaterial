/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.opengl.GL11
 */
package kotori.koncclient.konc.gui.konc.theme.hud;

import java.awt.Color;
import java.awt.Font;

import kotori.koncclient.konc.KONCMod;
import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.DisplayGuiScreen;
import kotori.koncclient.konc.gui.konc.KONCGUI;
import kotori.koncclient.konc.gui.konc.RenderHelper;
import kotori.koncclient.konc.gui.konc.RootFontRenderer;
import kotori.koncclient.konc.gui.konc.RootLargeFontRenderer;
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
import kotori.koncclient.konc.util.Bind;
import kotori.koncclient.konc.util.ColourHolder;
import kotori.koncclient.konc.util.Wrapper;
import org.lwjgl.opengl.GL11;

public class KONCFrameUI<T extends Frame>
extends AbstractComponentUI<Frame> {
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 21), true, false);
    ColourHolder frameColour = KONCGUI.primaryColour.setA(100);
    ColourHolder outlineColour = this.frameColour.darker();
    Component yLineComponent = null;
    Component xLineComponent = null;
    Component centerXComponent = null;
    Component centerYComponent = null;
    boolean centerX = false;
    boolean centerY = false;
    int xLineOffset = 0;
    private static final RootFontRenderer ff = new RootLargeFontRenderer();

    @Override
    public void renderComponent(Frame component, FontRenderer fontRenderer) {
        if (component.getOpacity() == 0.0f) {
            return;
        }
        GL11.glDisable((int)3553);
        if (component.isBoxEnabled()) {
            GL11.glColor4f((float)0.2f, (float)0.2f, (float)0.2f, (float)0.75f);
            RenderHelper.drawFilledRectangle(0.0f, 0.0f, component.getWidth(), component.getHeight());
            GL11.glColor4f((float)0.33f, (float)0.33f, (float)1.0f, (float)1.0f);
            RenderHelper.drawFilledRectangle(0.0f, 0.0f, component.getWidth(), ff.getFontHeight() + 1);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            if (ModuleManager.getModuleByName("SmoothFont").isEnabled()) {
                GL11.glDisable((int)2884);
                GL11.glEnable((int)3042);
                GL11.glEnable((int)3553);
                this.cFontRenderer.drawStringWithShadow(component.getTitle(), component.getWidth() / 2 - ff.getStringWidth(component.getTitle()) / 2, 1.0, Color.WHITE.getRGB());
                GL11.glEnable((int)2884);
                GL11.glDisable((int)3042);
                GL11.glDisable((int)3553);
            } else {
                ff.drawString(component.getWidth() / 2 - ff.getStringWidth(component.getTitle()) / 2, 1, component.getTitle());
            }
        }
        int top_y = 5;
        int bottom_y = component.getTheme().getFontRenderer().getFontHeight() - 9;
        if (component.isCloseable() && component.isMinimizeable()) {
            top_y -= 4;
            bottom_y -= 4;
        }
        if (component.isCloseable()) {
            GL11.glLineWidth((float)2.0f);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glBegin((int)1);
            GL11.glVertex2d((double)(component.getWidth() - 20), (double)top_y);
            GL11.glVertex2d((double)(component.getWidth() - 10), (double)bottom_y);
            GL11.glVertex2d((double)(component.getWidth() - 10), (double)top_y);
            GL11.glVertex2d((double)(component.getWidth() - 20), (double)bottom_y);
            GL11.glEnd();
        }
        if (component.isCloseable() && component.isMinimizeable()) {
            top_y += 12;
            bottom_y += 12;
        }
        if (component.isMinimizeable() && component.isBoxEnabled()) {
            GL11.glLineWidth((float)1.5f);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            if (component.isMinimized()) {
                GL11.glBegin((int)2);
                GL11.glVertex2d((double)(component.getWidth() - 15), (double)(top_y + 2));
                GL11.glVertex2d((double)(component.getWidth() - 15), (double)(bottom_y + 3));
                GL11.glVertex2d((double)(component.getWidth() - 10), (double)(bottom_y + 3));
                GL11.glVertex2d((double)(component.getWidth() - 10), (double)(top_y + 2));
                GL11.glEnd();
            } else {
                GL11.glBegin((int)1);
                GL11.glVertex2d((double)(component.getWidth() - 15), (double)(bottom_y + 4));
                GL11.glVertex2d((double)(component.getWidth() - 10), (double)(bottom_y + 4));
                GL11.glEnd();
            }
        }
        if (component.equals(this.xLineComponent)) {
            GL11.glColor3f((float)0.44f, (float)0.44f, (float)0.44f);
            GL11.glLineWidth((float)1.0f);
            GL11.glBegin((int)1);
            GL11.glVertex2d((double)this.xLineOffset, (double)(-GUI.calculateRealPosition(component)[1]));
            GL11.glVertex2d((double)this.xLineOffset, (double)Wrapper.getMinecraft().displayHeight);
            GL11.glEnd();
        }
        if (component == this.centerXComponent && this.centerX) {
            GL11.glColor3f((float)0.86f, (float)0.03f, (float)1.0f);
            GL11.glLineWidth((float)1.0f);
            GL11.glBegin((int)1);
            double x = component.getWidth() / 2;
            GL11.glVertex2d((double)x, (double)(-GUI.calculateRealPosition(component)[1]));
            GL11.glVertex2d((double)x, (double)Wrapper.getMinecraft().displayHeight);
            GL11.glEnd();
        }
        if (component.equals(this.yLineComponent)) {
            GL11.glColor3f((float)0.44f, (float)0.44f, (float)0.44f);
            GL11.glLineWidth((float)1.0f);
            GL11.glBegin((int)1);
            GL11.glVertex2d((double)(-GUI.calculateRealPosition(component)[0]), (double)0.0);
            GL11.glVertex2d((double)Wrapper.getMinecraft().displayWidth, (double)0.0);
            GL11.glEnd();
        }
        if (component == this.centerYComponent && this.centerY) {
            GL11.glColor3f((float)0.86f, (float)0.03f, (float)1.0f);
            GL11.glLineWidth((float)1.0f);
            GL11.glBegin((int)1);
            double y = component.getHeight() / 2;
            GL11.glVertex2d((double)(-GUI.calculateRealPosition(component)[0]), (double)y);
            GL11.glVertex2d((double)Wrapper.getMinecraft().displayWidth, (double)y);
            GL11.glEnd();
        }
        GL11.glDisable((int)3042);
    }

    @Override
    public void handleMouseRelease(Frame component, int x, int y, int button) {
        this.yLineComponent = null;
        this.xLineComponent = null;
        this.centerXComponent = null;
        this.centerYComponent = null;
    }

    @Override
    public void handleMouseDrag(Frame component, int x, int y, int button) {
        super.handleMouseDrag(component, x, y, button);
    }

    @Override
    public void handleAddComponent(final Frame component, Container container) {
        super.handleAddComponent(component, container);
        component.setOriginOffsetY(component.getTheme().getFontRenderer().getFontHeight() + 3);
        component.setOriginOffsetX(3);
        component.addMouseListener(new MouseListener(){

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                int y = event.getY();
                int x = event.getX();
                if (y < 0) {
                    if (x > component.getWidth() - 22) {
                        if (component.isMinimizeable() && component.isCloseable()) {
                            if (y > -component.getOriginOffsetY() / 2) {
                                if (component.isMinimized()) {
                                    component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MAXIMIZE));
                                } else {
                                    component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MINIMIZE));
                                }
                            } else {
                                component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.CLOSE));
                            }
                        } else if (component.isMinimized() && component.isMinimizeable()) {
                            component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MAXIMIZE));
                        } else if (component.isMinimizeable()) {
                            component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.MINIMIZE));
                        } else if (component.isCloseable()) {
                            component.callPoof(FramePoof.class, new FramePoof.FramePoofInfo(FramePoof.Action.CLOSE));
                        }
                    }
                    if (x < 10 && x > 0 && component.isPinneable()) {
                        component.setPinned(!component.isPinned());
                    }
                }
            }

            @Override
            public void onMouseRelease(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseDrag(MouseListener.MouseButtonEvent event) {
            }

            @Override
            public void onMouseMove(MouseListener.MouseMoveEvent event) {
            }

            @Override
            public void onScroll(MouseListener.MouseScrollEvent event) {
            }
        });
        component.addUpdateListener(new UpdateListener(){

            public void updateSize(Component component, int oldWidth, int oldHeight) {
                if (component instanceof Frame) {
                    KONCGUI.dock((Frame)component);
                }
            }

            public void updateLocation(Component component, int oldX, int oldY) {
            }
        });
        component.addPoof(new Frame.FrameDragPoof<Frame, Frame.FrameDragPoof.DragInfo>(){

            @Override
            public void execute(Frame component, Frame.FrameDragPoof.DragInfo info) {
                int diff;
                if (Bind.isShiftDown() || Bind.isAltDown() || Bind.isCtrlDown()) {
                    return;
                }
                int x = info.getX();
                int y = info.getY();
                KONCFrameUI.this.yLineComponent = null;
                KONCFrameUI.this.xLineComponent = null;
                component.setDocking(Docking.NONE);
                KONCGUI rootGUI = KONCMod.getInstance().getGuiManager();
                for (Component c : rootGUI.getChildren()) {
                    int xDiff;
                    if (c.equals(component)) continue;
                    int yDiff = Math.abs(y - c.getY());
                    if (yDiff < 4) {
                        y = c.getY();
                        KONCFrameUI.this.yLineComponent = component;
                    }
                    if ((yDiff = Math.abs(y - (c.getY() + c.getHeight() + 3))) < 4) {
                        y = c.getY() + c.getHeight();
                        y += 3;
                        KONCFrameUI.this.yLineComponent = component;
                    }
                    if ((xDiff = Math.abs(x + component.getWidth() - (c.getX() + c.getWidth()))) < 4) {
                        x = c.getX() + c.getWidth();
                        x -= component.getWidth();
                        KONCFrameUI.this.xLineComponent = component;
                        KONCFrameUI.this.xLineOffset = component.getWidth();
                    }
                    if ((xDiff = Math.abs(x - c.getX())) < 4) {
                        x = c.getX();
                        KONCFrameUI.this.xLineComponent = component;
                        KONCFrameUI.this.xLineOffset = 0;
                    }
                    if ((xDiff = Math.abs(x - (c.getX() + c.getWidth() + 3))) >= 4) continue;
                    x = c.getX() + c.getWidth() + 3;
                    KONCFrameUI.this.xLineComponent = component;
                    KONCFrameUI.this.xLineOffset = 0;
                }
                if (x < 5) {
                    x = 0;
                    ContainerHelper.setAlignment(component, AlignedComponent.Alignment.LEFT);
                    component.setDocking(Docking.LEFT);
                }
                if (-(diff = (x + component.getWidth()) * DisplayGuiScreen.getScale() - Wrapper.getMinecraft().displayWidth) < 5) {
                    x = Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale() - component.getWidth();
                    ContainerHelper.setAlignment(component, AlignedComponent.Alignment.RIGHT);
                    component.setDocking(Docking.RIGHT);
                }
                if (y < 5) {
                    y = 0;
                    if (component.getDocking().equals((Object)Docking.RIGHT)) {
                        component.setDocking(Docking.TOPRIGHT);
                    } else if (component.getDocking().equals((Object)Docking.LEFT)) {
                        component.setDocking(Docking.TOPLEFT);
                    } else {
                        component.setDocking(Docking.TOP);
                    }
                }
                if (-(diff = (y + component.getHeight()) * DisplayGuiScreen.getScale() - Wrapper.getMinecraft().displayHeight) < 5) {
                    y = Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale() - component.getHeight();
                    if (component.getDocking().equals((Object)Docking.RIGHT)) {
                        component.setDocking(Docking.BOTTOMRIGHT);
                    } else if (component.getDocking().equals((Object)Docking.LEFT)) {
                        component.setDocking(Docking.BOTTOMLEFT);
                    } else {
                        component.setDocking(Docking.BOTTOM);
                    }
                }
                if (Math.abs((x + component.getWidth() / 2) * DisplayGuiScreen.getScale() * 2 - Wrapper.getMinecraft().displayWidth) < 5) {
                    KONCFrameUI.this.xLineComponent = null;
                    KONCFrameUI.this.centerXComponent = component;
                    KONCFrameUI.this.centerX = true;
                    x = Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2;
                    if (component.getDocking().isTop()) {
                        component.setDocking(Docking.CENTERTOP);
                    } else if (component.getDocking().isBottom()) {
                        component.setDocking(Docking.CENTERBOTTOM);
                    } else {
                        component.setDocking(Docking.CENTERVERTICAL);
                    }
                    ContainerHelper.setAlignment(component, AlignedComponent.Alignment.CENTER);
                } else {
                    KONCFrameUI.this.centerX = false;
                }
                if (Math.abs((y + component.getHeight() / 2) * DisplayGuiScreen.getScale() * 2 - Wrapper.getMinecraft().displayHeight) < 5) {
                    KONCFrameUI.this.yLineComponent = null;
                    KONCFrameUI.this.centerYComponent = component;
                    KONCFrameUI.this.centerY = true;
                    y = Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2;
                    if (component.getDocking().isLeft()) {
                        component.setDocking(Docking.CENTERLEFT);
                    } else if (component.getDocking().isRight()) {
                        component.setDocking(Docking.CENTERRIGHT);
                    } else if (component.getDocking().isCenterHorizontal()) {
                        component.setDocking(Docking.CENTER);
                    } else {
                        component.setDocking(Docking.CENTERHOIZONTAL);
                    }
                } else {
                    KONCFrameUI.this.centerY = false;
                }
                info.setX(x);
                info.setY(y);
            }
        });
    }

}

