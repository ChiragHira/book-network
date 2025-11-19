package com.chirag.book.book;

import com.chirag.book.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Books")
public class BookController {

    private final BookServices services;

    @PostMapping
    public ResponseEntity<Integer> saveBook(@Valid @RequestBody BookRequest request,Authentication connectedUser
    ){
        return ResponseEntity.ok(services.save(request,connectedUser));
    }

    @GetMapping("{book-id}")
    public ResponseEntity<BookResponse>findBookById(@PathVariable("book-id") Integer bookId){
       return ResponseEntity.ok(services.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(@RequestParam(name = "page",defaultValue = "0",required = false) int page, @RequestParam(name = "size",defaultValue = "10",required = false) int size, Authentication connectedUser){
        return  ResponseEntity.ok(services.findAllBooks(page,size,connectedUser));
    }
}
