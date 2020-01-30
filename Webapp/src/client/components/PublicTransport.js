import React from 'react';
import Form from 'react-bootstrap/Form';
import Cookies from 'js-cookie';
import axios from 'axios';

function redirectHome(){
    alert("Added!");
    window.location.replace("/home");
}

function handleToggle(e){
    e.preventDefault();
    var vehicleType =  e.target['vehicleType'].value;
    var distanceTravelled = e.target['distanceTravelled'].value;

    var date = new Date();
    date = date.toISOString();
    var time = date.substring(11,19);
    date  = date.substring(0,10);

    var obj = {
        date: date,
        time: time,
        carType: vehicleType,
        distance : distanceTravelled
    };
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "addpublictransport"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    axios.post(url, obj, config);
    redirectHome();
}
export default class PublicTransport extends React.Component{
    render(){
        return(
            <div class="personalTransportBox">
             <Form onSubmit={handleToggle}>
                <Form.Group controlId="countryName">
                    <Form.Label>Choose Public Transport</Form.Label><br />
                    <select id="vehicleType" name="vehicleType">
                        <option value="Bus">Bus</option>
                        <option value="Train">Train</option>
                        <option value="Metro">Metro</option>
                    </select>
                </Form.Group>
                <Form.Group controlId="distanceTravelled">
                    <Form.Label>Distance Travelled in Miles</Form.Label>
                    <Form.Control type="number" placeholder="Miles" />
                </Form.Group>
                <button className="vegeSubmit" type="submit">
                    Submit
                </button>
                </Form>
            </div>
        )
    }
}