import React, { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../store/auth-context";

const Home = () => {
  const authCtx = useContext(AuthContext);
  return (
    <div className="container">
      <h1 className="page-title">WCA Statistics</h1>
      <p>
        Navigate through interesting analysis over the World Cube Association's
        database.
      </p>

      <h3>Statistics List</h3>

      <p>
        Check our <Link to="statistics-list">list with intereting</Link>{" "}
        statistics
      </p>

      <h3>Logged Features</h3>

      {authCtx.isLogged && (
        <p>
          Since you are logged, you can check exclusive features like{" "}
          <Link to="database-query">Database Query</Link>.
        </p>
      )}

      {!authCtx.isLogged && (
        <p>Log in with the WCA's website to get more content.</p>
      )}
    </div>
  );
};

export default Home;
