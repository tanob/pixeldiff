package com.github.tanob.pixeldiff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AndroidDecorationCropper implements ScreenshotCropper {
    @Override
    public File crop(File original) throws IOException {
        BufferedImage originalScreenshot = ImageIO.read(original);
        int blackAndroidTitleBarHeight = 50;
        int blackAndroidBottomButtonsHeight = 96;
        int justAppHeight = originalScreenshot.getHeight() - blackAndroidTitleBarHeight - blackAndroidBottomButtonsHeight;

        BufferedImage justAppScreen = originalScreenshot.getSubimage(0, blackAndroidTitleBarHeight,
                originalScreenshot.getWidth(), justAppHeight);

        File destFile = File.createTempFile("android-cropper", "png");
        ImageIO.write(justAppScreen, "png", destFile);

        return destFile;
    }
}
