package com.algos.flexo.data.service;

import com.algos.flexo.data.entity.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.jpa.repository.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 11:20
 */
public interface IvaRepository extends JpaRepository<Iva, Integer> {


//    @Query("SELECT COUNT(s) FROM Simulation s WHERE s.generator=:generator")
//    int count();

}
