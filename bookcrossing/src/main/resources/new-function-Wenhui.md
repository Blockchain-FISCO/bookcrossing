## 首页

> 1. 热门图书 (url: /hotlist)
> 2. 捐书推荐 (url: /needlist)
> 3. 今日上新 (url: todaylist)

以上三个请求返回的数据格式均为 (和之前首页返回数据格式一致)：

```
{
    "count": 2,
    "books": [
        {
            "book_id": "03B1ADE73D2549819837CFC9C4FDD01A",
            "book_name": "我与地坛",
            "picture": "http://132.232.199.162:8080/staticimage/845217ab-eccc-404d-8800-2d09b90be5a6.jpg"
        },
        {
            "book_id": "0AA0FDB079684CA6AF4F454A68B5CCB9",
            "book_name": "深度学习",
            "picture": "http://132.232.199.162:8080/staticimage/71f3bcdc-e6b9-4430-aaf4-c5d2d2e82a4c.jpg"
        }
    ]
}
```

## 待还图书

返回数据格式 (url: /borrowed?stu_id=666) (比之前版本多了一个int类型的leftDays字段)：

```
{
    "count": 2,
    "books": [
        {
            "book_id": "03B1ADE73D2549819837CFC9C4FDD01A",
            "book_name": "我与地坛",
            "picture": "http://132.232.199.162:8080/staticimage/845217ab-eccc-404d-8800-2d09b90be5a6.jpg",
            "author": "xxx",
            "press": "xxx",
            "leftDays": 25
        },
        {
            "book_id": "0AA0FDB079684CA6AF4F454A68B5CCB9",
            "book_name": "深度学习",
            "picture": "http://132.232.199.162:8080/staticimage/71f3bcdc-e6b9-4430-aaf4-c5d2d2e82a4c.jpg",
            "author": "xxx",
            "press": "xxx",
            "leftDays": 25
        }
    ]
}
```