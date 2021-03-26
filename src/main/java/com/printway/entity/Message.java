package com.printway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long message_id;
    private From from;
    private Chat chat;
    private Long date;
    private String text;
}
