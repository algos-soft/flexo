package com.algos.flexo.data.entity;

import com.algos.flexo.data.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.persistence.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: gio, 15-lug-2021
 * Time: 16:15
 */
@Entity
public class Iva extends AbstractEntity {

    private String code;

    private String description;

    private Float percent;

    private String enDescription;

    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public String getEnDescription() {
        return enDescription;
    }

    public void setEnDescription(String enDescription) {
        this.enDescription = enDescription;
    }

    public  String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
