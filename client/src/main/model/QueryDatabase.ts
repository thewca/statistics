interface PageResponse {
  number: number; // Current page
  numberOfElements: number; // Elements in the current page
  size: number; // Current page size
  totalElements: number; // Total count
  totalPages: number;
  hasContent: boolean;
  hasNextPage: boolean;
  hasPreviousPage: boolean;
  firstPage: boolean;
  lastPage: boolean;
}

export interface QueryDatabaseResponse extends PageResponse {
  content: string[][];
  headers: string[];
}
