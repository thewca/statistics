import { Alert } from "antd";

const NoContent = () => (
  <Alert
    type="info"
    message="No results to show"
    style={{ marginTop: "5px" }}
  />
);

export default NoContent;
