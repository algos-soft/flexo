package com.algos.flexo;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.servlet.support.*;
import org.springframework.scheduling.annotation.*;

import javax.annotation.*;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    public static final String APP_NAME = "Flexo";

    // the resource for the generic index icon
    public static final String GENERIC_INDEX_ICON = "images/generic_index.jpg";

    // the file containing all the potentially available symbols
    public static final String ALL_AVAILABLE_SYMBOLS = "config/indexes.csv";

    // the file containing all the etoro symbols
    public static final String ETORO_INSTRUMENTS = "config/etoro_instruments.csv";

    // size of a stored icon on the database (the icon is always resized before storage)
    public static final int STORED_ICON_WIDTH = 128;

    public static final int STORED_ICON_HEIGHT = 128;


    public static void main(String[] args) {
        //LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
        SpringApplication.run(com.algos.flexo.Application.class, args);
    }


    @PostConstruct
    void started() {
        // set JVM timezone as UTC
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


}
