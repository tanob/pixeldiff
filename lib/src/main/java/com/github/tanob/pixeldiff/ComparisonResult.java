package com.github.tanob.pixeldiff;

import java.io.File;
import java.io.IOException;

public interface ComparisonResult {
    boolean areEqual();

    void cleanUp();

    void storeForInspectionOn(File inspectionDirectory) throws IOException;
}
