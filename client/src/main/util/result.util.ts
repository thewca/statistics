export type ResultType = "average" | "single";

const getMbldResult = (result: number) => {
  let resultString = "" + result;

  let missed = Number(
    resultString.slice(resultString.length - 2, resultString.length),
  );
  let DD = Number(resultString.slice(0, 2));
  let difference = 99 - DD;
  let solved: number = difference + missed;
  let attempted: number = solved + missed;

  let seconds = Number(resultString.slice(2, 7));

  let time = timeFormat(seconds * 100);
  if (time.endsWith(".00")) {
    time = time.slice(0, time.length - 3);
  }
  return [solved, attempted, time];
};

export const getMbldPoints = (result: number) => {
  const [solved, attempted] = getMbldResult(result);
  return 2 * (solved as number) - (attempted as number);
};

const formatResult = (
  result: number,
  event_id: string,
  type: ResultType,
): string => {
  if (result == null) {
    return "";
  }
  if (result === -2) {
    return "DNS";
  }
  if (result === -1) {
    return "DNF";
  }
  if (result === 0) {
    return "-";
  }

  if (event_id === "333fm") {
    if (type === "average") {
      return (result / 100).toFixed(2);
    }
    return "" + result;
  }
  if (event_id === "333mbf") {
    if (type === "single") {
      const [solved, attempted, time] = getMbldResult(result);
      return `${solved}/${attempted} (${time})`;
    }
  }
  return timeFormat(result);
};

const timeFormat = (result: number) => {
  let hours = Math.floor(result / 360000);
  result %= 360000;

  let minutes = Math.floor(result / 6000);
  result %= 6000;

  let seconds = Math.floor(result / 100);
  result %= 100;

  let results: any[] = [];

  if (hours > 0) {
    results.push(hours);
    results.push(padLeft(minutes, 2));
    results.push(padLeft(seconds, 2) + "." + padLeft(result, 2));
  } else if (minutes > 0) {
    results.push(minutes);
    results.push(padLeft(seconds, 2) + "." + padLeft(result, 2));
  } else if (seconds > 0) {
    results.push(seconds + "." + padLeft(result, 2));
  } else {
    results.push(0 + "." + padLeft(result, 2));
  }

  return results.join(":");
};

const padLeft = (result: number, totalSize: number) => {
  let out = result.toString();
  while (out.length < totalSize) {
    out = "0" + out;
  }
  return out;
};

export default formatResult;
