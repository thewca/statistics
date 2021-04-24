import { QueryDatabaseResponse } from "../main/model/QueryDatabase";

let columns = Math.floor(Math.random() * 10) + 1;
let rows = Math.floor(Math.random() * 20) + 1;
export const defaultQueryResponse: QueryDatabaseResponse = {
  headers: Array.from({ length: columns }, (_, id) => "Header " + id),
  content: Array.from({ length: rows }, (_, i) =>
    Array.from({ length: columns }, (_, j) => `Result ${i} ${j}`)
  ),
  number: 0,
  numberOfElements: 20,
  size: 20,
  totalElements: 109819,
  totalPages: 5491,
  hasContent: true,
  hasNextPage: true,
  hasPreviousPage: false,
  firstPage: true,
  lastPage: false,
};
