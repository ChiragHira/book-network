package com.chirag.book.feedBack;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Double note;
    private String comments;
    private Boolean ownFeedback;
}
