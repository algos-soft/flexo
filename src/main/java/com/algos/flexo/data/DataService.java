package com.algos.flexo.data;

import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: sab, 17-lug-2021
 * Time: 07:38
 */
public class DataService implements AIService {

    @Override
    public void save(AbstractEntity entityBean) {
    }

    @Override
    public void delete(AbstractEntity entityBean) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List fetch(Class<? extends AbstractEntity> entityClazz, Sort sortSpring, int offset, int limit) {
        return null;
    }

    @Override
    public int count(Class entityClazz) {
        return 0;
    }

}
