import { HashRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import About from "./main/components/About";
import DatabaseQuery from "./main/components/DatabaseQuery";
import Home from "./main/components/Home";
import StatisticsList from "./main/components/StatisticsList";
import Topbar from "./main/components/Topbar";
import { LinkItem } from "./main/model/LinkItem";

const links: LinkItem[] = [
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

function App() {
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

export default App;
