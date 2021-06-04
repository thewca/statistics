import {
  DatabaseOutlined,
  HomeOutlined,
  OrderedListOutlined,
  RiseOutlined,
  SolutionOutlined,
} from "@ant-design/icons";
import { message } from "antd";
import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";
import statisticsApi from "./main/api/statistics.api";
import Footer from "./main/components/Footer";
import StatisticsDisplay from "./main/components/StatisticsDisplay";
import Topbar from "./main/components/Topbar";
import { LinkItem } from "./main/model/LinkItem";
import { StatisticsList } from "./main/model/StatisticsList";
import UserInfo from "./main/model/UserInfo";
import AboutPage from "./main/pages/AboutPage";
import BestEverRanksPage from "./main/pages/BestEverRanksPage";
import DatabaseQueryPage from "./main/pages/DatabaseQueryPage";
import HomePage from "./main/pages/HomePage";
import NotFoundPage from "./main/pages/NotFoundPage";
import StatisticsListPage from "./main/pages/StatisticsListPage";

function App() {
  const [statisticsList, setStatisticsList] = useState<StatisticsList>();
  const [userInfo, setUserInfo] = useState<UserInfo>();

  const getStatisticsList = () => {
    statisticsApi
      .getStatisticsGroups()
      .then((response) => setStatisticsList(response.data))
      .catch(() => message.error("Error fetching statistics list"));
  };

  const links: LinkItem[] = [
    {
      name: "Home",
      href: "/",
      exact: true,
      icon: <HomeOutlined />,
      component: <HomePage />,
    },
    {
      name: "Statistics List",
      href: "/statistics-list",
      exact: true,
      icon: <OrderedListOutlined />,
      component: <StatisticsListPage statisticsList={statisticsList} />,
    },
    {
      name: "Database Query",
      href: "/database-query",
      exact: false,
      icon: <DatabaseOutlined />,
      component: <DatabaseQueryPage />,
      requiresLogin: true,
    },
    {
      name: "About",
      href: "/about",
      exact: false,
      icon: <SolutionOutlined />,
      component: <AboutPage />,
    },
    {
      name: "Best Ever Ranks",
      href: "/best-ever-ranks",
      exact: true,
      icon: <RiseOutlined />,
      component: <BestEverRanksPage />,
    },
  ]
    // Filter logged area
    .filter((it) => !it.requiresLogin || !!userInfo);

  useEffect(getStatisticsList, []);

  return (
    <Router>
      <div id="page-container">
        <Topbar
          links={links}
          statisticsGroups={statisticsList?.list}
          userInfo={userInfo}
          setUserInfo={setUserInfo}
        />
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
              <NotFoundPage />
            </Route>
          </Switch>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
