import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { PanelModule } from 'primeng/panel';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { AppComponent } from './app.component';
import { ToastModule } from 'primeng/toast';
import { PrincipalComponent } from './principal/principal.component';
import { InputTextModule } from 'primeng/inputtext';
import { ChartModule } from 'primeng/chart';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { CalendarModule } from 'primeng/calendar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { OrderListModule } from 'primeng/orderlist';
import { ChartComponent } from './principal/components/chart/chart.component';
import { MenubarComponent } from './principal/components/menubar/menubar.component';
import { ListaComponent } from './principal/components/lista/lista.component';

@NgModule({
  declarations: [
    AppComponent,
    PrincipalComponent,
    MenubarComponent,
    ChartComponent,
    ListaComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule,
    PanelModule,
    MenubarModule,
    ButtonModule,
    InputTextModule,
    ToastModule,
    ChartModule,
    DialogModule,
    CalendarModule,
    DropdownModule,
    OrderListModule,
    ProgressSpinnerModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
