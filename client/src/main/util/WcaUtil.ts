import { APP_CLIENT_ID, WCA_BASE_URL } from "../config/EnvVarConfig";

export const getPersonLink = (wcaId: string) =>
  `https://www.worldcubeassociation.org/persons/${wcaId}`;

// 2222XXXX22
export const WCA_ID_MAX_LENGTH = 10;

export const getWcaAuthenticationUrl = () => {
  const host = window.location.protocol + "//" + window.location.host;
  const redirect = window.location.href;
  return `${WCA_BASE_URL}/oauth/authorize?client_id=${APP_CLIENT_ID}&redirect_uri=${host}?redirect=${redirect}&response_type=token&scope=public`;
};
