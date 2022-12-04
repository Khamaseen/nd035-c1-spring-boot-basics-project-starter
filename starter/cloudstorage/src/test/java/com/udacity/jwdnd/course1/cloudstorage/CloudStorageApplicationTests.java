package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignUpPage signUpPage;
	private HomePage homePage;

	private String firstName = "firstName";
	private String lastName = "lastName";
	private String username = "username";
	private String password = "passwordOOO";

	private static boolean isSignedUp = false;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();

		this.signUpUserIfNotExist();
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
		this.login();
		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logOut();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("login-page-load-marker")));
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	// Test that creates a note, and verifies it is displayed.
	@Test
	public void createNoteByUser() throws InterruptedException {
		String noteTitle = "noteTitle123CREATE";
		String noteDescription = "noteDescription1234CREATE";

		this.login();

		Assertions.assertEquals("Home", driver.getTitle());
		HomePage homePage = new HomePage(driver);

		homePage.createNote(noteTitle, noteDescription);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));

		Assertions.assertTrue(homePage.doesNoteExist(noteTitle, noteDescription));
	}

	// Test that edits an existing note and verifies that the changes are displayed.
	@Test
	public void editCreatedNoteAndVerifyChange() throws InterruptedException {
		String noteTitle = "noteTitle123EDIT";
		String noteDescription = "noteDescription1234EDIT";
		String newNoteTitle = "123TitleChangedEDIT";
		String newNoteDescription = "1234NoteChangedEDIT";

		this.login();
		HomePage homePage = new HomePage(driver);

		homePage.createNote(noteTitle, noteDescription);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));

		homePage.editNote(noteTitle, noteDescription, newNoteTitle, newNoteDescription);

		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));

		Assertions.assertTrue(homePage.doesNoteExist(newNoteTitle, newNoteDescription));
	}

	// Test that deletes a note and verifies that the note is no longer displayed.
	@Test
	public void deleteCreatedNoteAndVerifyChange() throws InterruptedException {
		String noteTitle = "noteTitle123DELETE";
		String noteDescription = "noteDescription1234DELETE";

		this.login();
		HomePage homePage = new HomePage(driver);

		homePage.createNote(noteTitle, noteDescription);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertTrue(homePage.doesNoteExist(noteTitle, noteDescription));

		homePage.deleteNote(noteTitle, noteDescription);

		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertFalse(homePage.doesNoteExist(noteTitle, noteDescription));
	}

	@Test
	public void createCredential() throws InterruptedException {
		String url = "http://thisurl.comCREATE";
		String userName = "this nameCREATE";
		String password = "This passwordCREATE";


		this.login();
		HomePage homePage = new HomePage(driver);

		homePage.createCredential(url, userName, password);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertTrue(homePage.doesCredentialExistWithEncryptedPassword(url, userName, password));

		Assertions.assertTrue(homePage.isPasswordCorrect(url, userName, password));
	}

	@Test
	public void editCredential() throws InterruptedException {
		String url = "http://thisurl.comEDIT";
		String userName = "this nameEDIT";
		String password = "This passwordEDIT";
		String newUrl = "wrongButNewUrlEDIT";
		String newUserName = "this is a new nameEDIT";
		String newPassword = "This is a new passwordEDIT";

		this.login();

		HomePage homePage = new HomePage(driver);

		homePage.createCredential(url, userName, password);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertTrue(homePage.doesCredentialExistWithEncryptedPassword(url, userName, password));

		homePage.editCredential(url, userName, newUrl, newUserName, newPassword);

		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertTrue(homePage.doesCredentialExistWithEncryptedPassword(newUrl, newUserName, newPassword));
		Assertions.assertTrue(homePage.isPasswordCorrect(newUrl, newUserName, newPassword));
	}

	@Test
	public void deleteCredential() throws InterruptedException {
		String url = "http://thisurl.comDELETE";
		String userName = "this nameDELETE";
		String password = "This passwordDELETE";

		this.login();
		HomePage homePage = new HomePage(driver);

		homePage.createCredential(url, userName, password);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertTrue(homePage.doesCredentialExistWithEncryptedPassword(url, userName, password));

		homePage.deleteCredential(url, userName);

		wait.until(webDriver -> webDriver.findElement(By.id("success-page-load-marker")));
		driver.get("http://localhost:" + this.port + "/home");


		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
		Assertions.assertFalse(homePage.doesCredentialExistWithEncryptedPassword(url, userName, password));
	}

	private void signUpUserIfNotExist() {
		if (isSignedUp) {
			return;
		}

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage = new SignUpPage(driver);
		signUpPage.SignUp(this.firstName, this.lastName, this.username, this.password);
		WebDriverWait waitSignUp = new WebDriverWait(driver, 10);
		waitSignUp.until(webDriver -> webDriver.findElement(By.id("messageSignUpSuccess")));
		isSignedUp = true;
	}

	private void login() {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(this.username, this.password);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("home-page-load-marker")));
	}

}
