package com.algos.unit;

import com.algos.flexo.service.*;
import static com.algos.flexo.service.ResourceService.*;
import static com.algos.flexo.utils.FlexoCost.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 06:23
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
    @DisplayName("1 - readFile")
    public void readFile() {
        System.out.println("Legge files ovunque");
        System.out.println(VUOTA);
        String dirProject = System.getProperty("user.dir") + SLASH;
        String dirConfig = dirProject + CONFIG + SLASH;

        sorgente = "logback-spring.xml";
        ottenuto = service.readFile(dirConfig + sorgente);
        assertNotNull(ottenuto);
        System.out.println(String.format("Nella directory '%s' esiste il file: %s", CONFIG, sorgente));

        sorgente = "password.txt";
        ottenuto = service.readFile(dirProject + sorgente);
        assertNotNull(ottenuto);
        assertEquals(VUOTA, ottenuto);
        System.out.println(String.format("Nella directory '%s' NON esiste il file: %s", CONFIG, sorgente));
    }

    @Test
    @Order(2)
    @DisplayName("2 - readFileConfig")
    public void readFileConfig() {
        System.out.println("Legge files nella directory 'config'");
        System.out.println(VUOTA);

        sorgente = "logback-spring.xml";
        ottenuto = service.readFileConfig(sorgente);
        assertNotNull(ottenuto);
        System.out.println(String.format("Nella directory '%s' esiste il file: %s", CONFIG, sorgente));

        sorgente = "password.txt";
        ottenuto = service.readFileConfig(sorgente);
        assertNotNull(ottenuto);
        assertEquals(VUOTA, ottenuto);
        System.out.println(String.format("Nella directory '%s' NON esiste il file: %s", CONFIG, sorgente));
    }

}
