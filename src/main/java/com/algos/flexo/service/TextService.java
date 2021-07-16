package com.algos.flexo.service;

import static com.algos.flexo.utils.FlexoCost.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

/**
 * Project flexo
 * Created by Algos
 * User: gac
 * Date: ven, 16-lug-2021
 * Time: 15:15
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TextService {


    /**
     * Null-safe, short-circuit evaluation. <br>
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    public boolean isEmpty(final String stringa) {
        return stringa == null || stringa.length() < 1;
    }


    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa esiste e non è vuota
     */
    public boolean isValid(final String stringa) {
        return !isEmpty(stringa);
    }


    /**
      * Elimina dal testo il tagFinale, se esiste. <br>
      * <p>
      * Esegue solo se il testo è valido <br>
      * Se tagFinale è vuoto, restituisce il testo <br>
      * Elimina spazi vuoti iniziali e finali <br>
      *
      * @param testoIn   ingresso
      * @param tagFinale da eliminare
      *
      * @return testo ridotto in uscita
      */
        public String levaCoda(final String testoIn, final String tagFinale) {
            String testoOut = isEmpty(testoIn) ? VUOTA : testoIn.trim();
            String tag;

            if (isEmpty(testoOut) || isEmpty(tagFinale)) {
                return testoOut;
            }

            tag = tagFinale.trim();
            if (testoOut.endsWith(tag)) {
                testoOut = testoOut.substring(0, testoOut.length() - tag.length());
            }

            return testoOut.trim();
        }


}
