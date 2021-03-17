import { deleteParameter, getHashParameter } from "../util/query.param.util";
import statisticsApi from "./statistics.api";

const ACCESS_TOKEN = "access_token";
const EXPIRES_IN = "expires_in";
const TOKEN_TYPE = "token_type";
const USER_INFO = "user_info";

export class WcaApi {
  accessToken: string | null;
  tokenType: string | null;
  expiresIn: number | null;
  userInfo: any;

  constructor() {
    this.accessToken = getHashParameter(ACCESS_TOKEN);
    this.tokenType = getHashParameter(TOKEN_TYPE);
    this.expiresIn = null;
    this.userInfo = null;

    if (!!this.accessToken && !!this.tokenType) {
      let expiresIn = getHashParameter(EXPIRES_IN);
      let now = new Date();
      this.expiresIn = !!expiresIn
        ? now.setSeconds(now.getSeconds() + Number(expiresIn))
        : null;

      deleteParameter(ACCESS_TOKEN, EXPIRES_IN, TOKEN_TYPE);

      localStorage.setItem(ACCESS_TOKEN, this.accessToken);

      localStorage.setItem(TOKEN_TYPE, this.tokenType);

      if (!!this.expiresIn) {
        localStorage.setItem(EXPIRES_IN, "" + this.expiresIn);
      }
    } else {
      this.accessToken = localStorage[ACCESS_TOKEN];
      this.tokenType = localStorage[TOKEN_TYPE];
      this.userInfo = JSON.parse(localStorage[USER_INFO] || null);

      let expiresIn = localStorage[EXPIRES_IN];
      if (!!expiresIn) {
        this.expiresIn = Number(expiresIn);
      }
    }

    if (this.isLogged() && !this.userInfo) {
      statisticsApi
        .getUserInfo(this.accessToken!, this.tokenType!)
        .then((response) => {
          this.userInfo = response.data;
          localStorage.setItem(USER_INFO, JSON.stringify(this.userInfo));
        });
    }
  }

  handleLogin = () => {
    if (this.isLogged()) {
      return;
    }

    // We get the url from the server
    statisticsApi
      .getWcaAuthenticationUrl(window.location.href)
      .then((response) => {
        // Navigante to the url
        window.location.href = response.data;
      });
  };

  isLogged = () => {
    if (!this.accessToken) {
      return false;
    }

    if (!this.expiresIn) {
      return false;
    }

    if (new Date() < new Date(this.expiresIn)) {
      return true;
    }

    this.logout();
    return false;
  };

  logout = () => {
    delete localStorage[ACCESS_TOKEN];
    delete localStorage[TOKEN_TYPE];
    delete localStorage[USER_INFO];
    delete localStorage[EXPIRES_IN];
    this.accessToken = null;
    this.tokenType = null;
    this.userInfo = null;
    this.expiresIn = null;
  };

  getUserInfo = () => this.userInfo;
}

const wcaApi = new WcaApi();
export default wcaApi;
