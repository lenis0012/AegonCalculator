import { TestBed } from '@angular/core/testing';

import { CalculatorService } from './calculator.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('CalculatorService', () => {
  let service: CalculatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(CalculatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
