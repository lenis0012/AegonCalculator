export class Expression {
  constructor(public inputOne: number, public operation: Operation, public inputTwo: number) {
  }
}

export class Equation {
  constructor(public inputOne: number, public operation: Operation, public inputTwo: number, public result: number, public valid: boolean) {
  }
}

export class CalculationRequest {
  constructor(public expressions: Expression[]) {
  }
}

export class CalculationResult {
  constructor(public equations: Equation[]) {
  }
}

export enum Operation {
  ADD = 'ADD',
  SUBTRACT = 'SUBTRACT',
  MULTIPLY = 'MULTIPLY',
  DIVIDE = 'DIVIDE'
}

export const OperationLabel = new Map<string, string>([
  [Operation.ADD, '+'],
  [Operation.SUBTRACT, '-'],
  [Operation.MULTIPLY, '*'],
  [Operation.DIVIDE, '/']
]);
