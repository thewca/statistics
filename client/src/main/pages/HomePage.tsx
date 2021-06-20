import { Card } from "antd";
import { sample } from "lodash";
import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { StatisticsList } from "../model/StatisticsList";
import AuthContext from "../store/auth-context";

interface HomePageProps {
  statisticsList?: StatisticsList;
}

const Home = ({ statisticsList }: HomePageProps) => {
  const authCtx = useContext(AuthContext);
  const [randomLink, setRandomLink] = useState("");

  useEffect(() => {
    let randomLink =
      "statistics-list/" +
      (sample(statisticsList?.list.flatMap((it) => it.statistics))?.path || "");
    setRandomLink(randomLink);
  }, [statisticsList]);

  return (
    <div className="container">
      <h1 className="page-title">WCA Statistics</h1>
      <p>
        Navigate through interesting analysis over the World Cube Association's
        database.
      </p>

      <h3>Statistics List</h3>

      <p>
        Check our <Link to="statistics-list">list with intereting</Link>{" "}
        statistics
      </p>

      <p>
        We try always to add new statistics. You can suggest one by reaching the
        WCA Software Team. If that's widely interesting and feasible to
        implement, we might add it!
      </p>

      {!!statisticsList && (
        <Card
          style={{ textAlign: "center" }}
          prefix={"teste"}
          title="Current Statistics"
        >
          {statisticsList.totalSize}
        </Card>
      )}

      <h3>Take me to a random statistics</h3>
      <p>
        Click <Link to={randomLink}>here</Link> to be redirected to a random
        page
      </p>

      <h3>Logged Features</h3>

      {authCtx.isLogged && (
        <p>
          Since you are logged, you can check exclusive features like{" "}
          <Link to="database-query">Database Query</Link>.
        </p>
      )}

      {!authCtx.isLogged && (
        <p>Log in with the WCA's website to get more content.</p>
      )}
    </div>
  );
};

export default Home;
