import { GithubFilled } from "@ant-design/icons";
import { Divider, Row } from "antd";
import "./Footer.css";

const Footer = () => {
  return (
    <footer>
      <Divider />
      <Row justify="center">
        <ul>
          <li>
            <a href="https://www.worldcubeassociation.org/about">About us</a>
          </li>
          <li>
            <a href="https://www.worldcubeassociation.org/faq">FAQ</a>
          </li>
          <li>
            <a href="https://www.worldcubeassociation.org/contact/website">
              Contact
            </a>
          </li>
          <li>
            <a href="https://github.com/thewca/statistics">
              <GithubFilled />
              GitHub
            </a>
          </li>
          <li>
            <a href="https://www.worldcubeassociation.org/privacy">Privacy</a>
          </li>
          <li>
            <a href="https://www.worldcubeassociation.org/disclaimer">
              Disclaimer
            </a>
          </li>
        </ul>
      </Row>
    </footer>
  );
};

export default Footer;
