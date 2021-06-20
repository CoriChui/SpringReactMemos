import React, { useState } from 'react';
import axios from 'axios'

const Register = () => {
  const initialState = {
    email: "",
    firstName: "",
    lastName: "",
    password: "",
    passwordMatch: "",
  }

  const [response, setResponse] = useState("")
  const [registrationDetails, setRegistrationDetails] = useState(initialState)

  console.log("register rendered")

  const onChangeHandler = e => {
    const { name, value } = e.target
    setRegistrationDetails({ ...registrationDetails, [name]: value })
  }

  const onRegisterHandler = async (e) => {
    e.preventDefault()
    try {
      const { data } = await axios.post("/api/v1/auth/register", registrationDetails, {
        header: {
          "Content-Type": "application/json"
        }
      })
      setResponse(data)
    } catch (error) {
      setResponse(error.response.data.message)
    }
  }

  return (
    <div className="container" style={{ marginTop: 100 }}>
      <form className="row col-lg-4 col-md-6 mx-auto text-center" onSubmit={onRegisterHandler}>
        <div className="mb-3">
          <label htmlFor="Email" className="form-label">Email</label>
          <input
            type="email"
            name="email"
            className="form-control"
            id="Email"
            required
            onChange={onChangeHandler}
            value={registrationDetails.email} />
        </div>
        <div className="mb-3">
          <label htmlFor="FirstName" className="form-label">First Name</label>
          <input
            type="text"
            name="firstName"
            className="form-control"
            id="FirstName"
            required
            onChange={onChangeHandler}
            value={registrationDetails.firstName} />
        </div>
        <div className="mb-3">
          <label htmlFor="LastName" className="form-label">Last Name</label>
          <input
            type="text"
            name="lastName"
            className="form-control"
            id="LastName"
            required
            onChange={onChangeHandler}
            value={registrationDetails.lastName} />
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
            value={registrationDetails.password} autoComplete="off" />
        </div>
        <div className="mb-5">
          <label htmlFor="passwordMatch" className="form-label">Confirm Password</label>
          <input
            type="password"
            name="passwordMatch"
            className="form-control"
            id="passwordMatch"
            required
            onChange={onChangeHandler}
            value={registrationDetails.passwordMatch} autoComplete="off" />
        </div>
        <button type="submit" className="btn btn-primary text-light">Register</button>
        <p className="mt-3">{response}</p>
      </form>
    </div>
  )
}

export default Register