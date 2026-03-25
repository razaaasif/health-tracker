import axios from 'axios';
import type { RegisterRequest, ApiResponse, AuthResponse } from '../types/auth';

const API_URL = 'http://localhost:8080/api/auth';

export const registerUser = async (
  data: RegisterRequest,
): Promise<ApiResponse> => {
  const response = await axios.post<ApiResponse>(`${API_URL}/register`, data);
  return response.data;
};

export const loginUser = async (
  email: string,
  password: string,
): Promise<AuthResponse> => {
  const res = await axios.post(`${API_URL}/login`, {
    email,
    password,
  });
  return res.data;
};