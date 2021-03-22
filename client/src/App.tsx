import {
  DatabaseOutlined,
  HomeOutlined,
  OrderedListOutlined,
  SolutionOutlined,
} from "@ant-design/icons";
import { message } from "antd";
import "bootstrap/dist/css/bootstrap.min.css";
import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import statisticsApi from "./main/api/statistics.api";
import About from "./main/components/About";
import DatabaseQuery from "./main/components/DatabaseQuery";
import Footer from "./main/components/Footer";
import Home from "./main/components/Home";
import StatisticsDetail from "./main/components/StatisticsDetail";
import StatisticsList from "./main/components/StatisticsList";
import Topbar from "./main/components/Topbar";
import { LinkItem } from "./main/model/LinkItem";
import { StatisticsItem } from "./main/model/StatisticItem";

function App() {
  const [statisticsList, setStatisticsList] = useState<StatisticsItem[]>([]);
  const getStatisticsList = () => {
    statisticsApi
      .getStatisticsList()
      .then((response) => setStatisticsList(response.data))
      .catch(() => message.error("Error fetching statistics list"));
  };
  useEffect(getStatisticsList, []);

  const links: LinkItem[] = [
    {
      name: "Home",
      href: "/",
      exact: true,
      icon: <HomeOutlined />,
      component: <Home />,
    },
    {
      name: "Statistics List",
      href: "/statistics-list",
      exact: true,
      icon: <OrderedListOutlined />,
      component: <StatisticsList statisticsList={statisticsList} />,
    },
    {
      name: "Database Query",
      href: "/database-query",
      exact: false,
      icon: <DatabaseOutlined />,
      component: <DatabaseQuery />,
    },
    {
      name: "About",
      href: "/about",
      exact: false,
      icon: <SolutionOutlined />,
      component: <About />,
    },
  ];

  return (
    <Router>
      <div id="page-container">
        <div id="content-wrapper">
          <Topbar links={links} statisticsList={statisticsList} />
          <Switch>
            {links.map((link) => (
              <Route key={link.href} path={link.href} exact={link.exact}>
                {link.component}
              </Route>
            ))}
            <Route
              path="/statistics-list/:pathId"
              component={StatisticsDetail}
            />
          </Switch>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
