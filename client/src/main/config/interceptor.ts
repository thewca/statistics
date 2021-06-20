import { message } from "antd";
import { AxiosError, AxiosRequestConfig } from "axios";
import { checkIsLogged, EXPIRES_IN, TOKEN } from "../store/auth-context";

export const errorInterceptor = (response: AxiosError) => {
  if (response.response?.status === 404) {
    message.error("Not found");
  } else if (response.response?.status === 401) {
    // Refresh screen so the login process can start
    window.location.href = "/";
  }
  return Promise.reject(response);
};

export const requestIntercetor = (item: AxiosRequestConfig) => {
  let token = localStorage.getItem(TOKEN);
  let expiresIn = localStorage.getItem(EXPIRES_IN);
  debugger;
  if (checkIsLogged(token, expiresIn)) {
    item.headers.Authorization = token;
  }
  return item;
};
