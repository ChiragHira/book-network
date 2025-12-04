package com.chirag.book.feedBack;


import com.chirag.book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name="Feedback")
public class FeedbackController {

    private final FeedBackServices services;

    @PostMapping("/save")
    public ResponseEntity<Integer> saveFeebBack(@Valid @RequestBody FeedBackRequest request, Authentication connectedUser){
        return ResponseEntity.ok(services.save(request,connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> getFeedbackByBookId(
            @PathVariable(name = "book-id") int bookId,
            @RequestParam(name = "page",defaultValue = "0",required = false) int page,
            @RequestParam(name = "size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(services.findAllFeedBackByBook(bookId,page,size,connectedUser));
    }

}
