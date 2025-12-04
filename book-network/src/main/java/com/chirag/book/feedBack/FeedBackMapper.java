package com.chirag.book.feedBack;

import com.chirag.book.book.Book;

import java.util.Objects;

public class FeedBackMapper {

    public FeedBack toFeedBack(FeedBackRequest response){
        return FeedBack.builder()
                .note(response.note())
                .comment(response.comments())
                .book(Book.builder()
                        .id(response.bookId())
                        .build())
                .build();
    }

    public FeedbackResponse toFeedBackResponse(FeedBack feedBack,Integer userId) {

        return FeedbackResponse.builder()
                .note(feedBack.getNote())
                .comments(feedBack.getComment())
                .ownFeedback(Objects.equals(feedBack.getCreatedBy(),userId))
                .build();
    }
}
