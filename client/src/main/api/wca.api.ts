import { deleteParameter, getHashParameter } from "../util/query.param.util";
import statisticsApi from "./statistics.api";

const ACCESS_TOKEN = "access_token";
const EXPIRES_IN = "expires_in";
const TOKEN_TYPE = "token_type";

export class WcaApi {
  accessToken: string | null;
  tokenType: string | null;
  expiresIn: number | null;

  constructor() {
    this.accessToken = getHashParameter(ACCESS_TOKEN);
    this.tokenType = getHashParameter(TOKEN_TYPE);
    this.expiresIn = null;

    if (!!this.accessToken) {
      let expiresIn = getHashParameter(EXPIRES_IN);
      let now = new Date();
      this.expiresIn = !!expiresIn
        ? now.setSeconds(now.getSeconds() + Number(expiresIn))
        : null;

      deleteParameter(ACCESS_TOKEN, EXPIRES_IN, TOKEN_TYPE);

      localStorage.setItem(ACCESS_TOKEN, this.accessToken);

      if (!!this.tokenType) {
        localStorage.setItem(TOKEN_TYPE, this.tokenType);
      }
      if (!!this.expiresIn) {
        localStorage.setItem(EXPIRES_IN, "" + this.expiresIn);
      }
    } else {
      this.accessToken = localStorage[ACCESS_TOKEN];
      this.tokenType = localStorage[TOKEN_TYPE];

      let expiresIn = localStorage[EXPIRES_IN];
      if (!!expiresIn) {
        this.expiresIn = Number(expiresIn);
      }
    }
  }

  handleLogin = () => {
    // We get the url from the server
    statisticsApi
      .getWcaAuthenticationUrl(window.location.href)
      .then((response) => {
        // Navigante to the url
        window.location.href = response.data;
      });
  };

  isLogged = () => {
    if (this.accessToken == null) {
      return false;
    }

    if (this.expiresIn == null) {
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
    delete localStorage[EXPIRES_IN];
    this.accessToken = null;
    this.tokenType = null;
    this.expiresIn = null;
    window.location.reload();
  };
}

const wcaApi = new WcaApi();
export default wcaApi;
