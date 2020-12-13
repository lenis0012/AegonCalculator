import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CalculatorComponent} from './calculator.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {FormsModule} from '@angular/forms';
import {Type} from '@angular/core';
import {Operation} from '../model';

describe('CalculatorComponent', () => {
  let component: CalculatorComponent;
  let fixture: ComponentFixture<CalculatorComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalculatorComponent ],
      imports: [ HttpClientTestingModule, FormsModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CalculatorComponent);
    component = fixture.componentInstance;
    httpMock = fixture.debugElement.injector.get<HttpTestingController>(HttpTestingController as Type<HttpTestingController>);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    httpMock.expectOne('/api/equations');
  });

  it('should call the api when enterring form', () => {
    component.expressions[0].operation = Operation.ADD;
    component.expressions[0].inputOne = 1;
    component.expressions[0].inputTwo = 2;

    component.onSubmit();
    fixture.detectChanges();

    httpMock.expectOne(req => req.url.includes('/api/equations') && req.method === 'GET');
    const request = httpMock.expectOne(req => req.url.includes('/api/equations') && req.method === 'POST');
    expect(request.request.body.expressions[0].inputOne).toBe(1);
    expect(request.request.body.expressions[0].operation).toBe('ADD');
    expect(request.request.body.expressions[0].inputTwo).toBe(2);
    request.flush({equations: []});
  });
});
