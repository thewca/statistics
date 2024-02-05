import statisticsApi from "./statistics.api";

export class WcaApi {
  login = () => {
    // We get the url from the server
    statisticsApi
      .getWcaAuthenticationUrl(
        window.location.protocol + "//" + window.location.host,
        // Redirect is encoded to base 64 to avoid problems with query strings
        btoa(window.location.href)
      )
      .then((response) => {
        // Navigante to the url
        window.location.href = response.data.url;
      });
  };
}

const wcaApi = new WcaApi();
export default wcaApi;
