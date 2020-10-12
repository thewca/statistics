import React from "react";
import { act } from "react-dom/test-utils";

import { render, unmountComponentAtNode } from "react-dom";

import App from "./App";

let container = null;

beforeEach(() => {
  // setup a DOM element as a render target
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  // cleanup on exiting
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("Just render", () => {
  // Render component
  act(() => {
    render(<App />, container);
  });

  // Menu shows some links
  const links = container.querySelectorAll("a");

  expect(links).not.toBe(null);
});
