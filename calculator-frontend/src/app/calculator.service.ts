import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CalculatorService {
  constructor(private http: HttpClient) {}

  add(a: number, b: number): Observable<HttpResponse<number>> {
    return this.request('/api/add', a, b);
  }

  subtract(a: number, b: number): Observable<HttpResponse<number>> {
    return this.request('/api/subtract', a, b);
  }

  multiply(a: number, b: number): Observable<HttpResponse<number>> {
    return this.request('/api/multiply', a, b);
  }

  divide(a: number, b: number): Observable<HttpResponse<number>> {
    return this.request('/api/divide', a, b);
  }

  private request(url: string, a: number, b: number): Observable<HttpResponse<number>> {
    const params = new HttpParams()
      .set('a', String(a))
      .set('b', String(b));

    return this.http.get<number>(url, {
      params,
      observe: 'response'
    });
  }
}
