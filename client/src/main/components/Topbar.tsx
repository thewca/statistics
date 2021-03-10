import { Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";
import { LinkItem } from "../model/LinkItem";
import "./Topbar.css";

import logo from "../assets/wca_logo.svg";

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
            <Link key={i} to={link.href} className="text-white">
              {link.name}
            </Link>
          ))}
        </Nav>
        <Nav>
          <Nav.Link className="text-white">Login</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default Topbar;
