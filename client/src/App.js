import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Topbar from "./main/components/Topbar";
import { HashRouter, Switch, Route } from "react-router-dom";
import DatabaseQuery from "./main/components/DatabaseQuery";
import Home from "./main/components/Home";
import StatisticsList from "./main/components/StatisticsList";
import About from "./main/components/About";

const links = [
  { name: "Home", href: "/", exact: true },
  {
    name: "Statistics List",
    href: "/statistics-list",
    exact: false,
  },
  {
    name: "Database Query",
    href: "/database-query",
    exact: false,
  },
  {
    name: "About",
    href: "/about",
    exact: false,
  },
];

class App extends Component {
  render() {
    return (
      <HashRouter basename={links[0].href}>
        <div>
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
      </HashRouter>
    );
  }
}

export default App;
