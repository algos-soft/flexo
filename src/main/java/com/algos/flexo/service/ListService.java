package com.algos.flexo.service;

import com.algos.flexo.data.*;
import static com.algos.flexo.utils.FlexoCost.*;
import com.vaadin.flow.component.grid.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: sab, 17-lug-2021
 * Time: 06:15
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti SpringBoot non la costruisce <br>
 * Estende la classe astratta AAbstractService che mantiene i riferimenti agli altri services <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AListService.class); <br>
 * 3) @Autowired public AListService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ListService {


    public Grid getGrid(Class<? extends AbstractEntity> entityClazz) {
        return getGrid(entityClazz, false);
    }

    // flag=true vengono create tutte le colonne in automatico
    public Grid getGrid(Class<? extends AbstractEntity> entityClazz, boolean creazioneautomaticaColonne) {
        Grid grid;

        grid = new Grid<>(entityClazz, creazioneautomaticaColonne);

        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);

        if (!creazioneautomaticaColonne && grid.getColumnByKey(GRID_KEY) != null) {
            grid.removeColumnByKey(GRID_KEY);
        }

        return grid;
    }

}