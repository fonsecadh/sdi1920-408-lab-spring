package com.uniovi.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorCompTests {

	// GNU/Linux
//	static String PathFirefox = "/usr/bin/firefox";
//	static String GeckDriver024 = "/home/asuka/Universidad/Tercero/SDI/Lab/SecondPart/Other_files/Materials/geckodriver";

	// Windows (Lab)
	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String GeckDriver024 = "C:\\Users\\UO258318\\Documents\\SDI\\Spring\\Materiales\\geckodriver024win64.exe";

	// Same for all OS
	static WebDriver driver = getDriver(PathFirefox, GeckDriver024);
	static String URL = "http://localhost:8090";

	private static WebDriver getDriver(String pathFirefox2, String geckDriver0242) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", GeckDriver024);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() throws Exception {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() throws Exception {
		driver.manage().deleteAllCookies();
	}

	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}
	
	// PR01. Registro de profesores con datos válidos
	@Test
	public void PR01() {
		driver.navigate().to(URL + "/professor/add");
		PO_LoginView.fillForm(driver,"99999988F", "123456");
		PO_RegisterView.fillProfessorForm(driver, "12345678A", "Frederika", "Bernkastel", "Matemáticas");
		PO_View.checkElement(driver, "text", "Bernkastel");
	}
	
	// PR02. Registro de profesores con datos inválidos: nombre y categoría
	@Test
	public void PR02() {
		driver.navigate().to(URL + "/professor/add");
		PO_LoginView.fillForm(driver,"99999988F", "123456");
		PO_RegisterView.fillProfessorForm(driver, "123456789123A", "Ange", "Ushiromiya", "Magia");
		PO_RegisterView.checkKey(driver, "Error.professor.add.dni.length", PO_Properties.getSPANISH());
		PO_RegisterView.fillProfessorForm(driver, "12345678A", "Ange", "Ushiromiya", "Magia");
		PO_RegisterView.checkKey(driver, "Error.professor.add.dni.duplicate", PO_Properties.getSPANISH());
		PO_RegisterView.fillProfessorForm(driver, "123", "Ange", "Ushiromiya", "Magia");
		PO_RegisterView.checkKey(driver, "Error.professor.add.dni.length", PO_Properties.getSPANISH());
		PO_RegisterView.fillProfessorForm(driver, "123789455", "Ange", "Ushiromiya", "Magia");
		PO_RegisterView.checkKey(driver, "Error.professor.add.dni.letter", PO_Properties.getSPANISH());
	}
	
	// PR03. Registro de profesores con rol incorrecto
	@Test
	public void PR03() {
		driver.navigate().to(URL + "/professor/add");
		PO_LoginView.fillForm(driver,"99999990A", "123456");	
		By enlace = By.xpath("/html/body/h1");
		WebElement forbiddenText = driver.findElement(enlace);
		assertNotEquals(null, forbiddenText);
	}

}
