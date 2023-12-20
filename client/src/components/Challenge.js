import React from "react";
import ItemRow from "./ItemRow";
import axios from "axios";
import { useState } from "react";

export default function Challenge() {
  const baseUrl = 'http://localhost:4567';
  const [inventoryItems, setInventoryItems] = useState([]);


  const getLowStockItems = () => {
    axios.get(`${baseUrl}/low-stock`)
      .then(response => {
        setInventoryItems(response.data);
        console.log(response.data);
      })
      .catch(error => {
        console.error("Error fetching low-stock items:", error);
      });
  };


  return (
    <>
      <table>
        <thead>
          <tr>
            <td>SKU</td>
            <td>Item Name</td>
            <td>Amount in Stock</td>
            <td>Capacity</td>
            <td>Order Amount</td>
          </tr>
        </thead>
        <tbody>
          {inventoryItems.map(item => (
            <ItemRow key={item.id} item={item} />
          ))}
        </tbody>
      </table>
      {/* TODO: Display total cost returned from the server */}
      <div>Total Cost: </div>
      {/* 
      TODO: Add event handlers to these buttons that use the Java API to perform their relative actions.
      */}
      <button onClick={getLowStockItems}>Get Low-Stock Items</button>
      <button>Determine Re-Order Cost</button>
    </>
  );
}

/*
        TODO: Create an <ItemRow /> component that's rendered for every inventory item. The component
        will need an input element in the Order Amount column that will take in the order amount and 
        update the application state appropriately.
        */