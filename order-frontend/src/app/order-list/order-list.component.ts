import {Component, OnInit} from '@angular/core';
import {OrderService} from '../services/order.service';
import {Order} from '../model/order';
import {OrderListSettings} from "../model/order-list-settings";
import {StateService} from "../services/state.service";

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {
  dataSource: Order[];
  totalPages: number;
  pageButtons: number[];
  settings: OrderListSettings;

  constructor(
    private orderService: OrderService,
    private stateService: StateService
  ) {
  }

  ngOnInit() {
    this.settings = this.stateService.get();
    if (!this.settings) {
      console.log("no settings found, creating new")
      this.settings = new OrderListSettings();
      this.settings.page = 1;
      this.settings.sortField = 'description';
      this.settings.sortDirection = 'asc';
    }
    this.retrievePage();
  }

  private retrievePage() {
    this.orderService.getPage(this.settings.page,
      this.settings.sortField,
      this.settings.sortDirection
    ).subscribe(response => {
      this.dataSource = response.data;
      this.totalPages = response.totalPages;
      this.calculatePagination();

      this.settings.page = response.page;

      console.log(`pushing: ${this.settings.page}, ${this.settings.sortDirection}`)

      this.stateService.push(this.settings);

    })
  }

  public select(id: number): void {
    console.log(`selected order: ${id}`);
  }

  public delete(id: number): void {
    if (confirm("are you sure?")) {
      console.log(`delete called for order: ${id}`);
    }
    event.stopPropagation();
  }

  public requestPage(pageRequest: number): void {
    this.settings.page = pageRequest;
    this.retrievePage();
  }

  public requestSort(field: string): void {
    console.log(`sort requested on field: ${field}`);
    this.settings.sortField = field;

    if (!this.settings.sortDirection){
      this.settings.sortDirection = 'asc';

    } else if (this.settings.sortDirection == 'asc') {
      this.settings.sortDirection = 'desc';
    } else {
      this.settings.sortDirection = 'asc';
    }

    this.retrievePage()

  }

  private calculatePagination(): void {
    // todo: something smarter than 1 button per page
    this.pageButtons = [];
    let i: number = 1;
    while (i <= this.totalPages) {
      this.pageButtons.push(i);
      i++;
    }
  }

}
