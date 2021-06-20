package com.kaonstudio.security.memos;

import lombok.Data;

@Data
public class EditMemoRequest {

    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private Long userId;

}
