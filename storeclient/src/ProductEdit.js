import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class ProductEdit extends Component{
    emptyItem = {
        name: '',
        description: '',
        categoryId: '',
        price: '',
        currency: '',
        numberOfDaysForFreeReturn: '',
        freeShipping: '',
        shippingPrice: '',
        deliveryRangeFrom: '',
        deliveryRangeTo: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            categories: []
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const product = await (await fetch(`/api/products/${this.props.match.params.id}`)).json();

            fetch('/api/categories')
            .then(response => response.json())
            .then(data => this.setState({categories: data, item: product}));

            this.setState({item: product});
        }

        fetch('/api/categories')
        .then(response => response.json())
        .then(data => this.setState({categories: data}));

    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/api/products' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/products');
    }

    render() {
        const {item} = this.state;
        let ddlcategories = this.state.categories;
        let optionItems = ddlcategories.map((category) =>
            <option value={category.id}>{category.name}</option>
        );
        const title = <h2>{item.id ? 'Edit Product' : 'Add Product'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               required onChange={this.handleChange} autoComplete="name" maxLength="500"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               required onChange={this.handleChange} autoComplete="address-level1" maxLength="5000"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="categoryId">Category</Label>
                        <select name="categoryId" id="categoryId" value={item.categoryId || ''}
                                required onBlur={this.handleChange} onChange={this.handleChange}
                                autoComplete="address-level1" className="form-control">
                            {optionItems}
                        </select>
                    </FormGroup>
                    <div className="row">
                        <FormGroup className="col-md-4 mb-3">
                            <Label for="price">price</Label>
                            <Input type="number" name="price" id="price" value={item.price || ''}
                                   required onChange={this.handleChange} autoComplete="address-level1" min="1"/>
                        </FormGroup>
                        <FormGroup className="col-md-5 mb-3">
                            <Label for="currency">currency</Label>
                            {/*
                            <Input type="text" name="currency" id="currency" value={item.currency || ''}
                                   required onChange={this.handleChange} autoComplete="address-level1"/>*/}
                            <select name="currency" id="currency" value={item.currency || ''}
                                    required onBlur={this.handleChange} onChange={this.handleChange} className="form-control">
                                <option selected="selected" value="EUR">EUR</option>
                            </select>
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="numberOfDaysForFreeReturn">number Of Days For Free Return</Label>
                            <Input type="number" name="numberOfDaysForFreeReturn" id="numberOfDaysForFreeReturn" value={item.numberOfDaysForFreeReturn || ''}
                                   required onChange={this.handleChange} autoComplete="address-level1" min="1"/>
                        </FormGroup>
                    </div>
                    <div className="row">
                        <FormGroup className="col-md-4 mb-3">
                            <Label for="freeShipping">free Shipping</Label>
                            <select name="freeShipping" id="freeShipping" value={item.freeShipping}
                                    required onBlur={this.handleChange} onChange={this.handleChange} className="form-control">
                                <option value="true">Yes</option>
                                <option value="false">No</option>
                            </select>
                        </FormGroup>
                        <FormGroup className="col-md-5 mb-3">
                            <Label for="shippingPrice">shipping Price</Label>
                            <Input type="number" name="shippingPrice" id="shippingPrice" value={item.shippingPrice || ''}
                                   onChange={this.handleChange} autoComplete="address-level1" min="0"/>
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="deliveryRangeFrom">delivery Range From</Label>
                            <Input type="number" name="deliveryRangeFrom" id="deliveryRangeFrom" value={item.deliveryRangeFrom || ''}
                                   onChange={this.handleChange} autoComplete="address-level1" min="1"/>
                        </FormGroup>
                        <FormGroup className="col-md-3 mb-3">
                            <Label for="deliveryRangeTo">delivery Range To</Label>
                            <Input type="number" name="deliveryRangeTo" id="deliveryRangeTo" value={item.deliveryRangeTo || ''}
                                   onChange={this.handleChange} autoComplete="address-level1" min="1"/>
                        </FormGroup>
                    </div>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/products">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(ProductEdit);
