import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OrderListComponent } from './order-list/order-list.component';
import {ElementComponent} from "./element/element.component";

const routes: Routes = [
  {path: 'overview', component: OrderListComponent},
  {path: 'elements', component: ElementComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
