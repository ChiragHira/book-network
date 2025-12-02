package com.chirag.book.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionRepository extends JpaRepository<BookTransaction,Integer> {

    @Query("""
            SELECT history
            FROM BookTransactional history
            WHERE history.user.id = :userId
            """
            )
    Page<BookTransaction> findAllBorrowedBooks(Pageable pageable, Integer userId);


    @Query("""
            SELECT history
            FROM BookTransactional history
            WHERE history.book.owner.id = :userId
            """
    )
    Page<BookTransaction> findAllReturnedBooks(Pageable pageable, Integer Userid);

    @Query("""
            SELECT
            (COUNT(*)>0) AS isBorrowed
            FROM BookTransactionalHistory bookTransactionalHistory
            WHERE bookTransactionalHistory.user.id = :userId
            AND bookTransactionalHistory.book.id = :bookId
            AND bookTransactionalHistory.returnedApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);



    @Query("""
            SELECT transaction
            FROM BookTransaction transaction
            WHERE transaction.user.id = :userId
            AND transaction.book.id=:bookId
            AND transaction.returned = false
            AND transaction.returnedApproved = false
            """)
    Optional<BookTransaction> findByBookAndUserId(Integer bookId, Integer userId);


    @Query("""
            SELECT transaction
            FROM BookTransaction transaction
            WHERE transaction.book.owner.id = :userId
            AND transaction.book.id=:bookId
            AND transaction.returned = true
            AND transaction.returnedApproved = false
            """)
    Optional<BookTransaction> findByBookAndOwnerId(Integer bookId, Integer id);
}
