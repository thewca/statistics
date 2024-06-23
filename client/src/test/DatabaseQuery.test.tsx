import { fireEvent, render, screen } from "@testing-library/react";
import React, { act } from "react";
import { beforeEach, describe, expect, it, vi } from "vitest";
import databaseQueryApi from "../main/api/DatabaseQueryApi";
import { DatabaseQueryPage } from "../main/pages/DatabaseQueryPage";

beforeEach(() => {
  // https://jestjs.io/docs/en/manual-mocks#mocking-methods-which-are-not-implemented-in-jsdom
  Object.defineProperty(window, "matchMedia", {
    writable: true,
    value: vi.fn().mockImplementation((query) => ({
      matches: false,
      media: query,
      onchange: null,
      addListener: vi.fn(), // deprecated
      removeListener: vi.fn(), // deprecated
      addEventListener: vi.fn(),
      removeEventListener: vi.fn(),
      dispatchEvent: vi.fn(),
    })),
  });
});

let query = `select
    *
from
    Results
where
    eventId = ':EVENT_ID'
    or countryId = ':COUNTRY_ID'`;
const event = "333fm";
const country = "brazil";

describe("Database query page", () => {
  it("Should accept placeholders and submit them with the query", async () => {
    const apiCall = vi.spyOn(databaseQueryApi, "queryDatabase");

    render(
      <React.StrictMode>
        <DatabaseQueryPage />
      </React.StrictMode>,
    );

    const textArea = screen.getByTestId("query-input");
    expect(textArea).toBeDefined();

    fireEvent.change(textArea, { target: { value: query } });

    const inputs = screen.getAllByTestId("replace-item");
    expect(inputs.length).toBe(2);

    fireEvent.change(inputs[0], { target: { value: country } });
    fireEvent.change(inputs[1], { target: { value: event } });

    const submitButton = screen.getByTestId("submit-button");
    expect(submitButton).toBeDefined();

    await act(async () => {
      fireEvent.click(submitButton);
    });

    expect(apiCall).toHaveBeenLastCalledWith(
      query.replace(":EVENT_ID", event).replace(":COUNTRY_ID", country),
      0,
      20,
    );
  });
});
