import { message } from "antd";
import { AxiosError, AxiosRequestConfig } from "axios";
import wcaApi from "../api/wca.api";

export const errorInterceptor = (response: AxiosError) => {
  if (response.response?.status === 404) {
    message.error("Not found");
  } else if (response.response?.status === 401) {
    wcaApi.logout();
  }
  return response;
};

export const requestIntercetor = (item: AxiosRequestConfig) => {
  let tokenType = wcaApi.tokenType;
  let accessToken = wcaApi.accessToken;
  if (!!accessToken && !! tokenType){
    item.headers.Authorization = `${tokenType} ${accessToken}`
  }
  return item;
}
