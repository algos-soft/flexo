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

}
