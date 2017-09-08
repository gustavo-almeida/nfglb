package testCases;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import pageObjects.FotogalleriesPage;

public class MobileChromeFotogalleries {
	
	public static WebDriver driver;
	public final String baseUrl = "https://m.oglobo.globo.com/fotogalerias/";

	@BeforeSuite
	public static void setup() {
		//Check OS for correct chromedriver selection
		String systemName = (System.getProperty("os.name").toLowerCase());
		String chromeExec = "chromedriver";
		
		File path = new File("drivers");
		File pathOs = new File("default");
		
		if (systemName.startsWith("linux")){
			pathOs = new File(path, "linux");
		}
		else if (systemName.startsWith("mac")) {
			pathOs = new File(path, "mac");
		}
		else {
			pathOs = new File(path, "windows");
			chromeExec = "chromedriver.exe";
		}
		
		//fix for not executable some OS
		File file = new File(pathOs, chromeExec);
		file.setExecutable(true);

		//Chrome Driver Declaration
		System.out.println("Os: " + systemName);
		System.out.println("Chrome Driver: " + file.toString());
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		//Chrome Options to mobile
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 7.0; Nexus 9 Build/NRD90R) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Safari/537.36");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  
	}
	
	@AfterSuite
	public static void quitDriver() {
		driver.quit();
	}
	
	@Test
	public void checkFotogalleriesTitle(){
		// Go to Fotogalleries
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		rd.galleryTitle().isDisplayed();
	}
	
	@Test
	public void recommendedAreaValidate() {
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		assertEquals(rd.recommendedArea().size(), 6);
	}
	
	@Test
	public void mostViewedAreaValidate() {
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		assertEquals(rd.mostViewedArea().size(), 3);
	}
	
	@Test
	public void checkDefaultOptionEditorials() {
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		Select select = new Select(rd.selectEditorials());
		assertEquals(select.getFirstSelectedOption().getText(), "Todas");
	}

	@Test
	public void checkDefaultEditorialsContentQty() {
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		assertEquals(rd.lastFromArea().size(), 3);
	}
	
	@Test
	//not the best testcase, but should works on a reduced time test suit, or as smoke-test
	public void checkRandomEditorialsContentQty() {
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		
		List<WebElement> editorialsList = new Select(rd.selectEditorials()).getOptions();
		Random randomizer = new Random();
		String editorial = editorialsList.get(randomizer.nextInt(editorialsList.size())).getText();
		Select select = new Select(rd.selectEditorials());
		
		System.out.println(editorial);
		select.selectByVisibleText(editorial);		
		assertEquals(rd.lastFromArea().size(), 3);
	}
	
	@Test
	//not the best testcase because is validating more than one specification on a loop, but is a possible solution
	public void checkEditorialsContentQtyAllPossibleOptions() {
		driver.navigate().to(baseUrl);
		FotogalleriesPage rd = new FotogalleriesPage(driver);
		List<WebElement> liOp = new Select(rd.selectEditorials()).getOptions();
		for(WebElement eachElem:liOp){
		    new Select(rd.selectEditorials()).selectByVisibleText(eachElem.getText());
			assertEquals(rd.lastFromArea().size(), 3);
		}
	}	
}
