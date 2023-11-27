package org.gross.bibleperday;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.bibleperday.dto.SpecialOccasionDTO;
import org.gross.bibleperday.enums.MoveDate;
import org.gross.bibleperday.enums.Occasion;
import org.gross.bibleperday.enums.Speed;
import org.gross.utils.DateUtils;
import org.gross.utils.PageUtils;
import org.gross.utils.PrintUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BiblePerDayService {

    private static final String LINK = "https://biblianacodzien.pl/";
    private static final int QUICK_CHECK_TIMEOUT_MILLIS = 100;
    private static final int CHECK_TIMEOUT_MILLIS = (int) Speed.MEDIUM.getDurationMillis();
    private static final int IMPLICITLY_TIMEOUT_SECONDS = 1;

    private static final String START_DATE = "27.11.";
    private static final String END_DATE = "27.11.";

    public List<BiblePerDayDTO> downloadContentForYear(int year) {
        Date startDate = DateUtils.parse(START_DATE + year);
        Date endDate = DateUtils.parse(END_DATE + year);

        WebDriverManager.chromedriver().setup();

        WebDriver driver  = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_TIMEOUT_SECONDS));

        // Open page
        driver.navigate().to(LINK);

        moveToSpecificDate(driver, startDate, Speed.FAST);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate(driver));

        List<BiblePerDayDTO> biblePerDayList = new ArrayList<>();
        // Invoke until will be fetched day after IMPORT_END_DATE
        while (endDate.compareTo(calendar.getTime()) >= 0) {
            // Do import
            boolean specialOccasion = !doesNotContainSpecialOccasion(driver);
            BiblePerDayDTO biblePerDay = getBiblePerDay(driver, specialOccasion, calendar.getTime());
            PrintUtils.printMessageWithAutoFill(biblePerDay.toString());
            biblePerDayList.add(biblePerDay);

            // Move to the next day
            calendar.add(Calendar.DATE, 1);
            moveToSpecificDate(driver, calendar.getTime(), Speed.MEDIUM);
        }

        driver.quit();

        return biblePerDayList;
    }

    private BiblePerDayDTO getBiblePerDay(WebDriver driver, boolean specialOccasion, Date currentDate) {
        BiblePerDayDTO.Builder biblePerDayBuilder = new BiblePerDayDTO.Builder(currentDate);

        if (specialOccasion) {
            List<SpecialOccasionDTO> specialOccasionList = fetchSpecialOccasions(driver);
            biblePerDayBuilder.setSpecialOccasionList(specialOccasionList);
        } else {
            fetchAdditionalElements(driver, biblePerDayBuilder);
        }

        fetchStandardElements(driver, specialOccasion, biblePerDayBuilder);

        return biblePerDayBuilder.build();
    }

    private void fetchAdditionalElements(WebDriver driver, BiblePerDayDTO.Builder biblePerDayBuilder) {
        List<WebElement> standardElements = PageUtils.findWebElementsByAndWait(driver, By.cssSelector("div.txt-container p.txt-item"));

        biblePerDayBuilder.setFirstAdditional(standardElements.get(0).getText());
        biblePerDayBuilder.setSecondAdditional(standardElements.get(1).getText());
    }

    private void fetchStandardElements(WebDriver driver, boolean specialOccasion, BiblePerDayDTO.Builder biblePerDayBuilder) {
        List<WebElement> standardElements;
        try {
            // Quick check
            standardElements = PageUtils.findWebElementsByAndWait(driver, By.cssSelector("div div div.front-sup-container div.frontContainer div p.frontSource"));
        } catch (TimeoutException e) {
            // Longer check
            List<WebElement> standardElementsMainDiv = PageUtils.findWebElementsByAndWait(driver, By.cssSelector("div div div.front-sup-container div.frontContainer div"));
            standardElements = standardElementsMainDiv
                    .stream().filter(elem -> !elem.findElements(By.cssSelector("p.frontSource")).isEmpty()).toList();
        }

        int indexStart = 0;
        if (specialOccasion) {
            indexStart = (int) biblePerDayBuilder.build().getSpecialOccasionList().stream().filter(special -> !Occasion.NONE.equals(special.getOccasion())).count();
        }

        String firstStandard = standardElements.get(indexStart).getText();
        biblePerDayBuilder.setFirstStandard(firstStandard);

        String secondStandard = standardElements.get(indexStart + 1).getText();
        biblePerDayBuilder.setSecondStandard(secondStandard);

        String quoteNotFromBibleReference = standardElements.get(indexStart + 2).getText();
        biblePerDayBuilder.setQuoteNotFromBibleReference(quoteNotFromBibleReference);

        List<WebElement> quoteNotFromBibleElements = PageUtils.findWebElementsByAndWait(driver, By.cssSelector("div div div.front-sup-container div.frontContainer div.frontText"));
        String quoteNotFromBible = quoteNotFromBibleElements.get(2).getText();
        biblePerDayBuilder.setQuoteNotFromBible(quoteNotFromBible);
    }

    private List<SpecialOccasionDTO> fetchSpecialOccasions(WebDriver driver) {
        List<WebElement> specialOccasions = PageUtils.findWebElementsByAndWait(driver, By.className("ocasion-container"));
        // Expand all special occasions
        for (WebElement element : specialOccasions) {
            PageUtils.waitUntilDisplayed(element);
            element.click();
            sleep(CHECK_TIMEOUT_MILLIS);
        }
        List<SpecialOccasionDTO> specialOccasionList = new ArrayList<>();
        // Read elements
        for (WebElement element : specialOccasions) {
            SpecialOccasionDTO.Builder specialOccasionBuilder = new SpecialOccasionDTO.Builder();

            final String title = PageUtils.findWebElementByAndWait(element, By.className("ocasion-title")).getText();
            specialOccasionBuilder.setTitle(title);

            WebElement wholeSpecialOccasionDiv;
            try {
                wholeSpecialOccasionDiv = PageUtils.findWebElementByAndWait(element, By.className("ocasion-trans"));
            } catch (RuntimeException e) {
                if (e.getMessage().contains("Cannot find")) {
                    continue;
                } else {
                    throw e;
                }
            }

            String occasionString = "";
            try {
                occasionString = PageUtils.findWebElementByAndWait(wholeSpecialOccasionDiv, By.cssSelector("div .ocasion-subtitle")).getText();
            } catch(TimeoutException | StaleElementReferenceException e) {
                // Ignore
            } catch (RuntimeException e) {
                if (!e.getMessage().contains("Cannot find")) {
                    throw e;
                }
            }
            Occasion occasion = detectOccasion(occasionString);
            specialOccasionBuilder.setOccasion(occasion);

            if (!Occasion.NONE.equals(occasion)) {
                final String mainQuote = PageUtils.findWebElementByAndWait(wholeSpecialOccasionDiv, By.cssSelector("div div.front-sup-container div.frontContainer div p.frontSource")).getText();
                specialOccasionBuilder.setMainQuote(mainQuote);
            }

            List<WebElement> occasionSubDivList = new ArrayList<>();
            try {
                // Field can be optional f.e. 3.3.2023
                occasionSubDivList = PageUtils.findWebElementsByAndWait(wholeSpecialOccasionDiv, By.cssSelector("div .addition-container"));
            } catch (RuntimeException e) {
                if (!e.getMessage().contains("Cannot find")) {
                    throw e;
                }
            }

            fillOccasionRepeatable(occasionSubDivList, specialOccasionBuilder);

            specialOccasionList.add(specialOccasionBuilder.build());
        }

        return specialOccasionList;
    }

    private void fillOccasionRepeatable(List<WebElement> occasionSubDivList, SpecialOccasionDTO.Builder specialOccasionBuilder) {
        for (WebElement webElement : occasionSubDivList) {
            String key = PageUtils.findWebElementByAndWait(webElement, By.cssSelector("p.addition-title")).getText();

            switch (key) {
                case "Psalm tygodnia:":
                case "Psalm dnia:":
                    String psalm = getStandardElementValue(webElement);
                    validateIfNotEmpty(psalm);
                    specialOccasionBuilder.setPsalm(psalm);
                    break;
                case "Pieśń tygodnia:":
                case "Pieśni tygodnia:":
                case "Pieśń dnia:":
                case "Pieśni dnia:":
                    List<String> worshipSongs = getWorshipSongs(webElement);
                    validateIfNotEmpty(worshipSongs);
                    specialOccasionBuilder.setWorshipSongs(worshipSongs);
                    break;
                case "Stary Testament:":
                    String oldTestament = getStandardElementValue(webElement);
                    validateIfNotEmpty(oldTestament);
                    specialOccasionBuilder.setOldTestament(oldTestament);
                    break;
                case "Ewangelia:":
                    String gospel = getStandardElementValue(webElement);
                    validateIfNotEmpty(gospel);
                    specialOccasionBuilder.setGospel(gospel);
                    break;
                case "Tekst kazalny:":
                    List<String> sermonTextList = getSermonTextList(webElement);
                    validateIfNotEmpty(sermonTextList);
                    specialOccasionBuilder.setSermonTextList(sermonTextList);
                    break;
                case "Lekcja apostolska:":
                    String apostolicLesson = getStandardElementValue(webElement);
                    validateIfNotEmpty(apostolicLesson);
                    specialOccasionBuilder.setApostolicLesson(apostolicLesson);
                    break;
                default:
                    throw new RuntimeException("Unrecognised element key: " + key);
            }
        }
    }

    private List<String> getSermonTextList(WebElement webElement) {
        return PageUtils.findWebElementsByAndWait(webElement, By.cssSelector("div.addition-list-container p.addition-list-text-pointer"))
                        .stream().map(WebElement::getText).toList();
    }

    private List<String> getWorshipSongs(WebElement webElement) {
        return PageUtils.findWebElementsByAndWait(webElement, By.cssSelector("div.addition-list-container p.addition-list-text"))
                        .stream().map(WebElement::getText).toList();
    }

    private String getStandardElementValue(WebElement webElement) {
        return PageUtils.findWebElementByAndWait(webElement, By.cssSelector("p.addition-text-pointer")).getText();
    }

    private void validateIfNotEmpty(String value) {
        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("Element cannot be blank!");
        }
    }

    private void validateIfNotEmpty(List<String> values) {
        if (values.isEmpty()) {
            throw new RuntimeException("EList cannot be empty!");
        }
        values.forEach(this::validateIfNotEmpty);
    }

    private Occasion detectOccasion(String occasion) {
        if ("Hasło dnia:".equals(occasion)) {
            return Occasion.DAY;
        } else if ("Hasło tygodnia:".equals(occasion)) {
            return Occasion.WEEK;
        } else if (StringUtils.EMPTY.equals(occasion)) {
            return Occasion.NONE;
        } else  {
            throw new RuntimeException("Unknown occasion: '" + occasion + "'");
        }
    }

    private boolean doesNotContainSpecialOccasion(WebDriver driver) {
        try {
            return countSpecialOccasions(driver) == 0;
        } catch (TimeoutException | StaleElementReferenceException e) {
            return true;
        }
    }

    private int countSpecialOccasions(WebDriver driver) {
        sleep(QUICK_CHECK_TIMEOUT_MILLIS);
        return PageUtils.findWebElementsByAndWait(driver, By.className("ocasion-container"), 5).size();
    }

    private void moveToSpecificDate(WebDriver driver, Date targetDate, Speed speed) {
        Date currentDate = getCurrentDate(driver);
        int compared = targetDate.compareTo(currentDate);
        if (compared == 0) {
            // Already match
            return;
        } else if (compared < 0) {
            // Target date is before current date
            // move to the previous page
            moveDay(driver, currentDate, MoveDate.PREVIOUS, speed);
        } else {
            // Target date is after current date
            // move to the next page
            moveDay(driver, currentDate, MoveDate.NEXT, speed);
        }
        moveToSpecificDate(driver, targetDate, speed);
    }

    private void moveDay(WebDriver driver, Date currnetDate, MoveDate moveDate, Speed speed) {
        // Find arrow elements
        List<WebElement> elements = new WebDriverWait(driver, Duration.ofSeconds(CHECK_TIMEOUT_MILLIS))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("day-arrow")));
        // Click previous or next arrow
        elements.get(moveDate.getIndex()).click();
        waitUntilDateWillBeChanged(driver, currnetDate, speed);
    }

    private void waitUntilDateWillBeChanged(WebDriver driver, Date currnetDate, Speed speed) {
        while (currnetDate.compareTo(getCurrentDate(driver)) == 0) {
            sleep(speed.getDurationMillis());
        }
        sleep(speed.getDurationMillis());
    }

    private void sleep(long sleepTimeMillis) {
        try {
            Thread.sleep(sleepTimeMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException("Cannot invoke sleep in the loop", e);
        }
    }

    private Date getCurrentDate(WebDriver driver) {
        // Find date element
        WebElement dateElement = new WebDriverWait(driver, Duration.ofMillis(CHECK_TIMEOUT_MILLIS))
                .until(ExpectedConditions.elementToBeClickable(By.className("head-date")));

        String dateString = dateElement.getText();
        if (StringUtils.isBlank(dateString)) {
            throw new RuntimeException("Date is empty but should be not!");
        }

        return DateUtils.parse(dateString);
    }

}
