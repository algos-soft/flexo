package com.algos.flexo.views.iva;

import com.algos.flexo.data.entity.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dialog.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
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

    private Iva entityBean;

    private TextField codeField;

    private TextField descriptionField;

    private NumberField percentField;

    private TextField enDescriptionField;

    private TextField typeField;

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

        return header;
    }

    private Component buildBody() {
        VerticalLayout body = new VerticalLayout();
        body.addClassName("body");

        codeField = new TextField();
        codeField.setLabel("Code");
        codeField.setPlaceholder("code");
        codeField.setRequired(true);
        codeField.setErrorMessage("code must be filled in!");
        if (entityBean.getCode() != null) {
            codeField.setValue(entityBean.getCode());
        }

        descriptionField = new TextField();
        descriptionField.setLabel("Description");
        descriptionField.setPlaceholder("description");
        descriptionField.setRequired(true);
        descriptionField.setErrorMessage("description must be filled in!");
        if (entityBean.getDescription() != null) {
            descriptionField.setValue(entityBean.getDescription());
        }

        percentField = new NumberField();
        percentField.setLabel("Aliquota %");
        percentField.setPlaceholder("aliquota %");
        percentField.setErrorMessage("aliquota must be filled in!");
        if (entityBean.getPercent() != null) {
            percentField.setValue(entityBean.getPercent().doubleValue());
        }

        enDescriptionField = new TextField();
        enDescriptionField.setLabel("English description");
        enDescriptionField.setPlaceholder("optional");
        if (entityBean.getEnDescription() != null) {
            enDescriptionField.setValue(entityBean.getEnDescription());
        }

        typeField = new TextField();
        typeField.setLabel("Type");
        typeField.setPlaceholder("optional");
        if (entityBean.getType() != null) {
            typeField.setValue(entityBean.getType());
        }

        body.add(codeField, descriptionField, percentField, enDescriptionField, typeField);
        return body;
    }

    private Component buildFooter() {
        Div btnLayout = new Div();
        btnLayout.addClassName("footer");

        //        Button confirmButton = new Button("Confirm", event -> {
        //            UserModel model = modelFromDialog();
        //            confirmListener.onConfirm(model);
        //            close();
        //        });
        //        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //
        //        Button cancelButton = new Button("Cancel", event -> {
        //            close();
        //        });
        //
        //        btnLayout.add(cancelButton, confirmButton);

        return btnLayout;
    }

}
