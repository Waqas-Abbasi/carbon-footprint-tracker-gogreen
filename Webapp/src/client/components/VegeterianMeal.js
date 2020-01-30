import React from 'react';
import Form from 'react-bootstrap/Form';
import Cookies from 'js-cookie';
import axios from 'axios';

var optionsGlob = [];

function redirectHome(){
    window.location.replace("/home");
}
function handleToggle(e){
    console.log(optionsGlob)
    e.preventDefault();

    var date = new Date();
    var time = date.getTime();
    date = date.toISOString();
    time = date.substring(11,19);
    date  = date.substring(0,10);

    var obj = {
        date: date,
        time: time,
        foodList : optionsGlob.toString()
    };
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "addmeal"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    axios.post(url, obj, config);
    alert("Added!");
    redirectHome();
}
export default class VegeterianMeal extends React.Component{
    constructor() {
        super()
        // initialize your options array on your state
        this.state = {
          options: []
        }
      }
    
      onChange(e) {
        // current array of options
        const options = this.state.options
        let index
    
        // check if the check box is checked or unchecked
        if (e.target.checked) {
          // add the numerical value of the checkbox to options array
          
          options.push(e.target.id)
        } else {
          // or remove the value from the unchecked checkbox from the array
          index = options.indexOf(+e.target.value)
          options.splice(index, 1)
        }
    
        optionsGlob = options;
        // update the state with the new array of options
        this.setState({ options: options })
      }
        
    render(){
        return(
            <div class="middleBox">
                <p
                className="paraTitle">Ingredients Of Your Meal</p>
                <Form onSubmit={handleToggle}>
                    <div className="row">
                        <div className="col-4">
                            <Form.Group controlId="Lamb">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Lamb" id="Lamb"/>
                            </Form.Group>
                            <Form.Group controlId="Beef">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Beef" id="Beef"/>
                            </Form.Group>
                            <Form.Group controlId="Cheese">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Cheese" id="Cheese"/>
                            </Form.Group>
                            <Form.Group controlId="Pork">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Pork" id="Pork"/>
                            </Form.Group>
                            <Form.Group controlId="Turkey">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Turkey" id="Turkey"/>
                            </Form.Group>
                            <Form.Group controlId="Chicken">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Chicken" id="Chicken"/>
                            </Form.Group>
                        </div>
                        <div className="col-4">
                            <Form.Group controlId="Tuna">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Tuna" id="Tuna" />
                            </Form.Group>
                            <Form.Group controlId="Eggs">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Eggs" id="Eggs" />
                            </Form.Group>
                            <Form.Group controlId="Potatoes">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Potatoes" id="Potatoes" />
                            </Form.Group>
                            <Form.Group controlId="Rice">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Rice" id="Rice"/>
                            </Form.Group>
                            <Form.Group controlId="Nuts">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Nuts" id="Nuts" />
                            </Form.Group>
                            <Form.Group controlId="Beans">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Beans" id="Beans" />
                            </Form.Group>
                        </div>
                        <div className="col-4">
                            <Form.Group controlId="Tofu">
                                <Form.Check  type="checkbox" onChange={this.onChange.bind(this)} label="Tofu" id="Tofu"/>
                            </Form.Group>
                            <Form.Group controlId="Vegetables">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Vegetables" id="Vegetables" />
                            </Form.Group>
                            <Form.Group controlId="Milk">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Milk" id="Milk" />
                            </Form.Group>
                            <Form.Group controlId="Fruit">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Fruit" id="Fruit"/>
                            </Form.Group>
                            <Form.Group controlId="Lentils">
                                <Form.Check type="checkbox" onChange={this.onChange.bind(this)} label="Lentils" id="Lentils" />
                            </Form.Group>
                        </div>
                    </div>
                <button className="vegeSubmit" type="submit">
                    Submit
                </button>
                </Form>
            </div>
        )
    }
}   