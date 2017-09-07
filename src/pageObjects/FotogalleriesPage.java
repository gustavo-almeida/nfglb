package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FotogalleriesPage {
		
	WebDriver driver;
	
	public FotogalleriesPage(WebDriver driver) {
		this.driver = driver;
	}
	
	By title_fotogalleries = By.cssSelector(".editoria>b");
	By recommended_gallery = By.cssSelector(".hover");
	By most_viewed = By.cssSelector(".popular>ul>li>a>img");
	By dropdown_editorials = By.id("selectEditorias");
	By lastfrom_list = By.cssSelector("#listaConteudosMobi>li>a>img");
	
	public WebElement galleryTitle() {
		return driver.findElement(title_fotogalleries);
	}
	
	public List<WebElement> recommendedArea() {
		return driver.findElements(recommended_gallery);
	}
	
	public List<WebElement> mostViewedArea() {
		return driver.findElements(most_viewed);
	}
	
	public WebElement selectEditorials() {
		return driver.findElement(dropdown_editorials);
	}
	
	public List<WebElement> lastFromArea() {
		return driver.findElements(lastfrom_list);
	}
}
