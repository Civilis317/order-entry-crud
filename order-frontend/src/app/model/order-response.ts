import {Order} from "./order";

export interface OrderResponse {
  count: number,
  page: number,
  totalCount: number,
  totalPages: number,
  pageSize: number
  data: Order[]
}
