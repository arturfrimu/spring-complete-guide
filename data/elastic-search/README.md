```http request
http://localhost:8034/posts/search?searchText=dignissimos
```


```http request
###
POST http://localhost:8034/posts
Content-Type: application/json

{
  "userId": 1,
  "title": "Title 1 ",
  "body": "Body 1"
}
```

```http request
###
GET http://localhost:8034/posts/tzq4RJMBW39hMAwK7cFG
```

```http request
###
GET http://localhost:8034/posts
Content-Type: application/json
```

```http request
###
DELETE http://localhost:8034/posts/tzq4RJMBW39hMAwK7cFG
```