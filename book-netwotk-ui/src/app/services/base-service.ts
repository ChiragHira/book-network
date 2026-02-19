import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration } from './api-configuration';

/**
 * Base class for services
 */
@Injectable()
export class BaseService {
    constructor(
        protected config: ApiConfiguration,
        protected http: HttpClient
    ) {
    }

    private _rootUrl?: string;

    /**
     * Returns the root url for API operations. If not set directly here,
     * will fallback to `ApiConfiguration.rootUrl`.
     */
    get rootUrl(): string {
        return this._rootUrl || this.config.rootUrl;
    }

    /**
     * Sets the root URL for API operations
     */
    set rootUrl(rootUrl: string) {
        this._rootUrl = rootUrl;
    }
}
