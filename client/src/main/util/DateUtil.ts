export const millsToDate = (mills: number) => {
  return new Date(mills).toISOString().split("T")[0];
};
