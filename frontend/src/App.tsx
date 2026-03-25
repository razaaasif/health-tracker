import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Users from "./pages/Users";
import UserDetail from "./pages/UserDetail";
import UsersByRole from "./pages/UsersByRole";
import Analyze from "./pages/Analyze";
import SaveHealthLog from "./pages/SaveHealthLog";
import Layout from "./components/Layout";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/register" replace />} />

        {/* Public routes */}
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />

        {/* Protected / main app routes inside the Layout */}
        <Route
          path="/"
          element={<Layout />}
        >
          <Route index element={<Navigate to="/users" replace />} />
          <Route path="users" element={<Users />} />
          <Route path="users/:id" element={<UserDetail />} />
          <Route path="users/by-role" element={<UsersByRole />} />
          <Route path="analyze" element={<Analyze />} />
          <Route path="health/save" element={<SaveHealthLog />} />
        </Route>

        {/* catch-all: redirect unknown paths to register (change to a 404 page if desired) */}
        <Route path="*" element={<Navigate to="/register" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;