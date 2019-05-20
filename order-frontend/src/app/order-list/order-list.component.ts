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
    // see if state was stored
    this.settings = this.stateService.get();
    if (!this.settings) {
      // create new settings for the order table
      this.settings = new OrderListSettings();
      this.settings.page = 1;
      this.settings.sortField = 'description';
      this.settings.sortDirection = 'asc';
    }
    this.retrievePage();
  }

  /**
   * Call orderService to retrieve new paged data
   */
  private retrievePage() {
    this.orderService.getPage(this.settings.page,
      this.settings.sortField,
      this.settings.sortDirection
    ).subscribe(response => {
      this.dataSource = response.data;
      this.totalPages = response.totalPages;
      this.calculatePagination();

      // save this state
      this.stateService.push(this.settings);
    })
  }

  /**
   * target for the row-level click
   * purpose is to select an order from the list by its id
   * @param id
   */
  public select(id: number): void {
    console.log(`selected order: ${id}`);
  }

  /**
   * target for the delete-order button
   * @param id
   */
  public delete(id: number): void {
    if (confirm("are you sure?")) {
      console.log(`delete called for order: ${id}`);
    }
    event.stopPropagation();
  }

  /**
   * target for the pagination buttons
   * @param pageRequest
   */
  public requestPage(pageRequest: number): void {
    this.settings.page = pageRequest;
    this.retrievePage();
  }

  /**
   * target for the sort click actions
   * todo: keep track of direction per field
   * probably some headerField-object must be developed for this
   * @param field
   */
  public requestSort(field: string): void {
    this.settings.sortField = field;

    // toggle direction
    if (!this.settings.sortDirection){
      this.settings.sortDirection = 'asc';

    } else if (this.settings.sortDirection == 'asc') {
      this.settings.sortDirection = 'desc';
    } else {
      this.settings.sortDirection = 'asc';
    }

    this.retrievePage()

  }

  /**
   * to create an array of pagination buttons.
   * for now it is an array of (page-)numbers.
   * should probably be reworked into an pagination button object
   */
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
