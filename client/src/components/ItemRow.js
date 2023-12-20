import React from 'react';

const ItemRow = ({ item, onOrderAmountChange }) => {
    const { id, name, stockLevel, capacity } = item;

    // handle order amount input change
    const handleOrderAmountChange = (e) => {

        const amount = parseInt(e.target.value, 10) || 0;
        onOrderAmountChange(id, amount);
        //console.log("Order Amount for " + name + ": " + e.target.value); // for testing
    };

    return (
        <tr>
            <td>{id}</td>
            <td>{name}</td>
            <td>{stockLevel}</td>
            <td>{capacity}</td>
            <td>
                <input
                    id={id}
                    className='order-amount'
                    type="number"
                    min="0"
                    onChange={handleOrderAmountChange}
                    placeholder="Enter amount"
                />
            </td>
        </tr>
    );
};

export default ItemRow;
