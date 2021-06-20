import React, { useState } from 'react';
import { useLocation } from "react-router-dom";
import axios from 'axios'

const EmailConfirmation = () => {

  const [message, setMessage] = useState("")
  const { search } = useLocation()
  const response = new URLSearchParams(search).get('response');
  const token = new URLSearchParams(search).get('token');

  const onResendTokenHandler = async () => {
    try {
      const { data } = await axios.get(`/api/v1/auth//resend-email-token?token=${token}`)
      setMessage(data)
    } catch (error) {
      setMessage(error.message)
    }
  }

  return (
    <div className="container">
      <h3 style={{ marginTop: 50 }}>{response}</h3>
      {response === 'Confirmation period expired' && <button type="button" className="btn btn-dark" onClick={onResendTokenHandler}>Resend</button>}
      {message && <p>{message}</p>}
    </div>
  )
}

export default EmailConfirmation