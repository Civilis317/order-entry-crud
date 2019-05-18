import { Component, OnInit } from '@angular/core';
import { OrderService } from '../services/order.service';
import {Order} from '../model/order';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  orderList: [Order];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.list().subscribe(r => {
      console.log(r);
    })
  }

}
