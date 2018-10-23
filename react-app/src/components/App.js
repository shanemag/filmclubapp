import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Nav from '../layout/Nav';

const App = () => (
    <MuiThemeProvider>
        <Nav/>
    </MuiThemeProvider>
)

export default App