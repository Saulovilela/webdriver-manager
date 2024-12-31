package br.com.saulo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CadastroServerRestTest {

    private static WebDriver driver;

    // Seletores
    private static final String URL = "https://front.serverest.dev/login";
    private static final By LINK_CADASTRO = By.linkText("Cadastre-se");
    private static final By INPUT_NOME = By.id("nome");
    private static final By INPUT_EMAIL = By.id("email");
    private static final By INPUT_PASSWORD = By.id("password");
    private static final By CHECK_ADMINISTRADOR = By.id("administrador");
    private static final By BTN_CADASTRAR = By.cssSelector(".btn-primary");
    private static final By ALERT_ELEMENT = By.cssSelector(".alert");
    private static final By HEADER_ELEMENT = By.cssSelector("h1");

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();  // Configura o WebDriver
    }

    @BeforeEach
    public void inicioTesteUnico() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));  // Ajuste de timeout
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Teste Finalizado");
        driver.quit();  // Finaliza o WebDriver após cada teste
    }

    @Test
    @Order(1)
    @DisplayName("Teste caminho feliz cadastro simples com dados válidos")
    public void testCadastroServerRest() {
        driver.get(URL);
        driver.findElement(LINK_CADASTRO).click();
        driver.findElement(INPUT_NOME).sendKeys("Teste Selenium");
        driver.findElement(INPUT_EMAIL).sendKeys("testecomseleni123um@sewlewniu1m12.com");
        driver.findElement(INPUT_PASSWORD).sendKeys("1234567");
        driver.findElement(CHECK_ADMINISTRADOR).click();
        driver.findElement(BTN_CADASTRAR).click();

        WebElement alertElement = driver.findElement(ALERT_ELEMENT);
        Assertions.assertTrue(alertElement.getText().contains("Cadastro realizado com sucesso"));

        WebElement headerElement = driver.findElement(HEADER_ELEMENT);
        Assertions.assertTrue(headerElement.getText().contains("Bem Vindo Teste Selenium"));
    }

    @Test
    @Order(2)
    @DisplayName("Teste com mensagem email já existente")
    public void testCadastroUsuarioExistenteServerRest() {
        driver.get(URL);
        driver.findElement(LINK_CADASTRO).click();
        driver.findElement(INPUT_NOME).sendKeys("Teste Selenium");
        driver.findElement(INPUT_EMAIL).sendKeys("testecomselenium@selenium.com");
        driver.findElement(INPUT_PASSWORD).sendKeys("1234567");
        driver.findElement(CHECK_ADMINISTRADOR).click();
        driver.findElement(BTN_CADASTRAR).click();

        WebElement alertElement = driver.findElement(ALERT_ELEMENT);
        Assertions.assertTrue(alertElement.getText().contains("Este email já está sendo usado"));
    }

    @Test
    @Order(3)
    @DisplayName("Login no sistema com sucesso")
    public void testLoginUsuarioCadastradoServerRestTeste() {
        driver.get(URL);
        driver.findElement(INPUT_EMAIL).sendKeys("testecomselenium@selenium.com");
        driver.findElement(INPUT_PASSWORD).sendKeys("1234567");
        driver.findElement(BTN_ENTRAR).click();

        WebElement BtnLogoutTest = driver.findElement(HEADER_ELEMENT );
        Assertions.assertTrue(BtnLogoutTest.getText().contains("Bem Vindo "));
    }
     @Test
    @Order(4)
    @DisplayName("Login no sistema com usuário não cadastrado")
    public void testLoginUsuarioNaocadastrado() {
        driver.get(URL);
        driver.findElement(INPUT_EMAIL).sendKeys("123123testecomselenium@selenium.com");
        driver.findElement(INPUT_PASSWORD).sendKeys("1123");
        driver.findElement(BTN_ENTRAR).click();

        WebElement BtnLogoutTest = driver.findElement(HEADER_ELEMENT );
        Assertions.assertTrue(BtnLogoutTest.getText().contains("Email e/ou senha inválidos"));
    }
}
