package com.chirag.book.book;

import com.chirag.book.file.FileUtils;
import com.chirag.book.history.BookTransaction;
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
                .cover(FileUtils.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowBookResponse toBorrowedBookResponse(BookTransaction bookTransaction) {
        return BorrowBookResponse.builder()
                .id(bookTransaction.getBook().getId())
                .title(bookTransaction.getBook().getTitle())
                .authorName(bookTransaction.getBook().getAuthorName())
                .isbn(bookTransaction.getBook().getIsbn())
                .rate(bookTransaction.getBook().getRate())
                .returned(bookTransaction.isReturned())
                .returnedApproved(bookTransaction.isReturnedApproved())
                .build();
    }
}
