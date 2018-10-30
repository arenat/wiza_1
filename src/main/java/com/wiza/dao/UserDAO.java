package com.wiza.dao;

import com.wiza.model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserDAO {
    @SqlQuery("select id from user_info where user_email = :email")
    Integer getUserIdByEmail(@Bind("email") String email);
}
