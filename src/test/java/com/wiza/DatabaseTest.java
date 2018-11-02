package com.wiza;

import com.wiza.dao.PeopleDAO;
import com.wiza.representation.PeopleTable;
import io.dropwizard.testing.junit.DAOTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {
    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(PeopleTable.class).build();

    private PeopleDAO peopleDAO;

    @Before
    public void setUp() {
        peopleDAO = new PeopleDAO(database.getSessionFactory());
    }

    @Test
    public void createsFoo() {
        Integer userId = 1123;
        PeopleTable peopleTable = new PeopleTable();
        peopleTable.setId(userId);
        peopleTable.setFullName("MrR");
        peopleTable.setJobTitle("developer");
        PeopleTable result = database.inTransaction(() -> {
            peopleDAO.create(peopleTable);
            return peopleDAO.findById(userId);
        });
        assertEquals(result, peopleTable);
    }
}
