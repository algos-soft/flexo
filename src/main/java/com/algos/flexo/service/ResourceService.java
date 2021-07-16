package com.algos.flexo.service;

import static com.algos.flexo.utils.FlexoCost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: gio, 15-lug-2021
 * Time: 20:34
 * <p>
 * Static files: {project directory}/src/main/resources/META-INF/resources/
 * CSS files and JavaScript: {project directory}/frontend/styles/
 * JAR external resources: {project directory}/config/
 *
 * @see(https://vaadin.com/docs/flow/importing-dependencies/tutorial-ways-of-importing.html)
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ResourceService {

    public static final String CONFIG = "config";

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public FileService fileService;


    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public TextService textService;


    /**
     * Legge una lista di righe di risorse da {project directory}/config/ <br>
     * La prima riga contiene i titoli
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return lista di righe grezze
     */
    public List<String> readListConfig(String simpleNameFileToBeRead) {
        List<String> rows = null;
        String rawText = fileService.readFileConfig(simpleNameFileToBeRead);
        String[] beans;

        if (rawText != null && rawText.length() > 0) {
            rows = new ArrayList<>();
            beans = rawText.split(A_CAPO);
            if (beans != null && beans.length > 0) {
                for (String row : beans) {
                    if (row != null && row.length() > 0) {
                        rows.add(row);
                    }
                }
            }
        }

        return rows;
    }


    /**
     * Legge una lista di mappe di risorse da {project directory}/config/ <br>
     * Ogni elemento della lista è una mappa con tutti campi chiave->valore <br>
     * Le chiavi vengono letta dalla prima riga <br>
     *
     * @param simpleNameFileToBeRead nome del file senza path e senza directory
     *
     * @return lista di mappe di valori con chiave->valore
     */
    public List<Map<String, String>> readMapConfig(String simpleNameFileToBeRead) {
        List<Map<String, String>> listaMappe = new ArrayList<>();
        Map<String, String> mappa;
        List<String> rowsConTitoli = readListConfig(simpleNameFileToBeRead);
        List<String> rowsSenzaTitoli = null;
        String[] parti;
        String[] titoli = null;

        // la prima riga contiene i titoli
        // in questa mappa key = value
        if (rowsConTitoli != null) {
            titoli = readCsv(rowsConTitoli.get(0));
            mappa = new HashMap<>();
            for (String titolo : titoli) {
                mappa.put(titolo, titolo);
            }
            listaMappe.add(mappa);
            rowsSenzaTitoli = rowsConTitoli.subList(1, rowsConTitoli.size());
        }

        // le altre riga contengono i valori
        // mappe key -> value
        if (rowsSenzaTitoli != null) {
            for (String riga : rowsSenzaTitoli) {
                parti = readCsv(riga);
                if (parti.length != titoli.length) {
                    // errore
                    continue;
                }

                mappa = new HashMap<>();
                for (int k = 0; k < parti.length - 1; k++) {
                    mappa.put(titoli[k], parti[k]);
                }
                listaMappe.add(mappa);
            }
        }

        return listaMappe;
    }

    /**
     * Legge una lista (array) di valori da un testo csv <br>
     * Separatore virgola <br>
     * Le virgole nel testo DEVONO essere gestite <br>
     *
     * @param csvText da leggere/interpretare
     *
     * @return lista valori
     */
    public String[] readCsv(String csvText) {
        String[] matrice = null;
        int count = StringUtils.countOccurrencesOf(csvText, VIRGOLA);
        int countEscape = StringUtils.countOccurrencesOf(csvText, VIRGOLETTE + VIRGOLA + VIRGOLETTE);
        int pos = 0;
        int posEscape = 0;

        count = count - countEscape;
        if (count < 1) {
            return matrice;
        }

        matrice = new String[count + 1];
        for (int k = 0; k < matrice.length - 1; k++) {
            pos = csvText.indexOf(VIRGOLA);
            posEscape = csvText.indexOf(VIRGOLETTE + VIRGOLA + VIRGOLETTE);

            if (posEscape > 0 && posEscape < pos) {
                pos = csvText.indexOf(VIRGOLA, pos + 1);
            }

            matrice[k] = csvText.substring(0, pos);
            csvText = csvText.substring(pos + 1);
        }
        matrice[matrice.length - 1] = csvText;

        // pulisce la matrice dalle 'false' virgolette di escape
        for (int k = 0; k < matrice.length; k++) {
            matrice[k] = matrice[k].replace(VIRGOLETTE + VIRGOLA + VIRGOLETTE, VIRGOLA);
        }

        return matrice;
    }

}
