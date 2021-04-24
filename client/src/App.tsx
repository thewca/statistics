import {
  DatabaseOutlined,
  HomeOutlined,
  OrderedListOutlined,
  SolutionOutlined,
} from "@ant-design/icons";
import { message } from "antd";
import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import statisticsApi from "./main/api/statistics.api";
import Footer from "./main/components/Footer";
import StatisticsDisplay from "./main/components/StatisticsDisplay";
import Topbar from "./main/components/Topbar";
import { LinkItem } from "./main/model/LinkItem";
import { StatisticsGroup } from "./main/model/StatisticItem";
import About from "./main/pages/About";
import DatabaseQuery from "./main/pages/DatabaseQuery";
import Home from "./main/pages/Home";
import NotFound from "./main/pages/NotFound";
import StatisticsList from "./main/pages/StatisticsList";
import "./App.css";

function App() {
  const [statisticsGroups, setStatisticsGroups] = useState<StatisticsGroup[]>();
  const getStatisticsList = () => {
    statisticsApi
      .getStatisticsGroups()
      .then((response) => setStatisticsGroups(response.data))
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
      component: <StatisticsList statisticsGroups={statisticsGroups} />,
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
        <Topbar links={links} statisticsGroups={statisticsGroups} />
        <div id="content-wrapper">
          <Switch>
            {links.map((link) => (
              <Route key={link.href} path={link.href} exact={link.exact}>
                {link.component}
              </Route>
            ))}
            <Route
              path="/statistics-list/:pathId"
              component={StatisticsDisplay}
            />
            <Route path="*">
              <NotFound />
            </Route>
          </Switch>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
