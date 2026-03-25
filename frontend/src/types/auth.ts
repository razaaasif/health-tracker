export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface ApiResponse {
  message: string;
}

export interface User {
  id: string;
  email: string;
  enabled: boolean;
  roles: string[];
  createdOn: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface LoginRequest {
  email: string;
  password: string;
}