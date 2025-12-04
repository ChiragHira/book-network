package com.chirag.book.feedBack;

import com.chirag.book.book.Book;
import com.chirag.book.book.BookRepository;
import com.chirag.book.common.PageResponse;
import com.chirag.book.exception.OperationNotPermittedException;
import com.chirag.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackServices {

    private final FeedBackRepository repository;

    private final BookRepository bookRepository;

    private final FeedBackMapper mapper;

    public Integer save(FeedBackRequest request, Authentication connectedUser){
        Book book = bookRepository.findById(request.bookId()).orElseThrow(()-> new EntityNotFoundException("No book found with id ::"+request.bookId()));

        if (book.isArchive() || !book.isShareable()){
            throw  new OperationNotPermittedException("You cannot give the feedback for an archive and not sharable book");
        }

        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(book.getOwner().getId(),user.getId())){
            throw new OperationNotPermittedException("You cannot give feedback to own books");
        }

        FeedBack feedBack = mapper.toFeedBack(request);
        return repository.save(feedBack).getId();

    }

    public PageResponse<FeedbackResponse> findAllFeedBackByBook(int bookId, int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size);
        Page<FeedBack> responses = repository.findByBookId(bookId,pageable);
        List<FeedbackResponse> responseList = responses.stream()
                .map(f->mapper.toFeedBackResponse(f,user.getId()))
                .toList();

        return new PageResponse<>(
                responseList,
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.isFirst(),
                responses.isLast()
        );

    }
}
