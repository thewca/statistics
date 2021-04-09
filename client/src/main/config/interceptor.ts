import { AxiosError } from "axios";
import wcaApi from "../api/wca.api";

export const interceptorError = (response: AxiosError) => {
  if (response.response?.status === 404) {
    window.location.href = "/not-found";
  } else if (response.response?.status === 401) {
    wcaApi.logout();
  }
  return response;
};
