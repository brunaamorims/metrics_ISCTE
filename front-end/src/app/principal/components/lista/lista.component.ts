import { Component, Input } from '@angular/core';
import { Coluna } from '../../model/coluna.model';

@Component({
  selector: 'lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})

export class ListaComponent {

  @Input() colunas!: Coluna[];

  constructor() {}

  ngOnInit() {}

  onReorder() {
    let cont = 0;
    this.colunas.forEach((element: any) => {
      element.index = cont;
      ++cont;
    })
  }
  
}