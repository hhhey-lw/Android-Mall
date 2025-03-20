package com.wei.diploma_project.util;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerificationCodeUtil {

    private static Integer width = 80;
    private static Integer height = 28;

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    static public BufferedImage getCode(HttpServletRequest request) {

        BufferedImage bi;
        Graphics g;
        String alphaTable = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = bi.getGraphics();
        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, 0, width, height);

        Random random = new Random();
        String randomNumStr = "";
        for (int j = 0; j < 4; j++) {
            String s = alphaTable.charAt(random.nextInt(alphaTable.length())) + "";
            randomNumStr += s;
            g.setColor(new Color(random.nextInt(130), random.nextInt(130), random.nextInt(130)));
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString(s, 20 * j + 4, 20);
        }

        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            g.drawOval(x, y, 1, 1);
        }

        request.getSession().setAttribute("verificationCode", randomNumStr);

        return bi;
    }

}
