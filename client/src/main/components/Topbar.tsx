import { UserOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import MenuItem from "antd/lib/menu/MenuItem";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import wcaApi from "../api/wca.api";
import { LinkItem } from "../model/LinkItem";
import "./Topbar.css";

interface TopbarProps {
  links: LinkItem[];
}

const Topbar = ({ links }: TopbarProps) => {
  const [logged, setLogged] = useState(wcaApi.isLogged());

  const handle = () => {
    if (logged) {
      wcaApi.logout();
      setLogged(false);
    } else {
      wcaApi.handleLogin();
    }
  };

  return (
    <Menu theme="dark" mode="horizontal">
      <Menu.Item>
        <Link to={links[0].href}></Link>
      </Menu.Item>
      {links.map((link) => (
        <MenuItem key={link.href}>
          <Link to={link.href} className="text-white align-center">
            <span>{link.icon}</span>
            {link.name}
          </Link>
        </MenuItem>
      ))}
      <div id="login">
        <button onClick={handle}>
          {logged ? (
            <img
              src={wcaApi.getUserInfo()?.avatar?.thumb_url}
              width="30"
              height="30"
              alt="Avatar"
            />
          ) : (
            <UserOutlined />
          )}{" "}
          {logged ? "Logout" : "Login"}
        </button>
      </div>
    </Menu>
  );
};

export default Topbar;
