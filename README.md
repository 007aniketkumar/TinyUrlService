# TinyUrlService
This service runs on the premise of converting url to base 62 encoding . 

How the algorithm works :

For every url - generate a unique number .
Do a base 62 encoding on this number.
Store the above result in the db and map it with the long url.
This tiny url is combination of the host/service followed by the above result.
For example : if you are running this on your local , the tiny url will be 
localhost/result
Test this on postman /browser.

The unique numbers are stored in Redis DB.

Redis image dependecy is resolved from Docker.
  docker run -p 6379:6379 redis



