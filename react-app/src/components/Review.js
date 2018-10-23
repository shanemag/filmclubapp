const Reviews = () => (
    <div>
        <h2>REVIEW PAGE</h2>
        <Switch>
            <Route exact path='/review' component={NewReview}/>
            <Route path='/review/:id' component={Review}/>
        </Switch>
    </div>
)