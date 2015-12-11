# Communication protocol

## List all articles:
(id, itemname)

    GET /items

## List specific article by id:

    GET /items/<id>

## Get open orders with prices:
(id, itemID, buy/sell, price, quantity)

    GET /orders

## Add new order:

    POST /orders

## Get order details:

    GET /orders/<id>

## Update order:

    PUT /orders/<id>

## Cancel order:

    DELETE /orders/<id>

## Get item from order:

    GET /orders/<id>/item
