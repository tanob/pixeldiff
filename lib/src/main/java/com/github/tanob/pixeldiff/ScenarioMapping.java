package com.github.tanob.pixeldiff;

import java.io.File;
import java.io.IOException;

public interface ScenarioMapping {
    boolean firstTimeFor(String scenarioName);

    File get(String scenarioName);

    File put(String scenarioName, File croppedVersion) throws IOException;
}
