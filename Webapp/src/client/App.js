import React from "react";
import Sidebar from "react-sidebar";
import Dashboard from "./components/Dashboard";
import Dropdown from 'react-bootstrap/Dropdown'
import { BrowserRouter as Router, Route, Link, Redirect } from "react-router-dom";
import LocalProduce from './components/LocalProduce';
import VegeterianMeal from './components/VegeterianMeal';
import PersonalTransport from './components/PersonTransport';
import PublicTransport from './components/PublicTransport';
import Temperature from './components/Temperature';
import Solarpanels from './components/SolarPanels';
import className from 'classnames';

const mql = window.matchMedia(`(min-width: 800px)`);
 
const sidebarStyles = {
  sidebar: {
    width: 260,
    color:"white",
    textAlign: "center",
    backgroundImage: "linear-gradient(to bottom right, rgba(0, 181, 204, 0.6), rgba(107, 255, 185, 0.8))"
  }
};

const menuStyle = {
  fontSize: "40px",
}


export default class App extends React.Component {
  constructor(props) {
    super(props); 
    this.state = {
      sidebarDocked: mql.matches,
      sidebarOpen: false
    };
 
    this.mediaQueryChanged = this.mediaQueryChanged.bind(this);
    this.onSetSidebarOpen = this.onSetSidebarOpen.bind(this);
  }
 
  componentWillMount() {
    mql.addListener(this.mediaQueryChanged);
  }
 
  componentWillUnmount() {
    this.state.mql.removeListener(this.mediaQueryChanged);
  }
 
  onSetSidebarOpen(open) {
    this.setState({ sidebarOpen: open });
  }
 
  mediaQueryChanged() {
    this.setState({ sidebarDocked: mql.matches, sidebarOpen: false });
  }

 
  render() {
    return (
      <div>
      <Router>
      <Sidebar
        styles={sidebarStyles}
        sidebar={<b style={menuStyle}>
        <br/>
        C0₂ Tracker
        <br/><br/>
        <Link to="/home" style={{ textDecoration: 'none', color:'white'}}>
          <button
            className="btn btn-default menuButton">
            <div className="dd-header-title">Dashboard</div>
          </button>
        </Link>
        <Dropdown>
              <Dropdown.Toggle className={className({'menuButton':true, 'btn-default':true})}>
                Add Activity
              </Dropdown.Toggle>
              
              <Dropdown.Menu>
                <Dropdown.Item>
                  <Link to="/vegeterianmeal" style={{ textDecoration: 'none', color:'black'}}>
                  <div id="fullClick">
                    Eating A Vegeterian Meal
                    </div>
                  </Link>
                </Dropdown.Item>
                <Dropdown.Item>
                    <Link to="/localproduce" style={{ textDecoration: 'none', color:'black'}}>
                    <div id="fullClick">
                      Buying Local Produce
                      </div>
                    </Link>
                </Dropdown.Item>
                <Dropdown.Item>
                    <Link to="/personaltransport" style={{ textDecoration: 'none', color:'black'}}>
                    <div id="fullClick">
                      Using Personal Transport
                      </div>
                    </Link>
                </Dropdown.Item>
                <Dropdown.Item>
                    <Link to="/publictransport" style={{ textDecoration: 'none', color:'black'}}>
                    <div id="fullClick">
                      Using Public Transport
                      </div>
                    </Link>
                </Dropdown.Item>
                <Dropdown.Item>
                    <Link to="/temperature" style={{ textDecoration: 'none', color:'black'}}>
                    <div id="fullClick">
                    Lowering House Temperature
                    </div>
                    </Link>
                </Dropdown.Item>
                <Dropdown.Item>
                    <Link to="/solarpanels" style={{ textDecoration: 'none', color:'black'}}>
                    <div id="fullClick">
                    Adding Solar Panels
                    </div>
                    </Link>
                </Dropdown.Item>
              </Dropdown.Menu>
          </Dropdown>
        </b>}
        open={this.state.sidebarOpen}
        docked={this.state.sidebarDocked}
        onSetOpen={this.onSetSidebarOpen}>
        <div id="scene">
        <Route exact path="/home" component={Dashboard} />
        <Route exact path="/vegeterianmeal" component={VegeterianMeal} />
        <Route exact path="/localproduce" component={LocalProduce} />
        <Route exact path="/personaltransport" component={PersonalTransport} />
        <Route exact path="/publictransport" component={PublicTransport} />
        <Route exact path="/temperature" component={Temperature} />
        <Route exact path="/solarpanels" component={Solarpanels} />
        </div>
      </Sidebar>
      </Router>
      </div>
    );
  }
}
