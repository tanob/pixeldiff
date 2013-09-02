import com.github.tanob.pixeldiff.AndroidDecorationCropper;
import com.github.tanob.pixeldiff.PixelDiff;
import com.github.tanob.pixeldiff.SimpleScenarioMapping;
import com.github.tanob.pixeldiff.WebDriverScreenshotTaker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;

import static com.github.tanob.pixeldiff.PixelDiff.fileRelativeToModuleRoot;

public class SampleAppTest {
    private WebDriver driver;
    private PixelDiff pixelDiff;

    private static final DesiredCapabilities capabilities = new DesiredCapabilities() {{
        setCapability("device", "android");
        setCapability("app", new File("target/pixeldiff-sample.apk").getAbsolutePath());
        setCapability("app-package", "com.github.tanob.pixeldiff.sample");
        setCapability("app-activity", "SampleActivity");
    }};

    @Before
    public void setUp() throws Exception {
        driver = new RemoteWebDriver(new URL("http://localhost:4723/wd/hub"), capabilities);

        File inspectionDirectory = fileRelativeToModuleRoot(getClass(), "target/pixel-diff-inspection");
        inspectionDirectory.mkdirs();

        File pixelDiffRepo = fileRelativeToModuleRoot(getClass(), "pixel-diff-repo");
        pixelDiffRepo.mkdirs();

        File mappingFile = fileRelativeToModuleRoot(getClass(), "pixel-diff-mapping.txt");
        mappingFile.createNewFile();

        SimpleScenarioMapping mapping = new SimpleScenarioMapping(mappingFile, pixelDiffRepo);
        pixelDiff = new PixelDiff(new WebDriverScreenshotTaker(driver), new AndroidDecorationCropper(), mapping, inspectionDirectory);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void navigatesToTheMainScreen() throws Exception {
        pixelDiff.compare("main-screen");
    }
}
