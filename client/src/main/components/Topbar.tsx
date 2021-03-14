import { UserOutlined } from "@ant-design/icons";
import { Menu, message } from "antd";
import MenuItem from "antd/lib/menu/MenuItem";
import React from "react";
import { Link } from "react-router-dom";
import logo from "../assets/wca_logo.svg";
import { LinkItem } from "../model/LinkItem";
import "./Topbar.css";

interface TopbarProps {
  links: LinkItem[];
}

const Topbar = ({ links }: TopbarProps) => {
  return (
    <Menu theme="dark" mode="horizontal">
      <Menu.Item className="h-100">
        <Link to={links[0].href}>
          <img src={logo} width="30" height="30" alt="WCA logo" />
        </Link>
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
        <button onClick={() => message.info("Not implemented")}>
          <UserOutlined /> Login
        </button>
      </div>
    </Menu>
  );
};

export default Topbar;
