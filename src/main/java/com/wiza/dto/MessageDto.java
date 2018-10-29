package com.wiza.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDto {
    private long id;
    private String content;

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

}
