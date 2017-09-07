package org.mondora.qoodle;

import java.util.ArrayList;

public class Details {


        private String nome="";
        private Detail[] elements;


        public Details(String nome)
        {
            this.nome = nome;
        }


        public  Details(String n, Detail[] ar)
        {
            this.nome = n;
            this.elements = ar;
        }
    }
