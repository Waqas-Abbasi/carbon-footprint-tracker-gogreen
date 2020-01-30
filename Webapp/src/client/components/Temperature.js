import React from 'react';
import Slider from '@material-ui/lab/Slider';
import Form from 'react-bootstrap/Form';
import Cookies from 'js-cookie';
import axios from 'axios';


var insideTemp;
var outsideTemp;

function redirectHome(){
    alert("Added!");
    window.location.replace("/home");
}

function handleToggle(e){
    e.preventDefault();

    var date = new Date();
    date = date.toISOString();
    var time = date.substring(11,19);
    date  = date.substring(0,10);

    var obj = {
        date: date,
        time: time,
        tempInside: ""+insideTemp,
        tempOutside : ""+outsideTemp
    };
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "loweringtemp"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    axios.post(url, obj, config);
    redirectHome();
}

const styles = {
    root: {
      width: 300,
    },
    slider: {
      padding: '22px 0px',
    },
  };
  
export default class Temperature extends React.Component{
    state = {
        value: 0,
        value2: 0
    };
    
    handleChange = (event, value) => {
        insideTemp = value;
        this.setState({ value });
    };
    
    handleChange2 = (event, value2) => {
        outsideTemp = value2;
        this.setState({ value2 });
    };
    render(){
        const { value } = this.state;
        const {value2 } = this.state;
        return(
            <div class="temperatureBox">
            <Form onSubmit={handleToggle}>
                <Form.Group controlId="TempOutisde">
                    <p>Outside Temperature: {value}°C</p>
                    <Slider
                    value={value}
                    aria-labelledby="label"
                    onChange={this.handleChange}
                    min={-50}
                    max={50}
                    />
                </Form.Group><br />
                <Form.Group controlId="TempInside">
                    <p>Inside Temperature: {value2}°C</p>
                    <Slider
                    value={value2}
                    aria-labelledby="label"
                    onChange={this.handleChange2}
                    min={-50}
                    max={50}
                    />
                </Form.Group>
                <br />
                <button className="vegeSubmit" type="submit">
                    Submit
                </button>
            </Form>
            </div>
        )
    }
}