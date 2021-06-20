import React from 'react';
import { Link, useHistory } from 'react-router-dom'
import { useAuth } from '../context/AuthProvider'

const Navbar = () => {

  const { logout, token } = useAuth()
  const history = useHistory()

  const logoutHandler = (e) => {
    e.preventDefault()
    logout()
    history.push("/")
  }

  console.log('navbar rendered')

  return (
    <div>
      <nav className="navbar navbar-expand-md navbar-dark bg-dark ">
        <div className="container">
          <a className="navbar-brand " href="/home" >
            Memos
          </a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <Link to={"/"} className="nav-link ">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                {!token && <Link to={"/register"} className="nav-link">
                  Register
                </Link>}
              </li>
              <li className="nav-item">
                {token ? <Link to={"/"} className="nav-link" onClick={logoutHandler}>Logout</Link> :
                  <Link to={"/login"} className="nav-link">Login</Link>}
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  )
}

export default Navbar