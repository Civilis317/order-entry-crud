import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OrderService} from "../services/order.service";
import {Order} from "../model/order";
import {Alert} from "../model/alert";

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {
  order: Order = <Order>{};
  worker: Order = <Order>{};
  alert: Alert = new Alert();
  newOrder: boolean;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let id: number = +params['id']; // (+) converts string 'id' to a number
      if (id) {
        this.newOrder = false;
        this.getOrderDetails(id);
      } else {
        this.newOrder = true;
        this.worker.date = new Date();
      }
    });
  }

  public cancel(): void {
    if (confirm("lose your changes?")) {
      this.copy(this.order, this.worker);
    }
  }

  public save(): void {
    this.copy(this.worker, this.order);
    this.orderService.saveOrder(this.order).subscribe(response => {
      this.order = response.data[0];
      this.copy(this.order, this.worker);
      this.setAlert(Alert.SUCCESS, 'Order saved...');
    })
  }

  private copy(source: Order, target: Order): void {
    target.id = source.id;
    target.description = source.description;
    target.date = source.date;
    target.customer = source.customer;
    target.employee = source.employee;
  }

  private getOrderDetails(id: number): void {
    this.orderService.getOrderById(id).subscribe(response => {
      if (response.count == 1) {
        this.order = response.data[0];
        this.copy(this.order, this.worker);
      } else {
        // throw some error?
      }

    })
  }

  private setAlert(clazz: string, msg: string): void {
    this.alert.clazz = clazz;
    this.alert.msg = msg;
    this.alert.active = true;
    setTimeout(() => {
      this.closeAlert();
    }, 5000);
  }

  public closeAlert(): void {
    this.alert = new Alert();
  }

}
