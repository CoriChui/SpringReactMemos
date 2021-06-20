import { useCallback } from 'react';
import jwt_decode from "jwt-decode";

export const useDecode = () => {
  const decode = useCallback(token => {
    return jwt_decode(token)
  }, [])

  return { decode }
}
