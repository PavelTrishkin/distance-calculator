package com.trishkin.calculator.utils;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class Resources {
    @Produces
    @PersistenceContext
    private EntityManager entityManager;
}
