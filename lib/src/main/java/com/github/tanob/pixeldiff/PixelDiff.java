package com.github.tanob.pixeldiff;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PixelDiff {
    private final ScreenshotTaker screenshotTaker;
    private final ScreenshotCropper cropper;
    private final ScenarioMapping mapping;
    private final ImageComparator comparator = new ImageMagickComparator();
    private final File inspectionDirectory;

    public PixelDiff(ScreenshotTaker screenshotTaker, ScreenshotCropper cropper, ScenarioMapping mapping, File inspectionDirectory) {
        this.screenshotTaker = screenshotTaker;
        this.cropper = cropper;
        this.mapping = mapping;
        this.inspectionDirectory = inspectionDirectory;
    }

    public void compare(String scenarioName) throws IOException, InterruptedException {
        File currentVersion = screenshotTaker.take();
        File croppedVersion = cropper.crop(currentVersion);

        if (mapping.firstTimeFor(scenarioName)) {
            mapping.put(scenarioName, croppedVersion);
        } else {
            File referenceVersion = mapping.get(scenarioName);
            ComparisonResult result = comparator.compare(scenarioName, referenceVersion, croppedVersion);

            if (result.areEqual()) {
                croppedVersion.deleteOnExit();
                result.cleanUp();
            } else {
                result.storeForInspectionOn(inspectionDirectory);
                printInstructionsFor(result);
                throw new AssertionError("Comparison failed.");
            }
        }
    }

    public static File fileRelativeToModuleRoot(Class anyTestClass, String directory) {
        final String clsUri = anyTestClass.getName().replace('.', File.separatorChar) + ".class";
        final URL url = anyTestClass.getClassLoader().getResource(clsUri);
        final String clsPath = url.getPath();
        final File root = new File(clsPath.substring(0, clsPath.length() - clsUri.length()));
        final File targetDirectory = root.getParentFile();
        final File moduleRoot = targetDirectory.getParentFile();
        return new File(moduleRoot, directory);
    }

    private void printInstructionsFor(ComparisonResult result) {
        // TODO
        System.out.println("Print instructions for comparison result...");
    }
}
