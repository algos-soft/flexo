package com.algos.flexo.utils;

import com.vaadin.flow.spring.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: gio, 15-lug-2021
 * Time: 20:40
 * <p>
 * Abstract static class for general cost of application <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class FlexoCost {

    public static final String ENCODE = "UTF-8";

    public static final String VUOTA = "";

    public static final String A_CAPO = "\n";

    public static final String DOPPIE_GRAFFE_INI = "{{";

    public static final String DOPPIE_GRAFFE_END = "}}";

}
