export const getQueryParameter = (name: string) => {
  let searchParams = new URLSearchParams(window.location.search);
  return searchParams.get(name);
};

export const setQueryParameter = (name: string, value: string | number) => {
  let searchParams = new URLSearchParams(window.location.search);
  searchParams.set(name, "" + value);
  let currentLocationWithoutQuery = window.location.href.split("?")[0];
  let url = currentLocationWithoutQuery + "?" + searchParams.toString();
  window.history.pushState(null, "", url);
};
