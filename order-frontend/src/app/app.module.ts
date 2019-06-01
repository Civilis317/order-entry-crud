import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {OrderListComponent} from './order-list/order-list.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatInputModule,
  MatPaginatorModule,
  MatTableModule
} from "@angular/material";
import {FormsModule} from "@angular/forms";
import { ElementComponent } from './element/element.component';
import { OcticonDirective } from './octicon-directive';
import {OrderService} from "./services/order.service";
import {StateService} from "./services/state.service";
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    OrderListComponent,
    ElementComponent,
    OcticonDirective,
    OrderDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    AngularFontAwesomeModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [StateService, OrderService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
