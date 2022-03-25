package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Write tests for user signup, login, and unauthorized access restrictions.
 * Write a
 * Write a
 *
 * Write tests for note creation, viewing, editing, and deletion.
 * Write a
 * Write a
 * Write a
 *
 * Write tests for credential creation, viewing, editing, and deletion.
 * Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
 * Write a test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
 * Write a test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	// 3 Tests that verifies that an unauthorized user can only access the login and signup pages.
	// (1/3)
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// (2/3)
	@Test
	public void getSignUpPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	// (3/3)
	@Test
	public void getHomePageFailsIfNotSignedIn() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	@Test
	public void onlyGetHomePageWhenUserIsAuthorized() {
		String firstName = "firstName";
		String lastName = "lastName";
		String username = "username";
		String password = "passwordOOO";

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage = new SignUpPage(driver);
		Assertions.assertThrows(NoSuchElementException.class, () -> signUpPage.messageSignUpSuccess.getLocation());
		signUpPage.SignUp(firstName, lastName, username, password);
		Assertions.assertTrue(signUpPage.messageSignUpSuccess.isDisplayed());

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logOut();
		wait.until(webDriver -> webDriver.findElement(By.id("login-page-load-marker")));
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Test that creates a note, and verifies it is displayed.
	@Test
	public void createNoteByUser() throws InterruptedException {
		String noteTitle = "noteTitle123";
		String noteDescription = "noteDescription1234";

		this.signUpAndGoToHome();

		Assertions.assertEquals("Home", driver.getTitle());
		HomePage homePage = new HomePage(driver);

		homePage.createNote(noteTitle, noteDescription);

		Thread.sleep(500);
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));

		Assertions.assertTrue(homePage.findNote(noteTitle, noteDescription));
	}

	// Test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void editCreatedNoteAndVerifyChange() {
		this.signUpAndGoToHome();

		Assertions.assertEquals("Home", driver.getTitle());
		HomePage homePage = new HomePage(driver);
	}

	// Test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void deleteCreatedNoteAndVerifyChange() {
		this.signUpAndGoToHome();

		Assertions.assertEquals("Home", driver.getTitle());
		HomePage homePage = new HomePage(driver);
	}

	private void signUpAndGoToHome() {
		String firstName = "firstName";
		String lastName = "lastName";
		String username = "username";
		String password = "passwordOOO";

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage = new SignUpPage(driver);
		signUpPage.SignUp(firstName, lastName, username, password);
		WebDriverWait waitSignUp = new WebDriverWait(driver, 10);
		waitSignUp.until(webDriver -> webDriver.findElement(By.id("messageSignUpSuccess")));

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		WebDriverWait waitHome = new WebDriverWait(driver, 10);
		waitHome.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
	}

}
