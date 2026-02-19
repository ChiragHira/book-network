import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiConfiguration } from '../api-configuration';
import { BaseService } from '../base-service';
import { StrictHttpResponse } from '../strict-http-response';

import { findAllBooks, FindAllBooks$Params } from '../fn/books/find-all-books';
import { saveBook, SaveBook$Params } from '../fn/books/save-book';
import { findBookById, FindBookById$Params } from '../fn/books/find-book-by-id';
import { borrowedBook, BorrowedBook$Params } from '../fn/books/borrowed-book';
import { findAllBorrowedBook, FindAllBorrowedBook$Params } from '../fn/books/find-all-borrowed-book';
import { findAllReturnBook, FindAllReturnBook$Params } from '../fn/books/find-all-return-book';
import { returnBorrowedBook, ReturnBorrowedBook$Params } from '../fn/books/return-borrowed-book';
import { updateArchiveStatus, UpdateArchiveStatus$Params } from '../fn/books/update-archive-status';
import { updateSharableStatus, UpdateSharableStatus$Params } from '../fn/books/update-sharable-status';
import { uploadBookCoverPicture, UploadBookCoverPicture$Params } from '../fn/books/upload-book-cover-picture';
import { approvedReturnBorrowedBook, ApprovedReturnBorrowedBook$Params } from '../fn/books/approved-return-borrowed-book';
import { findAllBookByOwner, FindAllBookByOwner$Params } from '../fn/books/find-all-book-by-owner';

import { PageResponseBookResponse } from '../models/page-response-book-response';
import { BookResponse } from '../models/book-response';
import { PageResponseBorrowBookResponse } from '../models/page-response-borrow-book-response';

@Injectable({
    providedIn: 'root'
})
export class BookService extends BaseService {
    constructor(
        config: ApiConfiguration,
        http: HttpClient
    ) {
        super(config, http);
    }

    findAllBooks$Response(params?: FindAllBooks$Params): Observable<StrictHttpResponse<PageResponseBookResponse>> {
        return findAllBooks(this.http, this.rootUrl, params);
    }

    findAllBooks(params?: FindAllBooks$Params): Observable<PageResponseBookResponse> {
        return this.findAllBooks$Response(params).pipe(
            map((r: StrictHttpResponse<PageResponseBookResponse>) => r.body as PageResponseBookResponse)
        );
    }

    saveBook$Response(params: SaveBook$Params): Observable<StrictHttpResponse<number>> {
        return saveBook(this.http, this.rootUrl, params);
    }

    saveBook(params: SaveBook$Params): Observable<number> {
        return this.saveBook$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    findBookById$Response(params: FindBookById$Params): Observable<StrictHttpResponse<BookResponse>> {
        return findBookById(this.http, this.rootUrl, params);
    }

    findBookById(params: FindBookById$Params): Observable<BookResponse> {
        return this.findBookById$Response(params).pipe(
            map((r: StrictHttpResponse<BookResponse>) => r.body as BookResponse)
        );
    }

    borrowedBook$Response(params: BorrowedBook$Params): Observable<StrictHttpResponse<number>> {
        return borrowedBook(this.http, this.rootUrl, params);
    }

    borrowedBook(params: BorrowedBook$Params): Observable<number> {
        return this.borrowedBook$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    findAllBorrowedBook$Response(params?: FindAllBorrowedBook$Params): Observable<StrictHttpResponse<PageResponseBorrowBookResponse>> {
        return findAllBorrowedBook(this.http, this.rootUrl, params);
    }

    findAllBorrowedBook(params?: FindAllBorrowedBook$Params): Observable<PageResponseBorrowBookResponse> {
        return this.findAllBorrowedBook$Response(params).pipe(
            map((r: StrictHttpResponse<PageResponseBorrowBookResponse>) => r.body as PageResponseBorrowBookResponse)
        );
    }

    findAllReturnBook$Response(params?: FindAllReturnBook$Params): Observable<StrictHttpResponse<PageResponseBorrowBookResponse>> {
        return findAllReturnBook(this.http, this.rootUrl, params);
    }

    findAllReturnBook(params?: FindAllReturnBook$Params): Observable<PageResponseBorrowBookResponse> {
        return this.findAllReturnBook$Response(params).pipe(
            map((r: StrictHttpResponse<PageResponseBorrowBookResponse>) => r.body as PageResponseBorrowBookResponse)
        );
    }

    returnBorrowedBook$Response(params: ReturnBorrowedBook$Params): Observable<StrictHttpResponse<number>> {
        return returnBorrowedBook(this.http, this.rootUrl, params);
    }

    returnBorrowedBook(params: ReturnBorrowedBook$Params): Observable<number> {
        return this.returnBorrowedBook$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    updateArchiveStatus$Response(params: UpdateArchiveStatus$Params): Observable<StrictHttpResponse<number>> {
        return updateArchiveStatus(this.http, this.rootUrl, params);
    }

    updateArchiveStatus(params: UpdateArchiveStatus$Params): Observable<number> {
        return this.updateArchiveStatus$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    updateSharableStatus$Response(params: UpdateSharableStatus$Params): Observable<StrictHttpResponse<number>> {
        return updateSharableStatus(this.http, this.rootUrl, params);
    }

    updateSharableStatus(params: UpdateSharableStatus$Params): Observable<number> {
        return this.updateSharableStatus$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    uploadBookCoverPicture$Response(params: UploadBookCoverPicture$Params): Observable<StrictHttpResponse<{
    }>> {
        return uploadBookCoverPicture(this.http, this.rootUrl, params);
    }

    uploadBookCoverPicture(params: UploadBookCoverPicture$Params): Observable<{
    }> {
        return this.uploadBookCoverPicture$Response(params).pipe(
            map((r: StrictHttpResponse<{
            }>) => r.body as {
            })
        );
    }

    approvedReturnBorrowedBook$Response(params: ApprovedReturnBorrowedBook$Params): Observable<StrictHttpResponse<number>> {
        return approvedReturnBorrowedBook(this.http, this.rootUrl, params);
    }

    approvedReturnBorrowedBook(params: ApprovedReturnBorrowedBook$Params): Observable<number> {
        return this.approvedReturnBorrowedBook$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    findAllBookByOwner$Response(params?: FindAllBookByOwner$Params): Observable<StrictHttpResponse<PageResponseBookResponse>> {
        return findAllBookByOwner(this.http, this.rootUrl, params);
    }

    findAllBookByOwner(params?: FindAllBookByOwner$Params): Observable<PageResponseBookResponse> {
        return this.findAllBookByOwner$Response(params).pipe(
            map((r: StrictHttpResponse<PageResponseBookResponse>) => r.body as PageResponseBookResponse)
        );
    }
}
