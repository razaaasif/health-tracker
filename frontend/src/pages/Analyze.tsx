import React, { useState } from "react";
import { useLazyQuery } from "@apollo/client/react";
import { ANALYZE_QUERY } from "../services/graphqlUsers";

const Analyze: React.FC = () => {
  const [patientId, setPatientId] = useState("");
  const [fetch, { data, loading, error }] = useLazyQuery<{ analyze: string }>(ANALYZE_QUERY);

  const submit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!patientId) return;
    fetch({ variables: { patientId } });
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Analyze Patient</h2>
      <form onSubmit={submit}>
        <input value={patientId} onChange={(e) => setPatientId(e.target.value)} placeholder="patient id" />
        <button type="submit">Analyze</button>
      </form>

      {loading && <div>Analyzing…</div>}
      {error && <div>Error: {String(error)}</div>}
      {data && <div><h4>Result</h4><pre>{data.analyze}</pre></div>}
    </div>
  );
};

export default Analyze;
