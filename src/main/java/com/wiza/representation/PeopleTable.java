package com.wiza.representation;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "people")
@NamedQueries({@NamedQuery( name =
        "com.wiza.representation.PeopleTable.findAll",
        query = "SELECT o from PeopleTable o")})
public class PeopleTable {

    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    @JsonProperty
    private Integer id;

    @Column(name = "fullName")
    @NotNull
    @JsonProperty
    private String fullName;

    @Column(name = "jobTitle")
    @NotNull
    @JsonProperty
    private String jobTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeopleTable)) return false;
        PeopleTable that = (PeopleTable) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFullName(), that.getFullName()) &&
                Objects.equals(getJobTitle(), that.getJobTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFullName(), getJobTitle());
    }
}
