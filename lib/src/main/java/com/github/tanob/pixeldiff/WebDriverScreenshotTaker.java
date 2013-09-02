package com.github.tanob.pixeldiff;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;

public class WebDriverScreenshotTaker implements ScreenshotTaker {
    private final WebDriver driver;

    public WebDriverScreenshotTaker(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public File take() {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        return ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
    }
}
