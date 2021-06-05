import { UserOutlined } from "@ant-design/icons";
import { Menu, message } from "antd";
import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import statisticsApi from "../api/statistics.api";
import wcaApi from "../api/wca.api";
import logo from "../assets/wca_logo.svg";
import { LinkItem } from "../model/LinkItem";
import StatisticsGroup from "../model/StatisticsGroup";
import UserInfo from "../model/UserInfo";
import { deleteParameter, getHashParameter } from "../util/query.param.util";
import "./Topbar.css";

const { SubMenu } = Menu;

interface TopbarProps {
  links: LinkItem[];
  statisticsGroups?: StatisticsGroup[];
  userInfo?: UserInfo;
  setUserInfo: (userInfo?: UserInfo) => void;
}

const STATISTICS_LIST = "Statistics List";
const ACCESS_TOKEN = "access_token";
const EXPIRES_IN = "expires_in";
const TOKEN_TYPE = "token_type";
const USER_INFO = "user_info";
const AUTHORIZATION = "authorization";

const Topbar = ({
  links,
  statisticsGroups,
  userInfo,
  setUserInfo,
}: TopbarProps) => {
  const [authorization, setAuthorization] = useState<string>();
  const [expiresIn, setExpiresIn] = useState<number>();

  const logout = useCallback(() => {
    delete localStorage[ACCESS_TOKEN];
    delete localStorage[TOKEN_TYPE];
    delete localStorage[USER_INFO];
    delete localStorage[EXPIRES_IN];
    setAuthorization(undefined);
    setUserInfo(undefined);
    setExpiresIn(undefined);

    // Return to home after logout
    // TODO do this only for pages that requires login
    window.location.href = "/";
  }, [setUserInfo]);

  const handle = () => {
    if (!!userInfo) {
      logout();
    } else {
      wcaApi.login();
    }
  };

  const isLogged = useCallback(() => {
    if (!authorization) {
      return false;
    }

    if (!expiresIn) {
      return false;
    }

    if (new Date() < new Date(expiresIn)) {
      return true;
    }

    logout();
    return false;
  }, [authorization, expiresIn, logout]);

  const errorInterceptor = useCallback(
    (response: AxiosError) => {
      if (response.response?.status === 404) {
        message.error("Not found");
      } else if (response.response?.status === 401) {
        logout();
      }
      return Promise.reject(response);
    },
    [logout]
  );

  const requestIntercetor = useCallback(
    (item: AxiosRequestConfig) => {
      if (isLogged()) {
        item.headers.Authorization = authorization;
      }
      return item;
    },
    [authorization, isLogged]
  );

  useEffect(() => {
    axios.interceptors.response.use(undefined, errorInterceptor);
    axios.interceptors.request.use(requestIntercetor);

    let accessToken = getHashParameter(ACCESS_TOKEN);
    let tokenType = getHashParameter(TOKEN_TYPE);
    let expiration = getHashParameter(EXPIRES_IN);

    let token: string;
    let userInfo: UserInfo | null = null;

    if (!!accessToken && !!tokenType && !!expiration) {
      deleteParameter(ACCESS_TOKEN, EXPIRES_IN, TOKEN_TYPE);

      let now = new Date();
      let expiresIn = now.setSeconds(now.getSeconds() + Number(expiration));
      setExpiresIn(expiresIn);
      localStorage.setItem(EXPIRES_IN, "" + expiresIn);

      token = `${tokenType} ${accessToken}`;
      setAuthorization(token);
      localStorage.setItem(AUTHORIZATION, token);
    } else {
      token = localStorage[AUTHORIZATION];
      setAuthorization(token);

      userInfo = JSON.parse(localStorage[USER_INFO] || null);
      if (!!userInfo) {
        setUserInfo(userInfo);
      }

      let expiresIn = localStorage[EXPIRES_IN];
      if (!!expiresIn) {
        setExpiresIn(Number(expiresIn));
      }
    }

    if (isLogged() && !userInfo) {
      statisticsApi.getUserInfo().then((response) => {
        setUserInfo(response.data);
        localStorage.setItem(USER_INFO, JSON.stringify(response.data));
      });
    }
  }, [errorInterceptor, requestIntercetor, setUserInfo, isLogged]);

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
            {!!userInfo ? "Logout" : "Login"}
          </Menu.Item>
        </Menu>
      </div>
    </Menu>
  );
};

export default Topbar;
