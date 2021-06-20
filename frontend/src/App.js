import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import './scss/main.css';
import Register from './components/Register'
import Navbar from './components/Navbar'
import EmailConfirmation from './components/EmailConfirmation'
import Login from './components/Login'
import Home from './components/Home'
import AuthProvider from './context/AuthProvider';

const App = () => {

  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Navbar />
          <Switch>
            <Route exact path={["/", "/home"]} component={Home} />
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/confirm" component={EmailConfirmation} />
          </Switch>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
