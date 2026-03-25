/* eslint-disable @typescript-eslint/no-explicit-any */
declare module "@apollo/client" {
  import * as React from "react";

  // Minimal type shims so TypeScript accepts the React bindings from @apollo/client in this project.
  // These are intentionally permissive; replace with stricter types if you add full Apollo typings.

  export const ApolloProvider: React.FC<{ client: any; children?: React.ReactNode }>;
  export function useMutation<TData = any, TVariables = any>(mutation: any): [
    (options?: { variables?: TVariables }) => Promise<{ data?: TData }>,
    { loading: boolean; error?: any; data?: TData }
  ];
  export function useQuery<TData = any>(query: any, options?: any): {
    data?: TData;
    loading: boolean;
    error?: any;
  };
  export function useLazyQuery<TData = any>(query: any): [
    (options?: any) => Promise<{ data?: TData }>,
    { loading: boolean; error?: any; data?: TData }
  ];

  export const gql: any;
  export const ApolloClient: any;
  export const InMemoryCache: any;
  export const HttpLink: any;
}

declare module "@apollo/client/react" {
  import * as React from "react";
  export const ApolloProvider: React.FC<{ client: any; children?: React.ReactNode }>;
  export function useMutation<TData = any, TVariables = any>(mutation: any): [
    (options?: { variables?: TVariables }) => Promise<{ data?: TData }>,
    { loading: boolean; error?: any; data?: TData }
  ];
  export function useQuery<TData = any>(query: any, options?: any): {
    data?: TData;
    loading: boolean;
    error?: any;
  };
  export function useLazyQuery<TData = any>(query: any): [
    (options?: any) => Promise<{ data?: TData }>,
    { loading: boolean; error?: any; data?: TData }
  ];
}

