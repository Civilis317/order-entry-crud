import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from 'rxjs';
import {OrderResponse} from "../model/order-response";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly URL: string = "http://localhost:9094/order-service/secured/api/order";

  constructor(protected httpClient: HttpClient) {
  }

  public list(): Observable<OrderResponse> {
    return this.httpClient.get<OrderResponse>(`${this.URL}/get`)
  }

  public getPage(page: number, sortField: string, sortDirection: string): Observable<OrderResponse> {
    let urlParams = new HttpParams()
      .set('page', `${page}`)
      .append('sort-field', sortField)
      .append('sort-direction', sortDirection);
    let options = {params: urlParams}
    return this.httpClient.get<OrderResponse>(`${this.URL}/get`, options);
  }

}
