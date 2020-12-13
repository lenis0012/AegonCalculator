import {Component, OnInit} from '@angular/core';
import {CalculatorService} from '../calculator.service';
import {Observable, Subject, throwError} from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {Expression, Operation, CalculationResult, OperationLabel} from '../model';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  expressions: Expression[] = [
    new Expression(0, Operation.ADD, 0)
  ];
  result$: Observable<HttpResponse<CalculationResult>>;
  calculationError$ = new Subject<boolean>();
  operationLabels = OperationLabel;

  constructor(private calculatorService: CalculatorService) { }

  ngOnInit(): void {
    this.result$ = this.calculatorService.getEquations();
  }

  addOption(): void {
    this.expressions.push(new Expression(0, Operation.ADD, 0));
  }

  onSubmit(): void {
    this.result$ = this.calculatorService.calculate(this.expressions)
      .pipe(catchError((err) => {
        this.calculationError$.next(true);
        return throwError(err);
      }));
  }
}
