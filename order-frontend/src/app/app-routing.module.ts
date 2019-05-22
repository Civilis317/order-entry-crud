import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OrderListComponent } from './order-list/order-list.component';
import {ElementComponent} from "./element/element.component";
import {OrderDetail} from "./model/OrderDetail";
import {OrderDetailComponent} from "./order-detail/order-detail.component";

const routes: Routes = [
  {path: 'overview', component: OrderListComponent},
  {path: 'details', component: OrderDetailComponent},
  {path: 'details/:id', component: OrderDetailComponent},
  {path: 'elements', component: ElementComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
