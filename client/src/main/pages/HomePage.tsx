import { CompassOutlined, LoadingOutlined } from "@ant-design/icons";
import { Col, Row, Statistic } from "antd";
import { sample } from "lodash";
import React, { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import banner from "../assets/homepage_banner.svg";
import { StatisticsList } from "../model/StatisticsList";
import AuthContext from "../store/auth-context";
import "./HomePage.css";

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

      <Row gutter={16} justify="center">
        <Col xs={20} md={6}>
          <div className="stat-card">
            <h3>Statistics List</h3>
            <p>
              Check out our <Link to="statistics-list">list of interesting
              statistics.</Link>
            </p>

            <p>
              You can suggest a new statistic by reaching out to the WCA Software Team.
              If it's widely interesting and feasible to implement, we might
              add it!
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
          </div>
        </Col>
        <Col xs={20} md={6}>
          <div className="stat-card">
            <h3>Take me to a random statistic</h3>
            <p>
              Click <Link to={randomLink}>here</Link> to be redirected to a
              random page.
            </p>
          </div>
        </Col>
        <Col xs={20} md={6}>
          <div className="stat-card">
            <h3>Extra Features</h3>
            {authCtx.isLogged && (
              <>
                <p>
                  Since you are logged in, you can use exclusive features like{" "}
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
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default Home;
