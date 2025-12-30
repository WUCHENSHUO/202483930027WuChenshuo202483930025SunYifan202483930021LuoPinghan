import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class TextOverIconUtil {

    public static Image generateTextOverImage(Image baseImage, String text) {
        // 参数校验
        if (baseImage == null) {
            throw new IllegalArgumentException("原始图片(baseImage)不能为null");
        }
        if (text == null) {
            text = ""; // 允许文字为null，默认空字符串
        }

        // 获取原始图片尺寸
        int imgWidth = baseImage.getWidth(null);
        int imgHeight = baseImage.getHeight(null);

        // 计算合成后图片的总高度（原始图片高度 + 文字区域高度）
        // 文字区域高度：基于字体大小动态计算（这里用字体高度的1.5倍作为区域高度）
        Font font = new Font("微软雅黑", Font.PLAIN, 16); // 文字字体（可自定义）
        FontMetrics fontMetrics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
                .getGraphics().getFontMetrics(font);
        int textHeight = fontMetrics.getHeight(); // 文字实际高度（包含行间距）
        int totalHeight = imgHeight + textHeight + 10; // 10px是文字与图片的间距

        // 创建缓冲图片用于绘制（宽度与原始图片一致，高度为总高度）
        BufferedImage resultImage = new BufferedImage(
                imgWidth,
                totalHeight,
                BufferedImage.TYPE_INT_ARGB // 支持透明背景
        );
        Graphics2D g2d = resultImage.createGraphics();

        // 抗锯齿处理（让文字和线条更平滑）
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. 绘制背景（可选：这里用白色，如需透明可省略）
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, totalHeight);

        // 2. 绘制文字（居中显示在上方）
        g2d.setFont(font);
        g2d.setColor(Color.BLACK); // 文字颜色
        int textWidth = fontMetrics.stringWidth(text);
        int textX = (imgWidth - textWidth) / 2; // 水平居中
        int textY = textHeight - fontMetrics.getDescent(); // 垂直方向：文字基线位置（避免文字被截断）
        g2d.drawString(text, textX, textY);

        // 3. 绘制原始图片（在文字下方，居中对齐）
        int imgX = (imgWidth - imgWidth) / 2; // 水平居中（和原始宽度一致，所以X=0）
        int imgY = textHeight + 10; // 图片Y坐标：文字高度 + 间距
        g2d.drawImage(baseImage, imgX, imgY, null);

        // 释放资源
        g2d.dispose();

        return resultImage;
    }
}