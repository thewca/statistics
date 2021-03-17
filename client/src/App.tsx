import {
  DatabaseOutlined,
  HomeOutlined,
  OrderedListOutlined,
  SolutionOutlined,
} from "@ant-design/icons";
import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import About from "./main/components/About";
import DatabaseQuery from "./main/components/DatabaseQuery";
import Footer from "./main/components/Footer";
import Home from "./main/components/Home";
import StatisticsList from "./main/components/StatisticsList";
import Topbar from "./main/components/Topbar";
import { LinkItem } from "./main/model/LinkItem";

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
    exact: false,
    icon: <OrderedListOutlined />,
    component: <StatisticsList />,
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

function App() {
  return (
    <Router>
      <div id="page-container">
        <div id="content-wrapper">
          <Topbar links={links} />
          <Switch>
            {links.map((link) => (
              <Route key={link.href} path={link.href} exact={link.exact}>
                {link.component}
              </Route>
            ))}
          </Switch>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
