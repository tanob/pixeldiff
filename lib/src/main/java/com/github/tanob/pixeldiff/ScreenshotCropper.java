package com.github.tanob.pixeldiff;

import java.io.File;
import java.io.IOException;

public interface ScreenshotCropper {
    File crop(File original) throws IOException;
}
