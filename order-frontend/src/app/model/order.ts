import { OrderDetail } from './OrderDetail';

export interface Order {
    id: number,
    description: string,
    date: Date,
    employee: string,
    customer: string,
    orderDetails: [OrderDetail];
}
