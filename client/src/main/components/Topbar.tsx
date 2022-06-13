import { UserOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import { useContext } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/wca_logo.svg";
import { LinkItem } from "../model/LinkItem";
import StatisticsGroup from "../model/StatisticsGroup";
import AuthContext from "../store/auth-context";
import "./Topbar.css";

const { SubMenu } = Menu;

interface TopbarProps {
  links: LinkItem[];
  statisticsGroups?: StatisticsGroup[];
}

const STATISTICS_LIST = "Statistics List";

const Topbar = ({ links, statisticsGroups }: TopbarProps) => {
  const authCtx = useContext(AuthContext);

  const handle = () => {
    if (authCtx.isLogged) {
      authCtx.logout();
    } else {
      authCtx.login();
    }
  };

  const statisticsListLink = links.find((it) => it.name === STATISTICS_LIST);
  return (
    <Menu mode="horizontal" id="top-bar">
      <Menu.Item key="logo">
        <Link to={links[0].href}>
          <img src={logo} width="30" height="30" alt="Logo" />
        </Link>
      </Menu.Item>
      {links
        .filter((link) => link.name !== STATISTICS_LIST)
        .map((link) =>
          link.subItems ? (
            <SubMenu key={link.href} title={link.name} icon={link.icon}>
              {link.subItems.map((s) => (
                <Menu.Item key={s.href} icon={s.icon}>
                  <Link to={s.href}>{s.name}</Link>
                </Menu.Item>
              ))}
            </SubMenu>
          ) : (
            <Menu.Item key={link.href} icon={link.icon}>
              <Link to={link.href} className="text-white align-center">
                {link.name}
              </Link>
            </Menu.Item>
          )
        )}
      <SubMenu
        key="sub2"
        icon={statisticsListLink?.icon}
        title={<Link to="/statistics-list">Statistics List</Link>}
      >
        {statisticsGroups?.map((statisticsGroup) => (
          <SubMenu key={statisticsGroup.group} title={statisticsGroup.group}>
            {statisticsGroup.statistics.map((stat) => (
              <Menu.Item key={stat.path}>
                <Link to={`/statistics-list/${stat.path}`}>{stat.title}</Link>
              </Menu.Item>
            ))}
          </SubMenu>
        ))}
      </SubMenu>
      <Menu.Item key="login" onClick={handle} id="login">
        {!!authCtx.userInfo ? (
          <img
            src={authCtx.userInfo.avatar?.thumb_url}
            width="30"
            height="30"
            alt="Avatar"
          />
        ) : (
          <UserOutlined />
        )}{" "}
        {authCtx.isLogged ? "Logout" : "Login"}
      </Menu.Item>
    </Menu>
  );
};

export default Topbar;
