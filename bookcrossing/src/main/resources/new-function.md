# 图书漂流数据与后端接口设计-新增功能 V1.0


## 1.后端数据库设计

-  ### scho_map (学院)
```
  `scho_id` varchar(100)
  `scho_name` varchar(40)
```
-  ### stu_map (学生注册信息)
```
  `stu_id` varchar(100)
  `stu_name` varchar(40)
  `password' varchar(32) /*32位MD5加密*/
```
- ### book_map (书籍非关键字段信息)
```
  `book_id` varchar(100) 
  `book_name` varchar(40) 
  `picture` varchar(100)
  `author` varchar(40)
  `book_description` varchar(255)
  `press` varchar(40)
```
- ### borrow_record (学生历史借阅记录)
```
  `book_id` varchar(100) 
  `stu_id` varchar(100)
```
---

## 2.区块链数据字段设计

-  ### StudentInfo (学生信息)
```
  string schoolName;
  string emailAddr;
  string[] donatedBooksId;
  uint donatedCount;
  mapping(string=>bool) borrowedBooksId;
  uint borrowedCount;
  mapping(string=>bool) wantBooksId;
```
-  ### BookInfo (书籍信息)
```
 string studentId;
 string bookName;
 string bookCategory;
```
-  ### BookStatus (书籍状态)
```
 string studentId;
 bool avail;
 uint want;
 uint maxBorrowDuration; // in seconds
 uint startBorrowTime;
```  
- ### data_map (相关字段映射)
```
 mapping (string => StudentInfo) studentsMap;
 uint numStudents;
 mapping (string => BookInfo) booksInfoMap;
 uint numBooks;
 mapping (string => BookStatus) bookStatusMap;
 mapping (string => string[]) bookCategoryMap;
 mapping (string => uint) bookCategoryCountMap;
```
---
## 3. 后端接口输入与返回(安卓+微信小程序请求)

- ### 3.1.1 学生注册: `/user/register (stu_id, passwd, stu_name)`
>###### `  返回字段`  `true or false ` 

- ### 3.1.2 学生登陆: `user/login (stu_id, passwd) `
>###### `  返回字段`  `true or false ` 

- ### 3.2.1 书籍列表（主页初始化界面）  :  ` /list`
> ###### `返回字段`  ` book_name   picture   book_id   count`
> ###### `返回格式` 
 ```
 {
    "count": 60,
    "books": [
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg"
        }, 
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg"
        },
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg"
        }
    ]
}
 ```
- ### 3.2.2 书籍列表--按书籍名称搜索 :  `/search?book_name=x`
> ######  `返回字段` `book_id book_name  picture author  book_description  press`
>
> ###### `返回格式`
```
{
    "count": 2,
    "books": [
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg",
            "author": "xxx",
            "press": "xxx"
        },
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg",
            "author": "xxx",
            "press": "xxx"
        }
    ]
}
```
- ### 3.2.3 书籍列表--按书籍类别名称搜索 :  `/search?book_categories=x`
> ######  `返回字段` `book_id book_name  picture author  book_description  press`
>
> ###### `返回格式`
```
{
    "count": 2,
    "books": [
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg",
            "author": "xxx",
            "press": "xxx"
        },
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg",
            "author": "xxx",
            "press": "xxx"
        }
    ]
}
```
- ### 3.3.1 具体书籍内容--查看书籍 :  `/book?book_id=x`
> ###### `返回字段` `book_id    book_name    picture   author    book_description  press    available    location` `location 字段存储在区块链上，为所在学院的名称或者借走学生的详细信息（scho_name email_addr）`

- ### 3.3.2 具体书籍内容--点击想看 :  `/book?book_id=x`
> ###### `  返回字段`  `true or false `

- ### 3.4.1 借书: `/borrow?book_id=x&stu_id=x` 
> ###### `返回字段` `true or false` `更新 location 字段，以适应借书、归还逻辑` `同时设置结束的最大借阅数量限制（=捐献书籍数量），每次借书的时候进行逻辑判断`

- ### 3.4.2 借书剩余时间（剩余天数）: `/borrow?book_id=x&stu_id=x` 
> ###### `返回字段` `true or false` `更新 location 字段，以适应借书、归还逻辑`

- ### 3.4.3 归还: `/borrow?book_id=x&stu_id=x` 
> ###### `返回字段` `true or false` `更新 location 字段，以适应借书、归还逻辑`
> 
- ### 3.4.4 已借书籍: `/borrowed?stu_id=x`
>###### `返回字段` `book_id   book_name    picture   author   book_description  press   available   location` `location 字段存储在区块链上`
>###### `返回格式`
```
{
    "count": 2,
    "books": [
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg",
            "author": "xxx",
            "press": "xxx"
        },
        {
            "book_id": 12,
            "book_name": "xxx",
            "picture": "https://img3.doubanio.com/view/subject/s/public/s27094300.jpg",
            "author": "xxx",
            "press": "xxx"
        }
    ]
}
```

## 4. 后端接口输入与返回(web端系统管理员请求)
- ### 4..1 获取图书列表 :  `/booklist`

- ### 4.2 添加书籍 :  `/addBook`

- ### 4.3 编辑书籍: `/editBook` 

- ### 4.4 更新书籍: `/updateBook` 

- ### 4.5 更新书籍: `/deleteBook` 