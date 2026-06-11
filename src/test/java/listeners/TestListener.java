package listeners;

import tests.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            System.out.println("Test failed: " + result.getName() + ". Capturing screenshot...");
            ((BaseTest) testInstance).captureScreenshot(result.getName());
        }
    }
}
