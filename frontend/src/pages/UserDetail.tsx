import React from "react";
import { useParams } from "react-router-dom";
import { useQuery } from "@apollo/client/react";
import { USER_QUERY } from "../services/graphqlUsers";

type User = { id: string; email: string; enabled?: boolean; roles?: string[]; createdOn?: string };

const UserDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data, loading, error } = useQuery<{ user: User }>(USER_QUERY, { variables: { id } });

  if (loading) return <div>Loading…</div>;
  if (error) return <div>Error: {String(error)}</div>;

  const u = data?.user;

  if (!u) return <div>User not found</div>;

  return (
    <div style={{ padding: 24 }}>
      <h2>{u.email}</h2>
      <p>Enabled: {String(u.enabled)}</p>
      <p>Roles: {u.roles?.join(", ")}</p>
      <p>Created: {u.createdOn}</p>
    </div>
  );
};

export default UserDetail;
