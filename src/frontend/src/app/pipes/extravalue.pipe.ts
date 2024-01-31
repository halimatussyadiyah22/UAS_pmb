import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ExtractArrayValue'
})
export class ExtractArrayValue implements PipeTransform {

  transform(value: any, args: string): any {
    let total: number = 0;
    if (args === 'number') {
      let numberArray: number[] = [];
      for (let i = 0; i < value; i++) {
        numberArray.push(i);
      }
      return numberArray;
    }
    if (args === 'cards') {
      value.forEach(card => {
        total += card.total;
      })
      return total.toFixed(2);
    }
    return 0;
  }
}
