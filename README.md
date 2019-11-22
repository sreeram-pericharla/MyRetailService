# MyRetailService

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. MyRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps.

The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 

This a RESTful service that can retrieve product and price details by ID and update the price details for the ID. <br />

### HTTP GET - To Retrieve product details
Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 

*Example product IDs:* 

	15117729, 16483589, 16696652, 16752456, 15643793)


*Example request url:* 

	GET - http://localhost:8089/myretail/products/13860429
 
 
*Example response:* 

	{
      "id": 13860429,
      "name": "SpongeBob SquarePants: SpongeBob's Frozen Face-off",
      "currency_price": {
          "value": 44.36,
          "currency_code": "USD"
       }
     }
  
Performs an HTTP GET to retrieve the product name from an external API. 
(For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail) 
Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

Reads pricing information from a NoSQL data store and combinesit with the product id and name from the HTTP request into a single response. 

### HTTP PUT - To update the product price in the db of a product
Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store.

*Example request url:* 

	PUT - http://localhost:8089/myretail/products/13860429
 
 
*Example request:* 

	{
      "value": 44.36,
      "currency_code": "USD"
    }



*Example response:* 

	{
      "isPriceUpdated": true
    }


*Technologies used:* 

	Spring boot - 2.2.1.RELEASE
	Cassandra - 3.11.4 
	JAVA - 1.8.0_231 
  
*Prerequisites:* 

1. Java 8
2. Postman
3. Cassandra running on localhost 9042 port
4. Create a keyspace with name **myretail**, 
   create table **product_prices** with schema mentioned below and add some data to work on it.
     
     ```
     CREATE TABLE product_prices (
      product_id BIGINT PRIMARY KEY,
      value DECIMAL,
      currency_code TEXT
      );
  ```INSERT INTO product_prices (product_id, value, currency_code) VALUES (13860429, 34.30, 'USD');```
     
*Execution Procedure:*

		Run the following command in command prompt/Terminal 
			java -jar casestudy-0.0.1-SNAPSHOT.jar 
      

![](myretail/Screen%20Shot%202019-11-21%20at%207.07.24%20PM.png)

![](myretail/Screen%20Shot%202019-11-21%20at%207.10.45%20PM.png)

![](myretail/Screen%20Shot%202019-11-21%20at%207.15.15%20PM.png)

![](myretail/Screen%20Shot%202019-11-21%20at%207.15.36%20PM.png)
