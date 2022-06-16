import { message, Pagination, Select, Skeleton, Space, Tooltip } from "antd";
import { useCallback, useEffect, useState } from "react";
import { sumOfRanksApi } from "../../api/SumOfRanksApi";
import { MetaSorInfo } from "../../model/rank/MetaSorInfo";
import { SumOfRanks } from "../../model/rank/SumOfRanks";
import { getPersonLink } from "../../util/WcaUtil";
import styles from "./SumOfRanksPage.module.css";

const { Option, OptGroup } = Select;

const INITIAL_PAGE_SIZE = 20;

export const SumOfRanksPage = () => {
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(INITIAL_PAGE_SIZE);
  const [totalSize, setTotalSize] = useState<number>();
  const [ranks, setRanks] = useState<SumOfRanks[]>([]);
  const [loading, setLoading] = useState(false);
  const [metaSor, setMetaSor] = useState<MetaSorInfo[]>([]);
  const [resultType, setResultType] = useState<string>();
  const [selectedRegionGroup, setSelectedRegionGroup] = useState<string>();
  const [regionType, setRegionType] = useState<string>();
  const [region, setRegion] = useState<string>();

  const fetchMetaInfor = useCallback(() => {
    sumOfRanksApi
      .meta()
      .then((response) => {
        setMetaSor(response.data);
        setResultType(response.data[0].resultType);
        setSelectedRegionGroup(
          `${response.data[0].regionGroups[0].regionType}-${response.data[0].regionGroups[0].regions[0].region}`
        );
      })
      .catch(() => {
        message.error("Error searching for Sum of Ranks information");
      });
  }, []);

  const fetchSumOfRanks = useCallback(
    (
      resultType: string,
      regionType: string,
      region: string,
      page: number,
      pageSize: number
    ) => {
      setLoading(true);
      sumOfRanksApi
        .listSumOfRanks(resultType, regionType, region, page, pageSize)
        .then((response) => {
          setRanks(response.data);
          setPage(page);
          setPageSize(pageSize);
        })
        .finally(() => setLoading(false));
    },
    []
  );

  useEffect(fetchMetaInfor, [fetchMetaInfor]);

  useEffect(() => {
    if (!resultType || !selectedRegionGroup) {
      return;
    }
    let [regionType, region] = selectedRegionGroup.split("-");
    setRegion(region);
    setRegionType(regionType);

    let newTotal = metaSor
      .find((m) => m.resultType === resultType)
      ?.regionGroups.find((r) => r.regionType === regionType)
      ?.regions.find((r) => r.region === region)?.totalSize;
    setTotalSize(newTotal);

    fetchSumOfRanks(resultType, regionType, region, 0, INITIAL_PAGE_SIZE);
  }, [metaSor, resultType, selectedRegionGroup, fetchSumOfRanks]);

  return (
    <>
      <h1 className="page-title">Sum of Ranks</h1>
      <Space direction="vertical" className={styles.contentContainer}>
        <div>
          <Select
            className={styles.regionTypes}
            value={resultType}
            onChange={setResultType}
            options={metaSor.map((r) => ({
              label: r.resultType,
              value: r.resultType,
            }))}
          />
          <Select
            className={styles.regionTypes}
            value={selectedRegionGroup}
            onChange={setSelectedRegionGroup}
            optionFilterProp="children"
            showSearch
          >
            {metaSor
              .find((r) => r.resultType === resultType)
              ?.regionGroups.map((g) => (
                <OptGroup key={g.regionType}>
                  {g.regions.map((r) => (
                    <Option
                      key={r.region}
                      value={`${g.regionType}-${r.region}`}
                    >
                      {r.region}
                    </Option>
                  ))}
                </OptGroup>
              ))}
          </Select>
        </div>
        <Skeleton loading={loading} />

        {!loading && (
          <>
            <div>
              <Pagination
                total={totalSize}
                pageSize={pageSize}
                current={page + 1}
                showQuickJumper
                onChange={(p, s) => {
                  fetchSumOfRanks(resultType!, regionType!, region!, p - 1, s);
                }}
              />
            </div>
            <div className={styles.ranksTable}>
              <table>
                <thead>
                  <tr>
                    <th>Rank</th>
                    <th>Person</th>
                    <th>Overall</th>
                    {metaSor
                      .find((m) => m.resultType === resultType)
                      ?.availableEvents.map((e) => (
                        <th key={e.id}>
                          <Tooltip title={e.name}>
                            <span className={`cubing-icon event-${e.id}`} />
                          </Tooltip>
                        </th>
                      ))}
                  </tr>
                </thead>
                <tbody>
                  {ranks.map((r, i) => (
                    <tr key={r.wcaId}>
                      <th>
                        {i === 0 || ranks[i].overall !== ranks[i - 1].overall
                          ? r.regionRank
                          : "-"}
                      </th>
                      <td>
                        <a
                          target="_blank"
                          rel="noreferrer"
                          href={getPersonLink(r.wcaId)}
                        >
                          {r.name}
                        </a>
                      </td>
                      <td>{r.overall}</td>
                      {r.events.map((e, i) => (
                        <td
                          key={i}
                          className={
                            !e.completed
                              ? styles.notCompleted
                              : e.regionalRank <= 10
                              ? styles.top10
                              : ""
                          }
                        >
                          {e.regionalRank}
                        </td>
                      ))}
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </>
        )}
      </Space>
    </>
  );
};
