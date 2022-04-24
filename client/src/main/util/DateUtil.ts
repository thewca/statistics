export const formatToDate = (mills: number | string) =>
  new Date(mills).toISOString().split("T")[0];
