import statisticsApi from "./statistics.api";

export class WcaApi {

  login = () => {
    // We get the url from the server
    statisticsApi
      .getWcaAuthenticationUrl(window.location.href)
      .then((response) => {
        // Navigante to the url
        window.location.href = response.data;
      });
  };

}

const wcaApi = new WcaApi();
export default wcaApi;
