package org;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("usuario");


    }


}
