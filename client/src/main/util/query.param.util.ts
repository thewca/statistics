export const getParameter = (name: string, type: "hash") => {
  let params = new URLSearchParams(window.location[type]);
  return params.get(name);
};

export const getQueryParameter = (name: string) => {
  let params = new URLSearchParams(window.location.search);
  return params.get(name);
};

export const getHashParameter = (name: string) => {
  handleHash();
  return getQueryParameter(name);
};

export const setQueryParameter = (name: string, value: string | number) => {
  let searchParams = new URLSearchParams(window.location.search);
  searchParams.set(name, "" + value);
  updateUrl(searchParams);
};

export const deleteParameter = (...names: string[]) => {
  let searchParams = new URLSearchParams(window.location.search);
  names.forEach((name) => searchParams.delete(name));
  updateUrl(searchParams);
};

const updateUrl = (searchParams: URLSearchParams) => {
  let currentLocationWithoutQuery = window.location.href.split("?")[0];
  let url = currentLocationWithoutQuery + "?" + searchParams.toString();
  window.history.pushState(null, "", url);
};

const handleHash = () => {
  // Replace hash with query
  let currentLocation = window.location.href.replaceAll("#", "?");

  // If we end up with multiple, '?', we replace the other ones
  let index = currentLocation.indexOf("?");

  for (let i = index + 1; i < currentLocation.length; i++) {
    if (currentLocation.charAt(i) === "?") {
      currentLocation =
        currentLocation.substr(0, i) +
        "&" +
        currentLocation.substr(i + 1, currentLocation.length);
    }
  }

  window.history.pushState(null, "", currentLocation);
};
