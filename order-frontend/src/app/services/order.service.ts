import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from 'rxjs';
import {OrderResponse} from "../model/order-response";
import {Order} from "../model/order";
import {OrderRequest} from "../model/order-request";
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly URL: string ;

  constructor(protected httpClient: HttpClient) {
    this.URL = `${environment.backend}/${environment.order_api}`;
  }

  public getPage(page: number, sortField: string, sortDirection: string): Observable<OrderResponse> {
    let urlParams = new HttpParams()
      .set('page', `${page}`)
      .append('sort-field', sortField)
      .append('sort-direction', sortDirection);
    // let options = {params: urlParams, withCredentials: true}
    let options = {params: urlParams}
    return this.httpClient.get<OrderResponse>(`${this.URL}/get`, options);
  }

  public getOrderById(id: number): Observable<OrderResponse> {
    return this.httpClient.get<OrderResponse>(`${this.URL}/get/${id}`);
  }

  public saveOrder(order: Order): Observable<OrderResponse> {
    const request: OrderRequest = new OrderRequest();
    request.order = order;
    return this.httpClient.post<OrderResponse>(`${this.URL}/save`,request);
  }

  public deleteOrder(id: number): Observable<number> {
    return this.httpClient.delete<number>(`${this.URL}/delete/${id}`);
  }

}
