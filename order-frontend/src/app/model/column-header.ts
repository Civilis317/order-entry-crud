export class ColumnHeader {
  value: string;
  size: string;
  sortField: string;
  sortClass: string;

  constructor(value: string, size: string, sortField: string, sortClass: string) {
    this.value = value;
    this.size = size;
    this.sortField = sortField;
    this.sortClass = sortClass;
  }
}
