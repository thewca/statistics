import { CompassOutlined, LoadingOutlined } from "@ant-design/icons";
import { Card, Col, Divider, Row, Statistic } from "antd";
import { sample } from "lodash";
import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { StatisticsList } from "../model/StatisticsList";
import AuthContext from "../store/auth-context";
import "./HomePage.css";

import banner from "../assets/homepage_banner.svg";

interface HomePageProps {
  statisticsList?: StatisticsList;
  loading: boolean;
}

const Home = ({ statisticsList, loading }: HomePageProps) => {
  const authCtx = useContext(AuthContext);
  const [randomLink, setRandomLink] = useState("");

  useEffect(() => {
    let randomLink =
      "statistics-list/" +
      (sample(statisticsList?.list.flatMap((it) => it.statistics))?.path || "");
    setRandomLink(randomLink);
  }, [statisticsList]);

  return (
    <div
      className="homepage-banner"
      style={{ backgroundImage: "url(" + banner + ")" }}
    >
      <h1 className="page-title main-title">WCA Statistics</h1>

      <Divider />

      <Row gutter={[8, 8]}>
        <Col xs={24} md={8}>
          <Card title="Statistics List" className="stat-card">
            <p>
              Check our <Link to="statistics-list">list with interesting</Link>{" "}
              statistics.
            </p>

            <p>
              We try always to add new statistics. You can suggest one by
              reaching the WCA Software Team. If that's widely interesting and
              feasible to implement, we might add it!
            </p>

            {statisticsList && (
              <Statistic
                className="stat-stat"
                title="Current Statistics"
                value={statisticsList.totalSize}
              />
            )}

            {loading && (
              <div style={{ textAlign: "center" }}>
                <LoadingOutlined />
              </div>
            )}
          </Card>
        </Col>
        <Col xs={24} md={8}>
          <Card title="Take me to a random statistics" className="stat-card">
            <p>
              Click <Link to={randomLink}>here</Link> to be redirected to a
              random page.
            </p>
          </Card>
        </Col>
        <Col xs={24} md={8}>
          <Card title="Logged Feature" className="stat-card">
            {authCtx.isLogged && (
              <>
                <p>
                  Since you are logged, you can check exclusive features like{" "}
                  <Link to="database-query">Database Query</Link> (in case you
                  have SQL skills or a query).
                </p>
                <p>
                  If you find a statistic with the icon{" "}
                  <Link
                    to={
                      "/database-query?sqlQuery=select id, name from Persons p where id = ':WCA_ID'"
                    }
                  >
                    <CompassOutlined />
                  </Link>
                  , you can click it to be redirected to a custom query. This
                  means that you can query for specific results (like your own)
                  in a query based on the one that generated the statistic.
                </p>
              </>
            )}

            {!authCtx.isLogged && (
              <p>Log in with the WCA's website to get more content.</p>
            )}
          </Card>
        </Col>
      </Row>

      <Divider />
    </div>
  );
};

export default Home;
