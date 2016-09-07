package com.lanchui.commonBiz.util;

import com.xiwa.base.util.StringUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by usagizhang on 15-10-20.
 */
public class ValidationCodeUtil {

    private static final Font mFont = new Font("Times New Roman", Font.PLAIN, 17);


    /**
     * Get Rand Color
     *
     * @param fc
     * @param bc
     * @return
     */
    private  static  Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * Service
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws java.io.IOException
     */
    public static void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        int width = 70, height = 18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(102, 102, 102));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(mFont);

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }
//        for (int i = 0; i < 70; i++) {
//            int x = random.nextInt(width - 1);
//            int y = random.nextInt(height - 1);
//            int xl = random.nextInt(12) + 1;
//            int yl = random.nextInt(6) + 1;
//            g.drawLine(x, y, x - xl, y - yl);
//        }

        String sRand = "";
        for (int i = 0; i < 4; i++) {
            int itmp = random.nextInt(10);
//            char ctmp = (char)itmp;
            sRand += String.valueOf(itmp);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(String.valueOf(itmp), 15 * i + 10, 16);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(Validation_Code, sRand);
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    public void destroy()
    {
    }

    public static final String Validation_Code = "validation_code";

    /**
     * �����֤���Ƿ���ȷ
     */
    public static boolean checkValidationCode(HttpSession session, String validationCode)
    {
        if (StringUtil.isValid(validationCode)) {
            String vCode = (String)session.getAttribute(Validation_Code);
            return StringUtil.isValid(vCode) && vCode.equalsIgnoreCase(validationCode);
        }
        else {
            return false;
        }
    }
}
