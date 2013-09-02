package com.github.tanob.pixeldiff;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class SimpleScenarioMapping implements ScenarioMapping {
    private final File mapping;
    private final File bucketDirectory;
    private final Properties scenarioToReferenceImage = new Properties();

    public SimpleScenarioMapping(File mapping, File bucketDirectory) throws IOException {
        this.mapping = mapping;
        this.bucketDirectory = bucketDirectory;
        this.scenarioToReferenceImage.load(new FileReader(mapping));
    }

    @Override
    public boolean firstTimeFor(String scenarioName) {
        return !scenarioToReferenceImage.containsKey(scenarioName);
    }

    @Override
    public File get(String scenarioName) {
        return new File(bucketDirectory, (String) scenarioToReferenceImage.get(scenarioName));
    }

    @Override
    public File put(String scenarioName, File version) throws IOException {
        String entryName = UUID.randomUUID().toString() + ".png";
        File destFile = new File(bucketDirectory, entryName);
        FileUtils.copyFile(version, destFile);
        scenarioToReferenceImage.put(scenarioName, entryName);
        updateMapping();
        return destFile;
    }

    private void updateMapping() throws IOException {
        scenarioToReferenceImage.store(new FileWriter(mapping), null);
    }
}
