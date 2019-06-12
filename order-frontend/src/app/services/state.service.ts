import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable()
export class StateService {
  private storageMap = new Map<string, any>();
  public static ORDERLIST_SETTINGS = 'ORDERLIST_SETTINGS';

  private displayName: Subject<string> = new Subject<string>();

  public pushDisplayname(displayName: string): void {
    this.displayName.next(displayName);
  }

  public clearDisplayname(): void {
    this.displayName.next();
  }

  public getDisplayname(): Observable<string> {
    return this.displayName.asObservable();
  }

  public push(key: string, value: any): void {
    this.storageMap.set(key, value);
  }

  public remove(key: string): void {
    this.storageMap.delete(key);
  }

  public clear(): void {
    this.storageMap.clear();
  }

  public pop(key: string): any {
    return this.storageMap.get(key);
  }

}
