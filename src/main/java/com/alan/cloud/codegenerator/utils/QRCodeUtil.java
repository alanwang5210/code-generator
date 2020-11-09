package com.alan.cloud.codegenerator.utils;

/**
 * @author 王合
 * @date 2020-02-14 11:25:30
 */

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * QRCodeUtil 生成二维码工具类
 */
public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private static BufferedImage encode(String content, String imgPath) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        //QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }


    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        //QrCodeUtils.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**/
    public File encode(String contents, int width, int height, File qrFile) {
        //生成条形码时的一些配置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);

        BitMatrix bitMatrix;
        try {
            OutputStream out = new FileOutputStream(qrFile);
            // 生成二维码
            bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrFile;
    }

 /*   public File encodeWithLogo(File qrFile, File logoFile, File newQrFile) {
        OutputStream os = null;
        try {
            Image image2 = ImageIO.read(qrFile);
            int width = image2.getWidth(null);
            int height = image2.getHeight(null);
            BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //BufferedImage bufferImage =ImageIO.read(image) ;
            Graphics2D g2 = bufferImage.createGraphics();
            g2.drawImage(image2, 0, 0, width, height, null);
            int matrixWidth = bufferImage.getWidth();
            int matrixHeigh = bufferImage.getHeight();

            //读取Logo图片
            BufferedImage logo = ImageIO.read(logoFile);
            //开始绘制图片
            g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
            BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke);// 设置笔画对象
            //指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
            g2.setColor(Color.white);
            g2.draw(round);// 绘制圆弧矩形

            //设置logo 有一道灰色边框
            BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke2);// 设置笔画对象
            RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
            g2.setColor(new Color(128, 128, 128));
            g2.draw(round2);// 绘制圆弧矩形

            g2.dispose();

            bufferImage.flush();
            os = new FileOutputStream(newQrFile);
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(bufferImage);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newQrFile;
    }*/

    /**
     * ZXing解码
     *
     * @param qrFile
     * @return
     */
    public String decode(File qrFile) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(qrFile);
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.getText();
    }

//    /**
//     * 获取指定文件的输入流，获取logo
//     *
//     * @param logoPath 文件的路径
//     * @return
//     */
//    public static InputStream getResourceAsStream(String logoPath) {
//        return QrCodeUtil.class.getResourceAsStream(logoPath);
//    }

    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        return image;
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static BufferedImage encode(String content, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
        return image;
    }

    public static void encode(String content, OutputStream output) throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }
}