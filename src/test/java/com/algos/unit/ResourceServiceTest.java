package com.algos.unit;

import com.algos.flexo.service.*;
import static com.algos.flexo.service.ResourceService.*;
import static com.algos.flexo.utils.FlexoCost.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 07:15
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("ResourceService - Gestione delle risorse.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResourceServiceTest {

    protected String sorgente;

    protected String previsto;

    protected String ottenuto;

    protected List<String> sorgenteArray;

    protected List<String> previstoArray;

    protected List<String> ottenutoArray;

    @InjectMocks
    protected FileService fileService;

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    ResourceService service;

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

        MockitoAnnotations.initMocks(fileService);
        Assertions.assertNotNull(fileService);
        service.fileService = fileService;
    }


    @BeforeEach
    void setUpEach() {
        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = VUOTA;
        sorgenteArray = null;
        previstoArray = null;
        ottenutoArray = null;
    }


    @Test
    @Order(1)
    @DisplayName("1 - readFile")
    public void readFile() {
        System.out.println("Legge files di risorse ovunque");
        System.out.println(VUOTA);
        String dirProject = System.getProperty("user.dir") + SLASH;

        sorgente = "logback-spring.xml";
        ottenutoArray = service.readListConfig(sorgente);
        assertNotNull(ottenuto);
        System.out.println(String.format("Nella directory '%s' esiste il file: %s", CONFIG, sorgente));
        System.out.println(String.format("Il file '%s' è composto di %s righe", sorgente, ottenutoArray.size()));

        sorgente = "iva-codici";
        ottenutoArray = service.readListConfig(sorgente);
        assertNotNull(ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Nella directory '%s' esiste il file: %s", CONFIG, sorgente));
        System.out.println(String.format("Il file '%s' è composto di %s righe", sorgente, ottenutoArray.size()));
        System.out.println(VUOTA);
        for (String riga : ottenutoArray) {
            System.out.println(riga);
        }

    }

}
