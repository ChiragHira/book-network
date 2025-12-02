package com.chirag.book.book;

import com.chirag.book.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBookByOwner(@RequestParam(name = "page",defaultValue = "0",required = false) int page, @RequestParam(name = "size",defaultValue = "10",required = false) int size, Authentication connectedUser){
        return  ResponseEntity.ok(services.findAllBooksByOwner(page,size,connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowBookResponse>> findAllBorrowedBook(@RequestParam(name = "page",defaultValue = "0",required = false) int page, @RequestParam(name = "size",defaultValue = "10",required = false) int size, Authentication connectedUser){
        return  ResponseEntity.ok(services.findAllBorrowedBook(page,size,connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowBookResponse>> findAllReturnBook(@RequestParam(name = "page",defaultValue = "0",required = false) int page, @RequestParam(name = "size",defaultValue = "10",required = false) int size, Authentication connectedUser){
        return  ResponseEntity.ok(services.findAllReturnedBook(page,size,connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateSharableStatus(@PathVariable(name = "book-id")Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok(services.updateSharableStatus(bookId,connectedUser));
    }

    @PatchMapping("/archive/{book-id}")
    public ResponseEntity<Integer> updateArchiveStatus(@PathVariable(name = "book-id")Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok(services.updateArchiveStatus(bookId,connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Integer> borrowedBook(@PathVariable(name = "book-id")Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok(services.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowedBook(@PathVariable(name = "book-id")Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok(services.returnedBorrowedBook(bookId,connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<Integer> approvedReturnBorrowedBook(@PathVariable(name = "book-id")Integer bookId,Authentication connectedUser){
        return ResponseEntity.ok(services.approvedReturnedBorrowedBook(bookId,connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable(name = "book-id")Integer bookId,
            @Parameter()
            @RequestPart("file")MultipartFile file,
            Authentication connectedUser
            ){
        services.uploadCoverPicture(file,connectedUser,bookId);
        return ResponseEntity.accepted().build();
    }




}
