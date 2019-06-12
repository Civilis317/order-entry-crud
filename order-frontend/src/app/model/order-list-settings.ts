import {ColumnHeader} from "./column-header";

export class OrderListSettings {
  page: number;
  sortField: string;
  sortDirection: string;
  columnHeaders: ColumnHeader[];
}
