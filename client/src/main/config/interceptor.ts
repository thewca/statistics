import { message } from "antd";
import axios, { AxiosError, AxiosRequestConfig } from "axios";
import { useContext } from "react";
import AuthContext from "../store/auth-context";

const authCtx = useContext(AuthContext);

const errorInterceptor = (response: AxiosError) => {
  if (response.response?.status === 404) {
    message.error("Not found");
  } else if (response.response?.status === 401) {
    authCtx.logout();
  }
  return Promise.reject(response);
};

const requestIntercetor = (item: AxiosRequestConfig) => {
  if (!!authCtx.token) {
    item.headers.Authorization = authCtx.token;
  }
  return item;
};

axios.interceptors.response.use(undefined, errorInterceptor);
axios.interceptors.request.use(requestIntercetor);
