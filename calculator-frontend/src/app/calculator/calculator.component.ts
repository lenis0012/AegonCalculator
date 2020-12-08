import {Component, OnInit} from '@angular/core';
import { CalculatorService } from '../calculator.service';
import {Observable, ObservableInput, Subject, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  inputOne = 0;
  operation = 'add';
  inputTwo = 0;
  result$: Observable<HttpResponse<number>>;
  calculationError$ = new Subject<boolean>();

  constructor(private calculatorService: CalculatorService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const handleError = catchError<HttpResponse<number>, ObservableInput<HttpResponse<number>>>((err => {
      this.calculationError$.next(true);
      return throwError(err);
    }));

    switch (this.operation) {
      case 'add':
        this.result$ = this.calculatorService.add(Math.round(this.inputOne), Math.round(this.inputTwo)).pipe(handleError);
        break;
      case 'subtract':
        this.result$ = this.calculatorService.subtract(Math.round(this.inputOne), Math.round(this.inputTwo)).pipe(handleError);
        break;
      case 'multiply':
        this.result$ = this.calculatorService.multiply(Math.round(this.inputOne), Math.round(this.inputTwo)).pipe(handleError);
        break;
      case 'divide':
        this.result$ = this.calculatorService.divide(Math.round(this.inputOne), Math.round(this.inputTwo)).pipe(handleError);
        break;
    }
  }
}
