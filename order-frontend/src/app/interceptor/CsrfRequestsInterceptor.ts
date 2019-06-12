import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpXsrfTokenExtractor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class CsrfRequestsInterceptor implements HttpInterceptor {
  constructor(private tokenExtractor: HttpXsrfTokenExtractor) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let requestToForward = req;
    let token = this.tokenExtractor.getToken() as string;

    if (token !== null) {
      requestToForward = req.clone({ setHeaders: { 'X-XSRF-TOKEN': token } });
    }
    return next.handle(requestToForward);
  }
}
