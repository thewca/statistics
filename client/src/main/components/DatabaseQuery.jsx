import React, { useState } from "react";
import { queryDatabase } from "../api/statistics.api";

function DatabaseQuery() {
  const [query, setQuery] = useState("select * from Competitions limit 10");
  const [queryResults, setQueryResults] = useState([]);
  const [headers, setHeaders] = useState([]);
  const [noResult, setNoResult] = useState(false);
  const [error, setError] = useState(null);

  const handleQueryChange = (evt) => {
    setQuery(evt.target.value);
  };

  const handleSubmit = () => {
    setError(null);
    queryDatabase(query).then((response) => {
      let content = response.content;
      let headers = response.headers;

      if (!content || !headers) {
        setError(response);
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
            className="form-control my-3"
            onChange={handleQueryChange}
            value={query}
            placeholder="Type your query here"
          />
          <div className="text-center">
            <button className="btn btn-primary">Query</button>
          </div>
        </div>
      </form>
      {noResult && <div className="alert alert-info">No results to show</div>}
      {queryResults.length > 0 && (
        <div className="container-fluid">
          <div className="table-responsive">
            <table className="table table-hover table-striped table-bordered">
              <thead className="thead thead-dark">
                <tr>
                  <th scope="col">#</th>
                  {headers.map((header, i) => (
                    <th key={i} className="text-center" scope="col">
                      {header}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody className="tbody">
                {queryResults.map((result, i) => (
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
