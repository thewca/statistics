export const millsToDate = (mills: number | string) =>
  new Date(mills).toISOString().split("T")[0];
