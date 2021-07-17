package com.algos.flexo.views.iva;

import com.algos.flexo.*;
import com.algos.flexo.beans.*;
import com.algos.flexo.data.entity.*;
import com.algos.flexo.data.service.*;
import com.algos.flexo.service.*;
import com.algos.flexo.views.*;
import com.algos.flexo.views.main.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.data.provider.*;
import com.vaadin.flow.router.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import javax.annotation.*;
import java.util.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 07:39
 */
@Route(value = "iva", layout = MainView.class)
@PageTitle(Application.APP_NAME + " | Iva")
@PageSubtitle("Iva")
public class IvaView extends Div {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public IvaService ivaService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ListService listService;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public DataProviderService providerService;

    private Grid<Iva> grid;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Utils utils;

    private CallbackDataProvider provider;

    @PostConstruct
    private void init() {

        addClassName("indexes-view");
        setSizeFull();

        createGrid();

        // customize the header
        addAttachListener((ComponentEventListener<AttachEvent>) attachEvent -> {
            Optional<Component> parent = getParent();
            if (parent.isPresent()) {
                Optional<HorizontalLayout> customArea = utils.findCustomArea(parent.get());
                if (customArea.isPresent()) {
                    customArea.get().removeAll();
                    customizeHeader(customArea.get());
                }
            }
        });

    }

    private void createGrid() {
        //        grid.addColumn("code");
        //        grid.addColumn("description");
        //        grid.addColumn("percent");
        //        grid.addColumn("enDescription");
        //        grid.addColumn("type");

        // flag=true vengono create tutte le colonne in automatico
        grid = listService.getGrid(Iva.class, true);

        this.add(grid);
        grid.addItemDoubleClickListener(event -> openItem(event));

        // la prima volta recupera la lista originaria da un file csv nella directory 'config'
        if (ivaService.count() == 0) {
            ivaService.saveListaConfig();
        }

        List items = ivaService.findAll();
        grid.setItems(items);
//        provider = providerService.get(Iva.class, ivaService);
//        grid.setDataProvider(provider);
//        grid.getDataProvider().refreshAll();
    }

    private void customizeHeader(HorizontalLayout header) {

        header.getStyle().set("flex-direction", "row");

        Button addButton = new Button("New codice", new Icon(VaadinIcon.PLUS_CIRCLE));
        addButton.getStyle().set("margin-left", "1em");
        addButton.getStyle().set("margin-right", "1em");
        addButton.setIconAfterText(true);
        addButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            addNewItem();
        });

        header.add(addButton);

    }


    /**
     * Present a dialog to update an item
     */
    private void addNewItem() {
        context.getBean(IvaDialog.class).open();
    }

    /**
     * Present a dialog to update an item
     */
    private void openItem(ItemDoubleClickEvent<Iva> event) {
        context.getBean(IvaDialog.class, event.getItem()).open();
        grid.getDataProvider().refreshAll();
    }

}
