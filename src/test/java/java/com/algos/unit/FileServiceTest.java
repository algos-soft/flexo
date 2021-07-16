package java.com.algos.unit;

import com.algos.flexo.service.*;
import static com.algos.flexo.utils.FlexoCost.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: gio, 15-lug-2021
 * Time: 22:27
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("FileService - Gestione dei files.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceTest {

    protected String sorgente;

    protected String previsto;

    protected String ottenuto;

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    FileService service;


    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpStartUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    void setUpAll() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        Assertions.assertNotNull(service);
    }


    @BeforeEach
    void setUpEach() {
        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = VUOTA;
    }


    @Test
    @Order(1)
    @DisplayName("14 - leggeFile")
    public void leggeFile() {
        String result = System.getenv("PATH");
        System.out.println(result);
        String path = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow15/" + result;

        sorgente = "password.txt";
        sorgente = path + "/" + sorgente;
        ottenuto = service.readFile(sorgente);
        System.out.println(ottenuto);
    }

}
