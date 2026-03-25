import React, { useState } from "react";
import Input from "../components/Input";
import { useMutation } from "@apollo/client/react";
import { REGISTER_MUTATION } from "../services/graphqlAuth";
import type { RegisterRequest } from "../types/auth";
import "./Register.css";

const Register: React.FC = () => {

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: ""
  });

  const [message, setMessage] = useState("");

  const [registerMutation, { loading }] = useMutation<{ register: string }, { input: RegisterRequest }>(
    REGISTER_MUTATION
  );

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const res = await registerMutation({ variables: { input: form } });
      setMessage("✅ " + (res.data?.register ?? "Registered"));
    } catch (err: unknown) {
      if (err instanceof Error) setMessage("❌ " + err.message);
      else setMessage("❌ Registration failed");
    }
  };

  return (
    <div className="register-page">
      <div className="register-card">
        <div className="register-header">
          <h2>Create your account</h2>
          <div className="register-sub">Create an account to track your health goals.</div>
        </div>

        <form onSubmit={handleSubmit}>
          <Input label="First Name" name="firstName" value={form.firstName} onChange={handleChange} />
          <Input label="Last Name" name="lastName" value={form.lastName} onChange={handleChange} />
          <Input label="Email" name="email" value={form.email} onChange={handleChange} />
          <Input label="Password" name="password" type="password" value={form.password} onChange={handleChange} />

          <div className="register-actions">
            <button className="ht-btn" type="submit" disabled={loading}>
              {loading ? "Registering..." : "Create account"}
            </button>
          </div>
        </form>

        <p className={`register-message ${message.startsWith("✅") ? "success" : message ? "error" : ""}`}>
          {message}
        </p>

        <p style={{ marginTop: 10 }}>
          Already have an account? <a href="/login">Login</a>
        </p>
      </div>
    </div>
  );
};

export default Register;