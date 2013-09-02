package com.github.tanob.pixeldiff;

import java.io.File;
import java.io.IOException;

public interface ImageComparator {
    ComparisonResult compare(String scenarioName, File referenceVersion, File candidateVersion) throws IOException, InterruptedException;
}
