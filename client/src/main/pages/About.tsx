import "./About.css";

const About = () => {
  return (
    <div className="container">
      <h1 className="page-title">About</h1>
      <p>
        WCA Statistics is a collection of interesting analysis over the WCA's
        database.
      </p>
      <p>Version: {process.env.REACT_APP_VERSION}</p>
      <p>Date: {process.env.REACT_APP_DATE_VERSION}</p>
    </div>
  );
};

export default About;
