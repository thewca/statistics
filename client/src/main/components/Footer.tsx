import {
  FacebookOutlined,
  InstagramOutlined,
  YoutubeOutlined,
} from "@ant-design/icons";
import { Col, Row } from "antd";
import "./Footer.css";

const Footer = () => {
  return (
    <div id="footer">
      <footer>
        <Row justify="center">
          <Col span={6}>
            <h3 className="footer-title">Links</h3>
            <ul className="list-unstyled">
              <li>
                <a href="https://www.worldcubeassociation.org/">
                  World Cube Association
                </a>
              </li>
              <li>
                <a href="https://forum.worldcubeassociation.org/">WCA Forum</a>
              </li>
            </ul>
          </Col>
          <Col span={6}>
            <h3 className="footer-title">Media</h3>
            <ul className="list-unstyled">
              <li>
                <a href="https://www.facebook.com/WorldCubeAssociation">
                  <FacebookOutlined /> Facebook
                </a>
              </li>
              <li>
                <a href="https://www.instagram.com/thewcaofficial">
                  <InstagramOutlined /> Instagram
                </a>
              </li>
              <li>
                <a href="https://www.youtube.com/channel/UC5OUMUnS8PvY1RvtB1pQZ0g">
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
