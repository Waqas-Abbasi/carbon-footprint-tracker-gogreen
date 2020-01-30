import React from "react";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Cookies from 'js-cookie';
import axios from 'axios';

const stylez = {
    width:'100%'
}

const scoreSize = {
    fontSize: '30px'
}

let id = 0;
function createData(type, score, date) {
  id += 1;
  return {id, type, score, date};
}

var rows = [];

var score = 0;
var carbonFootprintVal = 0;
var activitiesList = [];

async function getScore(){
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "getscore"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    var response = await axios.get(url, config);
    score = response.data;
    score = parseFloat(score);
    score = Math.round(score * 100) / 100;

    if(score > 10000){
        scoreSize.fontSize = '30px';
    }
}

async function getActivities(){
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "myactivities"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    var response = await axios.get(url, config);
    activitiesList = response.data;
    rows = [];
    activitiesList.forEach(element => {
        var obj = JSON.parse(element);
        var temp = obj.score;
        temp = parseFloat(temp);
        temp = Math.round(temp * 100) / 100;
        rows.push(createData(obj.type.replace(" ", ""),temp , obj.date));
    });
}

async function getCarbonFootprint(){
    var cookieSession = Cookies.get('set-cookie');
    cookieSession = cookieSession.substring(3);
    cookieSession = cookieSession.replace("]", "").replace("\"", "").replace("\"", "");
    const config = {
        headers: {
            'set-cookie': cookieSession,
            'Content-type': 'application/json',
            'path': "getemission"
        }
    }
    const url = 'https://salty-wave-43787.herokuapp.com/sendreq';
    var response = await axios.get(url, config);
    carbonFootprintVal = response.data;
    carbonFootprintVal = parseFloat(carbonFootprintVal);
    carbonFootprintVal = Math.round(carbonFootprintVal * 100) / 100;
}
export default class Dashboard extends React.Component{
    state = {
        totalScore: 100,
        carbonFootprint: 100,
        data : {},
        loading: true  
    };

    componentDidMount() {
        getScore().then(() =>{
            this.state.totalScore = score;
            getCarbonFootprint().then(()=>{
                this.state.carbonFootprint = carbonFootprintVal;
                getActivities().then(()=>{
                    this.setState({loading: false});
                })
            })
        })
    }
    SimpleTable(props) {
        const { classes } = props;
    }
    render(){
        const { totalScore } = this.state;
        const { carbonFootprint } = this.state;

        if(this.state.loading) {

            return (
                <div class="loader"></div>
                )
        }
        return (
            <div id="mainBox">
                <div id="dashBoardHeader">
                Dashboard / My Dashboard
                </div>
                <div id="userInformation">
                    <div id="scoreBox">
                        <p id="totalScore" style={scoreSize}>{totalScore}</p>
                        <hr></hr>
                        <p id="totalScoreLabel">Total Score</p>
                    </div>
                    <div id="carbonFootprintBox">
                        <p id="carbonFootprint" style={scoreSize}>{carbonFootprint}</p>
                        <hr></hr>
                        <p id="carbonFootprintLabel">Carbon Footprint</p>
                    </div>
                </div>
                <div id="empty"></div>
                <div id="dashBoardHeader">
                        Activities / My Activities
                </div>
                <div id="myActivities">
                    <Paper style={stylez}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Activity Name</TableCell>
                                    <TableCell align="right">Score</TableCell>
                                    <TableCell align="right">Date</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {rows.map(row => (
                                    <TableRow key={row.id}>
                                        <TableCell component="th" scope="row">
                                            {row.type}
                                        </TableCell>
                                        <TableCell align="right">{row.score}</TableCell>
                                        <TableCell align="right">{row.date}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>
                </div>
            </div>
        )
    }

}