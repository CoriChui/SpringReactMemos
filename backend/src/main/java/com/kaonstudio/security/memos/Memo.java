package com.kaonstudio.security.memos;

import com.kaonstudio.security.appuser.AppUser;
import com.kaonstudio.security.security.SecurityUserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "memos")
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private Long userId;

    public Memo(String title, String description, String createdAt, Long userId) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.userId = userId;
    }
}
