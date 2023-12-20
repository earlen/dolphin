import React from 'react';

const ItemRow = ({ item }) => {
    const { id, name, stockLevel, capacity } = item;

    // handle order amount input change
    const handleOrderAmountChange = (e) => {


        console.log("Order Amount for " + name + ": " + e.target.value);
    };

    return (
        <tr>
            <td>{id}</td>
            <td>{name}</td>
            <td>{stockLevel}</td>
            <td>{capacity}</td>
            <td>
                <input
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
