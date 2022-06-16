export interface PageResponse<T> {
  page: number;
  pageSize: number;
  content: T[];
}
