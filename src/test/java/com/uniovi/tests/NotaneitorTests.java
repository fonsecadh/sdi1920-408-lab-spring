package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

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

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {

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

	// PR01. Acceder a la página principal
	@Test
	public void PR01() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// PR02. Opción de navegación. Pinchar en el enlace Registro en la página Home.
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	// PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página
	// Home.
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	// PR04. Opción de navegación. Cambio de idioma de Español a Ingles y vuelta a
	// Español.
	@Test
	public void PR04() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		// SeleniumUtils.esperarSegundos(driver, 2);
	}

	// PR05. Prueba del formulario de registro. Registro con datos correctos.
	@Test
	public void PR05() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	// PR06. Prueba del formulario de registro. DNI repetido en la BD, nombre corto,
	// ...
	@Test
	public void PR06() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
		PO_View.getP();
		// Comprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.dni.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
		// Comprobamos el error de Nombre corto.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Per", "77777", "77777");
		// Comprobamos el error de Apellidos cortos
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Perez", "12", "12");
		// Comprobamos el error de Contraseña corta
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "Perez", "123456", "123457");
		// Comprobamos el error de Contraseñas no coincidiendo
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PR07. Loguearse con éxito desde el Rol de Usuario, 99999990A, 123456
	@Test
	public void PR07() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	// PR08. Loguearse con éxito desde el Rol de Profesor, 99999993D, 123456
	@Test
	public void PR08() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D", "123456");
		// Comprobamos que entramos en la pagina privada de Profesor
		PO_View.checkElement(driver, "text", "99999993D");
	}

	// PR09. Loguearse con éxito desde el Rol de Administrador, 99999988F, 123456
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// Comprobamos que entramos en la pagina privada de Administrador
		PO_View.checkElement(driver, "text", "99999988F");
	}

	// PR10. Loguearse sin éxito desde el Rol de Alumno, 99999990A, 123456
	@Test
	public void PR10() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123458");
		// Comprobamos que seguimos en el formulario de login
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR11. Loguearse con éxito y desconexión desde el Rol de Usuario, 99999990A,
	// 123456
	@Test
	public void PR11() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
		// Nos desconectamos
		PO_HomeView.clickOption(driver, "logout", "text", "Identifícate");
		// Comprobamos que nos devuelve al formulario de login
		PO_View.checkElement(driver, "text", "Identifícate");
	}

	// PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse
	// usando el rol de estudiante
	@Test
	public void PR12() {
		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999990A", "123456", "Notas del usuario");
		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 4);
		// Ahora nos desconectamos
		PO_PrivateView.logout(driver, "Identifícate");
	}

	// PR13. Loguearse como estudiante y ver los detalles de la nota con Descripción
	// = Nota A2.
	// P13. Ver la lista de Notas.
	@Test
	public void PR13() {
		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999990A", "123456", "Notas del usuario");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Contamos las notas
		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]");
		driver.findElement(enlace).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Esperamos por la ventana de detalle
		PO_View.checkElement(driver, "text", "Detalles de la nota");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Ahora nos desconectamos
		PO_PrivateView.logout(driver, "Identifícate");
	}

	// PR14. Loguearse como profesor y Agregar Nota A2.
	// P14. Esta prueba podría encapsularse mejor.
	@Test
	public void PR14() {
		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999993D", "123456", "99999993D");
		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
		elementos.get(0).click();
		// Esperamos a que aparezca la opción de añadir nota: //a[contains(@href,
		// 'mark/add')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
		// Pinchamos en agregar Nota.
		elementos.get(0).click();
		// Ahora vamos a rellenar lanota. //option[contains(@value, '4')]
		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
		// Esperamos a que se muestren los enlaces de paginación en la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamos a la última página
		elementos.get(3).click();
		// Comprobamos que aparece la nota en la pagina
		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
		// Ahora nos desconectamos
		PO_PrivateView.logout(driver, "Identifícate");
	}

	// PR15. Loguearse como Profesor, vamos a la última página y eliminamos la Nota
	// Nueva 1.
	// PR15. Ver la lista de notas.
	@Test
	public void PR15() {
		PO_PrivateView.fillFormCheckPrivateZone(driver, "99999993D", "123456", "99999993D");
		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
		elementos.get(0).click();
		// Pinchamos en la opción de lista de notas.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
		elementos.get(0).click();
		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		// Nos vamosa la última página
		elementos.get(3).click();
		// Esperamos a que aparezca la Nueva nota en la ultima pagina
		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
		// //td[contains(text(), 'Nota Nueva
		// 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
		elementos.get(0).click();
		// Volvemos a la última pagina
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(3).click();
		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
		// Ahora nos desconectamos
		PO_PrivateView.logout(driver, "Identifícate");
	}

}
