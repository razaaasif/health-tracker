import React, { useState } from "react";
import { loginUser } from "../services/authService";
import type { LoginRequest } from "../types/auth";
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';

const Login: React.FC = () => {
  const [form, setForm] = useState<LoginRequest>({
    email: "",
    password: ""
  });

  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setLoading(true);

      const auth = await loginUser(form.email, form.password);

      if (auth?.token) {
        localStorage.setItem("authToken", auth.token);
        localStorage.setItem("authUser", JSON.stringify(auth.user));

        setMessage("✅ Logged in");
        navigate("/users");
      } else {
        setMessage("❌ Login failed");
      }

    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        setMessage("❌ " + (err.response?.data?.message || err.message));
      } else {
        setMessage("❌ " + (err as Error).message);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: "40px auto", padding: 20 }}>
      <h2>Login</h2>

      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: 12 }}>
          <label>Email</label>
          <input
            name="email"
            value={form.email}
            onChange={handleChange}
            style={{ width: "100%", padding: 8 }}
          />
        </div>

        <div style={{ marginBottom: 12 }}>
          <label>Password</label>
          <input
            name="password"
            type="password"
            value={form.password}
            onChange={handleChange}
            style={{ width: "100%", padding: 8 }}
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>
      </form>

      <p>{message}</p>

      <p>
        Don't have an account? <Link to="/register">Register</Link>
      </p>
    </div>
  );
};

export default Login;