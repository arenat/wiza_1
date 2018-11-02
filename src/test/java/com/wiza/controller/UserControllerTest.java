package com.wiza.controller;

import com.wiza.dao.UserDAO;
import com.wiza.dto.MessageDto;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private static final UserDAO dao = mock(UserDAO.class);
    private String emailToPath = "hp@hp.com";
    private Integer returnedValue = 1;

    // loads a given controller instance in an in-memory Jersey server
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserController(dao))
            .build();

    @Before
    public void setup() {
        when(dao.getUserIdByEmail(eq(emailToPath))).thenReturn(returnedValue);
    }

    @After
    public void tearDown() {
        reset(dao);
    }

    @Test
    public void testGetIdMethod() {

        MessageDto expectedMessageDto = new MessageDto(String.valueOf(returnedValue), Long.valueOf(returnedValue));
        assertThat(resources.target("/user")
                .queryParam("email", emailToPath)
                .request().get(MessageDto.class))
                .isEqualTo(expectedMessageDto);

        verify(dao).getUserIdByEmail(emailToPath);
    }
}