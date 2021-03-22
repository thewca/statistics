import { message, Pagination } from "antd";
import { useState } from "react";
import statisticsApi from "../api/statistics.api";
import { getQueryParameter, setQueryParameter } from "../util/query.param.util";
import "./DatabaseQuery.css";
import StatisticsTable from "./StatisticsTable";

const SQL_QUERY = "sqlQuery";

function DatabaseQuery() {
  const [query, setQuery] = useState(getQueryParameter(SQL_QUERY) || "");
  const [queryResults, setQueryResults] = useState<string[][]>([]);
  const [headers, setHeaders] = useState<string[]>([]);
  const [noResult, setNoResult] = useState(false);
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [totalElements, setTotalElements] = useState<number>(0);
  const [lastSearchedQuery, setLastSearchedQuery] = useState<string>();

  const handleSubmit = (page: number, pageSize = 0) => {
    // Avoid fetch in the first render
    if (!query) {
      return;
    }

    setQueryParameter(SQL_QUERY, query);

    // Resets page if it's a new query
    if (query !== lastSearchedQuery) {
      page = 1;
      setPage(1);
    }
    setLastSearchedQuery(query);

    statisticsApi
      .queryDatabase(query, page - 1, pageSize)
      .then((response) => {
        let content = response.data.content;
        let headers = response.data.headers;

        setNoResult(content.length === 0);
        setHeaders(headers);
        setQueryResults(content);
        setTotalElements(response.data.totalElements);
      })
      .catch((e) => message.error(e.response?.data?.message || "Error"));
  };

  const handlePaginationChange = (newPage: number, newSize?: number) => {
    if (newSize !== pageSize) {
      newPage = 1;
    }
    setPage(newPage);
    setPageSize((oldSize) => newSize || oldSize);

    handleSubmit(newPage, newSize);
  };

  return (
    <div className="container">
      <h1 className="page-title">Database Query</h1>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSubmit(page, pageSize);
        }}
      >
        <div className="form-group">
          <textarea
            className="form-control my-3 shadow"
            onChange={(evt) => setQuery(evt.target.value)}
            value={query}
            placeholder="Type your query here"
            rows={10}
            id="query-input"
          />
          <div className="text-center">
            <button
              className="btn btn-primary btn-lg"
              disabled={!query}
              title={!query ? "You need to provide an SQL query" : ""}
            >
              Submit
            </button>
          </div>
        </div>
      </form>
      {noResult && <div className="alert alert-info">No results to show</div>}
      {totalElements > 0 && (
        <div className="my-3 text-center">
          <Pagination
            defaultPageSize={pageSize}
            current={page}
            total={totalElements}
            onChange={handlePaginationChange}
          />
        </div>
      )}
      {queryResults.length > 0 && (
        <StatisticsTable
          headers={headers}
          content={queryResults}
          page={page}
          pageSize={pageSize}
        />
      )}
    </div>
  );
}

export default DatabaseQuery;
