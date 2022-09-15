import { AfterViewInit, Component, ComponentFactoryResolver, ViewChild, ViewContainerRef } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ChartComponent } from './components/chart/chart.component';
import { BackService } from './service/back.service';

@Component({
  providers: [MessageService],
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements AfterViewInit {
  
  @ViewChild("wip", { read: ViewContainerRef }) wip!: ViewContainerRef;
  @ViewChild("cfd", { read: ViewContainerRef }) cfd!: ViewContainerRef;
  @ViewChild("thr", { read: ViewContainerRef }) thr!: ViewContainerRef;
  @ViewChild("lea", { read: ViewContainerRef }) lea!: ViewContainerRef;
  @ViewChild("cyc", { read: ViewContainerRef }) cyc!: ViewContainerRef;

  load: boolean = false;

  constructor(private resolver: ComponentFactoryResolver, private backService : BackService, private messageService: MessageService) {}

  ngAfterViewInit() {
    setTimeout(()=> {
      this.wip.clear();
      const factory = this.resolver.resolveComponentFactory(ChartComponent);
      const componentRefWip = this.wip.createComponent(factory);
      componentRefWip.instance.type = 'bar';
      componentRefWip.instance.toast = false;
      componentRefWip.instance.data = {};
      this.cfd.clear();
      const componentRefCfd = this.cfd.createComponent(factory);
      componentRefCfd.instance.type = 'line';
      componentRefCfd.instance.toast = true;
      componentRefCfd.instance.data = {};
      this.thr.clear();
      const componentRefThr = this.thr.createComponent(factory);
      componentRefThr.instance.type = 'bar';
      componentRefThr.instance.toast = false;
      componentRefThr.instance.data = {};
      this.lea.clear();
      const componentRefLea = this.lea.createComponent(factory);
      componentRefLea.instance.type = 'scatter';
      componentRefLea.instance.toast = true;
      componentRefLea.instance.data = {};
      this.cyc.clear();
      const componentRefCyc = this.cyc.createComponent(factory);
      componentRefCyc.instance.type = 'line';
      componentRefCyc.instance.toast = true;
      componentRefCyc.instance.data = {};
    }
    ,100);  
  }

  configura(resposta: any) {
    this.configuraWip(resposta);
    this.configuraCfd(resposta);
    this.configuraThr(resposta);
    this.configuraLea(resposta);
    this.configuraCyc(resposta);
  }

  configuraCyc(resposta: any) {
    this.load = true;
    this.backService.getGrafico(resposta, 'cyc').subscribe(
      { next: res =>  {
        this.cyc.clear();
        const factory = this.resolver.resolveComponentFactory(ChartComponent);
        const componentRefCyc = this.cyc.createComponent(factory);
        componentRefCyc.instance.type = 'line';
        componentRefCyc.instance.toast = true;
        componentRefCyc.instance.data = res;
        this.load = false
      },
        error: err => {
          console.log(err)
          this.load = false
          this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao configurar o gráfico Cycle Time!'})
        }
      }
    ) 
  }

  configuraLea(resposta: any) {
    this.load = true;
    this.backService.getGrafico(resposta, 'lea').subscribe(
      { next: res =>  {
        this.lea.clear();
        const factory = this.resolver.resolveComponentFactory(ChartComponent);
        const componentRefLea = this.lea.createComponent(factory);
        componentRefLea.instance.type = 'scatter';
        componentRefLea.instance.toast = true;
        componentRefLea.instance.data = res;
        this.load = false
      },
        error: err => {
          console.log(err)
          this.load = false
          this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao configurar o gráfico Lead Time!'})
        }
      }
    ) 
  }

  configuraThr(resposta: any) {
    this.load = true;
    this.backService.getGrafico(resposta, 'thr').subscribe(
      { next: res =>  {
        this.thr.clear();
        const factory = this.resolver.resolveComponentFactory(ChartComponent);
        const componentRefThr = this.thr.createComponent(factory);
        componentRefThr.instance.type = 'bar';
        componentRefThr.instance.toast = false;
        componentRefThr.instance.data = res;
        this.load = false
      },
        error: err => {
          console.log(err)
          this.load = false
          this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao configurar o gráfico Throughput!'})
        }
      }
    ) 
  }

  configuraCfd(resposta: any) {
    this.load = true;
    this.backService.getGrafico(resposta, 'cfd').subscribe(
      { next: res =>  {
        this.cfd.clear();
        const factory = this.resolver.resolveComponentFactory(ChartComponent);
        const componentRefCfd = this.cfd.createComponent(factory);
        componentRefCfd.instance.type = 'line';
        componentRefCfd.instance.toast = true;
        componentRefCfd.instance.data = res;
        this.load = false
      },
        error: err => {
          console.log(err)
          this.load = false
          this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao configurar o gráfico Cumulative Flow Diagram!'})
        }
      }
    ) 
  }

  configuraWip(resposta: any) {
    this.load = true;
    this.backService.getGrafico(resposta, 'wip').subscribe(
      { next: res =>  {
        this.wip.clear();
        const factory = this.resolver.resolveComponentFactory(ChartComponent);
        const componentRefWip = this.wip.createComponent(factory);
        componentRefWip.instance.type = 'bar';
        componentRefWip.instance.toast = false;
        componentRefWip.instance.data = res; 
        this.load = false
      },
        error: err => {
          console.log(err)
          this.load = false
          this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao configurar o gráfico Work in progress!'})
        }
      }
    ) 
  }

}
