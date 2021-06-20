import React, { useEffect, useState, useRef } from 'react';
import { ReactComponent as ExclamationMark } from '../img/exclamation_mark.svg'
import './Home.css'
import Spinner from 'react-bootstrap/Spinner';
import { Memos } from './Memos';
import axios from 'axios'
import { useAuth } from '../context/AuthProvider'

const Home = () => {

  const loading = useRef(true)
  const [data, setData] = useState(null)
  const { token, userId } = useAuth()
  console.log("home rendered")

  useEffect(() => {
    const headers = token ? {
      headers: {
        "Authorization": "Bearer " + token
      }
    } : {}

    async function fetch() {
      try {
        await axios
          .get("/api/v1/memos", {
            headers
          })
          .then(res => {
            loading.current = false
            setData(res.data)
          })
      } catch (error) {
        console.log(error)
      }
    }
    fetch()
  }, [token])

  return (
    <div className="container">
      <div className="row justify-content-center text-center mt-5">
        {data && data.length === 0 && <h3 className="text-secondary">No memos</h3>}
        {data
          ? (<>
            <Memos token={token} data={data} userId={userId} />
          </>)
          : <Spinner className="mt-5 text-danger" animation="border" />}
        {!token && !loading.current && (
          <div>
            <ExclamationMark className="exclamation mt-3" />
            <p className="card-text text-danger">Login to add or edit memos</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default Home