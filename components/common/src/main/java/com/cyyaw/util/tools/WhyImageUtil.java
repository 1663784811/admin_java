package com.cyyaw.util.tools;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class WhyImageUtil {


    public static BufferedImage getImageFromCode(int width, int height, String code) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(getRandomColor());
        g.fillRect(0, 0, width, height);


        int interLine = 5;
        Color lineColor = Color.blue;
        Random r = new Random();
        if (interLine > 0) {
            int x = r.nextInt(4), y = 0;
            int x1 = width - r.nextInt(4), y1 = 0;
            for (int i = 0; i < interLine; i++) {
                g.setColor(lineColor == null ? getRandomColor() : lineColor);
                y = r.nextInt(height - r.nextInt(4));
                y1 = r.nextInt(height - r.nextInt(4));
                g.drawLine(x, y, x1, y1);
            }
        }

        int fsize = (int) (height * 0.8);//字体大小为图片高度的80%
        int fx = 0;
        int fy = fsize;
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fsize));


        shearX(g, width, height, lineColor);
        shearY(g, width, height, lineColor);


        //写字符
        for (int i = 0; i < code.length(); i++) {
            fy = (int) ((Math.random() * 0.3 + 0.6) * height);//每个字符高低是否随机
            g.setColor(lineColor == null ? getRandomColor() : lineColor);
            g.drawString(code.charAt(i) + "", fx, fy);
            fx += (width / code.length()) * (Math.random() * 0.3 + 0.8); //依据宽度浮动
        }


        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * width * height);//噪点数量
        for (int i = 0; i < area; i++) {
            int xxx = r.nextInt(width);
            int yyy = r.nextInt(height);
            int rgb = getRandomColor().getRGB();
            image.setRGB(xxx, yyy, rgb);
        }
        g.dispose();
        return image;
    }


    private static void shearX(Graphics g, int w1, int h1, Color color) {
        Random random = new Random();
        int period = 2;

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (2.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }


    private static void shearY(Graphics g, int w1, int h1, Color color) {
        Random random = new Random();
        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = random.nextInt(2);
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (2.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }


    public static Color getRandomColor() {
        int r = WhyNumberUtils.createRandomInt(150, 200);
        int g = WhyNumberUtils.createRandomInt(150, 200);
        int b = WhyNumberUtils.createRandomInt(150, 200);
        return new Color(r, g, b);
    }


}
