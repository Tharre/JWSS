# Communication protocol

## List all articles:
(id, item_name)

    GET /items

## List specific article by id:

    GET /items/<id>

## Get open orders with prices:
(id, itemID, buy/sell, price, quantity)

    GET /orders

## Add new order:
Input: itemId, playerId, isBuy, limit, quantity

    POST /orders

## Get order details:

    GET /orders/<id>

## Update order:

    PUT /orders/<id>

## Cancel order:

    DELETE /orders/<id>

## Get item from order:

    GET /orders/<id>/item

## Register new player:
Input: name

	POST /players

## Show all players:

	GET /players

## Show specific player:

	GET /players/<id>

## Show current round:

	GET /rounds
	
## Show specific round:

	GET /rounds/<id>
	