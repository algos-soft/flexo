package com.algos.unit;

import com.algos.flexo.service.*;
import static com.algos.flexo.utils.FlexoCost.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 15:22
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("TextService - Gestione delle risorse.")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TextServiceTest {

    protected static final String PIENA = "Piena";

    protected boolean previstoBooleano;

    protected boolean ottenutoBooleano;

    protected String sorgente;

    protected String previsto;

    protected String ottenuto;

    protected String tag;

    /**
     * Classe principale di riferimento <br>
     */
    @InjectMocks
    TextService service;

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
        tag = VUOTA;
        previstoBooleano = false;
        ottenutoBooleano = false;
    }


    @Test
    @Order(1)
    @DisplayName("1 - Controlla testo vuoto")
    void testIsEmpty() {
        ottenutoBooleano = service.isEmpty(null);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(VUOTA);
        Assertions.assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(PIENA);
        Assertions.assertFalse(ottenutoBooleano);
    }


    @Test
    @Order(2)
    @DisplayName("2 - Controlla testo valido")
    void testIsValid() {
        ottenutoBooleano = service.isValid(null);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(VUOTA);
        Assertions.assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(PIENA);
        Assertions.assertTrue(ottenutoBooleano);
    }


    @Test
    @Order(3)
    @DisplayName("3 - Leva la parte finale di un testo")
    void levaCoda() {
        System.out.println("Leva la parte finale di un testo");

        sorgente = " Levare questa fine Non ";
        tag = "Non";
        previsto = "Levare questa fine";
        ottenuto = service.levaCoda(sorgente, tag);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Da '%s' %s levo '%s' e ottengo %s '%s'", sorgente, FORWARD, tag, FORWARD, ottenuto));

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = service.levaCoda(sorgente, tag);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Da '%s' %s levo '%s' e ottengo %s '%s'", sorgente, FORWARD, tag, FORWARD, ottenuto));

        sorgente = "Non Levare questa fine ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questa fine";
        ottenuto = service.levaCoda(sorgente, tag);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println(VUOTA);
        System.out.println(String.format("Da '%s' %s levo '%s' e ottengo %s '%s'", sorgente, FORWARD, tag, FORWARD, ottenuto));
    }

}
