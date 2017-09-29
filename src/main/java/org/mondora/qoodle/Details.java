package org.mondora.qoodle;

import java.util.ArrayList;

public class Details {


        private String nome="";
        private String type="";
        private Detail[] elements;


        public Details(String nome)
        {
            this.nome = nome;
        }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public  Details(String n, Detail[] ar, String ty)
        {
            this.nome = n;
            this.elements = ar;
            this.type = ty;
        }
    }
