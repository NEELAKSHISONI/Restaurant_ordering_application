//<<<<<<< HEAD:src/src/fastfood/order/OrderInProgress.java
package fastfood.order;


import fastfood.item.ItemFactory;
import fastfood.item.MenuItem;
import fastfood.item.SelectedItem;

public class OrderInProgress extends Order {
    boolean completed;
    //Constructor
    public OrderInProgress(String custName) {
        super(custName);
        this.completed = false;
    }

    //Getter and Setter
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    //Methods
    /*
    Description: This function will add an item to the list if its not already in there. If the item is already in the
        list then we update the quantity of the item in the list and recalculate the total.
    Parameters:
        item - This is the SelectedItem object that represents the item that the customer wants to add to their order.
    Local Variables:
        success - Used return if the the method completed successfully or not.
        temp - Is a temp object that we used to get the item reference that is in the list.
    Returns: Returns a boolean value that indicates if the insertion was successful.
     */
    public boolean addItem(MenuItem Mitem){
        boolean success = false;
        for(int i=0; i < this.itemList.size(); i++)
   	    {
            SelectedItem item = this.itemList.get(i);
	        if(this.itemList.get(i).getItemName().equals(Mitem.getItemName())){
	            System.out.println("Item is already in the order list.");
	            int updatedQuantity = item.getQuantity() + 1;//ReCalculate the quantity of the item in the list.
	            item.setQuantity(updatedQuantity);//update the value.
	            success = true;
	            reCalculateTotal(true, item.getPrice(), 1);//Recalculate the total with the quantity from the parameter and the price of the item.
	        }
   	    }
        if(success == false)
        {
        	 success = this.itemList.add((SelectedItem) ItemFactory.createItem("selected item", null, Mitem));
	         reCalculateTotal(true, Mitem.getPrice(), 1);//Recalculate the total with the quantity from the parameter and the price of the item.
        }
        return success;
    }

    /*
    Description: This fucntion will remove an item to the list if its in there. If the item isn't in the
        list then we report this to the console and return false. Otherwise we determine if the quantity of the item being
        removed is 0. If so we remove the item completely from the list and recalculate the total. If not zero keep in the list
        and recalculate the total.
    Parameters:
        item - This is the SelectedItem object that represents the item that the customer wants to remove from their order.
    Local Variables:
        success - Used return if the the method completed successfully or not.
        temp - Is a temp object that we used to get the item reference that is in the list.
    Returns: Returns a boolean value that indicates if the modification/deletion was successful.
     */
    public boolean removeItem(SelectedItem item){
        boolean success;
        if(this.itemList.contains(item)){
            int updatedQuantity =  item.getQuantity() - 1;//ReCalculate the quantity of the item in the list.
            
            reCalculateTotal(false, item.getPrice(), 1);//Recalculate the total with the quantity from the parameter and the price of the item.
            if(updatedQuantity <= 0){//If the quantity goes to 0 or below then we get rid of the item from the list
                success = (this.itemList.remove(this.itemList.indexOf(item)) != null);
            }
            else {
                item.setQuantity(updatedQuantity);//update the value.
                success = true;
            }
        }
        else{
            System.out.println("There is no "+item.getDescription()+" in the order list.");
            success = false;
        }
        return success;
    }

    /*
    Description: This function handles the recalculation of the order when a modification is made by the customer. The price and the quantity is used to determine
        the amount to add or subtract from the total.
    Parameters:
        op - This boolean value is used to determine if we are subtracting or adding to the total. False=Subtract; True=Add;
        price - This is the price of the item being added or subtracted.
        quantity - This is the quantity of the item being added or subtracted.
    Local Variables:
        newTotal - This holds the value of the product of price*quantity and is used to update the order total.
    Returns: None.
     */
    private void reCalculateTotal(boolean op, double price, int quantity) {
        if(op){//If op is true then we add 
            double subTotal = price*quantity;
            double curTotal = getTotal();

            curTotal += subTotal;
            curTotal = Math.round(curTotal * 100.0) / 100.0; //solves java problem of displaying numbers slightly off, like 7.49999995 instead of 7.5
            setTotal(curTotal);
        }
        else{//If op is false we subtract
            double subTotal = price*quantity;
            double curTotal = getTotal();

            curTotal -= subTotal;
            curTotal = Math.round(curTotal * 100.0) / 100.0;
            setTotal(curTotal);
        }
    }
}