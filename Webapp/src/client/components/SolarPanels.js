import React from 'react';
import Slider from '@material-ui/lab/Slider';
import Form from 'react-bootstrap/Form';
import Cookies from 'js-cookie';
import axios from 'axios';

var electricityUsage;
var percentageSaved;

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
        electricityUsage: ""+electricityUsage,
        percentageSaved : ""+(percentageSaved/100)
    };
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "addsolarpanels"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    axios.post(url, obj, config);
    redirectHome();
}

export default class SolarPanels extends React.Component{
    state = {
        value: 5000,
        value2: 0
    };
    
    handleChange = (event, value) => {
        electricityUsage = value;
        this.setState({ value });
    };
    
    handleChange2 = (event, value2) => {
        percentageSaved = value2;
        this.setState({ value2 });
    };
    render(){
        const { value } = this.state;
        const { value2 } = this.state;
        return(
            <div class="temperatureBox">
            <Form onSubmit={handleToggle}>
                <Form.Group controlId="electricityUsage">
                    <p>Electricity Usage: {value}kWh</p>
                    <Slider
                    value={value}
                    aria-labelledby="label"
                    onChange={this.handleChange}
                    min={0}
                    max={35000}
                    />
                </Form.Group><br />
                <Form.Group controlId="percentageSaved">
                    <p>Percentage Saved: {value2}%</p>
                    <Slider
                    value={value2}
                    aria-labelledby="label"
                    onChange={this.handleChange2}
                    min={0}
                    max={100}
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