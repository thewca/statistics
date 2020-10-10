import React, { useEffect, useState } from "react";
import { queryDatabase } from "../api/statistics.api";

function DatabaseQuery() {
  const [query, setQuery] = useState(
    "select * from Persons where id = '2015CAMP17'"
  );
  const [queryResults, setQueryResults] = useState([]);
  const [headers, setHeaders] = useState([]);

  const handleQueryChange = (evt) => {
    setQuery(evt.target.value);
  };

  const handleSubmit = () => {
    queryDatabase(query).then((response) => {
      setHeaders(response.headers);
      setQueryResults(response.content);
    });
  };

  // TODO remove. This is for development only
  useEffect(() => {
    handleSubmit();
  }, []);

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
      {queryResults.length > 0 && (
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
      )}
    </div>
  );
}

export default DatabaseQuery;
