import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {CalculationRequest, CalculationResult, Expression} from './model';

@Injectable({
  providedIn: 'root'
})
export class CalculatorService {
  constructor(private http: HttpClient) {}

  calculate(expressions: Expression[]): Observable<HttpResponse<CalculationResult>> {
    const request = new CalculationRequest(expressions);

    return this.http.post<CalculationResult>('/api/equations', request, {
      observe: 'response'
    });
  }

  getEquations(): Observable<HttpResponse<CalculationResult>> {
    return this.http.get<CalculationResult>('/api/equations', {
      observe: 'response'
    });
  }
}
