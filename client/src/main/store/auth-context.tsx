import React, { useEffect, useState } from "react";
import wcaApi from "../api/wca.api";
import UserInfo from "../model/UserInfo";
import { deleteParameter, getHashParameter } from "../util/query.param.util";

const ACCESS_TOKEN = "access_token";
const EXPIRES_IN = "expires_in";
const TOKEN_TYPE = "token_type";
const USER_INFO = "user_info";

const AuthContext = React.createContext({
  token: "",
  isLogged: false,
  login: () => {},
  logout: () => {},
});

let now = new Date();

let hashAccessToken = getHashParameter(ACCESS_TOKEN);
let hashTokenType = getHashParameter(TOKEN_TYPE);
let hashExpiresIn = Number(getHashParameter(EXPIRES_IN));

let initialToken = "";
let initialExpiresIn = Number(now);

if (!!hashAccessToken && !!hashTokenType) {
  deleteParameter(ACCESS_TOKEN, EXPIRES_IN, TOKEN_TYPE);
  initialToken = `${hashTokenType} ${hashAccessToken}`;
}

if (!!hashExpiresIn) {
  initialExpiresIn = now.setSeconds(now.getSeconds() + Number(hashExpiresIn));
}

export const AuthContextProvider = (props: any) => {
  const [token, setToken] = useState(initialToken);
  const [userInfo, setUserInfo] = useState<UserInfo>();
  const [expiresIn, setExpiresIn] =
    useState<number | undefined>(initialExpiresIn);
  const [isLogged, setIsLogged] = useState(false);

  const loginHandler = () => {
    wcaApi.login();
  };

  const logoutHandler = () => {
    delete localStorage[ACCESS_TOKEN];
    delete localStorage[TOKEN_TYPE];
    delete localStorage[USER_INFO];
    delete localStorage[EXPIRES_IN];
    setToken("");
    setUserInfo(undefined);
    setExpiresIn(undefined);

    // Return to home after logout
    // TODO do this only for pages that requires login
    window.location.href = "/";
  };

  useEffect(() => {
    if (!token || !expiresIn) {
      return;
    }

    setIsLogged(true);

    if (new Date() < new Date(expiresIn)) {
      setIsLogged(true);
      return;
    }

    // logoutHandler();
  }, [token, expiresIn]);

  const contextValue = {
    token,
    isLogged,
    login: loginHandler,
    logout: logoutHandler,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
