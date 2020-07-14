package kotori.koncclient.konc.gui.konc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Font;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import kotori.koncclient.konc.KONCMod;
import kotori.koncclient.konc.command.Command;
import kotori.koncclient.konc.gui.font.CFontRenderer;
import kotori.koncclient.konc.gui.konc.component.ActiveModules;
import kotori.koncclient.konc.gui.konc.component.Radar;
import kotori.koncclient.konc.gui.konc.component.SettingsPanel;
import kotori.koncclient.konc.gui.konc.theme.hud.KONCTheme;
import kotori.koncclient.konc.gui.rgui.GUI;
import kotori.koncclient.konc.gui.rgui.component.Component;
import kotori.koncclient.konc.gui.rgui.component.container.use.Frame;
import kotori.koncclient.konc.gui.rgui.component.container.use.Scrollpane;
import kotori.koncclient.konc.gui.rgui.component.listen.MouseListener;
import kotori.koncclient.konc.gui.rgui.component.listen.TickListener;
import kotori.koncclient.konc.gui.rgui.component.use.CheckButton;
import kotori.koncclient.konc.gui.rgui.component.use.Label;
import kotori.koncclient.konc.gui.rgui.render.theme.Theme;
import kotori.koncclient.konc.gui.rgui.util.ContainerHelper;
import kotori.koncclient.konc.gui.rgui.util.Docking;
import kotori.koncclient.konc.module.Module;
import kotori.koncclient.konc.module.ModuleManager;
import kotori.koncclient.konc.module.modules.client.InfoOverlay;
import kotori.koncclient.konc.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.util.text.TextFormatting;

import static kotori.koncclient.konc.util.InfoCalculator.cardinalToAxis;

public class KONCGUI
extends GUI {
    public static CFontRenderer cFontRenderer = new CFontRenderer(new Font("Verdana", 0, 18), true, false);
    public static final RootFontRenderer fontRenderer = new RootFontRenderer(1.0f);
    public Theme theme = this.getTheme();
    public static ArrayList<Frame> framesArray = new ArrayList();
    public static ColourHolder primaryColour = new ColourHolder(29, 29, 29);
    Minecraft mc = Wrapper.getMinecraft();
    private static final int CORNER = 5;
    private static final int BUFFER = 1;
    private static final int EDGE = 18;
    private static final int DOCK_OFFSET = 0;

    public KONCGUI() {
        super(new KONCTheme());
    }

    @Override
    public void drawGUI() {
        super.drawGUI();
    }

    @Override
    public void initializeGUI() {
        int y;
        HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>> categoryScrollpaneHashMap = new HashMap<Module.Category, Pair<Scrollpane, SettingsPanel>>();
        for (final Module module : ModuleManager.getModules()) {
            Scrollpane scrollpane;
            if (module.getCategory().isHidden()) continue;
            Module.Category moduleCategory = module.getCategory();
            if (!categoryScrollpaneHashMap.containsKey(moduleCategory)) {
                Stretcherlayout stretcherlayout = new Stretcherlayout(1);
                stretcherlayout.setComponentOffsetWidth(0);
                scrollpane = new Scrollpane(this.getTheme(), stretcherlayout, 300, 260);
                scrollpane.setMaximumHeight(360);
                categoryScrollpaneHashMap.put(moduleCategory, new Pair<>(scrollpane, new SettingsPanel(this.getTheme(), null)));
            }
            final Pair pair = (Pair)categoryScrollpaneHashMap.get(moduleCategory);
            scrollpane = (Scrollpane)pair.getKey();
            final CheckButton checkButton = new CheckButton(module.getName());
            checkButton.setToggled(module.isEnabled());
            checkButton.addTickListener(() -> {
                checkButton.setToggled(module.isEnabled());
                checkButton.setName(module.getName());
            });
            checkButton.addMouseListener(new MouseListener(){

                @Override
                public void onMouseDown(MouseListener.MouseButtonEvent event) {
                    if (event.getButton() == 1) {
                        ((SettingsPanel)pair.getValue()).setModule(module);
                        ((SettingsPanel)pair.getValue()).setX(event.getX() + checkButton.getX());
                        ((SettingsPanel)pair.getValue()).setY(event.getY() + checkButton.getY());
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
            checkButton.addPoof(new CheckButton.CheckButtonPoof<CheckButton, CheckButton.CheckButtonPoof.CheckButtonPoofInfo>(){

                @Override
                public void execute(CheckButton component, CheckButton.CheckButtonPoof.CheckButtonPoofInfo info) {
                    if (info.getAction().equals(CheckButton.CheckButtonPoof.CheckButtonPoofInfo.CheckButtonPoofInfoAction.TOGGLE)) {
                        module.setEnabled(checkButton.isToggled());
                    }
                }
            });
            scrollpane.addChild(checkButton);
        }
        int x = 10;
        int nexty = y = 10;
        for (Map.Entry entry : categoryScrollpaneHashMap.entrySet()) {
            Stretcherlayout stretcherlayout = new Stretcherlayout(1);
            stretcherlayout.COMPONENT_OFFSET_Y = 1;
            Frame frame = new Frame(this.getTheme(), stretcherlayout, ((Module.Category)(entry.getKey())).getName());
            Scrollpane scrollpane = (Scrollpane)((Pair)entry.getValue()).getKey();
            frame.addChild(scrollpane);
            frame.addChild((Component)((Pair)entry.getValue()).getValue());
            scrollpane.setOriginOffsetY(0);
            scrollpane.setOriginOffsetX(0);
            frame.setCloseable(false);
            frame.setX(x);
            frame.setY(y);
            this.addChild(frame);
            nexty = Math.max(y + frame.getHeight() + 10, nexty);
            if (!((x += frame.getWidth() + 10) > Wrapper.getMinecraft().displayWidth / 1.2f)) continue;
            nexty = y = nexty;
        }
        this.addMouseListener(new MouseListener(){

            private boolean isBetween(int min, int val, int max) {
                return val <= max && val >= min;
            }

            @Override
            public void onMouseDown(MouseListener.MouseButtonEvent event) {
                List<Component> panels = ContainerHelper.getAllChildren(SettingsPanel.class, KONCGUI.this);
                for (Component settingsPanel : panels) {
                    if (!settingsPanel.isVisible()) continue;
                    int[] real = GUI.calculateRealPosition(settingsPanel);
                    int pX = event.getX() - real[0];
                    int pY = event.getY() - real[1];
                    if (this.isBetween(0, pX, settingsPanel.getWidth()) && this.isBetween(0, pY, settingsPanel.getHeight())) continue;
                    settingsPanel.setVisible(false);
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
        ArrayList<Frame> frames = new ArrayList<>();

        Frame frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Active modules");
        frame.setCloseable(false);
        frame.addChild(new ActiveModules());
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        frames.add(frame);

        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Info");
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        Label information = new Label("");
        information.setShadow(true);
        information.addTickListener(() -> {
            InfoOverlay info = (InfoOverlay) ModuleManager.getModuleByName("InfoOverlay");
            information.setText("");
            info.infoContents().forEach(information::addLine);
        });
        frame.addChild(information);
        information.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Inventory Viewer");
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        Label inventory = new Label("");
        inventory.setShadow(false);
        inventory.addTickListener(() -> {
            inventory.setWidth(151);
            inventory.setHeight(40);
            inventory.setOpacity(0.1f);
        });
        frame.addChild(inventory);
        inventory.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(getTheme(), new Stretcherlayout(1), "Friends");
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        frame.setMinimumWidth(60);
        frame.setMinimumHeight(10);
        Label friendLabel = new Label("");
        friendLabel.addTickListener(() -> {
            friendLabel.setText("");
            if (OnlineFriends.getFriends().isEmpty()) {
                friendLabel.addLine("");
            } else {
                friendLabel.addLine("\u00A73\u00A7l The Fellas");
                Iterator var1 = OnlineFriends.getFriends().iterator();

                while (var1.hasNext()) {
                    Entity e = (Entity) var1.next();
                    friendLabel.addLine("\u00A76 " + e.getName());
                }
            }
        });
        frame.addChild(friendLabel);
        friendLabel.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Text Radar");
        Label list = new Label("");
        DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.HALF_UP);
        StringBuilder healthSB = new StringBuilder();
        list.addTickListener(() -> {
            if (!list.isVisible()) {
                return;
            }
            list.setText("");

            Minecraft mc = Wrapper.getMinecraft();

            if (mc.player == null) {
                return;
            }
            List<EntityPlayer> entityList = mc.world.playerEntities;

            Map<String, Integer> players = new HashMap<>();
            for (Entity e : entityList) {
                if (e.getName().equals(mc.player.getName())) {
                    continue;
                }
                String posString = (e.posY > mc.player.posY ? ChatFormatting.DARK_GREEN + "+ " : (e.posY == mc.player.posY ? " " : ChatFormatting.DARK_RED + "- "));

                String strengthfactor = "";
                EntityPlayer eplayer = (EntityPlayer) e;
                if (eplayer.isPotionActive(MobEffects.STRENGTH) && ModuleManager.isModuleEnabled("StrengthDetect")) {
                    strengthfactor = "S";
                }
                float hpRaw = ((EntityLivingBase) e).getHealth() + ((EntityLivingBase) e).getAbsorptionAmount();
                String hp = dfHealth.format(hpRaw);
                healthSB.append(Command.SECTIONSIGN());
                if (hpRaw >= 20) {
                    healthSB.append("a");
                } else if (hpRaw >= 10) {
                    healthSB.append("e");
                } else if (hpRaw >= 5) {
                    healthSB.append("6");
                } else {
                    healthSB.append("c");
                }
                healthSB.append(hp);
                players.put(ChatFormatting.AQUA + posString + "Player " + healthSB.toString() + " " + ChatFormatting.RED + strengthfactor + (strengthfactor.equals("S") ? " " : "") + (Friends.isFriend(e.getName()) ? ChatFormatting.GREEN : ChatFormatting.DARK_BLUE) + e.getName(), (int) mc.player.getDistance(e));
                healthSB.setLength(0);
            }

            if (players.isEmpty()) {
                list.setText("");
                return;
            }

            players = sortByValue(players);

            for (Map.Entry<String, Integer> player : players.entrySet()) {
                list.addLine(Command.SECTIONSIGN() + "7" + player.getKey() + " " + Command.SECTIONSIGN() + "4" + player.getValue());
            }
        });
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        frame.setMinimumWidth(75);
        list.setShadow(true);
        frame.addChild(list);
        list.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Entities");
        final Label entityLabel = new Label("");
        frame.setCloseable(false);
        Frame finalFrame1 = frame;
        entityLabel.addTickListener(new TickListener() {
            Minecraft mc = Wrapper.getMinecraft();

            @Override
            public void onTick() {
                if (!finalFrame1.isMinimized()) {
                    if (mc.player == null || !entityLabel.isVisible()) {
                        return;
                    }

                    final List<Entity> entityList = new ArrayList<>(mc.world.loadedEntityList);
                    if (entityList.size() <= 1) {
                        entityLabel.setText("");
                        return;
                    }
                    final Map<String, Integer> entityCounts = entityList.stream()
                            .filter(Objects::nonNull)
                            .filter(e -> !(e instanceof EntityPlayer))
                            .collect(Collectors.groupingBy(KONCGUI::getEntityName,
                                    Collectors.reducing(0, ent -> {
                                        if (ent instanceof EntityItem) {
                                            return ((EntityItem) ent).getItem().getCount();
                                        }
                                        return 1;
                                    }, Integer::sum)
                            ));

                    entityLabel.setText("");
                    finalFrame1.setWidth(50);
                    entityCounts.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .map(entry -> TextFormatting.GRAY + entry.getKey() + " " + TextFormatting.DARK_GRAY + "x" + entry.getValue())
                            .forEach(entityLabel::addLine);

                } else {
                    finalFrame1.setWidth(50);
                }
            }
        });
        frame.addChild(entityLabel);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        entityLabel.setShadow(true);
        entityLabel.setFontRenderer(fontRenderer);
        frames.add(frame);

        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Coordinates");
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        final Label coordsLabel = new Label("");
        coordsLabel.addTickListener(new TickListener() {
            Minecraft mc = Minecraft.getMinecraft();

            @Override
            public void onTick() {
                boolean inHell = (mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell"));

                int posX = (int) mc.player.posX;
                int posY = (int) mc.player.posY;
                int posZ = (int) mc.player.posZ;

                float f = !inHell ? 0.125f : 8;
                int hposX = (int) (mc.player.posX * f);
                int hposZ = (int) (mc.player.posZ * f);

                String direction = cardinalToAxis(Character.toUpperCase(mc.player.getHorizontalFacing().toString().charAt(0)));
                String colouredSeparator = KONCMod.colour + "7 " + KONCMod.separator + KONCMod.colour + "r";

                /* The 7 in the string formatter is the color */
                coordsLabel.setText(direction + colouredSeparator + String.format(" %sf%,d%s7, %sf%,d%s7, %sf%,d %s7[%s4%,d%s7, %s4%,d%s7, %s4%,d%s7]",
                        KONCMod.colour,
                        posX,
                        KONCMod.colour,
                        KONCMod.colour,
                        posY,
                        KONCMod.colour,
                        KONCMod.colour,
                        posZ,
                        KONCMod.colour,
                        KONCMod.colour,
                        hposX,
                        KONCMod.colour,
                        KONCMod.colour,
                        posY,
                        KONCMod.colour,
                        KONCMod.colour,
                        hposZ,
                        KONCMod.colour
                ));
            }
        });
        frame.addChild(coordsLabel);
        coordsLabel.setFontRenderer(fontRenderer);
        coordsLabel.setShadow(true);
        frame.setHeight(20);
        frames.add(frame);

        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Radar");
        frame.setCloseable(false);
        frame.setMinimizeable(true);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        frame.addChild(new Radar());
        frame.setWidth(100);
        frame.setHeight(100);
        frames.add(frame);

        frame = new Frame(this.getTheme(), new Stretcherlayout(1), "Welcomer");
        frame.setCloseable(false);
        frame.setPinneable(true);
        frame.displayPinButton(false);
        frame.setBox(false);
        frame.setTitleEnabled(false);
        Label welcomer = new Label("");
        welcomer.addTickListener(() -> {
            welcomer.setText("");
            welcomer.addLine("\u00a7fWelcome, \u00a7d" + Wrapper.getPlayer().getDisplayNameString() + " OwO");
        });
        frame.addChild(welcomer);
        information.setFontRenderer(fontRenderer);
        frames.add(frame);
        for (Frame frame1 : frames) {
            frame1.setX(x);
            frame1.setY(y);
            nexty = Math.max(y + frame1.getHeight() + 10, nexty);
            x += frame1.getWidth() + 10;
            if ((x * DisplayGuiScreen.getScale()) > Wrapper.getMinecraft().displayWidth / 1.2f) {
                nexty = y = nexty;
                x = 10;
            }
            this.addChild(frame1);
        }
        framesArray.addAll(frames);
    }

    private static String getEntityName(@Nonnull Entity entity) {
        if (entity instanceof EntityItem) {
            return TextFormatting.DARK_AQUA + ((EntityItem) entity).getItem().getItem().getItemStackDisplayName(((EntityItem) entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return TextFormatting.DARK_GRAY + "Wither skull";
        }
        if (entity instanceof EntityEnderCrystal) {
            return TextFormatting.LIGHT_PURPLE + "End crystal";
        }
        if (entity instanceof EntityEnderPearl) {
            return "Thrown ender pearl";
        }
        if (entity instanceof EntityMinecart) {
            return "Minecart";
        }
        if (entity instanceof EntityItemFrame) {
            return "Item frame";
        }
        if (entity instanceof EntityEgg) {
            return "Thrown egg";
        }
        if (entity instanceof EntitySnowball) {
            return "Thrown snowball";
        }

        return entity.getName();
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        LinkedList<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Comparator.comparing(o -> (Comparable)o.getValue()));
        LinkedHashMap result = new LinkedHashMap();
        for (Map.Entry entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void renderBackground(int x, int y, int width, int height, int color) {
        GlStateManager.color((((color & 16711680) >> 16) / 255.0f), (((color & 65280) >> 8) / 255.0f), ((color & 255) / 255.0f));
        RenderHelper.disableStandardItemLighting();
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture((x + 5 + 18 * width), (y + 5 + 18 * height), 25.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture((x + 5 + 18 * width), y, 25.0f, 0.0f, 5, 5, 256.0f, 256.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, (y + 5 + 18 * height), 0.0f, 25.0f, 5, 5, 256.0f, 256.0f);
        for (int row = 0; row < height; ++row) {
            Gui.drawModalRectWithCustomSizedTexture(x, (y + 5 + 18 * row), 0.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            Gui.drawModalRectWithCustomSizedTexture((x + 5 + 18 * width), (y + 5 + 18 * row), 25.0f, 6.0f, 5, 18, 256.0f, 256.0f);
            for (int col = 0; col < width; ++col) {
                if (row != 0) continue;
                Gui.drawModalRectWithCustomSizedTexture((x + 5 + 18 * col), y, 6.0f, 0.0f, 18, 5, 256.0f, 256.0f);
                Gui.drawModalRectWithCustomSizedTexture((x + 5 + 18 * col), (y + 5 + 18 * height), 6.0f, 25.0f, 18, 5, 256.0f, 256.0f);
                Gui.drawModalRectWithCustomSizedTexture((x + 5 + 18 * col), (y + 5 + 18 * row), 6.0f, 6.0f, 18, 18, 256.0f, 256.0f);
            }
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }

    @Override
    public void destroyGUI() {
        this.kill();
    }

    public static void dock(Frame component) {
        Docking docking = component.getDocking();
        if (docking.isTop()) {
            component.setY(DOCK_OFFSET);
        }
        if (docking.isBottom()) {
            component.setY((Wrapper.getMinecraft().displayHeight / DisplayGuiScreen.getScale()) - component.getHeight() - DOCK_OFFSET);
        }
        if (docking.isLeft()) {
            component.setX(DOCK_OFFSET);
        }
        if (docking.isRight()) {
            component.setX((Wrapper.getMinecraft().displayWidth / DisplayGuiScreen.getScale()) - component.getWidth() - DOCK_OFFSET);
        }
        if (docking.isCenterHorizontal()) {
            component.setX((Wrapper.getMinecraft().displayWidth / (DisplayGuiScreen.getScale() * 2) - component.getWidth() / 2));
        }
        if (docking.isCenterVertical()) {
            component.setY(Wrapper.getMinecraft().displayHeight / (DisplayGuiScreen.getScale() * 2) - component.getHeight() / 2);
        }

    }

    @Override
    public String getTitle() {
        return null;
    }
}

