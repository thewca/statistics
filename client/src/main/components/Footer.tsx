import {
  FacebookOutlined,
  InstagramOutlined,
  YoutubeOutlined
} from "@ant-design/icons";
import { Col, Divider, Row } from "antd";
import "./Footer.css";

const Footer = () => {
  return (
    <div id="footer">
      <Divider />
      <footer>
        <Row justify="center">
          <Col xs={12} md={8}>
            <h3 className="footer-title">Links</h3>
            <ul className="list-unstyled">
              <li>
                <a href="https://www.worldcubeassociation.org/" target="_blank" rel="noreferrer">
                  World Cube Association
                </a>
              </li>
              <li>
                <a href="https://forum.worldcubeassociation.org/" target="_blank" rel="noreferrer">WCA Forum</a>
              </li>
            </ul>
          </Col>
          <Col xs={12} md={8}>
            <h3 className="footer-title">Media</h3>
            <ul className="list-unstyled">
              <li>
                <a href="https://www.facebook.com/WorldCubeAssociation" target="_blank" rel="noreferrer">
                  <FacebookOutlined /> Facebook
                </a>
              </li>
              <li>
                <a href="https://www.instagram.com/thewcaofficial" target="_blank" rel="noreferrer">
                  <InstagramOutlined /> Instagram
                </a>
              </li>
              <li>
                <a href="https://www.youtube.com/channel/UC5OUMUnS8PvY1RvtB1pQZ0g" target="_blank" rel="noreferrer">
                  <YoutubeOutlined /> YouTube
                </a>
              </li>
            </ul>
          </Col>
        </Row>
      </footer>
    </div>
  );
};

export default Footer;
