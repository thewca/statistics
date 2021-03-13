import {
  DatabaseOutlined,
  HomeOutlined,
  OrderedListOutlined,
  SolutionOutlined,
} from "@ant-design/icons";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { HashRouter, Route, Switch } from "react-router-dom";
import About from "./main/components/About";
import DatabaseQuery from "./main/components/DatabaseQuery";
import Footer from "./main/components/Footer";
import Home from "./main/components/Home";
import StatisticsList from "./main/components/StatisticsList";
import Topbar from "./main/components/Topbar";
import { LinkItem } from "./main/model/LinkItem";

const links: LinkItem[] = [
  { name: "Home", href: "/", exact: true, icon: <HomeOutlined /> },
  {
    name: "Statistics List",
    href: "/statistics-list",
    exact: false,
    icon: <OrderedListOutlined />,
  },
  {
    name: "Database Query",
    href: "/database-query",
    exact: false,
    icon: <DatabaseOutlined />,
  },
  {
    name: "About",
    href: "/about",
    exact: false,
    icon: <SolutionOutlined />,
  },
];

function App() {
  return (
    <HashRouter basename={links[0].href}>
      <div id="page-container">
        <div id="content-wrapper">
          <Topbar links={links} />
          <Switch>
            <Route exact path={links[0].href}>
              <Home />
            </Route>
            <Route path={links[1].href}>
              <StatisticsList />
            </Route>
            <Route path={links[2].href}>
              <DatabaseQuery />
            </Route>
            <Route path={links[3].href}>
              <About />
            </Route>
          </Switch>
        </div>
        <Footer />
      </div>
    </HashRouter>
  );
}

export default App;
