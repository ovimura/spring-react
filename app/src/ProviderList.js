import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class ProviderList extends Component {

  constructor(props) {
    super(props);
    this.state = {providers: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/providers')
      .then(response => response.json())
      .then(data => this.setState({providers: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/provider/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedProviders = [...this.state.providers].filter(i => i.id !== id);
      this.setState({providers: updatedProviders});
    });
  }

  render() {
    const {providers, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const providerList = providers.map(provider => {
    console.log(provider);
      const address = `${provider.address || ''} ${provider.city || ''} ${provider.stateOrProvince || ''}`;
      return <tr key={provider.id}>
        <td style={{whiteSpace: 'nowrap'}}>{provider.name}</td>
        <td>{address}</td>
        <td>{provider.meetings.map(event => {
          return <div key={event.id}>{new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: 'long',
            day: '2-digit'
          }).format(new Date(event.date))}: {event.title}</div>
        })}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/providers/" + provider.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(provider.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/providers/new">Add Provider</Button>
          </div>
          <h3>My List Of Providers</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th>Meetings</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {providerList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default ProviderList;