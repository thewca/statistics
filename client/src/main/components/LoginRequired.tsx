import { UserOutlined } from "@ant-design/icons";
import { Result } from "antd";

export const LoginRequired = () => {
  return (
    <div>
      <Result
        title="Login is required to access this page"
        icon={<UserOutlined />}
      />
    </div>
  );
};
