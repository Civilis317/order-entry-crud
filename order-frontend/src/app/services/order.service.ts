import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

import { Observable } from 'rxjs';

import {Order} from '../model/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly URL: string = "http://localhost:9094/order-service/secured/api/order";

  constructor(protected httpClient: HttpClient) { }

  public list(): Observable<Array<Order>> {
    return this.httpClient.get<Array<Order>>(`${this.URL}/list`)
  }

}
