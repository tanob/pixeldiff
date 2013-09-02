package com.github.tanob.pixeldiff;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImageMagickComparisonResult implements ComparisonResult {
    private final String scenarioName;
    private final File referenceVersion;
    private final File candidateVersion;
    private final String compareOutput;
    private final File result;

    public ImageMagickComparisonResult(String scenarioName, File referenceVersion, File candidateVersion, String compareOutput, File result) {
        this.scenarioName = scenarioName;
        this.referenceVersion = referenceVersion;
        this.candidateVersion = candidateVersion;
        this.compareOutput = compareOutput;
        this.result = result;
    }

    @Override
    public boolean areEqual() {
        return compareOutput.contains("all: 0 (0)");
    }

    @Override
    public void cleanUp() {
        result.deleteOnExit();
    }

    @Override
    public void storeForInspectionOn(File inspectionDirectory) throws IOException {
        String entryNameForCandidate = UUID.randomUUID().toString();
        FileUtils.copyFile(candidateVersion, new File(inspectionDirectory, entryNameForCandidate + ".png"));
        FileUtils.copyFile(result, new File(inspectionDirectory, scenarioName + "-" + entryNameForCandidate + "-pdiff.png"));
    }
}
