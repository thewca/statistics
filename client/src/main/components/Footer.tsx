import {
  FacebookOutlined,
  InstagramOutlined,
  YoutubeOutlined,
} from "@ant-design/icons";
import "./Footer.css";

const Footer = () => {
  return (
    <div id="footer-wrapper" className="container-fluid border-top text-muted">
      <footer className="container">
        <div className="row text-small">
          <div className="col-6 col-md">
            <h5>Links</h5>
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
          </div>
          <div className="col-6 col-md">
            <h5>Media</h5>
            <ul className="list-unstyled">
              <li>
                <a href="https://www.facebook.com/WorldCubeAssociation">
                  <FacebookOutlined />
                </a>
              </li>
              <li>
                <a href="https://www.instagram.com/thewcaofficial">
                  <InstagramOutlined />
                </a>
              </li>
              <li>
                <a href="https://www.youtube.com/channel/UC5OUMUnS8PvY1RvtB1pQZ0g">
                  <YoutubeOutlined />
                </a>
              </li>
            </ul>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Footer;
