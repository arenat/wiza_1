package com.wiza.dao;

import com.wiza.representation.PeopleTable;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class PeopleDAO extends AbstractDAO<PeopleTable> {
    public PeopleDAO(SessionFactory factory) {
        super(factory);
    }

    public PeopleTable findById(Integer id) {
        return get(id);
    }

    public void create(PeopleTable peopleTable) {
        persist(peopleTable);
    }

    public List<PeopleTable> findAll() {
        return list(namedQuery("com.wiza.representation.PeopleTable.findAll"));
    }
}
