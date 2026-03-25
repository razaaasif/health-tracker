import React from "react";
import { Link } from "react-router-dom";
import { useQuery } from "@apollo/client/react";
import { ALL_USERS_QUERY } from "../services/graphqlUsers";

type User = { id: string; email: string; enabled?: boolean; roles?: string[]; createdOn?: string };

const Users: React.FC = () => {
  const { data, loading, error } = useQuery<{ allUsers: User[] }>(ALL_USERS_QUERY);

  if (loading) return <div>Loading users…</div>;
  if (error) return <div>Error loading users: {String(error)}</div>;

  return (
    <div style={{ padding: 24 }}>
      <h2>All users</h2>
      <ul>
        {data?.allUsers.map((u: User) => (
          <li key={u.id}>
            <Link to={`/users/${u.id}`}>{u.email}</Link> — {u.roles?.join(", ")}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Users;
