import { UserOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import { useContext } from "react";
import { Link } from "react-router-dom";
import logo from "../assets/wca_logo.svg";
import { LinkItem } from "../model/LinkItem";
import StatisticsGroup from "../model/StatisticsGroup";
import UserInfo from "../model/UserInfo";
import AuthContext from "../store/auth-context";
import "./Topbar.css";

const { SubMenu } = Menu;

interface TopbarProps {
  links: LinkItem[];
  statisticsGroups?: StatisticsGroup[];
  userInfo?: UserInfo;
  setUserInfo: (userInfo?: UserInfo) => void;
}

const STATISTICS_LIST = "Statistics List";

const Topbar = ({
  links,
  statisticsGroups,
  userInfo,
  setUserInfo,
}: TopbarProps) => {
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
    <Menu theme="dark" mode="horizontal" id="top-bar">
      <Menu.Item key="logo">
        <Link to={links[0].href}>
          <img src={logo} width="30" height="30" alt="Logo" />
        </Link>
      </Menu.Item>
      {links
        .filter((link) => link.name !== STATISTICS_LIST)
        .map((link) => (
          <Menu.Item key={link.href}>
            <Link to={link.href} className="text-white align-center">
              <span>{link.icon}</span>
              {link.name}
            </Link>
          </Menu.Item>
        ))}
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
      <div id="login">
        <Menu theme="dark" mode="horizontal" id="top-bar" onClick={handle}>
          <Menu.Item key="login">
            {!!userInfo ? (
              <img
                src={userInfo.avatar?.thumb_url}
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
      </div>
    </Menu>
  );
};

export default Topbar;
