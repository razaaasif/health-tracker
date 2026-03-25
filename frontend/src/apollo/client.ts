import { ApolloClient, InMemoryCache, HttpLink, ApolloLink } from "@apollo/client/core";
import { setContext } from "@apollo/client/link/context";

const GRAPHQL_URL = ((import.meta as unknown) as { env?: Record<string, string> }).env?.VITE_GRAPHQL_URL ||
  "http://localhost:8080/graphql";

// Http link to the GraphQL endpoint
const httpLink = new HttpLink({ uri: GRAPHQL_URL });

// Auth link to attach Authorization header (Bearer <token>) from localStorage
const authLink = setContext((_, previousContext) => {
  // previousContext may not have precise typing for headers from this package; treat as unknown and narrow
  const prev = previousContext as unknown as { headers?: Record<string, string> } | undefined;
  const headers = prev?.headers || {};

  let token: string | null = null;
  try {
    token = typeof window !== "undefined" ? localStorage.getItem("authToken") : null;
  } catch {
    token = null;
  }

  return {
    headers: {
      ...headers,
      Authorization: token ? `Bearer ${token}` : "",
    },
  };
});

export const client = new ApolloClient({
  link: authLink.concat(httpLink as unknown as ApolloLink),
  cache: new InMemoryCache(),
});

export default client;
