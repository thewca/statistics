import { message, Select, Skeleton, Tooltip } from "antd";
import { useCallback, useEffect, useState } from "react";
import { sumOfRanksApi } from "../../api/SumOfRanksApi";
import WcaEvent from "../../model/Event";
import { SorResultType } from "../../model/rank/MetaSorInfo";
import { SumOfRanks } from "../../model/rank/SumOfRanks";
import styles from "./SumOfRanksPage.module.css";

const { Option, OptGroup } = Select;

const INITIAL_PAGE_SIZE = 20;

export const SumOfRanksPage = () => {
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(INITIAL_PAGE_SIZE);
  const [ranks, setRanks] = useState<SumOfRanks[]>([]);
  const [loading, setLoading] = useState(false);
  const [resultTypes, setResultTypes] = useState<SorResultType[]>([]);
  const [resultType, setResultType] = useState<string>();
  const [availableEvents, setAvailableEvents] = useState<WcaEvent[]>([]);
  const [selectedRegionGroup, setSelectedRegionGroup] = useState<string>();

  const fetchMetaInfor = useCallback(() => {
    sumOfRanksApi
      .meta()
      .then((response) => {
        setResultTypes(response.data.resultTypes);
        setResultType(response.data.resultTypes[0].resultType);
        setAvailableEvents(response.data.availableEvents);
        setSelectedRegionGroup(
          `${response.data.resultTypes[0].regionGroups[0].regionType}-${response.data.resultTypes[0].regionGroups[0].regions[0].region}`
        );
      })
      .catch(() => {
        message.error("Error searching for Sum of Ranks information");
      });
  }, []);

  const fetchSumOfRanks = useCallback(
    (
      region: string,
      regionType: string,
      resultType: string,
      page: number,
      pageSize: number
    ) => {
      setLoading(true);
      sumOfRanksApi
        .listSumOfRanks(region, regionType, resultType, page, pageSize)
        .then((response) => {
          setRanks(response.data);
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
    setRanks([]);
    let [regionType, region] = selectedRegionGroup.split("-");

    fetchSumOfRanks(resultType, regionType, region, 0, INITIAL_PAGE_SIZE);
  }, [resultType, selectedRegionGroup, fetchSumOfRanks]);

  return (
    <>
      <h1 className="page-title">Sum of Ranks</h1>

      <Select
        className={styles.regionTypes}
        value={resultType}
        onChange={(e) => setResultType(e)}
        options={resultTypes.map((r) => ({
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
        {resultTypes
          .find((r) => r.resultType === resultType)
          ?.regionGroups.map((g) => (
            <OptGroup key={g.regionType}>
              {g.regions.map((r) => (
                <Option key={r.region} value={`${g.regionType}-${r.region}`}>
                  {r.region}
                </Option>
              ))}
            </OptGroup>
          ))}
      </Select>
      <Skeleton loading={loading} />

      {ranks.length > 0 && (
        <div className={styles.ranksTable}>
          <table>
            <thead>
              <tr>
                <th>Rank</th>
                <th>Person</th>
                <th>Overall</th>
                {availableEvents.map((e) => (
                  <th key={e.id}>
                    <Tooltip title={e.name}>
                      <span className={`cubing-icon event-${e.id}`} />
                    </Tooltip>
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {ranks.map((r) => (
                <tr key={r.wcaId}>
                  <th>{r.regionRank}</th>
                  <td>{r.wcaId}</td>
                  <td>{r.overall}</td>
                  {r.events.map((e) => (
                    <td
                      key={e.eventId}
                      className={
                        !e.completed
                          ? styles.notCompleted
                          : e.rank <= 10
                          ? styles.top10
                          : ""
                      }
                    >
                      {e.rank}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </>
  );
};
