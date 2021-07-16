package com.algos.flexo.service;

import static com.algos.flexo.service.ResourceService.*;
import com.algos.flexo.utils.*;
import static com.algos.flexo.utils.FlexoCost.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.*;

import java.io.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: gio, 15-lug-2021
 * Time: 20:38
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileService {


    /**
     * Read external file <br>
     *
     * @param pathFileToBeRead complete path name of file
     */
    public String readFile(String pathFileToBeRead) {
        String text = VUOTA;
        String currentLine;

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileToBeRead))) {
            while ((currentLine = br.readLine()) != null) {
                text += currentLine;
                text += A_CAPO;
            }

            if (text.endsWith(A_CAPO)) {
                text = text.substring(0, text.length() - A_CAPO.length());
            }
        } catch (Exception unErrore) {
//            logger.error(unErrore, this.getClass(), "leggeFile");
            int a=87;
        }

        return text;
    }

    /**
     * Read external file from 'config' directory <br>
     *
     * @param simpleFileName to be read
     */
    public String readFileConfig(String simpleFileName) {
        String dirProject = System.getProperty("user.dir") + SLASH;
        String dirConfig = dirProject + CONFIG + SLASH;

        return readFile(dirConfig+simpleFileName);
    }
}
