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
    var productName = e.target['productName'].value;
    var countryName = e.target['countryName'].value;

    var date = new Date();
    date = date.toISOString();
    var time = date.substring(11,19);
    date  = date.substring(0,10);

    var obj = {
        date: date,
        time: time,
        country_name: countryName,
        productName : productName
    };
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "addproduct"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    axios.post(url, obj, config);
    redirectHome();
}

export default class LocalProduce extends React.Component{
    render(){
        return(
            <div class="localProduceBox">
            <Form onSubmit={handleToggle}>
                <Form.Group controlId="productName">
                    <Form.Label>Enter Product Name</Form.Label>
                    <Form.Control type="text" placeholder="Product Name" />
                </Form.Group>
                <Form.Group controlId="countryName">
                    <Form.Label>Enter Manufacturing Country</Form.Label>
                    <Form.Control type="text" placeholder="Country Name" />
                </Form.Group>
                <button className="vegeSubmit" type="submit">
                    Submit
                </button>
            </Form>
            </div>
        )
    }
}