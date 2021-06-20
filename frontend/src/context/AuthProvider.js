import { useContext, createContext, useCallback, useEffect, useState } from 'react';
import { useDecode } from '../hooks/jwt.hook'

const tokenStorageName = 'authToken'
const idStorageName = 'authId'

const AuthContext = createContext()

export const useAuth = () => {
  return useContext(AuthContext)
}

const AuthProvider = ({ children }) => {

  const [token, setToken] = useState(null)
  const [userId, setUserId] = useState(null)
  const { decode } = useDecode()

  const login = useCallback((jwtToken) => {
    const userId = decode(jwtToken).iss
    setToken(jwtToken)
    setUserId(userId)
    localStorage.setItem(tokenStorageName, jwtToken)
    localStorage.setItem(idStorageName, userId)
  }, [decode])

  const logout = useCallback(() => {
    setToken(null)
    setUserId(null)
    localStorage.removeItem(tokenStorageName)
    localStorage.removeItem(idStorageName)
  }, [])

  console.log("authProvider rendered")

  useEffect(() => {
    let token = localStorage.getItem(tokenStorageName)
    let userId = localStorage.getItem(idStorageName)
    setToken(token)
    setUserId(userId)
  }, [])

  return (
    <AuthContext.Provider value={{ login, logout, token, userId }}>
      {children}
    </AuthContext.Provider>
  )
}

export default AuthProvider