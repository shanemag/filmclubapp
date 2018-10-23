// components/Home.js
import React from 'react';
 
const Club = () => (
    <div>
    <h2>CLUB PAGE</h2>
    <Switch>
        <Route exact path='/club' component={NewClub}/>
        <Route path='/club/:id' component={Club}/>
    </Switch>
    </div>
)