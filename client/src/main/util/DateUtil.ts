export const millsToDate = (mills: number) =>
  new Date(mills).toISOString().split("T")[0];
