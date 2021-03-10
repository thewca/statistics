import { SyntheticEvent, useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import { getQueryParameter, setQueryParameter } from "../util/query.param.util";
import "./DatabaseQuery.css";

const SQL_QUERY = "sqlQuery";

function DatabaseQuery() {
  const [query, setQuery] = useState("");
  const [queryResults, setQueryResults] = useState<string[][]>([]);
  const [headers, setHeaders] = useState<string[]>([]);
  const [noResult, setNoResult] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    let sqlQuery = getQueryParameter(SQL_QUERY);
    if (!!sqlQuery) {
      setQuery(sqlQuery);
    }
  }, []);

  const handleSubmit = (evt: SyntheticEvent) => {
    // Avoid refreshing the entire page
    evt.preventDefault();

    setQueryParameter(SQL_QUERY, query);

    setError(null);
    statisticsApi.queryDatabase(query).then((response) => {
      let content = response.data.content;
      let headers = response.data.headers;

      if (!content || !headers) {
        setHeaders([]);
        setQueryResults([]);
      } else {
        setNoResult(content.length === 0);
        setHeaders(headers);
        setQueryResults(content);
      }
    });
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="container">
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
                    <th scope="row">{i + 1}</th>
                    {result.map((entry, j) => (
                      <td key={j}>{entry}</td>
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
