package org.gross.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageUtils {

    private static final int REPEAT_NUMBER = 5;
    private static final int QUICK_CHECK_TIMEOUT_MILLIS = 1000;
    
    private PageUtils() {
    }

    public static WebElement findWebElementByAndWait(WebDriver driver, By by) {
        List<WebElement> webElements = findWebElementsByAndWait(driver, by);
        if (webElements.size() == 1) {
            return webElements.get(0);
        } else {
            throw new RuntimeException("Fund multiple matching objects (" + webElements.size() + ") on page");
        }
    }

    public static List<WebElement> findWebElementsByAndWait(WebDriver driver, By by) {
        return findWebElementsByAndWait(driver, by, QUICK_CHECK_TIMEOUT_MILLIS);
    }

    public static List<WebElement> findOptionalWebElementsByAndWait(WebDriver driver, By by) {
        try {
            return findWebElementsByAndWait(driver, by, QUICK_CHECK_TIMEOUT_MILLIS);
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }
    }

    public static List<WebElement> findWebElementsByAndWait(WebDriver driver, By by, long durationMillis) {
        return new WebDriverWait(driver, Duration.ofMillis(durationMillis))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public static Optional<WebElement> findOptionalWebElementByAndWait(WebElement baseElement, By by) {
        try {
            return Optional.of(PageUtils.findWebElementByAndWait(baseElement, by));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Cannot find")) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    public static WebElement findWebElementByAndWait(WebElement element, By by) {
        List<WebElement> webElements = findWebElementsByAndWait(element, by);
        if (webElements.size() == 1) {
            return webElements.get(0);
        } else {
            throw new RuntimeException("Fund multiple matching objects (" + webElements.size() + ") in the tag ");
        }
    }

    public static Optional<List<WebElement>> findOptionalWebElementsByAndWait(WebElement baseElement, By by) {
        try {
            return Optional.of(PageUtils.findWebElementsByAndWait(baseElement, by));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Cannot find")) {
                return Optional.empty();
            } else {
                throw e;
            }
        }
    }

    public static List<WebElement> findWebElementsByAndWait(WebElement element, By by) {
        for (int i = 0; i < REPEAT_NUMBER; i++) {
            List<WebElement> webElements = element.findElements(by);
            if (!webElements.isEmpty() && webElements.stream().allMatch(WebElement::isDisplayed)) {
                return webElements;
            } else {
                try {
                    Thread.sleep(QUICK_CHECK_TIMEOUT_MILLIS);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Cannot invoke sleep command", e);
                }
            }
        }
        throw new RuntimeException("Cannot find element on the page");
    }

    public static void waitUntilDisplayed(WebElement element) {
        for (int i = 0; i < REPEAT_NUMBER; i++) {
            if (element.isDisplayed()) {
                sleep(QUICK_CHECK_TIMEOUT_MILLIS);
                return;
            } else {
                sleep(QUICK_CHECK_TIMEOUT_MILLIS);
            }
        }
        throw new RuntimeException("Cannot find element on the page");
    }

    private static void sleep(long sleepTimeMillis) {
        try {
            Thread.sleep(sleepTimeMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot invoke sleep in the loop", e);
        }
    }
    
}
