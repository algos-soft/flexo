package com.algos.flexo.views.iva;

import com.algos.flexo.data.entity.*;
import com.algos.flexo.data.service.*;
import com.algos.flexo.fields.*;
import static com.algos.flexo.utils.FlexoCost.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.*;
import com.vaadin.flow.component.notification.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.function.*;
import com.vaadin.flow.spring.annotation.*;
import org.apache.poi.ss.formula.functions.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.*;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 09:58
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IvaDialog extends Dialog {

    /**
     * Istanza di una interfaccia SpringBoot <br>
     * Iniettata automaticamente dal framework SpringBoot con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public ApplicationContext appContext;

    /**
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public IvaService ivaService;

    private Iva entityBean;

    // se c'è corrispondenza semplice, entrano automaticamente nel binder
    @PropertyId("code")
    private TextField codeField;

    // se c'è corrispondenza semplice, entrano automaticamente nel binder
    @PropertyId("description")
    private TextArea descriptionField;

    // se non c'è corrispondenza occorre un converter per il binder
    private NumberField percentField;

    // se c'è corrispondenza semplice, entrano automaticamente nel binder
    @PropertyId("enDescription")
    private TextArea enDescriptionField;

    // se non c'è corrispondenza occorre un converter per il binder
    private ComboField<String> typeField;

    private Binder<Iva> binder;

    public IvaDialog() {
    }

    public IvaDialog(Iva entityBean) {
        this.entityBean = entityBean;
    }

    @PostConstruct
    private void init() {
        // newItem -> se manca entityBean, la crea vuota (valori eventuali default)
        this.entityBean = entityBean != null ? entityBean : ivaService.newEntity();

        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        add(buildContent());

        fixBinder();
    }


    private Component buildContent() {

        Div layout = new Div();
        layout.addClassName("indexes-dialog");
        Component header = buildHeader();
        Component body = buildBody();
        Component footer = buildFooter();
        layout.add(header, body, footer);

        return layout;
    }

    private Component buildHeader() {
        Div header = new Div();
        String nuovo = entityBean.getId() != null ? VUOTA : " - New";

        header.addClassName("header");
        Span span = new Span(String.format("Codice Iva%s", nuovo));
        span.getStyle().set("marginLeft", "15px");
        span.getStyle().set("font-weight", "bold");
        span.getStyle().set("color", "green");

        header.add(span);
        return header;
    }

    private Component buildBody() {
        VerticalLayout body = new VerticalLayout();
        body.addClassName("body");
        String lar1 = "6em";
        String lar2 = "30em";

        HorizontalLayout primaRiga = new HorizontalLayout();
        HorizontalLayout secondaRiga = new HorizontalLayout();
        HorizontalLayout terzaRiga = new HorizontalLayout();

        codeField = new TextField();
        codeField.setLabel("Code");
        codeField.setWidth(lar1);
        codeField.setPlaceholder("code");
        codeField.setRequired(true);
        codeField.setErrorMessage("code must be filled in!");

        descriptionField = new TextArea();
        descriptionField.setLabel("Description");
        descriptionField.setWidth(lar2);
        descriptionField.setPlaceholder("description");
        descriptionField.setRequired(true);
        descriptionField.setErrorMessage("description must be filled in!");

        percentField = new NumberField();
        percentField.setLabel("Aliquota %");
        percentField.setWidth(lar1);
        percentField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        percentField.setPlaceholder("aliquota %");
        percentField.setErrorMessage("aliquota must be filled in!");

        enDescriptionField = new TextArea();
        enDescriptionField.setLabel("English description");
        enDescriptionField.setWidth(lar2);
        enDescriptionField.setPlaceholder("optional");

        List items= ivaService.getTypes();
        typeField = appContext.getBean(ComboField.class,items);
        typeField.setLabel("Type");
        typeField.setWidth(lar2);

        primaRiga.add(codeField, percentField);
        secondaRiga.add(descriptionField);
        terzaRiga.add(typeField);
        body.add(primaRiga, secondaRiga, terzaRiga);
        return body;
    }


    private Component buildFooter() {
        HorizontalLayout btnLayout = new HorizontalLayout();
        btnLayout.setSpacing(true);
        btnLayout.setMargin(true);

        btnLayout.addClassName("footer");

        Button confirmButton = new Button("Confirm", event -> { confirm(); });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmButton.setIcon(new Icon(VaadinIcon.CHECK));

        Button deleteButton = new Button("Delete", event -> { delete(); });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));

        Button cancelButton = new Button("Cancel", event -> { close(); });
        cancelButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));

        btnLayout.add(cancelButton, deleteButton, confirmButton);

        return btnLayout;
    }

    protected void fixBinder() {
        binder = new Binder<>(Iva.class);

        //--Aggiunge in automatico i fields normali al binder
        binder.bindInstanceFields(this);

        //--Eventuali fields aggiunti extra binder con converters e validators
        binder.forField(percentField)
                .withConverter(doble -> Float.parseFloat(doble.toString()), flot -> flot != null ? Double.valueOf(flot.toString()) : 0.0)
                .bind(Iva::getPercent, Iva::setPercent);

        binder.forField(codeField)
                .withValidator(text -> text.length() > 0, "Code must not be null")
                .withValidator(text -> text.length() <= 3, "Code max is 3")
                .bind(Iva::getCode, Iva::setCode);

        binder.forField(descriptionField)
                .withValidator(text -> text.length() > 0, "Description must not be null")
                .withValidator(text -> text.length() > 4, "Description must be 4 at least")
                .bind(Iva::getDescription, Iva::setDescription);

        binder.forField(typeField).bind(Iva::getType,Iva::setType);

        // Updates the value in each bound field component
        //--Sincronizza il binder all' apertura della scheda
        //--Trasferisce (binder read) i valori dal DB alla UI
        binder.readBean(entityBean);
    }


    private void delete() {
        ivaService.delete(entityBean);

        // chiude il dialogo
        close();
    }

    private void confirm() {
        // trasferisce i valori dalla UI al binder
        try {
            // controllo validità nel binder
            if (binder.writeBeanIfValid(entityBean)) {
                // registrazione nel service che si connette al database
                // con eventuali beforeSave() e afterSave()
                ivaService.save(entityBean);

                // chiude il dialogo
                close();
            }
            else {
                BinderValidationStatus<Iva> status = binder.validate();
                Notification.show(status.getValidationErrors()
                        .stream()
                        .map(ValidationResult::getErrorMessage)
                        .collect(Collectors.joining("; ")), 3000, Notification.Position.BOTTOM_START);
            }
        } catch (Exception e) {
            int a = 87;
        }
    }

}
