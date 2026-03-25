import React, { useState } from "react";
import { useMutation } from "@apollo/client/react";
import { SAVE_HEALTH_LOG_MUTATION } from "../services/graphqlUsers";

const SaveHealthLog: React.FC = () => {
  const [form, setForm] = useState({
    patientId: "",
    steps: 0,
    calories: 0,
    protein: 0,
    water: 0,
  });

  const [saveHealthLog, { loading, error, data }] = useMutation<{ saveHealthLog: string }, unknown>(
    SAVE_HEALTH_LOG_MUTATION
  );

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: name === "patientId" ? value : Number(value) }));
  };

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    await saveHealthLog({ variables: { input: form } });
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Save Health Log</h2>
      <form onSubmit={submit}>
        <div style={{ marginBottom: 8 }}>
          <label>Patient ID</label><br />
          <input name="patientId" value={form.patientId} onChange={handleChange} />
        </div>

        <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 8 }}>
          <div>
            <label>Steps</label><br />
            <input name="steps" type="number" value={form.steps} onChange={handleChange} />
          </div>
          <div>
            <label>Calories</label><br />
            <input name="calories" type="number" value={form.calories} onChange={handleChange} />
          </div>
        </div>

        <div style={{ marginTop: 12 }}>
          <button type="submit" disabled={loading}>{loading ? "Saving…" : "Save"}</button>
        </div>
      </form>

      {error && <div style={{ color: "red" }}>Error: {String(error)}</div>}
      {data && <div style={{ color: "green" }}>Saved: {data.saveHealthLog}</div>}
    </div>
  );
};

export default SaveHealthLog;
