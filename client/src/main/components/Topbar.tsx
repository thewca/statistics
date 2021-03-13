import { UserOutlined } from "@ant-design/icons";
import { message } from "antd";
import React from "react";
import { Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import logo from "../assets/wca_logo.svg";
import { LinkItem } from "../model/LinkItem";
import "./Topbar.css";

interface TopbarProps {
  links: LinkItem[];
}

const Topbar = ({ links }: TopbarProps) => {
  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Navbar.Brand>
        <Link to={links[0].href}>
          <img
            src={logo}
            width="60"
            height="60"
            className="d-inline-block align-top"
            alt="WCA logo"
          />
        </Link>
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr-auto">
          {links.map((link, i) => (
            <Link key={i} to={link.href} className="text-white align-center">
              <span>{link.icon}</span> &nbsp;
              {link.name}
            </Link>
          ))}
        </Nav>
        <Nav>
          <Nav.Link
            className="text-white"
            onClick={() => message.info("Not implemented yet")}
          >
            <UserOutlined /> Login
          </Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Topbar;
