import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiConfiguration } from '../api-configuration';
import { BaseService } from '../base-service';
import { authenticate, Authenticate$Params } from '../fn/authentication/authenticate';
import { register, Register$Params } from '../fn/authentication/register';
import { confirm, Confirm$Params } from '../fn/authentication/confirm';
import { StrictHttpResponse } from '../strict-http-response';
import { AuthenticationResponse } from '../models/authentication-response';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService extends BaseService {
    constructor(
        config: ApiConfiguration,
        http: HttpClient
    ) {
        super(config, http);
    }

    /**
     * Authenticate users
     *
     * This method provides access to the full `HttpResponse`, allowing access to specific headers.
     * To access only the response body, use `authenticate()` instead.
     *
     * This method sends `application/json` and handles request body of type `application/json`.
     */
    authenticate$Response(params: Authenticate$Params): Observable<StrictHttpResponse<AuthenticationResponse>> {
        return authenticate(this.http, this.rootUrl, params);
    }

    /**
     * Authenticate users
     *
     * This method provides access to only to the response body.
     * To access the full response (for headers, for example), `authenticate$Response()` instead.
     *
     * This method sends `application/json` and handles request body of type `application/json`.
     */
    authenticate(params: Authenticate$Params): Observable<AuthenticationResponse> {
        return this.authenticate$Response(params).pipe(
            map((r: StrictHttpResponse<AuthenticationResponse>) => r.body as AuthenticationResponse)
        );
    }

    /**
     * Register a new user
     *
     * This method provides access to the full `HttpResponse`, allowing access to specific headers.
     * To access only the response body, use `register()` instead.
     *
     * This method sends `application/json` and handles request body of type `application/json`.
     */
    register$Response(params: Register$Params): Observable<StrictHttpResponse<{}>> {
        return register(this.http, this.rootUrl, params);
    }

    /**
     * Register a new user
     *
     * This method provides access to only to the response body.
     * To access the full response (for headers, for example), `register$Response()` instead.
     *
     * This method sends `application/json` and handles request body of type `application/json`.
     */
    register(params: Register$Params): Observable<{}> {
        return this.register$Response(params).pipe(
            map((r: StrictHttpResponse<{}>) => r.body as {})
        );
    }

    /**
     * Activate user account
     *
     * This method provides access to the full `HttpResponse`, allowing access to specific headers.
     * To access only the response body, use `confirm()` instead.
     *
     * This method doesn't expect any request body.
     */
    confirm$Response(params: Confirm$Params): Observable<StrictHttpResponse<void>> {
        return confirm(this.http, this.rootUrl, params);
    }

    /**
     * Activate user account
     *
     * This method provides access to only to the response body.
     * To access the full response (for headers, for example), `confirm$Response()` instead.
     *
     * This method doesn't expect any request body.
     */
    confirm(params: Confirm$Params): Observable<void> {
        return this.confirm$Response(params).pipe(
            map((r: StrictHttpResponse<void>) => r.body as void)
        );
    }

}
