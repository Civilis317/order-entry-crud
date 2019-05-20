import { Injectable } from '@angular/core';
import {OrderListSettings} from "../model/order-list-settings";
import {Observable, Subject} from "rxjs";

@Injectable()
export class StateService {
  private storageMap: Map<string, OrderListSettings> = new Map<string, OrderListSettings>();
  static ORDERLIST_SETTINGS: string = 'ORDERLIST_SETTINGS';

  constructor() { }

  public push(settings: OrderListSettings): void {
    this.storageMap.set(StateService.ORDERLIST_SETTINGS, settings);
  }

  public clear(): void {
    this.storageMap.delete(StateService.ORDERLIST_SETTINGS);
  }

  public get(): OrderListSettings {
    return this.storageMap.get(StateService.ORDERLIST_SETTINGS);
  }




  // settings:  Subject<OrderListSettings>;
  //
  // constructor() {
  //   this.settings = new Subject<OrderListSettings>();
  // }

  // private subject = new Subject<OrderListSettings>();
  //
  // push(settings: OrderListSettings) {
  //   this.subject.next(settings);
  // }
  //
  // clearclear() {
  //   this.subject.next();
  // }
  //
  // get(): Observable<OrderListSettings> {
  //   return this.subject.asObservable();
  // }


}
