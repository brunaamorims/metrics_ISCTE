import { Component, EventEmitter, Output, ViewContainerRef, ViewChild, ComponentFactoryResolver} from '@angular/core';

import { MenuItem, MessageService, PrimeNGConfig } from 'primeng/api';
import { Coluna } from '../../model/coluna.model';
import { Projeto } from '../../model/projeto.model';
import { Repositorio } from '../../model/repositorio.model';
import { ProjetoService } from '../../service/projeto.service';
import { ListaComponent } from '../lista/lista.component';

@Component({
  providers: [MessageService],
  selector: 'menubar',
  templateUrl: './menubar.component.html',
  styleUrls: ['./menubar.component.css']
})

export class MenubarComponent {

  @Output() configuraProjeto = new EventEmitter();

  @ViewChild('dynamic', {read: ViewContainerRef}) viewContainerRef!: ViewContainerRef
  
  displayModal: boolean = false;
  user: string = '';
  token: string = '';
  projetos: Projeto[] = [];
  repositorys: Repositorio[] = [];
  colunas: Coluna[] = [];
  projeto: any;
  repository: any;
  colunato: any;
  colunain: any;
  periodicidades: any;
  periodicidade: any;
  date1!: Date;
  date2!: Date;
  load: boolean = false;

  items: MenuItem[] = [
    {
      label: 'Settings',
      icon: 'pi pi-fw pi-cog',
      command: (event) => {
        this.displayModal = true;
      }  
    },
  ];

  constructor(private primengConfig: PrimeNGConfig, 
    private projetoService : ProjetoService, private messageService: MessageService,
    private componentFactoryResolver: ComponentFactoryResolver) {}

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.periodicidades = [
      {id: '1', value: 'Day'},
      {id: '2', value: 'Week'},
      {id: '3', value: 'Month'}
    ];
    this.primengConfig.setTranslation({
      firstDayOfWeek: 0,
      dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
      dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
      dayNamesMin: ['Do', 'Se', 'Te', 'Qu', 'Qu', 'Se', 'Sa'],
      monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho',
        'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
      monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
      today: 'Hoje',
      clear: 'Limpar'
    });
  }

  showModalDialog() {
    this.displayModal = true;
  }

  getRepositorios() {
    if (this.user !== '' && this.token !== '' &&  this.repositorys.length === 0) {
      this.load = true;
      this.projetoService.getRepositorios(this.user, this.token).subscribe(
        { next: res =>  {
          res.forEach((element: any) => {
            this.repositorys.push({id: element.id, name: element.name});
          })
          this.load = false;
        },
          error: err => {
            console.log(err)
            this.load = false
            this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao listar Repositórios'})
          }
        }
      )
    }
  }


  getProjetos() {
    if (this.user !== '' && this.token !== '' &&  this.projetos.length === 0) {
      this.load = true;
      this.projetoService.getProjetos(this.user, this.token, this.repository.name).subscribe(
        { next: res =>  {
          res.forEach((element: any) => {
            this.projetos.push({id: element.id, value: element.name, repo: element.repo });
          }) 
          this.load = false;
        },
          error: err => {
            console.log(err)
            this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao listar Projetos'})
            this.load = false;
          }
        }
      )

    }
  }

  onChange(event: any) {
    if (this.projeto) {
      this.getColunas()
    }
  }

  getColunas() {
    this.load = true;
    this.projetoService.getColunas(this.projeto.id, this.token).subscribe(
      { next: res =>  {
        let cont = 0;
        res.forEach((element: any) => {
          this.colunas.push({id: element.id, name: element.name, index: cont});
          ++cont;
        })
        const componentFactory = this.componentFactoryResolver.resolveComponentFactory(ListaComponent);
        this.viewContainerRef.clear();
        const dyynamicComponent = <ListaComponent>this.viewContainerRef.createComponent(componentFactory).instance;
        dyynamicComponent.colunas = this.colunas;
        this.load = false;
      },
        error: err => {
          console.log(err)
          this.load = false
          this.messageService.add({severity:'error', summary: 'Erro', detail: 'Erro ao listar Colunas'})
        }
      }
    )
  }

  configurar() {
    let error = '';
    if (this.user === '' ) {
      error += ' Nome do usuário,'
    }
    if (this.token === '') {
      error += ' Token,'
    } 
    if (this.projeto === undefined || this.projeto === null) {
      error += ' Projetos,'
    }
    if (this.repository === undefined || this.repository === null) {
      error += ' Repositorios,'
    }
    if (this.colunato === undefined || this.colunato === null) {
      error += ' Coluna para Iniciar,'
    }
    if (this.colunain === undefined || this.colunain === null) {
      error += ' Coluna Iniciada,'
    }
    if (this.periodicidade === undefined || this.periodicidade === null) {
      error += ' Periodicidade,'
    }
    if (this.date1 === undefined || this.date1 === null) {
      error += ' Data inicial,'
    }
    if (error === '') {
      if (this.date1.getTime() > new Date().getTime()) {
        this.messageService.add({severity:'error', summary: 'Erro', detail: 'A Data inicial é maior que a Data atual!'})
      } else if (this.date2 !== undefined && this.date2 !== null && this.date1.getTime() > this.date2.getTime()) {
        this.messageService.add({severity:'error', summary: 'Erro', detail: 'A Data inicial é maior que a Data final!'})
      } else {
        this.configuraProjeto.emit(
          {'usuario': this.user, 'token': this.token, 'projeto': this.projeto, 'periodicidade': this.periodicidade, 
            'dt_inicial': this.date1, 'dt_final': this.date2, 'colunato': this.colunato, 'colunain': this.colunain, 
            'colunas': this.colunas, 'repository': this.repository}
        );
        this.displayModal = false;
      }      
    } else {
      this.messageService.add({severity:'error', summary: 'Erro', detail: 'Os campos' + error.substring(0, error.length-1) + ' são obrigatórios!'})
    }
  }
  
}