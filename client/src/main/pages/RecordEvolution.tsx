interface RecordEvolutionProps {}

export const RecordEvolution = ({}: RecordEvolutionProps) => {
  return (
    <div>
      <h1 className="page-title">Record Evolution</h1>
      <p>
        WCA Statistics is a collection of interesting analysis over the WCA's
        database.
      </p>
      <p>
        <strong>Version:</strong> {process.env.REACT_APP_VERSION}
      </p>
      <p>
        <strong>Date:</strong> {process.env.REACT_APP_DATE_VERSION}
      </p>
    </div>
  );
};
