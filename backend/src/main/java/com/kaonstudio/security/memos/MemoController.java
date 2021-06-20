package com.kaonstudio.security.memos;

import com.kaonstudio.security.exception.ApiRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/memos")
@AllArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping
    public ResponseEntity<Object> memos() {
        try {
            List<Memo> memos = memoService.getMemos();
            return new ResponseEntity<>(memos, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException("No memos found");
        }
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<Object> editMemo(@RequestBody EditMemoRequest editMemoRequest) {
        try {
            int result = memoService.updateMemo(editMemoRequest);
            if (!(result > 0)) throw new ApiRequestException("Could not update memo");
            return new ResponseEntity<>("Memo updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException("Could not update memo");
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addMemo(@RequestBody AddMemoRequest addMemoRequest) {
        try {
            Memo memo = memoService.insertMemo(addMemoRequest);
            return new ResponseEntity<>(memo, HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException("Could not add memo");
        }
    }

    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> removeMemo(@PathVariable Long id) {
        try {
            memoService.deleteMemo(id);
            return new ResponseEntity<>("Memo successfully removed", HttpStatus.OK);
        } catch (Exception e) {
            throw new ApiRequestException("No memo found");
        }
    }
}
