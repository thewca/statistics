import { fireEvent } from "@testing-library/dom";
import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import statisticsApi from "../main/api/statistics.api";
import DatabaseQuery from "../main/pages/DatabaseQuery";
import { defaultQueryResponse } from "./DatabaseQuery.test.mock";

let container = document.createElement("div");
beforeEach(() => {
  // setup a DOM element as a render target
  container = document.createElement("div");
  document.body.appendChild(container);

  // https://jestjs.io/docs/en/manual-mocks#mocking-methods-which-are-not-implemented-in-jsdom
  Object.defineProperty(window, "matchMedia", {
    writable: true,
    value: jest.fn().mockImplementation((query) => ({
      matches: false,
      media: query,
      onchange: null,
      addListener: jest.fn(), // deprecated
      removeListener: jest.fn(), // deprecated
      addEventListener: jest.fn(),
      removeEventListener: jest.fn(),
      dispatchEvent: jest.fn(),
    })),
  });
});

afterEach(() => {
  // cleanup on exiting
  unmountComponentAtNode(container);
  container.remove();
  container = document.createElement("div");
});

// This is also used for custom query in redirect
it("If the user types query with :ABC, there should be an input to replace it", async () => {
  let query = `select
    *
from
    Results
where
    eventId = ':EVENT_ID'
    or countryId = ':COUNTRY_ID'`;

  const axiosResponse = {
    status: 200,
    statusText: "OK",
    config: {},
    headers: {},
  };

  let searchQuery = "";

  jest
    .spyOn(statisticsApi, "queryDatabase")
    .mockImplementation((query, page, size) => {
      searchQuery = query;
      return Promise.resolve({ ...axiosResponse, data: defaultQueryResponse });
    });

  // Render component
  await act(async () => {
    render(
      <React.StrictMode>
        <DatabaseQuery />
      </React.StrictMode>,
      container
    );
  });

  let textArea = container.querySelector("textarea")!;
  expect(textArea).toBeDefined();

  await act(async () => {
    fireEvent.change(textArea, { target: { value: query } });
  });

  let databaseQueryPage = container.querySelector("#database-query-wrapper")!;

  let inputs = Array.from(databaseQueryPage.querySelectorAll("input")!);
  expect(inputs.length).toBe(2);

  let event = "333fm";
  let country = "Brazil";
  await act(async () => {
    fireEvent.change(inputs[0], { target: { value: country } });
    fireEvent.change(inputs[1], { target: { value: event } });
  });

  let submitButton = Array.from(
    databaseQueryPage.querySelectorAll("button")
  ).find((btn) => btn.innerHTML.includes("Submit"))!;
  await act(async () => {
    fireEvent.click(submitButton);
  });

  // Searched query should replace inputs
  expect(searchQuery).toEqual(
    query.replace(":EVENT_ID", event).replace(":COUNTRY_ID", country)
  );
});
