import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import 'typeface-roboto';
import './index.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import App from './components/App';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render((
    <Router>
        <App />
    </Router>
    ), document.getElementById('root'));
registerServiceWorker();
