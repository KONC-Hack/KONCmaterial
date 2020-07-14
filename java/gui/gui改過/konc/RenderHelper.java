package kotori.koncclient.konc.gui.konc;

import org.lwjgl.opengl.GL11;

public class RenderHelper {
    public static void drawArc(float cx, float cy, float r, float start_angle, float end_angle, int num_segments) {
        GL11.glBegin(4);
        int i = (int) ((num_segments / (360.0f / start_angle)) + 1);
        while (i <= num_segments / (360.0f / end_angle)) {
            double previousangle = 6.283185307179586 * (i - 1) / num_segments;
            double angle = 6.283185307179586 * i / num_segments;
            GL11.glVertex2d(cx, cy);
            GL11.glVertex2d((cx + Math.cos(angle) * r), (cy + Math.sin(angle) * r));
            GL11.glVertex2d((cx + Math.cos(previousangle) * r), (cy + Math.sin(previousangle) * r));
            ++i;
        }
        GL11.glEnd();
    }

    public static void drawArcOutline(float cx, float cy, float r, float start_angle, float end_angle, int num_segments) {
        GL11.glBegin(2);
        int i = (int) ((num_segments / (360.0f / start_angle)) + 1);
        while (i <= num_segments / (360.0f / end_angle)) {
            double angle = 6.283185307179586 * i / num_segments;
            GL11.glVertex2d((cx + Math.cos(angle) * r), (cy + Math.sin(angle) * r));
            ++i;
        }
        GL11.glEnd();
    }

    public static void drawCircleOutline(float x, float y, float radius) {
        RenderHelper.drawCircleOutline(x, y, radius, 0, 360, 40);
    }

    public static void drawCircleOutline(float x, float y, float radius, int start, int end, int segments) {
        RenderHelper.drawArcOutline(x, y, radius, start, end, segments);
    }

    public static void drawCircle(float x, float y, float radius) {
        RenderHelper.drawCircle(x, y, radius, 0, 360, 64);
    }

    public static void drawCircle(float x, float y, float radius, int start, int end, int segments) {
        RenderHelper.drawArc(x, y, radius, start, end, segments);
    }

    public static void drawOutlinedRoundedRectangle(int x, int y, int width, int height, float radius, float dR, float dG, float dB, float dA, float outlineWidth) {
        RenderHelper.drawRoundedRectangle(x, y, width, height, radius);
        GL11.glColor4f(dR, dG, dB, dA);
        RenderHelper.drawRoundedRectangle(x + outlineWidth, y + outlineWidth, width - outlineWidth * 2.0f, height - outlineWidth * 2.0f, radius);
    }

    public static void drawRectangle(float x, float y, float width, float height) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(2);
        GL11.glVertex2d(width, 0.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d(0.0, height);
        GL11.glVertex2d(width, height);
        GL11.glEnd();
    }

    public static void drawFilledRectangle(float x, float y, float width, float height) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glVertex2d((x + width), y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, (y + height));
        GL11.glVertex2d((x + width), (y + height));
        GL11.glEnd();
    }

    public static void drawRoundedRectangle(float x, float y, float width, float height, float radius) {
        GL11.glEnable(3042);
        RenderHelper.drawArc(x + width - radius, y + height - radius, radius, 0.0f, 90.0f, 16);
        RenderHelper.drawArc(x + radius, y + height - radius, radius, 90.0f, 180.0f, 16);
        RenderHelper.drawArc(x + radius, y + radius, radius, 180.0f, 270.0f, 16);
        RenderHelper.drawArc(x + width - radius, y + radius, radius, 270.0f, 360.0f, 16);
        GL11.glBegin(4);
        GL11.glVertex2d((x + width - radius), y);
        GL11.glVertex2d((x + radius), y);
        GL11.glVertex2d((x + width - radius), (y + radius));
        GL11.glVertex2d((x + width - radius), (y + radius));
        GL11.glVertex2d((x + radius), y);
        GL11.glVertex2d((x + radius), (y + radius));
        GL11.glVertex2d((x + width), (y + radius));
        GL11.glVertex2d(x, (y + radius));
        GL11.glVertex2d(x, (y + height - radius));
        GL11.glVertex2d((x + width), (y + radius));
        GL11.glVertex2d(x, (y + height - radius));
        GL11.glVertex2d((x + width), (y + height - radius));
        GL11.glVertex2d((x + width - radius), (y + height - radius));
        GL11.glVertex2d((x + radius), (y + height - radius));
        GL11.glVertex2d((x + width - radius), (y + height));
        GL11.glVertex2d((x + width - radius), (y + height));
        GL11.glVertex2d((x + radius), (y + height - radius));
        GL11.glVertex2d((x + radius), (y + height));
        GL11.glEnd();
    }
}

