import { useCallback, useEffect, useState } from "react";
import { ranksApi } from "../api/RankApi";

const INITIAL_PAGE_SIZE = 20;

export const SumOfRanksPage = () => {
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(INITIAL_PAGE_SIZE);
  const [region, setRegion] = useState("World");
  const [regionType, setRegionType] = useState("World");
  const fetchSumOfRanks = useCallback(
    (
      region: string,
      regionType: string,
      resultType: string,
      page: number,
      pageSize: number
    ) => {
      ranksApi
        .listSumOfRanks(region, regionType, resultType, page, pageSize)
        .then((response) => {
          console.log(response.data);
        });
    },
    []
  );

  useEffect(
    () => fetchSumOfRanks("World", "World", "single", 0, INITIAL_PAGE_SIZE),
    [fetchSumOfRanks]
  );

  return (
    <>
      <h1 className="page-title">Sum of Ranks</h1>
    </>
  );
};
