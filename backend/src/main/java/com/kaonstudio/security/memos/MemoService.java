package com.kaonstudio.security.memos;

import com.kaonstudio.security.appuser.AppUser;
import com.kaonstudio.security.appuser.AppUserRepository;
import com.kaonstudio.security.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final AppUserRepository userRepository;

    public List<Memo> getMemos() {
        return memoRepository.findAll();
    }

    public Optional<Memo> findById(Long id) {
        return memoRepository.findById(id);
    }

    public int updateMemo(EditMemoRequest editMemoRequest) {
        return memoRepository.update(
                editMemoRequest.getId(),
                editMemoRequest.getTitle(),
                editMemoRequest.getDescription(),
                editMemoRequest.getCreatedAt()
        );
    }

    public Memo insertMemo(AddMemoRequest addMemoRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByEmail(email).getId();
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy MMMM dd"));
        return memoRepository.save(new Memo(
                addMemoRequest.getTitle(),
                addMemoRequest.getDescription(),
                createdAt,
                userId
        ));
    }

    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}
