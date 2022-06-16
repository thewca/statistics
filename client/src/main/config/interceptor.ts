import { message } from "antd";
import { AxiosError, AxiosRequestConfig } from "axios";
import {
  checkIsLogged,
  deleteStorageItems,
  EXPIRES_IN,
  TOKEN,
} from "../store/auth-context";

export const errorInterceptor = (response: AxiosError) => {
  if (response.response?.status === 404) {
    message.error("Not found");
  } else if (response.response?.status === 401) {
    // Refresh screen so the login process can start
    deleteStorageItems();
  } else if (response.response?.status === 400) {
    let errors = extractMessages(response);
    errors.forEach((errorMessage) => message.error(errorMessage));
  }
  return Promise.reject(response);
};

export const requestIntercetor = (item: AxiosRequestConfig) => {
  let token = localStorage.getItem(TOKEN);
  let expiresIn = localStorage.getItem(EXPIRES_IN);
  if (checkIsLogged(token, expiresIn)) {
    item.headers.Authorization = token;
  }
  return item;
};

const extractMessages = (object: any, errors: string[] = []): string[] => {
  for (let key in object) {
    if (typeof object[key] === "object") {
      let res = extractMessages(object[key]);
      errors.push(...res);
    }
    if (key === "message") {
      if (typeof object[key] === "string") {
        errors.push(object[key]);
      } else {
        errors.push(JSON.stringify(object[key]));
      }
    }
  }
  return errors;
};
