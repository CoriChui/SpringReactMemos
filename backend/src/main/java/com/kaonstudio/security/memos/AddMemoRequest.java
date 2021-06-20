package com.kaonstudio.security.memos;

import lombok.Data;

@Data
public class AddMemoRequest {

    private String title;
    private String description;
    private Long userId;

}
