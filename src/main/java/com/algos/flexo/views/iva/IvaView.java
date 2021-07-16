package com.algos.flexo.views.iva;

import com.algos.flexo.*;
import com.algos.flexo.beans.*;
import com.algos.flexo.data.entity.*;
import com.algos.flexo.data.service.*;
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

    private Grid<Iva> grid;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Utils utils;

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
        // flag=true vengono create tutte le colonne in automatico
        grid = new Grid<>(Iva.class, true);
        //        grid.addColumn("code");
        //        grid.addColumn("description");
        //        grid.addColumn("percent");
        //        grid.addColumn("enDescription");
        //        grid.addColumn("type");

        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);


        // provvisorio per ricreare la lista ogni volta @todo Da levare
        ivaService.deleteAll();
        ivaService.saveListaConfig(); // recupera la lista da un file csv nella directory 'config'
        // provvisorio per ricreare la lista ogni volta @todo Da levare


        // recupero dal service/repository per adesso senza DataProvider
        List<Iva> items = ivaService.findAll();
        grid.setItems(items);


        this.add(grid);
        grid.addItemDoubleClickListener(event -> openItem(event));

        CallbackDataProvider<Iva, Void> provider;
        //                provider = DataProvider.fromCallbacks(fetchCallback -> {
        //                    try {
        //                        int offset = fetchCallback.getOffset();
        //                        int limit = fetchCallback.getLimit();
        ////                        order = fetchCallback.getSortOrders();
        //                        List<Iva> entities = null;
        //                        return null;
        //                    } catch (InvalidBigNumException e) {
        //                        return null;
        //                    }
        //                }, countCallback -> {
        //                    try {
        //                        int count=2;
        //                        return count;
        //                    } catch (InvalidBigNumException e) {
        //                        return 0;
        //                    }
        //                });
        //
        //                grid.setDataProvider(provider);

    }

    private void customizeHeader(HorizontalLayout header) {

        header.getStyle().set("flex-direction", "row-reverse");

        Button addButton = new Button("New Index", new Icon(VaadinIcon.PLUS_CIRCLE));
        addButton.getStyle().set("margin-left", "1em");
        addButton.getStyle().set("margin-right", "1em");
        addButton.setIconAfterText(true);
        //        addButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
        //            addNewItem();
        //        });

        Button expButton = new Button("Export", new Icon(VaadinIcon.ARROW_CIRCLE_DOWN_O));
        expButton.getStyle().set("margin-left", "1em");
        expButton.getStyle().set("margin-right", "1em");
        expButton.setIconAfterText(true);
        //        expButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
        //            byte[] barray = marketIndexService.exportExcel(grid.getDataProvider());
        //            if(barray!=null){
        //                excelInputStream = new ByteArrayInputStream(barray);
        //                anchorButton.clickInClient();
        //            }
        //        });

        header.add(addButton, expButton);

    }


    /**
     * Present a dialog to update an item
     */
    private void openItem(ItemDoubleClickEvent<Iva> event) {
        context.getBean(IvaDialog.class, event.getItem()).open();
    }

}
