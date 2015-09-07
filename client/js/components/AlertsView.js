import React from 'react';
import { Alert } from 'react-bootstrap';

import AlertActionCreator from '../actions/AlertActionCreator';
import AlertStore from '../stores/AlertStore';

export default class extends React.Component {
  constructor(props) {
    super(props);

    this.state = this._getState();

    this._onChange = () => {
      this.setState(this._getState());
    };
    AlertStore.addChangeListener(this._onChange);

    this.onDismiss = (index) => {
      return () => AlertActionCreator.alertDismissed(index);
    };
  }

  render() {
    const alerts = this.state.alerts;

    const alertItems = alerts.map((alert, i) =>
      <Alert bsStyle='danger' onDismiss={this.onDismiss(i)}>
        {alert}
      </Alert>
    );

    return <div>{alertItems}</div>;
  }

  _getState() {
    return {
      alerts: AlertStore.getAlerts()
    };
  }
}
