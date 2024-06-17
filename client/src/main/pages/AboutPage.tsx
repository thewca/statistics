import { APP_DATE_VERSION, APP_VERSION } from "../config/EnvVarConfig";
import "./AboutPage.css";

const AboutPage = () => {
  return (
    <div>
      <h1 className="page-title">About</h1>
      <p>
        WCA Statistics is a collection of interesting analysis over the WCA's
        database.
      </p>
      <p>
        <strong>Version:</strong> {APP_VERSION}
      </p>
      <p>
        <strong>Date:</strong> {APP_DATE_VERSION}
      </p>
    </div>
  );
};

export default AboutPage;
