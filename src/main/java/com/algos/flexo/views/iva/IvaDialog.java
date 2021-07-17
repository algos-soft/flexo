package com.algos.flexo.views.iva;

import com.algos.flexo.data.entity.*;
import com.algos.flexo.data.service.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

import javax.annotation.*;

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
     * Istanza unica di una classe @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con l'Annotation @Autowired <br>
     * Disponibile DOPO il ciclo init() del costruttore di questa classe <br>
     */
    @Autowired
    public IvaService ivaService;

    private Iva entityBean;

    private TextField codeField;

    private TextArea descriptionField;

    private NumberField percentField;

    private TextArea enDescriptionField;

    private ComboBox typeField;

    public IvaDialog() {
    }

    public IvaDialog(Iva entityBean) {
        this.entityBean = entityBean;
    }

    @PostConstruct
    private void init() {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        add(buildContent());

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

        header.addClassName("header");
        Span span = new Span("Codice Iva");
        span.getStyle().set("marginLeft", "15px");
        span.getStyle().set("font-weight", "bold");
        span.getStyle().set("color", "green");

        header.add(span);
        return header;
    }

    private Component buildBody() {
        VerticalLayout body = new VerticalLayout();
        body.addClassName("body");
        String lar = "30em";

        HorizontalLayout primaRiga = new HorizontalLayout();
        HorizontalLayout secondaRiga = new HorizontalLayout();
        HorizontalLayout terzaRiga = new HorizontalLayout();

        codeField = new TextField();
        codeField.setLabel("Code");
        codeField.setPlaceholder("code");
        codeField.setRequired(true);
        codeField.setErrorMessage("code must be filled in!");

        descriptionField = new TextArea();
        descriptionField.setLabel("Description");
        descriptionField.setWidth(lar);
        descriptionField.setPlaceholder("description");
        descriptionField.setRequired(true);
        descriptionField.setErrorMessage("description must be filled in!");

        percentField = new NumberField();
        percentField.setLabel("Aliquota %");
        percentField.setPlaceholder("aliquota %");
        percentField.setErrorMessage("aliquota must be filled in!");

        enDescriptionField = new TextArea();
        enDescriptionField.setLabel("English description");
        enDescriptionField.setPlaceholder("optional");

        typeField = new ComboBox();
        typeField.setItems(ivaService.getTypes());
        typeField.setLabel("Type");
        typeField.setWidth(lar);
        typeField.setPlaceholder("optional");

        fromDataBaseToUI();

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

        Button cancelButton = new Button("Cancel", event -> { close(); });

        btnLayout.add(cancelButton, confirmButton);

        return btnLayout;
    }

    // trasferisce i valori dal database al binder
    private void fromDataBaseToUI() {
        if (entityBean.getCode() != null) {
            if (codeField != null) {
                codeField.setValue(entityBean.getCode());
            }
        }

        if (entityBean.getDescription() != null) {
            if (descriptionField != null) {
                descriptionField.setValue(entityBean.getDescription());
            }
        }

        if (entityBean.getPercent() != null) {
            if (percentField != null) {
                percentField.setValue(entityBean.getPercent().doubleValue());
            }
        }

        if (entityBean.getEnDescription() != null) {
            if (enDescriptionField != null) {
                enDescriptionField.setValue(entityBean.getEnDescription());
            }
        }

        if (entityBean.getType() != null) {
            if (typeField != null) {
                typeField.setValue(entityBean.getType());
            }
        }
    }

    private void confirm() {
        // trasferisce i valori dalla UI al binder

        // controllo validità nel binder

        // trasferisce i valori dal binder alla entityBean

        // registrazione nel service che si connette al database
        // con eventuali beforeSave() e afterSave()
        ivaService.save(entityBean);

        // chiude il dialogo
        close();
    }

}
