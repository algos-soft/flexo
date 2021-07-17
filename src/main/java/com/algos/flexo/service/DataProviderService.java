package com.algos.flexo.service;

import com.algos.flexo.data.*;
import static com.algos.flexo.utils.FlexoCost.*;
import com.vaadin.flow.data.provider.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 04-ott-2020
 * Time: 16:33
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataProviderService {

    public static final String SORT_VAADIN_ASC = "ASCENDING";

    public static final String SORT_VAADIN_DESC = "DESCENDING";

    public static final String SORT_SPRING_ASC = "ASC";

    public static final String SORT_SPRING_DESC = "DESC";

    /**
     * versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    public CallbackDataProvider<AbstractEntity, Void> get(Class entityClazz, AIService dataService) {

        CallbackDataProvider dataProvider = DataProvider.fromCallbacks(

                // First callback fetches items based on a query
                fetchCallback -> {
                    // Esistono DUE tipi di Sort: quello di Spring e quello di Vaadin
                    Sort sortSpring ;

                    // The index of the first item to load
                    int offset = fetchCallback.getOffset();

                    // The number of items to load
                    int limit = fetchCallback.getLimit();

                    // Ordine delle colonne
                    // Vaadin mi manda sempre UNA sola colonna. Perché?
                    List<QuerySortOrder> sortVaadinList = fetchCallback.getSortOrders();

                    // Alla partenza (se l'ordinamento manca) usa l'ordine base della AEntity
                    // le volte successive usa l'ordine selezionato da un header della Grid
                    // Converto il tipo di sort
                    sortSpring = sortVaadinToSpring(sortVaadinList, entityClazz);

                    return dataService.fetch(entityClazz, sortSpring, offset, limit).stream();
                },

                // Second callback fetches the total number of items currently in the Grid.
                // The grid can then use it to properly adjust the scrollbars.
                countCallback -> dataService.count(entityClazz)
        );

        return dataProvider;
    }


    /**
     * DataProvider usa QuerySortOrder (Vaadin) <br>
     * Query di MongoDB usa Sort (springframework) <br>
     * Qui effettuo la conversione
     *
     * @param sortVaadinList sort di Vaadin
     * @param entityClazz    corrispondente ad una collection sul database mongoDB. Obbligatoria.
     *
     * @return sortSpring di springframework
     */
    public Sort sortVaadinToSpring(final List<QuerySortOrder> sortVaadinList, final Class<? extends AbstractEntity> entityClazz) {
        Sort sortSpring = null;
        Sort.Direction directionSpring;
        String fieldName;

        if (sortVaadinList != null) {
            for (QuerySortOrder sortVaadin : sortVaadinList) {
                directionSpring = getSortDirection(sortVaadin);
                fieldName = sortVaadin.getSorted();
                if (sortSpring == null) {
                    sortSpring = Sort.by(directionSpring, fieldName);
                }
                else {
                    sortSpring = sortSpring.and(Sort.by(directionSpring, fieldName));
                }
            }
        }

        // eventuale lettura (tramite @annotation) dal modello
        //        if (sortSpring == null) {
        //            sortSpring = annotation.getSortSpring(entityClazz);
        //        }

        if (sortSpring == null) {
            sortSpring = Sort.by(Sort.DEFAULT_DIRECTION, GRID_KEY);
        }

        return sortSpring;
    }


    /**
     * Estrae la Direction dal sortOrder (vaadin) <br>
     *
     * @param sortVaadin di vaadin
     *
     * @return direction di springframework
     */
    public Sort.Direction getSortDirection(final QuerySortOrder sortVaadin) {
        Sort.Direction directionSpring = null;
        SortDirection directionVaadin;

        directionVaadin = sortVaadin.getDirection();
        if (directionVaadin.name().equals(SORT_VAADIN_ASC)) {
            directionSpring = Sort.Direction.ASC;
        }
        if (directionVaadin.name().equals(SORT_VAADIN_DESC)) {
            directionSpring = Sort.Direction.DESC;
        }

        return directionSpring;
    }


}