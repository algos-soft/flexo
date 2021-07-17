package com.algos.flexo.data.service;

import com.algos.flexo.data.*;
import com.algos.flexo.data.entity.*;
import com.algos.flexo.service.*;
import com.vaadin.flow.spring.annotation.*;
import org.apache.poi.ss.formula.functions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 09:16
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IvaService extends DataService {

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ResourceService resourceService;

    private IvaRepository repository;

    public IvaService(@Autowired IvaRepository repository) {
        this.repository = repository;
    }

    /**
     * Legge una lista di entities dalle risorse esterne in 'config' <br>
     * Provvisorio per testare la Grid <br>
     */
    public List<Iva> getListConfig() {
        List<Iva> items = new ArrayList<>();
        List<String> righe;
        String simpleNameFileToBeRead = "iva";

        righe = resourceService.readListConfig(simpleNameFileToBeRead);

        // la prima riga contiene i nomi delle properties
        righe = righe.subList(1, righe.size());

        if (righe != null && righe.size() > 0) {
            for (String riga : righe) {
                items.add(creaIvaFromCsv(riga));
            }
        }

        return items;
    }

    /**
     * Registra una lista di entities letta dalle risorse esterne in 'config' <br>
     * Provvisorio per testare la Grid <br>
     */
    public void saveListaConfig() {
        List<Iva> items = getListConfig();

        if (items != null && items.size() > 0) {
            for (Iva iva : items) {
                repository.save(iva);
            }
        }
    }

    /**
     * Crea una singola entity da una riga csv <br>
     * Provvisorio per testare la Grid <br>
     */
    public Iva creaIvaFromCsv(String riga) {
        Iva iva = new Iva();
        ;
        String code;
        String description;
        String percentText;
        Float percent;
        String enDescription;
        String type;
        String[] parti = riga.split(",");

        if (parti != null) {
            if (parti.length > 0) {
                code = parti[0];
                iva.setCode(code);
            }

            if (parti.length > 1) {
                description = parti[1];
                iva.setDescription(description);
            }

            if (parti.length > 2) {
                percentText = parti[2];
                percent = Float.parseFloat(percentText);
                iva.setPercent(percent);
            }

            if (parti.length > 3) {
                enDescription = parti[3];
                iva.setEnDescription(enDescription);
            }

            if (parti.length > 4) {
                type = parti[4];
                iva.setType(type);
            }
        }

        return iva;
    }

    public List<String> getTypes() {
        return resourceService.readListConfig("iva-type");
    }

    @Override
    public void save(AbstractEntity entityBean) {
        repository.save((Iva) entityBean);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Iva> findAll() {
        return repository.findAll();
    }

    @Override
    public List fetch(Class<? extends AbstractEntity> entityClazz, Sort sortSpring, int offset, int limit) {
        return repository.findAll(); // provvisorio
    }

    @Override
    public int count(Class entityClazz) {
        return ((Long) repository.count()).intValue();
    }

}
