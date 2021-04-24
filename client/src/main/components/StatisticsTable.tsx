import { Table } from "antd";

interface StatisticsTableProps {
  headers: string[];
  content: string[][];
  page?: number;
  pageSize?: number;
  allowInnerHTML: boolean;
  positionTieBreakerIndex?: number;
  showPositions: boolean;
}

const StatisticsTable = ({
  content,
  headers,
  page = 0,
  pageSize = 0,
  allowInnerHTML = false,
  showPositions,
  positionTieBreakerIndex,
}: StatisticsTableProps) => {
  // Destructure list of strings and make it into object bu assgning [a, b] to {0:a, 1:b}
  // Those numbers are used in the table as dataIndex
  let dataSource = content.map((it, pos) =>
    showPositions
      ? {
          ...[
            positionTieBreakerIndex == null ||
            pos === 0 ||
            content[pos][positionTieBreakerIndex] !==
              content[pos - 1][positionTieBreakerIndex]
              ? (page - 1) * pageSize + pos + 1
              : "-",
            ...it,
          ],
        }
      : { ...it }
  );
  let columns = (showPositions ? ["#", ...headers] : headers).map(
    (title, i) => ({
      title,
      key: i,
      dataIndex: i,
      render: (item: string) => {
        return allowInnerHTML ? (
          <span dangerouslySetInnerHTML={{ __html: item }}></span>
        ) : (
          item
        );
      },
    })
  );
  return <Table columns={columns} dataSource={dataSource} pagination={false} />;
};

export default StatisticsTable;
