import React, { useState } from "react";
import { useLazyQuery } from "@apollo/client/react";
import { USERS_BY_ROLE_QUERY } from "../services/graphqlUsers";

const UsersByRole: React.FC = () => {
  const [role, setRole] = useState("");
  const [fetch, { data, loading, error }] = useLazyQuery<{ usersByRole: Array<{ id: string; email: string; roles: string[] }> }>(USERS_BY_ROLE_QUERY);

  const submit = (e: React.FormEvent) => {
    e.preventDefault();
    fetch({ variables: { role } });
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Users by role</h2>
      <form onSubmit={submit}>
        <input value={role} onChange={(e) => setRole(e.target.value)} placeholder="role" />
        <button type="submit">Fetch</button>
      </form>

      {loading && <div>Loading…</div>}
      {error && <div>Error: {String(error)}</div>}
      {data && (
        <ul>
          {data.usersByRole.map((u) => (
            <li key={u.id}>{u.email} — {u.roles.join(", ")}</li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default UsersByRole;
