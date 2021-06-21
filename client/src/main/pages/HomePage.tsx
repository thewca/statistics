import { Card, Col, Row, Statistic } from "antd";
import { sample } from "lodash";
import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { StatisticsList } from "../model/StatisticsList";
import AuthContext from "../store/auth-context";
import "./HomePage.css";

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

      <Row gutter={16}>
        <Col span={8}>
          <Card title="Statistics List" className="stat-card">
            <p>
              Check our <Link to="statistics-list">list with intereting</Link>{" "}
              statistics.
            </p>

            <p>
              We try always to add new statistics. You can suggest one by
              reaching the WCA Software Team. If that's widely interesting and
              feasible to implement, we might add it!
            </p>

            {!!statisticsList && (
              <Statistic
                className="stat-stat"
                title="Current Statistics"
                value={statisticsList.totalSize}
              />
            )}
          </Card>
        </Col>
        <Col span={8}>
          <Card title="Take me to a random statistics" className="stat-card">
            <p>
              Click <Link to={randomLink}>here</Link> to be redirected to a
              random page.
            </p>
          </Card>
        </Col>
        <Col span={8}>
          <Card title="Logged Feature" className="stat-card">
            {authCtx.isLogged && (
              <p>
                Since you are logged, you can check exclusive features like{" "}
                <Link to="database-query">Database Query</Link> (in case you
                have SQL skills or a query).
              </p>
            )}

            {!authCtx.isLogged && (
              <p>Log in with the WCA's website to get more content.</p>
            )}
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Home;
