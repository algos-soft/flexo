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

    protected int sorgenteIntero;

    protected int previstoIntero;

    protected int ottenutoIntero;

    @InjectMocks
    protected FileService fileService;

    @InjectMocks
    protected TextService textService;

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

        MockitoAnnotations.initMocks(textService);
        Assertions.assertNotNull(textService);
        service.textService = textService;
    }


    @BeforeEach
    void setUpEach() {
        sorgente = VUOTA;
        previsto = VUOTA;
        ottenuto = VUOTA;
        sorgenteArray = null;
        previstoArray = null;
        ottenutoArray = null;
        sorgenteIntero = 0;
        previstoIntero = 0;
        ottenutoIntero = 0;
    }


    @Test
    @Order(1)
    @DisplayName("1 - readFile")
    public void readFile() {
        System.out.println("Legge files di risorse nella directory 'config'");
        System.out.println(VUOTA);

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
        System.out.println(String.format("Il file '%s' è composto di %s righe (più la riga dei titoli)", sorgente, ottenutoArray.size() - 1));
        System.out.println(VUOTA);
        for (String riga : ottenutoArray) {
            System.out.println(riga);
        }
    }

    @Test
    @Order(2)
    @DisplayName("2 - read csv con virgole")
    public void readMapConfig() {
        StringBuffer buffer = new StringBuffer();
        System.out.println("Legge files di risorse csv con virgole nella directoty 'config'");
        System.out.println(VUOTA);
        List<Map<String, String>> listaMappe;
        Map<String, String> titoli = null;
        String rigaTitoli;
        String property;

        sorgente = "iva";
        previstoIntero = 5;
        listaMappe = service.readMapConfig(sorgente);
        assertNotNull(listaMappe);
        System.out.println(String.format("Nella directory '%s' esiste il file: %s", CONFIG, sorgente));
        System.out.println(String.format("Il file '%s' è composto di %s righe (più la riga dei titoli)", sorgente, listaMappe.size() - 1));

        System.out.println(VUOTA);
        titoli = listaMappe.get(0);
        System.out.println(String.format("Titoli %s", DUE_PUNTI_SPAZIO));
        for (String titolo : titoli.keySet()) {
            buffer.append(titolo);
            buffer.append(VIRGOLA_SPAZIO);
        }
        rigaTitoli = buffer.toString();
        rigaTitoli = textService.levaCoda(rigaTitoli, VIRGOLA);
        System.out.println(rigaTitoli);

        System.out.println(VUOTA);
        System.out.println(String.format("Valori %s", DUE_PUNTI_SPAZIO));
        listaMappe = listaMappe.subList(1, listaMappe.size());
        for (Map<String, String> mappaBean : listaMappe) {
            for (String key : mappaBean.keySet()) {
                property = QUADRA_INI + key + UGUALE_SPAZIO_BILATERALE + mappaBean.get(key) + QUADRA_END + SPAZIO;
                System.out.print(property);
            }
            System.out.print(A_CAPO);
        }
        assertEquals(previstoIntero,listaMappe.size());
    }

}
