package com.example.foyjoo.appgeo;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by foyjoo on 22/06/2019.
 */

public class Commune {

    private String code;

    private String nom;

    private String codeDepartement;

    private String codeRegion;

    private int population;

    private List codesPostaux;

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public int getPopulation() {
        return population;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public String getCodeRegion() {
        return codeRegion;
    }

    public List getCodesPostaux() {
        return codesPostaux;
    }
}
