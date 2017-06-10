package tk.icudi.headless;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

public class Headless {

	private WebDriver webdriver;

	private static Headless instance ;
	
	public static synchronized Headless getInstance() {
		if(instance == null){
			instance = new Headless();
		}
		return instance;
	}
	
	private Headless() {
		
		//System.setProperty("wdm.targetPath", "$OPENSHIFT_DATA_DIR/webdriver");
		
		System.setProperty("wdm.targetPath", "/tmp/webdriver");
		ChromeDriverManager.getInstance().setup();
		
		this.webdriver = new ChromeDriver();
	}

	public void login(String username, String password) {
		webdriver.get("https://www.ingress.com/intel");

		System.out.println(" --- title: " + webdriver.getTitle());
		
		List<WebElement> signIns = webdriver.findElements(By.cssSelector("#dashboard_container .button_link"));
		
		System.out.println(" --- signIns: " + signIns);
		
		if(!signIns.isEmpty()){
			login(signIns.get(0), username, password);
		}
				
		WebElement chat = new WebDriverWait(webdriver, 20).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#plexts")));
		
		System.out.println(" --- chat: " + chat);
		
	}

	private void login(WebElement signButton, String username, String password) {
		signButton.click();
		System.out.println(" --- title1: " + webdriver.getTitle());
		

		WebElement idElement = new WebDriverWait(webdriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("identifierId")));
		idElement.sendKeys(username);
		webdriver.findElement(By.id("identifierNext")).click();
		
		System.out.println(" --- title2: " + webdriver.getTitle());
		
		WebElement pwElement = new WebDriverWait(webdriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#password input")));
		pwElement.sendKeys(password);
		webdriver.findElement(By.cssSelector("#passwordNext")).click();
		
		
		System.out.println(" --- title3: " + webdriver.getTitle());
		
	}

	public String getPlext() {
		
		List<WebElement> plexts = webdriver.findElements(By.cssSelector("#plexts"));
		
		return plexts.toString();
	}
}
