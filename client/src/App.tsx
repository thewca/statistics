import {
  DatabaseOutlined,
  HomeOutlined,
  LineChartOutlined,
  OrderedListOutlined,
  RiseOutlined,
  SolutionOutlined,
} from "@ant-design/icons";
import { message } from "antd";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import statisticsApi from "./main/api/statistics.api";
import Footer from "./main/components/Footer";
import { LoginRequired } from "./main/components/LoginRequired";
import StatisticsDisplay from "./main/components/StatisticsDisplay";
import Topbar from "./main/components/Topbar";
import { errorInterceptor, requestIntercetor } from "./main/config/interceptor";
import { LinkItem } from "./main/model/LinkItem";
import { StatisticsList } from "./main/model/StatisticsList";
import AboutPage from "./main/pages/AboutPage";
import BestEverRanksPage from "./main/pages/BestEverRanksPage";
import DatabaseQueryPage from "./main/pages/DatabaseQueryPage";
import HomePage from "./main/pages/HomePage";
import NotFoundPage from "./main/pages/NotFoundPage";
import { RecordEvolutionPage } from "./main/pages/RecordEvolutionPage";
import StatisticsListPage from "./main/pages/StatisticsListPage";
import AuthContext from "./main/store/auth-context";

axios.interceptors.response.use(undefined, errorInterceptor);
axios.interceptors.request.use(requestIntercetor);

function App() {
  const [statisticsList, setStatisticsList] = useState<StatisticsList>();
  const [loading, setLoading] = useState(false);

  const authCtx = useContext(AuthContext);

  const getStatisticsList = () => {
    setLoading(true);
    statisticsApi
      .getStatisticsGroups()
      .then((response) => setStatisticsList(response.data))
      .catch(() => message.error("Error fetching statistics list"))
      .finally(() => setLoading(false));
  };

  const links: LinkItem[] = [
    {
      name: "Home",
      href: "/",
      exact: true,
      icon: <HomeOutlined />,
      component: <HomePage statisticsList={statisticsList} loading={loading} />,
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
      name: "Record Evolution",
      href: "/record-evolution",
      exact: true,
      icon: <LineChartOutlined />,
      component: <RecordEvolutionPage />,
    },
    {
      name: "Best Ever Ranks",
      href: "/best-ever-ranks",
      exact: true,
      icon: <RiseOutlined />,
      component: <BestEverRanksPage />,
    },
  ];

  useEffect(getStatisticsList, []);

  return (
    <BrowserRouter>
      <div id="page-container">
        <Topbar links={links} statisticsGroups={statisticsList?.list} />
        <div id="content-wrapper">
          <Routes>
            {links.map((link) => (
              <Route
                key={link.href}
                path={link.href}
                element={
                  !link.requiresLogin || authCtx.isLogged ? (
                    link.component
                  ) : (
                    <LoginRequired />
                  )
                }
              />
            ))}
            <Route
              path="/statistics-list/:pathId"
              element={<StatisticsDisplay />}
            />
            <Route path="*" element={<NotFoundPage />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;
