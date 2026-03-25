import { gql } from "@apollo/client/core";

export const ALL_USERS_QUERY = gql`
  query AllUsers {
    allUsers {
      id
      email
      enabled
      roles
      createdOn
    }
  }
`;

export const USER_QUERY = gql`
  query User($id: ID!) {
    user(id: $id) {
      id
      email
      enabled
      roles
      createdOn
    }
  }
`;

export const USERS_BY_ROLE_QUERY = gql`
  query UsersByRole($role: String!) {
    usersByRole(role: $role) {
      id
      email
      roles
    }
  }
`;

export const ANALYZE_QUERY = gql`
  query Analyze($patientId: ID!) {
    analyze(patientId: $patientId)
  }
`;

export const SAVE_HEALTH_LOG_MUTATION = gql`
  mutation SaveHealthLog($input: SaveHealthLogInput!) {
    saveHealthLog(input: $input)
  }
`;
