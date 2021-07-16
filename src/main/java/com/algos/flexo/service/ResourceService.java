package com.algos.flexo.service;

import static com.algos.flexo.utils.FlexoCost.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import java.io.*;
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

}
