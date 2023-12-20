import React from "react";
import ItemRow from "./ItemRow";
import axios from "axios";
import { useState } from "react";

export default function Challenge() {
  const baseUrl = 'http://localhost:4567';
  const [inventoryItems, setInventoryItems] = useState([]);
  const [reorderCost, setReorderCost] = useState(0.0);
  const [orderAmounts, setOrderAmounts] = useState({});



  const getLowStockItems = () => {
    axios.get(`${baseUrl}/low-stock`)
      .then(response => {
        setInventoryItems(response.data);
      })
      .catch(error => {
        console.error("Error fetching low-stock items:", error);
      });
  };

  async function handleOrderCostRequest() {

    const invalidItems = inventoryItems.filter(item => {
      const orderAmount = orderAmounts[item.id] || 0;
      return item.stockLevel + orderAmount > item.capacity;
    });

    if (invalidItems.length > 0) {
      console.error("Order amount exceeds capacity for some items.");
      let invalidItemIds = [];
      for (let invalidItem of invalidItems) {
        invalidItemIds.push(invalidItem.id);
      }
      const invalidSkusString = invalidItemIds.map(sku => `SKU: ${sku}`).join('\n');
      alert(`Slected order amount exceeds capacity for the following SKUs: \n${invalidSkusString}`);
      return;
    }

    axios.post(`${baseUrl}/restock-cost`, orderAmounts)
      .then(response => {
        let roundedResponse = Math.round(response.data * 100) / 100;
        setReorderCost(roundedResponse);
      })
      .catch(error => {
        console.error("Error retrieving order cost:", error);
      });


  }


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
            <ItemRow
              key={item.id}
              item={item}
              onOrderAmountChange={(id, amount) => {
                setOrderAmounts(prev => {
                  const newOrderAmounts = { ...prev, [id]: amount };
                  return newOrderAmounts;
                });
              }}
            />
          ))}
        </tbody>
      </table>
      <div>Total Cost: ${reorderCost} </div>
      <button onClick={getLowStockItems}>Get Low-Stock Items</button>
      <button onClick={handleOrderCostRequest}>Determine Re-Order Cost</button>
    </>
  );
}


