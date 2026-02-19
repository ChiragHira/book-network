import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiConfiguration } from '../api-configuration';
import { BaseService } from '../base-service';
import { StrictHttpResponse } from '../strict-http-response';

import { saveFeebBack, SaveFeebBack$Params } from '../fn/feedback/save-feeb-back';
import { getFeedbackByBookId, GetFeedbackByBookId$Params } from '../fn/feedback/get-feedback-by-book-id';

import { PageResponseFeedbackResponse } from '../models/page-response-feedback-response';

@Injectable({
    providedIn: 'root'
})
export class FeedbackService extends BaseService {
    constructor(
        config: ApiConfiguration,
        http: HttpClient
    ) {
        super(config, http);
    }

    saveFeebBack$Response(params: SaveFeebBack$Params): Observable<StrictHttpResponse<number>> {
        return saveFeebBack(this.http, this.rootUrl, params);
    }

    saveFeebBack(params: SaveFeebBack$Params): Observable<number> {
        return this.saveFeebBack$Response(params).pipe(
            map((r: StrictHttpResponse<number>) => r.body as number)
        );
    }

    getFeedbackByBookId$Response(params: GetFeedbackByBookId$Params): Observable<StrictHttpResponse<PageResponseFeedbackResponse>> {
        return getFeedbackByBookId(this.http, this.rootUrl, params);
    }

    getFeedbackByBookId(params: GetFeedbackByBookId$Params): Observable<PageResponseFeedbackResponse> {
        return this.getFeedbackByBookId$Response(params).pipe(
            map((r: StrictHttpResponse<PageResponseFeedbackResponse>) => r.body as PageResponseFeedbackResponse)
        );
    }
}
