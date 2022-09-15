import { Component, Input } from '@angular/core';
import { MessageService } from 'primeng/api';


@Component({
  providers: [MessageService],
  selector: 'chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent {

  @Input() type!: string;
  @Input() data: any;
  @Input() toast: boolean = true;

  constructor(private messageService: MessageService) {}

  selectData(event: any) {
    if (this.toast) {
      let dado = this.data.datasets[event.element.datasetIndex].data[event.element.index];
      if (this.type === 'scatter') {
        this.messageService.add({severity: 'info', summary: 'Dados selecionados', 'detail': 'x: ' + dado.x + ' y: ' + dado.y });
      } else {
        this.messageService.add({severity: 'info', summary: 'Dados selecionados', 'detail': dado});
      }
    }
  }
  
}