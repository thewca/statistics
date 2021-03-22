interface StatisticsTableProps {
  headers: string[];
  content: string[][];
  page?: number;
  pageSize?: number;
}

const StatisticsTable = ({
  content,
  headers,
  page = 0,
  pageSize = 0,
}: StatisticsTableProps) => {
  return (
    <div className="container">
      <div className="table-responsive">
        <table className="table table-hover table-striped table-bordered shadow table-condensed">
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
            {content.map((result: string[], i) => (
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
  );
};

export default StatisticsTable;
