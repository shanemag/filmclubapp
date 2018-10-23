import React, {Component} from 'react'
import AppBar from 'material-ui/AppBar';
import FlatButton from 'material-ui/FlatButton';
import {indigo500} from 'material-ui/styles/colors';

class Nav extends Component {
    state = {
        logged: false,
    };

    render() {
        return (
            <AppBar title="FILM CLVB"
            showMenuIconButton={false} 
            iconElementRight={<FlatButton {...this.props} label={this.state.logged ? "Logout" : "Login"}/>}
            />
        )
    }
}

export default Nav;