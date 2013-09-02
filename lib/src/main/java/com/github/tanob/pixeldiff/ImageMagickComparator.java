package com.github.tanob.pixeldiff;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImageMagickComparator implements ImageComparator {
    @Override
    public ComparisonResult compare(String scenarioName, File referenceVersion, File candidateVersion) throws IOException, InterruptedException {
        File result = File.createTempFile("img-magick", "comparator");
        Process process = Runtime.getRuntime().exec("compare -verbose -metric RMSE -highlight-color Red -compose Src " + referenceVersion.getAbsolutePath() + " " + candidateVersion.getAbsolutePath() + " " + result.getAbsolutePath());

        BufferedReader br = null;
        try {
            StringBuilder processOutput = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;

            while ((line = br.readLine()) != null) {
                processOutput.append(line);
            }

            return new ImageMagickComparisonResult(scenarioName, referenceVersion, candidateVersion, processOutput.toString(), result);

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
