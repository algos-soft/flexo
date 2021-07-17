package com.algos.flexo.data;

import org.springframework.data.domain.*;

import java.util.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: sab, 17-lug-2021
 * Time: 07:39
 */
public interface AIService {

     void save(AbstractEntity entityBean);

     void deleteAll();

     List findAll();

     List<AbstractEntity> fetch(Class<? extends AbstractEntity> entityClazz, Sort sortSpring, int offset, int limit);

     int count(Class entityClazz);

}// end of interface

