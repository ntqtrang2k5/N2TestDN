package pageobjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimetablePage extends GeneralPage {

    // Locators
    private final By _rows = By.xpath("//table//tr[position()>1]");

    public RouteInfo getRouteOrRandom(String depart, String arrive) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(_rows));
        
        List<RouteInfo> validRoutes = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);

            // Filter rows that have a 'book ticket' link
            if (row.findElements(By.xpath(".//a[contains(text(), 'book ticket')]")).isEmpty())
                continue;

            String d = row.findElement(By.xpath("./td[2]")).getText();
            String a = row.findElement(By.xpath("./td[3]")).getText();

            // If exact route found, return immediately
            if (d.equals(depart) && a.equals(arrive)) {
                return new RouteInfo(i, d, a);
            }

            validRoutes.add(new RouteInfo(i, d, a));
        }

        // If specific route not found, return a random one
        if (validRoutes.isEmpty()) {
            throw new RuntimeException("No bookable routes found in Timetable");
        }
        
        Random rand = new Random();
        return validRoutes.get(rand.nextInt(validRoutes.size()));
    }

    public BookTicketPage clickBookTicket(RouteInfo route) {
        List<WebElement> rows = Constant.WEBDRIVER.findElements(_rows);
        WebElement bookTicketLink = rows.get(route.getIndex()).findElement(By.xpath(".//a[contains(text(), 'book ticket')]"));
        
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", bookTicketLink);
        
        try {
            bookTicketLink.click();
        } catch (Exception e) {
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", bookTicketLink);
        }

        return new BookTicketPage();
    }

    public static class RouteInfo {
        private int index;
        private String depart;
        private String arrive;

        public RouteInfo(int index, String depart, String arrive) {
            this.index = index;
            this.depart = depart;
            this.arrive = arrive;
        }

        public int getIndex() { return index; }
        public String getDepart() { return depart; }
        public String getArrive() { return arrive; }
    }
}
