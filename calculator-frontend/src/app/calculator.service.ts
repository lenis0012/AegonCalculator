import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams, HttpResponse} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import {CalculationRequest, CalculationResult, Expression} from './model';

@Injectable({
  providedIn: 'root'
})
export class CalculatorService {
  constructor(private http: HttpClient) {}

  calculate(expressions: Expression[]): Observable<HttpResponse<CalculationResult>> {
    const request = new CalculationRequest(expressions);

    return this.http.post<CalculationResult>('/api/calculate', request, {
      observe: 'response'
    });
  }
}
