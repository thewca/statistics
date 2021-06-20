import React, { useEffect, useState } from "react";
import statisticsApi from "../api/statistics.api";
import wcaApi from "../api/wca.api";
import UserInfo from "../model/UserInfo";
import { deleteParameter, getHashParameter } from "../util/query.param.util";

const TOKEN_TYPE = "token_type";
const ACCESS_TOKEN = "access_token";
export const TOKEN = "token";
export const EXPIRES_IN = "expires_in";

interface AuthProps {
  token: string;
  userInfo?: UserInfo;
  isLogged: boolean;
  login: () => void;
  logout: () => void;
}

const AuthContext = React.createContext<AuthProps>({
  token: "",
  userInfo: undefined,
  isLogged: false,
  login: () => {},
  logout: () => {},
});

let now = new Date();

let hashAccessToken = getHashParameter(ACCESS_TOKEN);
let hashTokenType = getHashParameter(TOKEN_TYPE);
let hashExpiresIn = Number(getHashParameter(EXPIRES_IN));

let storageToken = localStorage[TOKEN];
let storageExpiresIn = localStorage[EXPIRES_IN];

let initialToken = "";
let initialExpiresIn = Number(now);

if (!!hashAccessToken && !!hashTokenType && !!hashExpiresIn) {
  deleteParameter(ACCESS_TOKEN, EXPIRES_IN, TOKEN_TYPE);

  initialToken = `${hashTokenType} ${hashAccessToken}`;
  initialExpiresIn = now.setSeconds(now.getSeconds() + Number(hashExpiresIn));

  localStorage.setItem(EXPIRES_IN, "" + initialExpiresIn);
  localStorage.setItem(TOKEN, initialToken);
} else if (!!storageToken && !!storageExpiresIn) {
  initialToken = storageToken;
  initialExpiresIn = Number(storageExpiresIn);
}

export const checkIsLogged = (
  token: string | null,
  expiresIn?: number | string | null
) => !!token && !!expiresIn && new Date() < new Date(Number(expiresIn));

const initialIsLogged = checkIsLogged(initialToken, initialExpiresIn);

export const AuthContextProvider = (props: any) => {
  const [token, setToken] = useState(initialToken);
  const [userInfo, setUserInfo] = useState<UserInfo>();
  const [isLogged, setIsLogged] = useState(initialIsLogged);

  useEffect(() => {
    if (isLogged && !userInfo) {
      statisticsApi
        .getUserInfo()
        .then((response) => setUserInfo(response.data));
    }
  }, [isLogged, userInfo]);

  const loginHandler = () => {
    wcaApi.login();
  };

  const logoutHandler = () => {
    setIsLogged(false);
    setToken("");
    setUserInfo(undefined);

    delete localStorage[TOKEN];
    delete localStorage[EXPIRES_IN];

    // Return to home after logout
    // TODO do this only for pages that requires login
    window.location.href = "/";
  };

  const contextValue = {
    token,
    isLogged,
    login: loginHandler,
    logout: logoutHandler,
    userInfo,
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
