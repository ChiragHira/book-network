package com.chirag.book.book;

import com.chirag.book.common.PageResponse;
import com.chirag.book.exception.OperationNotPermittedException;
import com.chirag.book.file.FileStorageService;
import com.chirag.book.history.BookTransaction;
import com.chirag.book.history.BookTransactionRepository;
import com.chirag.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookServices {

    private final FileStorageService fileStorageService;

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    private final BookTransactionRepository bookTransactionRepository;
    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book is found with ID :: "+ bookId));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable,user.getId());

        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("CreatedDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpeicifation.withOwnerId(user.getId()),pageable);

        List<BookResponse> bookResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );

    }

    public PageResponse<BorrowBookResponse> findAllBorrowedBook(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("CreatedDate").descending());
        Page<BookTransaction> allBorrowedBooks = bookTransactionRepository.findAllBorrowedBooks(pageable,user.getId());
        List<BorrowBookResponse> bookResponses = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );

    }

    public PageResponse<BorrowBookResponse> findAllReturnedBook(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("CreatedDate").descending());
        Page<BookTransaction> allBorrowedBooks = bookTransactionRepository.findAllReturnedBooks(pageable,user.getId());
        List<BorrowBookResponse> bookResponses = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public Integer updateSharableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("No Book found with id :: "+bookId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getOwner().getId(),user.getId())){
            // throw an exception
            throw new OperationNotPermittedException("You cannot update book shareable status");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchiveStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->new EntityNotFoundException("No Book found with id :: "+bookId));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(book.getOwner().getId(),user.getId())){
            // throw an exception
            throw new OperationNotPermittedException("You cannot update book shareable status");
        }
        book.setArchive(!book.isArchive());
        bookRepository.save(book);
        return bookId;
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("No book found with id ::"+bookId));

        if (book.isArchive() || book.isShareable()){
            throw  new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not sharable");
        }

        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(book.getOwner().getId(),user.getId())){
            throw new OperationNotPermittedException("You cannot borrowed own book");
        }

        final boolean isAlreadyBorrowed = bookTransactionRepository.isAlreadyBorrowedByUser(bookId,user.getId());

        if (isAlreadyBorrowed){
            throw new OperationNotPermittedException("The requested book is already borrowed");
        }
        BookTransaction bookTransaction = BookTransaction.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnedApproved(false)
                .build();

        return bookTransactionRepository.save(bookTransaction).getId();
    }

    public Integer returnedBorrowedBook(Integer bookId, Authentication connectedUser) {

        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("No book found with id ::"+bookId));

        if (book.isArchive() || book.isShareable()){
            throw  new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not sharable");
        }

        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(book.getOwner().getId(),user.getId())){
            throw new OperationNotPermittedException("You cannot borrowed or return own book");
        }

        BookTransaction bookTransaction = bookTransactionRepository.findByBookAndUserId(bookId,user.getId())
                .orElseThrow(()->new OperationNotPermittedException("You did not borrowed this book"));

        bookTransaction.setReturned(true);

        return bookTransactionRepository.save(bookTransaction).getId();
    }

    public Integer approvedReturnedBorrowedBook(Integer bookId, Authentication connectedUser) {

        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("No book found with id ::"+bookId));

        if (book.isArchive() || book.isShareable()){
            throw  new OperationNotPermittedException("The requested book cannot be borrowed since it is archived or not sharable");
        }

        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(book.getOwner().getId(),user.getId())){
            throw new OperationNotPermittedException("You cannot borrowed or return own book");
        }

        BookTransaction bookTransaction = bookTransactionRepository.findByBookAndOwnerId(bookId,user.getId())
                .orElseThrow(()->new OperationNotPermittedException("The book is not returned yet. you cannot approved its return"));

        bookTransaction.setReturnedApproved(true);

        return bookTransactionRepository.save(bookTransaction).getId();
    }

    public void uploadCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new EntityNotFoundException("No book found with id ::"+bookId));
        User user = (User) connectedUser.getPrincipal();

        var bookCover = fileStorageService.saveFile(file, user.getId());
        book.setBookCover(bookCover);
        bookRepository.save(book);
    }
}
