package com.wiza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class MessageDto {
    private long id;
    private String content;

    public MessageDto() {
    }
    public MessageDto(String content, Long id) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }


    @JsonProperty
    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageDto)) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent());
    }
}
