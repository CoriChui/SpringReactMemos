import React, { useState } from 'react';
import { useHistory } from "react-router-dom";
import axios from 'axios'
import { useAuth } from '../context/AuthProvider'


const Login = () => {

  const history = useHistory()
  const { login } = useAuth()

  const initialState = {
    email: "",
    password: ""
  }

  const [error, setError] = useState("")
  const [loginDetails, setLoginDetails] = useState(initialState)

  console.log("login rendered")

  const onChangeHandler = e => {
    const { name, value } = e.target
    setLoginDetails({ ...loginDetails, [name]: value })
  }

  const onLoginHandler = async (e) => {
    e.preventDefault()
    try {
      const { data } = await axios.post("/api/v1/auth/login", loginDetails)
      login(data)
      history.push('/')
    } catch (error) {
      setError(error.response.data.message)
    }
  }


  return (
    <div className="container" style={{ marginTop: 100 }}>
      <form className="row col-lg-4 col-md-6 mx-auto text-center" onSubmit={onLoginHandler}>
        <div className="mb-3">
          <label htmlFor="Email" className="form-label">Email</label>
          <input
            type="email"
            name="email"
            className="form-control"
            id="Email"
            required
            onChange={onChangeHandler}
            value={loginDetails.email} />
        </div>
        <div className="mb-3">
          <label htmlFor="Password" className="form-label">Password</label>
          <input
            type="password"
            name="password"
            className="form-control"
            id="Password"
            required
            onChange={onChangeHandler}
            value={loginDetails.password}
            autoComplete="off" />
        </div>
        <button type="submit" className="btn btn-primary text-light mt-4">Login</button>
        <p className="mt-3">{error}</p>
      </form>
    </div>
  )
}

export default Login