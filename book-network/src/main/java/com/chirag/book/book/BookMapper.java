package com.chirag.book.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title((request.title()))
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archive(false)
                .shareable((request.shareable()))
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .archived(book.isArchive())
                .sharable(book.isShareable())
                .owner(book.getOwner().getName())
                //Todo implement this later
                //.cover
                .build();
    }
}
