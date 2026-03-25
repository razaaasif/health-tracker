import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Layout: React.FC<{ children?: React.ReactNode }> = ({ children }) => {
  const navigate = useNavigate();

  let user: { email?: string } | null = null;
  try {
    const raw = localStorage.getItem("authUser");
    user = raw ? JSON.parse(raw) : null;
  } catch {
    user = null;
  }

  const logout = () => {
    localStorage.removeItem("authToken");
    localStorage.removeItem("authUser");
    navigate("/login");
  };

  return (
    <div>
      <header style={{ display: "flex", alignItems: "center", justifyContent: "space-between", padding: 12, borderBottom: "1px solid #eee" }}>
        <div style={{ display: "flex", gap: 12, alignItems: "center" }}>
          <Link to="/users" style={{ fontWeight: 700, textDecoration: "none" }}>Health Tracker</Link>

          <nav style={{ display: "flex", gap: 8 }}>
            <Link to="/users">Users</Link>
            <Link to="/users/by-role">Users by role</Link>
            <Link to="/analyze">Analyze</Link>
            <Link to="/health/save">Save health log</Link>
          </nav>
        </div>

        <div style={{ display: "flex", gap: 12, alignItems: "center" }}>
          {user?.email ? <span style={{ fontSize: 14 }}>Signed in as <strong>{user.email}</strong></span> : <Link to="/login">Login</Link>}
          {user?.email && <button onClick={logout}>Logout</button>}
        </div>
      </header>

      <main style={{ padding: 12 }}>{children}</main>
    </div>
  );
};

export default Layout;
