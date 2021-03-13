import { message, Pagination } from "antd";
import { useState } from "react";
import statisticsApi from "../api/statistics.api";
import { getQueryParameter, setQueryParameter } from "../util/query.param.util";
import "./DatabaseQuery.css";

const SQL_QUERY = "sqlQuery";

function DatabaseQuery() {
  const [query, setQuery] = useState(getQueryParameter(SQL_QUERY) || "");
  const [queryResults, setQueryResults] = useState<string[][]>([]);
  const [headers, setHeaders] = useState<string[]>([]);
  const [noResult, setNoResult] = useState(false);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [totalElements, setTotalElements] = useState<number>(0);

  const handleSubmit = (page: number, pageSize = 0) => {
    // Avoid fetch in the first render
    if (!query) {
      return;
    }

    setQueryParameter(SQL_QUERY, query);

    setError(null);
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
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSubmit(page, pageSize);
        }}
        className="container"
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
        <div className="container-fluid">
          <div className="table-responsive">
            <table className="table table-hover table-striped table-bordered shadow">
              <thead className="thead thead-dark">
                <tr>
                  <th className="text-center" scope="col">
                    #
                  </th>
                  {headers.map((header, i) => (
                    <th key={i} className="text-center" scope="col">
                      {header}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody className="tbody">
                {queryResults.map((result: string[], i) => (
                  <tr key={i}>
                    <th scope="row" className="text-center">
                      {(page - 1) * pageSize + i + 1}
                    </th>
                    {result.map((entry, j) => (
                      <td key={j} className="text-center">
                        {entry}
                      </td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
      {!!error && (
        <div className="alert alert-danger">{JSON.stringify(error)}</div>
      )}
    </div>
  );
}

export default DatabaseQuery;
