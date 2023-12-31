import {Injectable} from '@angular/core';
import {TokenStorageService} from '../service/token-storage.service';
import {
  HTTP_INTERCEPTORS,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Observable} from 'rxjs';

const TOKEN_HEADER_KEY = 'Authorization';

/**
 * HttpInterceptor has intercept() method to tranform HTTP requests before they sent to server.
 */
@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private tokenService: TokenStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authRequest = req;
    const token = this.tokenService.getToken();
    if (token != null) {
      authRequest = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, token)});
    }
    return next.handle(authRequest);
  }
}

export const authInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
];
